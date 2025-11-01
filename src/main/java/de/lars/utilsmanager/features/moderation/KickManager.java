package de.lars.utilsmanager.features.moderation;

import de.lars.utilsmanager.UtilsManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;

public class KickManager {

    private Map<String, String> reasons = new HashMap<>();
    private Map<String, Integer> times = new HashMap<>();

    public KickManager() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            for (String kickedPlayer: reasons.keySet()) {
                times.put(kickedPlayer, times.get(kickedPlayer)-1);
                if (times.get(kickedPlayer) <= 0) {
                    reasons.remove(kickedPlayer);
                    times.remove(kickedPlayer);
                }
            }
        }, 20, 20);
    }

    public boolean setKicked(OfflinePlayer player, String reason, Integer time) {
        if (reasons.containsKey(player.getName())) {
            reasons.remove(player.getName());
            times.remove(player.getName());
        }
        reasons.put(player.getName(), reason);
        times.put(player.getName(), time);
        return true;
    }

    public boolean setUnKicked(OfflinePlayer player, String reason, Integer time) {
        if (reasons.containsKey(player.getName())) {
            return false;
        }
        reasons.put(player.getName(), reason);
        times.put(player.getName(), time);
        return true;
    }

    public boolean isKicked(OfflinePlayer player) {
        return reasons.containsKey(player.getName());
    }

    public String getReason(OfflinePlayer player) {
        return reasons.get(player.getName());
    }
    public Integer getTime(OfflinePlayer player) {
        return times.get(player.getName());
    }
}
