package dev.lars.utilsmanager.features.freecam;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FreecamListener implements Listener {

    public Map<String, Location> freeCamUser = new HashMap<>();
    public final HashMap<UUID, ArmorStand> armorStandMap = new HashMap<>();

    @EventHandler
    public void onSwapItem(PlayerSwapHandItemsEvent event) {
        Player player = (Player) event.getPlayer();
        if (!freeCamUser.containsKey(player.getName())) return;
        player.performCommand("freecamleave");
        event.setCancelled(true);
    }

    public Map<String, Location> getFreeCamUser() {
        return freeCamUser;
    }

    public Map<UUID, ArmorStand> getFreeCamArmorStand() {
        return armorStandMap;
    }
}
