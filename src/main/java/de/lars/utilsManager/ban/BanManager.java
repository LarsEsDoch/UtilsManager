package de.lars.utilsManager.ban;

import de.lars.apiManager.banAPI.BanAPI;
import de.lars.utilsManager.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BanManager {

    public void runchecking() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), bukkitTask -> {
            for (Player target : Bukkit.getOnlinePlayers()) {
                if (BanAPI.getApi().getBanned(target)) {
                    Date now = new Date();

                    Date time = BanAPI.getApi().getBanDate(target).getTime();
                    time.setMonth(time.getMonth()-1);
                    time.setHours(0);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(time);
                    calendar.add(Calendar.DAY_OF_YEAR, BanAPI.getApi().getTime(target));
                    time = calendar.getTime();

                    String remaingDays = getTimeDifference(now, time);
                    target.kick(Component.text("You're banned! Reason:\n", NamedTextColor.WHITE)
                            .append(Component.text(BanAPI.getApi().getReason(target), NamedTextColor.RED))
                            .append(Component.text("Time to wait: ", NamedTextColor.WHITE))
                            .append(Component.text(remaingDays + "!")));
                    if (time.compareTo(now) <= 0) {
                        BanAPI.getApi().setUnBaned(target);
                    }
                }
            }
        }, 20, 20);
    }

    public static String getTimeDifference(Date date1, Date date2) {
        long diff = Math.abs(date2.getTime() - date1.getTime());
        long years = TimeUnit.MILLISECONDS.toDays(diff) / 365;
        long days = TimeUnit.MILLISECONDS.toDays(diff) % 365;
        long hours = TimeUnit.MILLISECONDS.toHours(diff) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60;
        return String.format("%02dy %02dd %02dh %02dm %02ds", years, days, hours, minutes, seconds);
    }

    public void checkBanned(Player target) {

        if (BanAPI.getApi().getBanned(target)) {
            Date now = new Date();

            Date time = BanAPI.getApi().getBanDate(target).getTime();
            time.setMonth(time.getMonth()-1);
            time.setHours(0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.add(Calendar.DAY_OF_YEAR, BanAPI.getApi().getTime(target));
            time = calendar.getTime();
            if (time.compareTo(now) <= 0) {
                BanAPI.getApi().setUnBaned(target);
            }
        }
    }
}
