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
            Main.getInstance().getDiscordBot().sendPlayerOffMessage(player);
        }, 20);

        event.quitMessage(Component.text(""));
    }
}
