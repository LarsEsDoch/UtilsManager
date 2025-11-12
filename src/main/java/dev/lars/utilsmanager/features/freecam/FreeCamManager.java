package dev.lars.utilsmanager.features.freecam;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FreeCamManager {
    public Map<UUID, Location> freeCamUser = new HashMap<>();
    public HashMap<UUID, ArmorStand> armorStandMap = new HashMap<>();

    public boolean enterFreeCam(Player player) {
        if (freeCamUser.containsKey(player.getUniqueId())) {
            return false;
        } else {
            freeCamUser.put(player.getUniqueId(), player.getLocation());
            player.setGameMode(GameMode.SPECTATOR);

            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setMarker(true);
            armorStand.setPersistent(false);
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            if (skullMeta != null) {
                PlayerProfile profile = Bukkit.createProfile(player.getUniqueId(), player.getName());
                skullMeta.setPlayerProfile(profile);
                skull.setItemMeta(skullMeta);
            }
            armorStandMap.put(player.getUniqueId(), armorStand);

            armorStand.getEquipment().setHelmet(skull);
            return true;
        }
    }

    public boolean exitFreeCam(Player player) {
        if (!freeCamUser.containsKey(player.getUniqueId())) {
            return false;
        } else {
            player.teleport(freeCamUser.get(player.getUniqueId()));

            Block above = player.getLocation().add(0,1,0).getBlock();
            if (!above.isEmpty()) above.setType(Material.AIR);

            player.setGameMode(GameMode.SURVIVAL);

            ArmorStand armorStand = armorStandMap.remove(player.getUniqueId());
            freeCamUser.remove(player.getUniqueId());

            if (armorStand != null && !armorStand.isDead()) {
                Chunk chunk = armorStand.getLocation().getChunk();
                boolean wasLoaded = chunk.isLoaded();
                if (!wasLoaded) chunk.load();

                armorStand.remove();

                if (!wasLoaded) chunk.unload();
            }
            return true;
        }
    }

    public boolean isFreeCamPlayer(Player player) {
        return freeCamUser.containsKey(player.getUniqueId());
    }
}