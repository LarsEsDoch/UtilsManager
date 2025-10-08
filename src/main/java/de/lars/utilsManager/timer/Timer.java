package de.lars.utilsManager.timer;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.timerAPI.TimerAPI;
import de.lars.utilsManager.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Timer {

    public Timer() {
        run();
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
            int seconds = time % 60;
            int minutes = (time / 60) % 60;
            int hours = (time / 3600);
            Component minsec = Component.text()
                    .append(Component.text(String.format("%02d", minutes), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("min ", NamedTextColor.BLUE))
                    .append(Component.text(String.format("%02d", seconds), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("sec", NamedTextColor.BLUE))
                    .build();

            Component hourminsec = Component.text()
                    .append(Component.text(String.format("%02d", hours), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("hr ", NamedTextColor.BLUE))
                    .append(Component.text(String.format("%02d", minutes), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("min ", NamedTextColor.BLUE))
                    .append(Component.text(String.format("%02d", seconds), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("sec", NamedTextColor.BLUE))
                    .build();
            if ((time / 3600) == 0) {
                player.sendActionBar(minsec);
            } else {
                player.sendActionBar(hourminsec);
            }

            if (TimerAPI.getApi().isTimer(player)) {
                playSound(player, time);
            }
        } else {
            if (TimerAPI.getApi().isTimer(player)) {
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
        for (Player onlineplayer:Bukkit.getOnlinePlayers()) {
            if (!TimerAPI.getApi().isRunning(player)) {
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    onlineplayer.sendActionBar(Component.text("Timer ist pausiert", NamedTextColor.RED));
                } else {
                    onlineplayer.sendActionBar(Component.text("Timer is paused", NamedTextColor.RED));
                }
                return;
            }

            int time = TimerAPI.getApi().getTime(player);
            if(!(time < 0)) {
                int seconds = time % 60;
                int minutes = (time / 60) % 60;
                int hours = (time / 3600);
                Component minsec = Component.text()
                        .append(Component.text(String.format("%02d", minutes), NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text("min ", NamedTextColor.BLUE))
                        .append(Component.text(String.format("%02d", seconds), NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text("sec", NamedTextColor.BLUE))
                        .build();

                Component hourminsec = Component.text()
                        .append(Component.text(String.format("%02d", hours), NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text("hr ", NamedTextColor.BLUE))
                        .append(Component.text(String.format("%02d", minutes), NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text("min ", NamedTextColor.BLUE))
                        .append(Component.text(String.format("%02d", seconds), NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text("sec", NamedTextColor.BLUE))
                        .build();
                if ((time / 3600) == 0) {
                    onlineplayer.sendActionBar(minsec);
                } else {
                    onlineplayer.sendActionBar(hourminsec);
                }
                if (TimerAPI.getApi().isTimer(player)) {
                    playSound(onlineplayer, time);
                }
            } else {
                if (TimerAPI.getApi().isTimer(player)) {
                    TimerAPI.getApi().setRunning(player, false);
                    TimerAPI.getApi().setTime(player, 0);
                    TimerAPI.getApi().setTimer(player, false);
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        onlineplayer.sendActionBar(Component.text("Ende", NamedTextColor.GREEN));
                    } else {
                        onlineplayer.sendActionBar(Component.text("End", NamedTextColor.GREEN));
                    }
                    onlineplayer.playSound(player, Sound.ENTITY_WITHER_DEATH, 0.8F, 1F);
                } else {
                    TimerAPI.getApi().setTime(player, 0);
                    TimerAPI.getApi().setTimer(player, false);
                }
            }
        }
    }



    private void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), bukkitTask -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (TimerAPI.getApi().isOff(player)) {
                    return;
                }
                if (TimerAPI.getApi().isPublic(player)) {
                    sendActionBarPublic(player);
                    if (TimerAPI.getApi().isRunning(player)) {
                        if(TimerAPI.getApi().isTimer(player)) {
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
                    if(TimerAPI.getApi().isTimer(player)) {
                        TimerAPI.getApi().setTime(player, TimerAPI.getApi().getTime(player) - 1);
                    } else {
                        TimerAPI.getApi().setTime(player, TimerAPI.getApi().getTime(player) + 1);
                    }
                }
            }
        }, 20, 20);
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
        if (time < 11) {
            if (time == 10) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 1F);
                return;
            }
            if (time == 9) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 1.1F);
                return;
            }
            if (time == 8) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.6F, 1.2F);
                return;
            }
            if (time == 7) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.6F, 1.3F);
                return;
            }
            if (time == 6) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.6F, 1.4F);
                return;
            }
            if (time == 5) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.7F, 1.5F);
                return;
            }
            if (time == 4) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.7F, 1.6F);
                return;
            }
            if (time == 3) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.8F, 1.7F);
                return;
            }
            if (time == 2) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.8F, 1.8F);
                return;
            }
            if (time == 1) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.9F, 1.9F);
                return;
            }
            if (time == 0) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.9F, 2F);
            }
        }
    }
}

