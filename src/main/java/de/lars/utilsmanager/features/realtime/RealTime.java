package de.lars.utilsmanager.features.realtime;

import de.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import de.lars.utilsmanager.UtilsManager;
import org.bukkit.Bukkit;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class RealTime {

    private LocalTime time;

    private LocalDate date;

    private Boolean activated;

    public RealTime() {
        run();
    }

    private void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            if (!ServerSettingsAPI.getApi().isRealTimeEnabled()) return;
            time = LocalTime.now();
            date = LocalDate.now();
            Bukkit.getScheduler().runTaskLater(UtilsManager.getInstance(), () -> {
                Objects.requireNonNull(Bukkit.getWorld("world")).setFullTime(date.getDayOfYear() * 24000 + time.getHour() * 1000 + time.getMinute() * 17 - 6000);
            }, 1);
        }, 1, 1);
    }
}
