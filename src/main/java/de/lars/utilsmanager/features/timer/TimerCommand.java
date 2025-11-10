package de.lars.utilsmanager.features.timer;

import dev.lars.apimanager.apis.courtAPI.CourtAPI;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.timerAPI.ITimerAPI;
import dev.lars.apimanager.apis.timerAPI.TimerAPI;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class TimerCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String @NotNull [] args) {
        Player player = (Player) stack.getSender();

        if (!player.hasPermission("plugin.timer")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (CourtAPI.getApi().getStatus(player) != 0) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix()
                        .append(Statements.getPrefix()
                        ).append(Component.text("Du kannst das aktuell nicht machen!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix()
                        .append(Statements.getPrefix()
                        ).append(Component.text("You can't do that right now!", NamedTextColor.RED)));
            }
            return;
        }

        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        ITimerAPI timer = TimerAPI.getApi();
        switch (args[0].toLowerCase()) {
            case "resume": {
                for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                    if(TimerAPI.getApi().isPublic(onlinePlayer)) {
                        if (onlinePlayer != player) {
                            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Du bist nicht der Besitzer des aktuellen Timers!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("You aren't the owner of the current timer!", NamedTextColor.RED)));
                            }
                            return;
                        }
                    }
                }
                if(!timer.isEnabled(player)) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Timer ist ausgeschaltet!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The timer is off!", NamedTextColor.RED)));
                    }
                    break;
                }
                if (timer.isRunning(player)) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Timer läuft bereits!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The timer is already running!", NamedTextColor.RED)));
                    }
                    break;
                }

                timer.setRunning(player, true);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Der Timer wurde gestartet.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("The timer was started.", NamedTextColor.WHITE)));
                }
                break;
            }
            case "stop": {
                for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                    if(TimerAPI.getApi().isPublic(onlinePlayer)) {
                        if (onlinePlayer != player) {
                            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Du bist nicht der Besitzer des aktuellen Timers!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("You aren't the owner of the current timer!", NamedTextColor.RED)));
                            }
                            return;
                        }
                    }
                }
                if (!timer.isRunning(player)) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Timer läuft nicht!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The timer isn´t running!", NamedTextColor.RED)));
                    }
                    break;
                }

                timer.setRunning(player,false);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Der Timer wurde gestoppt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("The timer was stopped.", NamedTextColor.WHITE)));
                }
                break;
            }
            case "time": {
                for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                    if(TimerAPI.getApi().isPublic(onlinePlayer)) {
                        if (onlinePlayer != player) {
                            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Du bist nicht der Besitzer des aktuellen Timers!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("You aren't the owner of the current timer!", NamedTextColor.RED)));
                            }
                            return;
                        }
                    }
                }
                if(args.length != 2) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/timer time <Zeit>");
                    } else {
                        player.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/timer time <time>");
                    }

                    return;
                }

                try {

                    timer.setRunning(player,false);
                    if(timer.isTimerEnabled(player)) {
                        timer.setTime(player, Integer.parseInt(args[1]));
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Der Timer wurde auf ", NamedTextColor.WHITE))
                                    .append(Component.text(timer.getTime(player), NamedTextColor.GOLD))
                                    .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                        } else {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("The timer was set on ", NamedTextColor.WHITE))
                                    .append(Component.text(timer.getTime(player), NamedTextColor.GOLD))
                                    .append(Component.text(".", NamedTextColor.WHITE)));
                        }

                    } else {
                        timer.setTime(player, Integer.parseInt(args[1]));
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Der Zeit wurde auf ", NamedTextColor.WHITE))
                                    .append(Component.text(timer.getTime(player), NamedTextColor.GOLD))
                                    .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                        } else {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("The time was set on ", NamedTextColor.WHITE))
                                    .append(Component.text(timer.getTime(player), NamedTextColor.GOLD))
                                    .append(Component.text(".", NamedTextColor.WHITE)));
                        }

                    }
                } catch (NumberFormatException e) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Parameter 2 muss eine Zahl sein.", NamedTextColor.RED)));
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Und maximal 10 Zeichen lang sein.", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Parameter 2 must be a number.", NamedTextColor.RED)));
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("And maxium 10 letters long.", NamedTextColor.RED)));
                    }

                }
                break;
            }
            case "reset": {
                for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                    if(TimerAPI.getApi().isPublic(onlinePlayer)) {
                        if (onlinePlayer != player) {
                            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Du bist nicht der Besitzer des aktuellen Timers!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("You aren't the owner of the current timer!", NamedTextColor.RED)));
                            }
                            return;
                        }
                    }
                }
                timer.setRunning(player,false);
                timer.setTime(player,0);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Der Timer wurde zurückgesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("The timer was resettet.", NamedTextColor.WHITE)));
                }
                break;
            }
            case "timer": {
                for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                    if(TimerAPI.getApi().isPublic(onlinePlayer)) {
                        if (onlinePlayer != player) {
                            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Du bist nicht der Besitzer des aktuellen Timers!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("You aren't the owner of the current timer!", NamedTextColor.RED)));
                            }
                            return;
                        }
                    }
                }
                if (timer.isTimerEnabled(player)) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Timer Modus ist bereits aktiviert.", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The timer mode is already enabled.", NamedTextColor.RED)));
                    }
                    break;
                }
                timer.setTimer(player,true);
                timer.setRunning(player,false);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Der Timer Modus ist nun aktiviert.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("The timer mode was enabled.", NamedTextColor.WHITE)));
                }
                break;
            }
            case "stopwatch": {
                for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                    if(TimerAPI.getApi().isPublic(onlinePlayer)) {
                        if (onlinePlayer != player) {
                            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Du bist nicht der Besitzer des aktuellen Timers!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("You aren't the owner of the current timer!", NamedTextColor.RED)));
                            }
                            return;
                        }
                    }
                }
                if (!timer.isTimerEnabled(player)) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Stoppuhr Modus ist bereits aktiviert.", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The stopwatch mode is already enabled.", NamedTextColor.RED)));
                    }
                    break;
                }
                timer.setTimer(player,false);
                timer.setRunning(player,false);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Der Stoppuhr Modus ist nun aktiviert.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("The stopwatch mode was enabled.", NamedTextColor.WHITE)));
                }
                break;
            }
            case "off": {
                for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                    if(TimerAPI.getApi().isPublic(onlinePlayer)) {
                        if (onlinePlayer != player) {
                            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Du bist nicht der Besitzer des aktuellen Timers!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("You aren't the owner of the current timer!", NamedTextColor.RED)));
                            }
                            return;
                        }
                    }
                }
                timer.setRunning(player,false);
                if (!timer.isEnabled(player)) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Timer ist bereits ausgeschaltet!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The timer is already off!", NamedTextColor.RED)));
                    }
                    break;
                }
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Der Timer wurde ausgeschaltet.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("The timer was switched off.", NamedTextColor.WHITE)));
                }

                timer.setEnabled(player, false);
                break;
            }
            case "on": {
                for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                    if(TimerAPI.getApi().isPublic(onlinePlayer)) {
                        if (onlinePlayer != player) {
                            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Du bist nicht der Besitzer des aktuellen Timers!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("You aren't the owner of the current timer!", NamedTextColor.RED)));
                            }
                            return;
                        }
                    }
                }
                if (timer.isEnabled(player)) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Timer ist bereits angeschaltet!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The timer is already on!", NamedTextColor.RED)));
                    }
                    break;
                }
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Der Timer wurde angeschaltet.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("The timer was switched on.", NamedTextColor.WHITE)));
                }

                timer.setEnabled(player,true);
                break;
            }
            case "public": {
                if (!player.hasPermission("plugin.timer.public")) {
                    player.sendMessage(Statements.getNotAllowed(player));
                    return;
                }
                if(args.length != 2) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.WHITE + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/timer public <on/off>");
                    } else {
                        player.sendMessage(NamedTextColor.WHITE + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/timer public <on/off>");
                    }
                    return;
                }
                if(Objects.equals(args[1], "on")) {
                    if(TimerAPI.getApi().isPublic(player)) {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Der Timer ist bereits öffentlich!", NamedTextColor.RED)));
                        } else {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("The timer is already public!", NamedTextColor.RED)));
                        }
                        return;
                    }
                    for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                        if(TimerAPI.getApi().isPublic(onlinePlayer)) {
                            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Es gibt bereits einen öffentlichen Timer!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("There is already a public Timer!", NamedTextColor.RED)));
                            }
                            return;
                        }
                    }
                    for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                        timer.setRunning(onlinePlayer, false);
                        timer.setEnabled(onlinePlayer, true);
                    }
                    timer.setRunning(player, true);
                    timer.setPublic(player, true);
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Timer wurde veröffentlicht.", NamedTextColor.WHITE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The timer has been made public.", NamedTextColor.WHITE)));
                    }
                    break;
                }

                if(Objects.equals(args[1], "off")) {
                    for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                        if(TimerAPI.getApi().isPublic(onlinePlayer)) {
                            if (onlinePlayer != player) {
                                if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                                    player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("Du bist nicht der Besitzer des aktuellen Timers!", NamedTextColor.RED)));
                                } else {
                                    player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("You aren't the owner of the current timer!", NamedTextColor.RED)));
                                }
                                return;
                            }
                        }
                    }

                    if(!TimerAPI.getApi().isPublic(player)) {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Der Timer ist bereits nicht öffentlich!", NamedTextColor.RED)));
                        } else {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("The Timer isn't public!", NamedTextColor.RED)));
                        }
                        return;
                    }
                    timer.setPublic(player, false);
                    for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                        timer.setEnabled(onlinePlayer, false);
                    }
                    timer.setEnabled(player, true);
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Timer ist nicht mehr öffentlich.", NamedTextColor.WHITE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The timer isn't longer public.", NamedTextColor.WHITE)));
                    }
                    break;
                }
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(NamedTextColor.WHITE + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/timer public <on/off>");
                } else {
                    player.sendMessage(NamedTextColor.WHITE + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/timer public <on/off>");
                }
                return;
            }
            default:
                sendUsage(player);
                break;
        }
    }

   @Override
   public @NotNull Collection<String> suggest(final @NotNull CommandSourceStack commandSourceStack, final String[] args) {
       if (args.length == 0 || args.length == 1) {
           Collection<String> timerCommands = new ArrayList<>();
           timerCommands.add("resume");
           timerCommands.add("stop");
           timerCommands.add("time");
           timerCommands.add("reset");
           timerCommands.add("off");
           timerCommands.add("on");
           timerCommands.add("timer");
           timerCommands.add("stopwatch");
           timerCommands.add("public");

           return timerCommands;
       }
       return Collections.emptyList();
   }

    private void sendUsage(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/timer resume, stop, time <Zeit>, reset, off/on, timer, stopwatch");
        } else {
            player.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/timer resume, stop, time <time>, reset, off/on, timer, stopwatch");
        }

    }
}
