package de.lars.utilsmanager.features.playtime;

import dev.lars.apimanager.apis.playerAPI.PlayerAPI;
import de.lars.utilsmanager.UtilsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaytimeManager {

    public void updateTime() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask ->  {
            for (Player online: Bukkit.getOnlinePlayers()) {
                PlayerAPI.getApi().setPlaytime(online, PlayerAPI.getApi().getPlaytime(online) + 5);
            }
        }, 100, 100);
    }
}
