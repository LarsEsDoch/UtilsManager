package de.lars.utilsmanager.listener.misc;

import de.lars.apiManager.banAPI.BanAPI;
import de.lars.utilsmanager.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class StairClickListener implements Listener {

    Player player;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (!event.hasBlock()) return;
        if (!(event.getClickedBlock().getType().name().endsWith("_STAIRS") || event.getClickedBlock().getType().name().endsWith("_SLAB"))) return;
        if(!event.hasItem()) return;
        if(!Objects.requireNonNull(event.getItem()).getType().equals(Material.PAPER)) return;
        player = event.getPlayer();
        for (Player player2: Bukkit.getOnlinePlayers()) {
            Integer criminal = BanAPI.getApi().isCriminal(player2);
            if (criminal == 3) {
                return;
            }
            if (Bukkit.getPlayer(BanAPI.getApi().getProsecutor(player2)) == player) {
                return;
            }
        }
        if (Main.getInstance().getCourtManager().witnesser.contains(player)) {
            return;
        }
        if (Main.getInstance().getCourtManager().members.contains(player)){
            return;
        }
        ItemMeta itemMeta = Objects.requireNonNull(event.getItem()).getItemMeta();
        String displayName = itemMeta.getDisplayName();

        if(!(Objects.equals(displayName.toLowerCase(), "seat"))) {
            return;
        }
        if (!event.getAction().name().contains("RIGHT_CLICK")) {
            return;
        }
        if (event.getClickedBlock().getType().name().endsWith("_STAIRS")) {
            Block stairBlock = event.getClickedBlock();
            Stairs stairs = (Stairs) stairBlock.getBlockData();
            Location location = player.getLocation();
            Location entityLocation = stairBlock.getLocation().add(0.5, -1.40, 0.5);
            if (stairs.getFacing() == BlockFace.NORTH) {
                location.setYaw(0);
                entityLocation.setYaw(0);
            }
            if (stairs.getFacing() == BlockFace.EAST) {
                location.setYaw(90);
                entityLocation.setYaw(90);
            }
            if (stairs.getFacing() == BlockFace.SOUTH) {
                location.setYaw(180);
                entityLocation.setYaw(180);
            }
            if (stairs.getFacing() == BlockFace.WEST) {
                location.setYaw(-90);
                entityLocation.setYaw(-90);
            }
            location.setPitch(0);

            if (stairBlock.getType().name().endsWith("_STAIRS")) {
                player.teleport(location);

                ArmorStand armorStand = (ArmorStand) stairBlock.getWorld().spawnEntity(entityLocation, EntityType.ARMOR_STAND);
                armorStand.setVisible(false);
                armorStand.setGravity(false);

                armorStand.addPassenger(player);
            }
        }
        if (event.getClickedBlock().getType().name().endsWith("_SLAB")) {
            Block stairBlock = event.getClickedBlock();
            Location location = player.getLocation();
            location.setYaw(player.getYaw() - 180);
            location.setPitch(0);
            Location entityLocation = stairBlock.getLocation().add(0.5, -1.40, 0.5);

            if (stairBlock.getType().name().endsWith("_SLAB")) {
                player.teleport(location);

                ArmorStand armorStand = (ArmorStand) stairBlock.getWorld().spawnEntity(entityLocation, EntityType.ARMOR_STAND);
                armorStand.setVisible(false);
                armorStand.setGravity(false);

                armorStand.addPassenger(player);
            }
        }
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        Entity entity = event.getEntity();
        Entity dismounted = event.getDismounted();

        if (entity instanceof Player && dismounted instanceof ArmorStand) {
            for (Player player: Bukkit.getOnlinePlayers()) {
                Integer criminal = BanAPI.getApi().isCriminal(player);
                if (criminal == 3) {
                    event.setCancelled(true);
                    return;
                }
            }
            ArmorStand armorStand = (ArmorStand) dismounted;
            armorStand.remove();
            player = (Player) event.getEntity();

            player.teleport(player.getLocation().add(0, 1, 0));
        }
    }
}
