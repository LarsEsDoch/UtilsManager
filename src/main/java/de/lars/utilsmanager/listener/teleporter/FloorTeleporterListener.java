package de.lars.utilsmanager.listener.teleporter;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.UtilsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FloorTeleporterListener implements Listener {

    private Map<UUID, Double> lastY = new HashMap<>();

    public FloorTeleporterListener() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("ported")) {
                    return;
                }
                if(player.isSneaking()) {
                    down(player);
                }
            }
        }, 1, 1);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        double currentY = player.getLocation().getY();
        if (player.hasPermission("ported")) {
            this.lastY.put(playerId, currentY);
            return;
        }
        double lastY = this.lastY.getOrDefault(playerId, currentY);

        if (currentY > lastY) {
            up(player);
        } else if (currentY < lastY) {

        }

        this.lastY.put(playerId, currentY);
    }

    public void up(Player player) {
        Block thisblock = player.getLocation().getBlock();
        Location second = player.getLocation();
        second.setY(second.getY()-1.0);
        Block thissecondblock = second.getBlock();
        if (thisblock.getType() == Material.DAYLIGHT_DETECTOR || thissecondblock.getType() == Material.DAYLIGHT_DETECTOR) {
            for (int y = player.getLocation().getBlockY() + 1; y <= player.getWorld().getMaxHeight(); y++) {
                Block block = player.getWorld().getBlockAt(player.getLocation().getBlockX(), y, player.getLocation().getBlockZ());
                if (block.getType() == Material.DAYLIGHT_DETECTOR) {
                    World world = player.getWorld();
                    Block blockbackup = player.getWorld().getBlockAt(player.getLocation().getBlockX(), y + 1, player.getLocation().getBlockZ());
                    if (blockbackup.getType() != Material.AIR) {
                        player.teleport(new Location(world, player.getLocation().getBlockX() + 0.5, y + 1.50, player.getLocation().getBlockZ() + 0.5).setDirection(player.getLocation().getDirection()));
                    } else {
                        player.teleport(new Location(world, player.getLocation().getBlockX() + 0.5, y + 0.50, player.getLocation().getBlockZ() + 0.5).setDirection(player.getLocation().getDirection()));
                    }
                    PermissionAttachment attachment = player.addAttachment(UtilsManager.getInstance());
                    attachment.setPermission("ported", true);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            attachment.setPermission("ported", false);
                        }
                    }.runTaskLater(UtilsManager.getInstance(), 10);
                    return;
                }
            }
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendActionBar(Component.text("Es gibt keine Teleporter über dir!", NamedTextColor.RED));
            } else {
                player.sendActionBar(Component.text("There are no teleporters over you", NamedTextColor.RED));
            }
        }
    }

    public void down(Player player) {
        Block thisblock = player.getLocation().getBlock();
        Location second = player.getLocation();
        second.setY(second.getY()-1.0);
        Block thissecondblock = second.getBlock();
        if (thisblock.getType() == Material.DAYLIGHT_DETECTOR || thissecondblock.getType() == Material.DAYLIGHT_DETECTOR) {
            for (int y = player.getLocation().getBlockY() - 2; y >= player.getWorld().getMinHeight(); y--) {
                Block block = player.getWorld().getBlockAt(player.getLocation().getBlockX(), y, player.getLocation().getBlockZ());
                if (block.getType() == Material.DAYLIGHT_DETECTOR) {
                    World world = player.getWorld();
                    Block blockbackup = player.getWorld().getBlockAt(player.getLocation().getBlockX(), y + 1, player.getLocation().getBlockZ());
                    if (blockbackup.getType() != Material.AIR) {
                        player.teleport(new Location(world, player.getLocation().getBlockX() + 0.5, y + 1.50, player.getLocation().getBlockZ() + 0.5).setDirection(player.getLocation().getDirection()));
                    } else {
                        player.teleport(new Location(world, player.getLocation().getBlockX() + 0.5, y + 0.50, player.getLocation().getBlockZ() + 0.5).setDirection(player.getLocation().getDirection()));
                    }
                    PermissionAttachment attachment = player.addAttachment(UtilsManager.getInstance());
                    attachment.setPermission("ported", true);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            attachment.setPermission("ported", false);
                        }
                    }.runTaskLater(UtilsManager.getInstance(), 10);
                    return;
                }
            }
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendActionBar(Component.text("Es gibt keine Teleporter unter dir!", NamedTextColor.RED));
            } else {
                player.sendActionBar(Component.text("There are no teleporters under you", NamedTextColor.RED));
            }
        }
    }
}
