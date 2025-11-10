package dev.lars.utilsmanager.features.freecam;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FreecamListener implements Listener {

    public Map<String, Location> freeCamUser = new HashMap<>();
    public final HashMap<UUID, ArmorStand> armorStandMap = new HashMap<>();

    @EventHandler
    public void onSwapItem(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (!freeCamUser.containsKey(player.getName())) return;
        player.teleport(freeCamUser.get(player.getName()));
        player.setGameMode(GameMode.SURVIVAL);
        Block aboveBlock = player.getLocation().add(0,1,0).getBlock();
        aboveBlock.setType(Material.AIR);
        freeCamUser.remove(player.getName());
    }
    @EventHandler
    public void onSwapItem(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!freeCamUser.containsKey(player.getName())) return;
        player.teleport(freeCamUser.get(player.getName()));
        player.setGameMode(GameMode.SURVIVAL);
        Block aboveBlock = player.getLocation().add(0,1,0).getBlock();
        aboveBlock.setType(Material.AIR);
        event.setCancelled(true);
        freeCamUser.remove(player.getName());
    }

    public Map<String, Location> getFreeCamUser() {
        return freeCamUser;
    }

    public Map<UUID, ArmorStand> getFreeCamArmorStand() {
        return armorStandMap;
    }
}
