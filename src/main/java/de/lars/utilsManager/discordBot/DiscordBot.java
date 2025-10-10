package de.lars.utilsManager.discordBot;

import de.lars.apiManager.dataAPI.DataAPI;
import de.lars.utilsManager.utils.Statements;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
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

            jda.upsertCommand("status", "Zeigt den Status des Minecraft-Servers").queue();
            jda.upsertCommand("players", "Zeigt eine Liste der Spieler, die online sind").queue();
            jda.upsertCommand("whitelist_add", "Fügt einen Spieler zur Whitelist hinzu").queue();
            jda.upsertCommand("whitelist_remove", "Entfernt einen Spieler von der Whitelist").queue();
            jda.upsertCommand("say", "Sendet eine Nachricht in den Minecraft-Chat").queue();
            jda.upsertCommand("msg", "Sendet eine private Nachricht an einen Spieler").queue();
            jda.upsertCommand("broadcast", "Sendet eine Broadcast-Nachricht an alle Spieler").queue();

            jda.getPresence().setStatus(OnlineStatus.ONLINE);
            jda.getPresence().setActivity(Activity.playing("Minecraft auf "+ Bukkit.getIp()));
            Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("Discord bot started!", NamedTextColor.GREEN)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendStatusMessage("Der Server ist online!");
    }

    public void disable() {
        LocalDateTime endTime = LocalDateTime.now();

        String message = "Der Server ist nun offline!\\nEr war für " + Duration.between(startTime, endTime).toMinutes() + " Minuten aktiv!";
        sendStatusMessage(message);

        if (jda != null) {
            jda.shutdownNow();
        }
    }

    public void sendStatusMessage(String message) {
        if (DataAPI.getApi().isMaintenanceActive()) return;
        TextChannel channel = jda.getTextChannelById(serverStatusChannelID);
        if (channel != null) {
            channel.sendMessage(message).queue();
        }
    }

    public void sendPlayerMessage(String message) {
        if (DataAPI.getApi().isMaintenanceActive()) return;
        TextChannel channel = jda.getTextChannelById(playerStatusChannelID);
        if (channel != null) {
            channel.sendMessage(message).queue();
        }
    }

    public void sendPunishmentMessage(String message) {
        if (DataAPI.getApi().isMaintenanceActive()) return;
        TextChannel channel = jda.getTextChannelById(punishmentsChannelID);
        if (channel != null) {
            channel.sendMessage(message).queue();
        }
    }
}
