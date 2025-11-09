package de.lars.utilsmanager.listener.player;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.apimanager.apis.playerSettingsAPI.PlayerSettingsAPI;
import de.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {

        Player player = event.getPlayer();
        Location respawnLocation = event.getRespawnLocation();
        Location bedSpawn = player.getRespawnLocation();
        Location worldSpawn = player.getWorld().getSpawnLocation();

        Location loc = new Location(Bukkit.getWorld("world"), -205.5, 78.0, -102.5, -90, 0);

        if (PlayerSettingsAPI.getApi().getBedToggle(player) || !respawnLocation.equals(bedSpawn)) {
            if (LanguageAPI.getApi().getLanguage(player ) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest an deinem eigenen Respawn Punkt wiederbelebt.", NamedTextColor.GOLD)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You were respawned at you're own respawn point.", NamedTextColor.GOLD)));
            }
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 2);
        } else {
            event.setRespawnLocation(loc);
            if (LanguageAPI.getApi().getLanguage(player ) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest am Offiziellen Spawn wiederbelebt.", NamedTextColor.GOLD)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You were respawned at the official Spawn.", NamedTextColor.GOLD)));
            }
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
        }
    }

}
