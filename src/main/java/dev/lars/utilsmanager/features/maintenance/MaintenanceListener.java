package dev.lars.utilsmanager.features.maintenance;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.rankAPI.RankAPI;
import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.time.Instant;

public class MaintenanceListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;
        if (RankAPI.getApi().getRankId(player) >= 8) return;

        if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, MaintenanceManager.kickMessage(player));
        }
    }
}