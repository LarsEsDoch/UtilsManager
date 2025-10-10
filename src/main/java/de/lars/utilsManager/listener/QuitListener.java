package de.lars.utilsManager.listener;

import de.lars.apiManager.dataAPI.DataAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.RankStatements;
import de.lars.utilsManager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                onlinePlayer.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                        .append(Component.text(" hat den Server verlassen.", NamedTextColor.WHITE)));
            } else {
                onlinePlayer.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                        .append(Component.text(" left the network.", NamedTextColor.WHITE)));
            }
        }

        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), bukkitTask -> {
            StringBuilder message = new StringBuilder();

            if (Bukkit.getOnlinePlayers().isEmpty()) {
                message = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " hat den Server verlassen.\\n\\nEs ist jetzt kein Spieler mehr online.");
            } if(Bukkit.getOnlinePlayers().size() == 1) {
                message = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " hat den Server verlassen.\\n\\nEs ist jetzt nur noch 1 Spieler online.\\n");
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    message.append(RankStatements.getUnformattedRank(onlinePlayer)).append(onlinePlayer.getName()).append("\\n");
                }
            } if (Bukkit.getOnlinePlayers().size() > 1) {
                message = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " hat den Server verlassen.\\n\\nEs sind jetzt nur noch " + (Bukkit.getOnlinePlayers().size()) + " Spieler online.\\n");
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    message.append(RankStatements.getUnformattedRank(onlinePlayer)).append(onlinePlayer.getName()).append("\\n");
                }
            }

            Main.getInstance().getDiscordBot().sendPlayerMessage(String.valueOf(message));
        }, 20);

        event.quitMessage(Component.text(""));
    }
}
