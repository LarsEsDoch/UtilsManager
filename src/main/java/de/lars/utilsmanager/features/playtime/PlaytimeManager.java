package de.lars.utilsmanager.features.playtime;

import de.lars.apiManager.playersAPI.PlayerAPI;
import de.lars.utilsmanager.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaytimeManager {

    public void updateTime() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), bukkitTask ->  {
            for (Player online: Bukkit.getOnlinePlayers()) {
                PlayerAPI.getApi().setPlaytime(online, PlayerAPI.getApi().getPlaytime(online) + 5);
            }
        }, 100, 100);
    }
}
