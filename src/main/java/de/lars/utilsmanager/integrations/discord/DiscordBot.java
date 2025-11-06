package de.lars.utilsmanager.integrations.discord;

import de.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import de.lars.utilsmanager.utils.Statements;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

import java.time.Duration;
import java.time.LocalDateTime;

public class DiscordBot {

    private JDA jda;
    private final LocalDateTime startTime;
    private final String serverStatusChannelID;
    private final String playerStatusChannelID;
    private final String punishmentsChannelID;

    public DiscordBot(String botToken, String serverChannelID, String playerChannelID, String punishmentsChannelId) {
        startTime = LocalDateTime.now();
        serverStatusChannelID = serverChannelID;
        playerStatusChannelID = playerChannelID;
        punishmentsChannelID = punishmentsChannelId;

        if (botToken == null || botToken.isEmpty() || serverChannelID == null || serverChannelID.isEmpty() || playerChannelID == null || playerChannelID.isEmpty() || punishmentsChannelId == null || punishmentsChannelId.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("Discord bot information not complete!", NamedTextColor.RED)));
            return;
        }

        try {
            jda = JDABuilder.createDefault(botToken)
                    .addEventListeners(new DiscordListener())
                    .build()
                    .awaitReady();

            jda.getPresence().setStatus(OnlineStatus.ONLINE);
            jda.getPresence().setActivity(Activity.playing("auf " + ServerSettingsAPI.getApi().getServerName()));
            Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("Discord bot started!", NamedTextColor.GREEN)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendStatusMessage("Der Server ist online!");
    }

    public void disable() {
        LocalDateTime endTime = LocalDateTime.now();

        String message = "Der Server ist nun offline!\nEr war für " + Duration.between(startTime, endTime).toMinutes() + " Minuten aktiv!";
        sendStatusMessage(message);

        if (jda != null) {
            jda.shutdownNow();
        }
    }

    public void sendStatusMessage(String message) {
        if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) return;
        TextChannel channel = jda.getTextChannelById(serverStatusChannelID);
        if (channel != null) {
            channel.getIterableHistory().takeAsync(100).thenAccept(messages -> {
                if (!messages.isEmpty()) {
                    channel.deleteMessages(messages).queue(
                        success -> channel.sendMessage(message).queue(),
                        error -> {
                            for (Message msg : messages) {
                                msg.delete().queue();
                            }
                            channel.sendMessage(message).queue();
                        }
                    );
                } else {
                    channel.sendMessage(message).queue();
                }
            });
        }
    }

    public void sendPlayerMessage(String message) {
        if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) return;
        TextChannel channel = jda.getTextChannelById(playerStatusChannelID);

        if (channel != null) {
            System.out.println("1");
            channel.getIterableHistory().takeAsync(100).thenAccept(messages -> {
                System.out.println("Fetched " + messages.size() + " messages");
                if (!messages.isEmpty()) {
                    channel.deleteMessages(messages).queue(
                        success -> channel.sendMessage(message).queue(),
                        error -> {
                            System.out.println("3");
                            for (Message msg : messages) {
                                msg.delete().queue();
                            }
                            System.out.println("4");
                            channel.sendMessage(message).queue();
                        }
                    );
                } else {
                    System.out.println("5");
                    channel.sendMessage(message).queue();
                }
                System.out.println("2");
            }).exceptionally(error -> {
                System.out.println("⚠️ Failed to fetch message history: " + error.getMessage());
                error.printStackTrace();
                channel.sendMessage(message).queue();
                return null;
            });
        }
    }

    public void sendPunishmentMessage(String message) {
        if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) return;
        TextChannel channel = jda.getTextChannelById(punishmentsChannelID);
        if (channel != null) {
            channel.sendMessage(message).queue();
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}