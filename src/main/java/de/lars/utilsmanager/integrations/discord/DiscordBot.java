package de.lars.utilsmanager.integrations.discord;

import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
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
import java.time.OffsetDateTime;

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
            jda.getPresence().setActivity(Activity.customStatus("🎮 Spielt auf " + ServerSettingsAPI.getApi().getServerName()));
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
        if (channel == null) return;

        try {
            for (Message msg : channel.getIterableHistory().takeAsync(100).join()) {
                if (msg.getTimeCreated().isAfter(OffsetDateTime.now().minusWeeks(2))) {
                    msg.delete().complete();
                }
            }

            channel.sendMessage(message).complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPlayerMessage(String message) {
        if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) return;
        TextChannel channel = jda.getTextChannelById(playerStatusChannelID);

        if (channel != null) {
            channel.getIterableHistory().takeAsync(100).thenAccept(messages -> {
                for (Message msg : messages) {
                    if (msg.getTimeCreated().isAfter(OffsetDateTime.now().minusWeeks(2))) {
                        msg.delete().queue();
                    }
                }
                channel.sendMessage(message).queue();
            }).exceptionally(error -> {
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