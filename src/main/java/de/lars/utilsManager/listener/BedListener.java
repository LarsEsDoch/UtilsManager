package de.lars.utilsManager.listener;

import de.lars.apiManager.dataAPI.DataAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.timerAPI.TimerAPI;
import de.lars.utilsManager.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class BedListener{
    private int sleepingPlayers;

    public BedListener() {
        run();
    }

    private void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), bukkitTask -> {
            int time = (int) Bukkit.getWorlds().get(0).getTime();
            if (time < 12600 || time >= 23460) {
                sleepingPlayers = 0;
                return;
            }
            if (DataAPI.getApi().isRealTimeActivated()) {
                return;
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.isSleeping()) {
                    sleepingPlayers++;
                }
            }
            int onlinePlayers = Bukkit.getOnlinePlayers().size();
            int playersNeeded = (int) Math.ceil(onlinePlayers / 3.0);
            int playersMissing = playersNeeded - sleepingPlayers;
            if (playersMissing > 0) {
                Component message;
                Component message2;
                if (playersMissing == 1) {
                    message = Component.text("Es muss noch ", NamedTextColor.DARK_PURPLE)
                            .append(Component.text(playersMissing, NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(" Spieler schlafen!", NamedTextColor.DARK_PURPLE));
                    message2 = Component.text(playersMissing, NamedTextColor.LIGHT_PURPLE)
                            .append(Component.text(" player still has to sleep!", NamedTextColor.DARK_PURPLE));
                } else {
                    message = Component.text("Es müsen noch ", NamedTextColor.DARK_PURPLE)
                            .append(Component.text("ein", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(" Spieler schlafen!", NamedTextColor.DARK_PURPLE));
                    message2 = Component.text("One", NamedTextColor.LIGHT_PURPLE)
                            .append(Component.text(" players still have to sleep!", NamedTextColor.DARK_PURPLE));
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!TimerAPI.getApi().isOff(player)) {
                        return;
                    }
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendActionBar(message);
                    } else {
                        player.sendActionBar(message2);
                    }
                }
            } else {
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), bukkitTask1 -> {
                    Bukkit.getWorlds().get(0).setTime(0);
                }, 1);

            }
        }, 40, 40);
    }

}

