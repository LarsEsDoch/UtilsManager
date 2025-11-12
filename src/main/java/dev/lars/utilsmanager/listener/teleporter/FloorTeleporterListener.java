package dev.lars.utilsmanager.listener.teleporter;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.UtilsManager;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FloorTeleporterListener implements Listener {

    private final Map<UUID, Double> lastY = new HashMap<>();
    private final Map<UUID, Long> teleportCooldown = new HashMap<>();
    private static final long COOLDOWN_TICKS = 10L;

    public FloorTeleporterListener() {
        Bukkit.getScheduler().runTaskTimer(UtilsManager.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.isSneaking() && !isOnCooldown(player.getUniqueId())) {
                    if (isOnDaylightDetector(player)) {
                        teleportDown(player);
                    }
                }
            }
        }, 1L, 1L);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getY() == to.getY()) {
            return;
        }

        if (isOnCooldown(playerId)) {
            return;
        }

        double currentY = to.getY();
        double previousY = lastY.getOrDefault(playerId, currentY);
        lastY.put(playerId, currentY);

        if (!isOnDaylightDetector(player)) {
            return;
        }

        if (currentY > previousY) {
            teleportUp(player);
        }
    }

    private boolean isOnCooldown(UUID playerId) {
        Long lastTeleport = teleportCooldown.get(playerId);
        if (lastTeleport == null) {
            return false;
        }

        long currentTime = System.currentTimeMillis();
        long cooldownMs = COOLDOWN_TICKS * 50;

        if (currentTime - lastTeleport < cooldownMs) {
            return true;
        }

        teleportCooldown.remove(playerId);
        return false;
    }

    private void setCooldown(UUID playerId) {
        teleportCooldown.put(playerId, System.currentTimeMillis());
    }

    private boolean isOnDaylightDetector(Player player) {
        Block blockAtFeet = player.getLocation().getBlock();
        Block blockBelow = player.getLocation().subtract(0, 1, 0).getBlock();

        return blockAtFeet.getType() == Material.DAYLIGHT_DETECTOR ||
               blockBelow.getType() == Material.DAYLIGHT_DETECTOR;
    }

    private void teleportUp(Player player) {
        int startY = player.getLocation().getBlockY() + 1;
        int maxY = player.getWorld().getMaxHeight();

        for (int y = startY; y <= maxY; y++) {
            Block block = player.getWorld().getBlockAt(
                player.getLocation().getBlockX(),
                y,
                player.getLocation().getBlockZ()
            );

            if (block.getType() == Material.DAYLIGHT_DETECTOR) {
                performTeleport(player, y);
                return;
            }
        }

        sendNoTeleporterMessage(player, true);
    }

    private void teleportDown(Player player) {
        int startY = player.getLocation().getBlockY() - 2;
        int minY = player.getWorld().getMinHeight();

        for (int y = startY; y >= minY; y--) {
            Block block = player.getWorld().getBlockAt(
                player.getLocation().getBlockX(),
                y,
                player.getLocation().getBlockZ()
            );

            if (block.getType() == Material.DAYLIGHT_DETECTOR) {
                performTeleport(player, y);
                return;
            }
        }

        sendNoTeleporterMessage(player, false);
    }

    private void performTeleport(Player player, int targetY) {
        World world = player.getWorld();
        int x = player.getLocation().getBlockX();
        int z = player.getLocation().getBlockZ();

        Block blockAbove = world.getBlockAt(x, targetY + 1, z);
        double yOffset = blockAbove.getType() != Material.AIR ? 1.50 : 0.50;

        Location teleportLocation = new Location(
            world,
            x + 0.5,
            targetY + yOffset,
            z + 0.5
        );
        teleportLocation.setDirection(player.getLocation().getDirection());

        player.teleport(teleportLocation);
        setCooldown(player.getUniqueId());
    }

    private void sendNoTeleporterMessage(Player player, boolean searchingUp) {
        String messageKey = searchingUp ?
            "Es gibt keine Teleporter über dir!" :
            "Es gibt keine Teleporter unter dir!";
        String messageEn = searchingUp ?
            "There are no teleporters above you!" :
            "There are no teleporters below you!";

        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendActionBar(Component.text(messageKey, NamedTextColor.RED));
        } else {
            player.sendActionBar(Component.text(messageEn, NamedTextColor.RED));
        }
    }
}