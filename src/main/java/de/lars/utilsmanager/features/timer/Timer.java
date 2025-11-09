package de.lars.utilsmanager.features.timer;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.apimanager.apis.timerAPI.TimerAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.utils.FormatNumbers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Timer {

    public Timer() {
        run();
    }

    private void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!TimerAPI.getApi().isEnabled(player)) {
                    return;
                }
                if (TimerAPI.getApi().isPublic(player)) {
                    sendActionBarPublic(player);
                    if (TimerAPI.getApi().isRunning(player)) {
                        if(TimerAPI.getApi().isTimerEnabled(player)) {
                            TimerAPI.getApi().setTime(player, TimerAPI.getApi().getTime(player) - 1);
                        } else {
                            TimerAPI.getApi().setTime(player, TimerAPI.getApi().getTime(player) + 1);
                        }
                    }
                    break;
                }
                if(!(player.hasPermission("plugin.timer"))) {
                    return;
                }
                sendActionBar(player);
                if (TimerAPI.getApi().isRunning(player)) {
                    if(TimerAPI.getApi().isTimerEnabled(player)) {
                        TimerAPI.getApi().setTime(player, TimerAPI.getApi().getTime(player) - 1);
                    } else {
                        TimerAPI.getApi().setTime(player, TimerAPI.getApi().getTime(player) + 1);
                    }
                }
            }
        }, 20, 20);
    }

    public void sendActionBar(Player player) {
        if (!TimerAPI.getApi().isRunning(player)) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendActionBar(Component.text("Timer ist pausiert", NamedTextColor.RED));
            } else {
                player.sendActionBar(Component.text("Timer is paused", NamedTextColor.RED));
            }
            return;
        }

        int time = TimerAPI.getApi().getTime(player);
        if(!(time < 0)) {
            player.sendActionBar(FormatNumbers.formatTimeComponent(time));

            if (TimerAPI.getApi().isTimerEnabled(player)) {
                playSound(player, time);
            }
        } else {
            if (TimerAPI.getApi().isTimerEnabled(player)) {
                TimerAPI.getApi().setRunning(player, false);
                TimerAPI.getApi().setTime(player, 0);
                TimerAPI.getApi().setTimer(player, false);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendActionBar(Component.text("Ende", NamedTextColor.GREEN));
                } else {
                    player.sendActionBar(Component.text("End", NamedTextColor.GREEN));
                }
                player.playSound(player, Sound.ENTITY_WITHER_DEATH, 0.8F, 1);
            } else {
                TimerAPI.getApi().setTime(player, 0);
                TimerAPI.getApi().setTimer(player, false);
            }
        }
    }

    public void sendActionBarPublic(Player player) {
        int time = TimerAPI.getApi().getTime(player);
        if (!TimerAPI.getApi().isRunning(player)) {
            for (Player onlineplayer:Bukkit.getOnlinePlayers()) {
                if (LanguageAPI.getApi().getLanguage(onlineplayer) == 2) {
                    onlineplayer.sendActionBar(Component.text("Timer ist pausiert", NamedTextColor.RED));
                } else {
                    onlineplayer.sendActionBar(Component.text("Timer is paused", NamedTextColor.RED));
                }
            }
            return;
        }


        if(time >= 0) {
            Component formattedTime = FormatNumbers.formatTimeComponent(time);
            for (Player onlineplayer:Bukkit.getOnlinePlayers()) {
                onlineplayer.sendActionBar(formattedTime);
                if (TimerAPI.getApi().isTimerEnabled(player)) {
                    playSound(onlineplayer, time);
                }
            }
        } else {
            if (TimerAPI.getApi().isTimerEnabled(player)) {
                TimerAPI.getApi().setRunning(player, false);
                TimerAPI.getApi().setTime(player, 0);
                TimerAPI.getApi().setTimer(player, false);
                for (Player onlineplayer:Bukkit.getOnlinePlayers()) {
                    if (LanguageAPI.getApi().getLanguage(onlineplayer) == 2) {
                        onlineplayer.sendActionBar(Component.text("Ende", NamedTextColor.GREEN));
                    } else {
                        onlineplayer.sendActionBar(Component.text("End", NamedTextColor.GREEN));
                    }
                    onlineplayer.playSound(player, Sound.ENTITY_WITHER_DEATH, 0.8F, 1F);
                }
            } else {
                TimerAPI.getApi().setTime(player, 0);
                TimerAPI.getApi().setTimer(player, false);
            }
        }
    }

    public void playSound(Player player, Integer time) {
        if (time == 3600) {
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
            return;
        }
        if (time == 1800) {
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.5F);
            return;
        }
        if (time == 1200) {
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.6F);
            return;
        }
        if (time == 600) {
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.7F);
            return;
        }
        if (time == 60) {
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.8F);
            return;
        }
        if (time >= 0 && time <= 10) {
            float volume = 0.5F + (10 - time) * 0.05F;
            float pitch = 1F + (10 - time) * 0.1F;
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, volume, pitch);
        }
    }
}