package de.lars.utilsManager.discordBot;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsManager.utils.Statements;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DiscordListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "status" -> handleStatus(event);
            case "players" -> handlePlayerList(event);
            case "whitelist_add" -> handleWhitelistAdd(event);
            case "whitelist_remove" -> handleWhitelistRemove(event);
            case "say" -> handleSay(event);
            case "msg" -> handleMsg(event);
            case "broadcast" -> handleBroadcast(event);
        }
    }

    private void handleStatus(SlashCommandInteractionEvent event) {
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        int maxPlayers = Bukkit.getMaxPlayers();
        long maxMemory = Runtime.getRuntime().maxMemory() / (1024 * 1024);
        long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);

        String message = "🌍 Server ist online!\n" +
                "👥 Spieler: " + onlinePlayers + "/" + maxPlayers + "\n" +
                "⚡ TPS: " + String.format("%.2f", Bukkit.getServer().getTPS()[0]) +
                "\nRAM: " + usedMemory + " MB / " + maxMemory + " MB";

        event.reply(message).setEphemeral(true).queue();
    }

    private void handlePlayerList(SlashCommandInteractionEvent event) {
        List<String> playerNames = Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toList());

        String message;
        if (playerNames.isEmpty()) {
            message = "👥 Keine Spieler online.";
        } else {
            message = "👥 Spieler online: " + String.join(", ", playerNames);
        }

        event.reply(message).setEphemeral(true).queue();
    }

    private void handleWhitelistAdd(SlashCommandInteractionEvent event) {
        String name = Objects.requireNonNull(event.getOption("name")).getAsString();
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);

        offlinePlayer.setWhitelisted(true);
        event.reply("✅ `" + name + "` wurde zur Whitelist hinzugefügt.").setEphemeral(true).queue();
    }

    private void handleWhitelistRemove(SlashCommandInteractionEvent event) {
        String name = Objects.requireNonNull(event.getOption("name")).getAsString();
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);

        offlinePlayer.setWhitelisted(false);
        event.reply("❌ `" + name + "` wurde von der Whitelist entfernt.").setEphemeral(true).queue();
    }

    private void handleSay(SlashCommandInteractionEvent event) {
        String nachricht = Objects.requireNonNull(event.getOption("nachricht")).getAsString();
        String sender = Objects.requireNonNull(event.getMember()).getNickname();

        for (Player player : Bukkit.getOnlinePlayers()) {
             player.sendMessage(sender + "[Discord]: " + nachricht);
        }
        event.reply("✅ Nachricht wurde an den Server gesendet.").setEphemeral(true).queue();
    }

    private void handleMsg(SlashCommandInteractionEvent event) {
        String playerName = Objects.requireNonNull(event.getOption("spieler")).getAsString();
        String nachricht = Objects.requireNonNull(event.getOption("nachricht")).getAsString();
        String sender = Objects.requireNonNull(event.getMember()).getNickname();

        Player target = Bukkit.getPlayerExact(playerName);
        if (target == null) {
            event.reply("❌ Spieler `" + playerName + "` ist nicht online.").setEphemeral(true).queue();
            return;
        }

        target.sendMessage(sender + "[Discord]: " + nachricht);
        event.reply("✅ Nachricht an `" + playerName + "` gesendet.").setEphemeral(true).queue();
    }

    private void handleBroadcast(SlashCommandInteractionEvent event) {
        String message = Objects.requireNonNull(event.getOption("nachricht")).getAsString();
        for (Player player : Bukkit.getOnlinePlayers()) {
             player.sendMessage(
                            Statements.getPrefix()
                                    .append(Component.text(message, NamedTextColor.GOLD, TextDecoration.BOLD)));
        }
        event.reply("✅ Broadcast gesendet.").setEphemeral(true).queue();
    }
}
