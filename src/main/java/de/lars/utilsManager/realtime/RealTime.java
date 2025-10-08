package de.lars.utilsManager.realtime;

import de.lars.apiManager.dataAPI.DataAPI;
import de.lars.utilsManager.Main;
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
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), bukkitTask -> {
            if (!DataAPI.getApi().isRealTimeActivated()) return;
            time = LocalTime.now();
            date = LocalDate.now();
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                Objects.requireNonNull(Bukkit.getWorld("world")).setFullTime(date.getDayOfYear() * 24000 + time.getHour() * 1000 + time.getMinute() * 17 - 6000);
            }, 1);
        }, 1, 1);
    }
}
