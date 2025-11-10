package de.lars.utilsmanager.listener.player;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.UtilsManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.ArrayList;
import java.util.List;

public class SpawnElytraListener implements Listener {

    private final Integer spawnRadius;
    private final Integer multiplyValue;
    private final World world;
    private final List<Player> flying = new ArrayList<>();
    private final List<Player> boosted = new ArrayList<>();

    public SpawnElytraListener() {
        multiplyValue = 10;
        spawnRadius = 25;
        world = Bukkit.getWorld("world");

        run();
    }

    public void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            world.getPlayers().forEach(player -> {
                if (player.getGameMode() != GameMode.SURVIVAL) return;
                player.setAllowFlight(isInSpawnRadius(player));
                if (flying.contains(player) && !player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isAir()) {
                    player.setAllowFlight(false);
                    player.setGliding(false);
                    boosted.remove(player);
                    Bukkit.getScheduler().runTaskLater(UtilsManager.getInstance(), () -> {
                        flying.remove(player);
                    }, 5);
                }
            });
        }, 0, 3);
    }

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        if (!isInSpawnRadius(event.getPlayer())) return;

        event.setCancelled(true);
        player.setGliding(true);
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendActionBar(Component.text("Drücke ").append(Component.keybind("key.swapOffhand")).append(Component.text(" um zu boosten.")));
        } else {
            player.sendActionBar(Component.text("Press ").append(Component.keybind("key.swapOffhand")).append(Component.text(" to boost.")));
        }

        flying.add(player);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER
                && (event.getCause() == EntityDamageEvent.DamageCause.FALL
                || event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL)
                && flying.contains(event.getEntity())) event.setCancelled(true);
    }


    @EventHandler
    public void onSwapItem(PlayerSwapHandItemsEvent event) {
        if (!flying.contains(event.getPlayer()) || boosted.contains(event.getPlayer())) return;
        event.setCancelled(true);
        boosted.add(event.getPlayer());
        event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(multiplyValue));
    }

    @EventHandler
    public void onToggleGlide(EntityToggleGlideEvent event) {
        if (event.getEntityType() == EntityType.PLAYER && flying.contains(event.getEntity())) event.setCancelled(true);
    }

    private boolean isInSpawnRadius(Player player) {
        if (!player.getWorld().equals(world)) return false;
        Location loc = new Location(Bukkit.getWorld("world"), -1.5, 139.0, 24.5, 90, 0);
        return loc.distance(player.getLocation()) <= spawnRadius;
    }
}
