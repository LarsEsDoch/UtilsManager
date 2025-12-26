package dev.lars.utilsmanager.features.realtime;

import dev.lars.apimanager.apis.serverFeatureAPI.ServerFeatureAPI;
import dev.lars.utilsmanager.UtilsManager;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.util.Objects;

public class RealTime {
    private Boolean activated = false;

    public RealTime() {
        updateActivation();
        updateTime();
    }

    public void updateActivation() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            if (Bukkit.getOnlinePlayers().isEmpty()) return;
            activated = ServerFeatureAPI.getApi().isRealTimeEnabled();
        }, 20, 20);
    }

    private void updateTime() {
        Bukkit.getScheduler().runTaskTimer(UtilsManager.getInstance(), bukkitTask -> {
            if (!activated) return;
            LocalDateTime date = LocalDateTime.now();
            Objects.requireNonNull(Bukkit.getWorld("world")).setFullTime(date.getDayOfYear() * 24000 + date.getHour() * 1000 + date.getMinute() * 17 - 6000);
        }, 1, 1);
    }
}