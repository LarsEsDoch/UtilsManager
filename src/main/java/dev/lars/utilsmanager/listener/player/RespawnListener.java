package dev.lars.utilsmanager.listener.player;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.playerSettingsAPI.PlayerSettingsAPI;
import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import dev.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Objects;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location respawnLocation = event.getRespawnLocation();
        Location bedSpawn = player.getRespawnLocation();
        Location worldSpawn = Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation();
        Location loc = ServerSettingsAPI.getApi().getSpawnLocation();

        if (PlayerSettingsAPI.getApi().getBedToggle(player) || !respawnLocation.equals(bedSpawn)) {
            if (LanguageAPI.getApi().getLanguage(player ) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest an deinem eigenen Respawn Punkt wiederbelebt.", NamedTextColor.GOLD)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You were respawned at you're own respawn point.", NamedTextColor.GOLD)));
            }
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 2);
        } else {
            if (loc != null) {
                event.setRespawnLocation(loc);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest am Spawn wiederbelebt.", NamedTextColor.GOLD)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("You were respawned at the spawn.", NamedTextColor.GOLD)));
                }
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
            } else {
                event.setRespawnLocation(worldSpawn);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest am Welt Spawn wiederbelebt.", NamedTextColor.GOLD)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("You were respawned at the world spawn.", NamedTextColor.GOLD)));
                }
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 2);
            }
        }
    }
}