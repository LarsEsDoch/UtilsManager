package de.lars.utilsManager.ban;

import de.lars.apiManager.banAPI.BanAPI;
import de.lars.utilsManager.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BanListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        Main.getInstance().getBanManager().checkBanned(player);
        if (BanAPI.getApi().getBanned(player)) {
            Date now = new Date();

            Date time = BanAPI.getApi().getBanDate(player).getTime();
            time.setMonth(time.getMonth()-1);
            time.setHours(0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.add(Calendar.DAY_OF_YEAR, BanAPI.getApi().getTime(player));
            time = calendar.getTime();

            String remaingdays = getTimeDifference(now, time);


            event.disallow(PlayerLoginEvent.Result.KICK_OTHER,  Component.text("You're banned! Reason:\n", NamedTextColor.WHITE)
                    .append(Component.text(BanAPI.getApi().getReason(player), NamedTextColor.RED))
                    .append(Component.text("\n"))
                    .append(Component.text("Time to wait: ", NamedTextColor.GOLD))
                    .append(Component.text(remaingdays + "!", NamedTextColor.GOLD)));
        }
        if (Main.getInstance().getKickManager().isKicked(player)) {
            String reason = Main.getInstance().getKickManager().getReason(player);
            Integer time = Main.getInstance().getKickManager().getTime(player);
            int seconds = time % 60;
            int minutes = (time / 60) % 60;
            int hours = (time / 3600);
            String formatedTime = String.format("%02dh %02dm %02ds", hours, minutes, seconds);
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER,  Component.text("You're kicked! Reason:\n", NamedTextColor.DARK_RED)
                    .append(Component.text(reason, NamedTextColor.RED))
                    .append(Component.text("\n"))
                    .append(Component.text("Time to wait: ", NamedTextColor.GOLD))
                    .append(Component.text(formatedTime + "!", NamedTextColor.GOLD)));
        }
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
}
