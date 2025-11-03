package de.lars.utilsmanager.listener.player;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import de.lars.apimanager.apis.timerAPI.TimerAPI;
import de.lars.utilsmanager.UtilsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BedListener{

    public BedListener() {
        run();
    }

    private void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            int time = (int) Bukkit.getWorlds().getFirst().getTime();
            if (time < 12600 || time >= 23460) {
                return;
            }
            if (ServerSettingsAPI.getApi().isRealTimeEnabled()) {
                return;
            }
            int sleepingPlayers = 0;
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
                Component message2 = Component.text(playersMissing, NamedTextColor.LIGHT_PURPLE)
                            .append(Component.text(" player still has to sleep.", NamedTextColor.DARK_PURPLE));
                if (playersMissing == 1) {
                    message = Component.text("Es muss noch ", NamedTextColor.DARK_PURPLE)
                            .append(Component.text(playersMissing, NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(" Spieler schlafen.", NamedTextColor.DARK_PURPLE));

                } else {
                    message = Component.text("Es müssen noch ", NamedTextColor.DARK_PURPLE)
                            .append(Component.text(playersMissing, NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(" Spieler schlafen.", NamedTextColor.DARK_PURPLE));
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
                Bukkit.getScheduler().runTaskLater(UtilsManager.getInstance(), bukkitTask1 -> {
                    Bukkit.getWorlds().getFirst().setTime(0);
                }, 1);

            }
        }, 20, 20);
    }
}