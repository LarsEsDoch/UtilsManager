package dev.lars.utilsmanager.features.maintenance;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.maintenanceAPI.MaintenanceAPI;
import dev.lars.apimanager.apis.rankAPI.RankAPI;
import dev.lars.utilsmanager.utils.FormatNumbers;
import dev.lars.utilsmanager.utils.Statements;
import dev.lars.utilsmanager.utils.SuggestHelper;
import dev.lars.utilsmanager.utils.TimeUtil;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.getOnlinePlayers;

public class MaintenanceCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

        if (!player.hasPermission("utilsmanager.maintenance")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "on": {
                if (MaintenanceAPI.getApi().isMaintenanceEnabled()) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nichts hat sich geändert! Aktuell ist die Wartung schon aktiviert!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nothing changed! Maintenance is currently activated!", NamedTextColor.RED)));
                    }
                } else {
                    if (MaintenanceAPI.getApi().getMaintenanceStart() == null) {
                        MaintenanceAPI.getApi().enableMaintenance("", null, null, null);
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Die Wartung hat begonnen.", NamedTextColor.GREEN)));
                        } else {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Maintenance was started.", NamedTextColor.GREEN)));
                        }
                        for (Player onlinePlayer : getOnlinePlayers()) {
                            if (onlinePlayer.isOp() || RankAPI.getApi().getRankId(onlinePlayer) > 7) continue;
                            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                                onlinePlayer.kick(Component.text("Der Server ist nun in Wartung! Bitte warten Sie, bis es weitere Informationen gibt."));
                            } else {
                                onlinePlayer.kick(Component.text("The server is in maintenance! Please wait until there is more information."));
                            }
                        }
                    } else {
                        MaintenanceAPI.getApi().setMaintenanceEnabled(true);
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Die Wartung hat nun frühzeitiger begonnen als geplant.", NamedTextColor.GREEN)));
                        } else {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Maintenance was started earlier than planned.", NamedTextColor.GREEN)));
                        }
                        for (Player onlinePlayer : getOnlinePlayers()) {
                            if (onlinePlayer.isOp() || RankAPI.getApi().getRankId(onlinePlayer) > 7) continue;
                            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                                onlinePlayer.kick(Component.text("Der Server ist nun in Wartung! Bitte warten Sie, bis es weitere Informationen gibt."));
                            } else {
                                onlinePlayer.kick(Component.text("The server is in maintenance! Please wait until there is more information."));
                            }
                        }
                    }
                }
                break;
            }
            case "off": {
                if (!MaintenanceAPI.getApi().isMaintenanceEnabled()) {
                    Instant maintenanceStart = MaintenanceAPI.getApi().getMaintenanceStart();
                    if (maintenanceStart == null) {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nichts hat sich geändert! Aktuell sind keine Wartungen!", NamedTextColor.RED)));} else {player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nothing changed! There is currently no maintenance!", NamedTextColor.RED)));}
                    } else {

                        Instant maintenanceEnd = MaintenanceAPI.getApi().getMaintenanceEstimatedEnd();
                        Instant maintenanceDeadline = MaintenanceAPI.getApi().getMaintenanceDeadline();
                        Instant now = Instant.now();

                        MaintenanceAPI.getApi().disableMaintenance();
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Die geplante Wartung wurde abgebrochen.", NamedTextColor.WHITE)));

                            long startDuration = Math.abs(Duration.between(maintenanceStart, now).getSeconds());
                                Component formattedStart = FormatNumbers.formatDuration(startDuration);
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Die Wartung hätte in ", NamedTextColor.GRAY))
                                        .append(formattedStart)
                                        .append(Component.text(" begonnen.", NamedTextColor.GRAY)));

                            if (maintenanceEnd != null) {
                                long endDuration = Duration.between(maintenanceStart, maintenanceEnd).getSeconds();
                                Component formattedEnd = FormatNumbers.formatDuration(Math.abs(endDuration));

                                player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("Die Wartung sollte voraussichtlich ", NamedTextColor.BLUE))
                                            .append(formattedEnd)
                                            .append(Component.text(" gebraucht.", NamedTextColor.BLUE)));
                            }

                            if (maintenanceDeadline != null) {
                                long deadlineDuration = Duration.between(now, maintenanceDeadline).getSeconds();
                                Component formattedDeadline = FormatNumbers.formatDuration(Math.abs(deadlineDuration));

                                player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("Die Wartung hätte in ", NamedTextColor.GOLD))
                                            .append(formattedDeadline)
                                            .append(Component.text(" automatisch beendet werden sollen.", NamedTextColor.GOLD)));
                            }
                        } else {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("The planned maintenance was cancelled.", NamedTextColor.WHITE)));

                            long startDuration = Math.abs(Duration.between(maintenanceStart, now).getSeconds());
                                Component formattedStart = FormatNumbers.formatDuration(startDuration);
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("The maintenance should've started in ", NamedTextColor.GRAY))
                                        .append(formattedStart)
                                        .append(Component.text(".", NamedTextColor.GRAY)));

                            if (maintenanceEnd != null) {
                                long endDuration = Duration.between(maintenanceStart, maintenanceEnd).getSeconds();
                                Component formattedEnd = FormatNumbers.formatDuration(Math.abs(endDuration));

                                player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("The maintenance was estimated to take ", NamedTextColor.BLUE))
                                            .append(formattedEnd)
                                            .append(Component.text(" .", NamedTextColor.BLUE)));
                            }

                            if (maintenanceDeadline != null) {
                                long deadlineDuration = Duration.between(now, maintenanceDeadline).getSeconds();
                                Component formattedDeadline = FormatNumbers.formatDuration(Math.abs(deadlineDuration));

                                player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("The maintenance would have been automatically disabled in ", NamedTextColor.GOLD))
                                            .append(formattedDeadline)
                                            .append(Component.text(".", NamedTextColor.GOLD)));
                            }
                        }
                    }
                } else {
                    Instant maintenanceStart = MaintenanceAPI.getApi().getMaintenanceStart();
                    Instant maintenanceEnd = MaintenanceAPI.getApi().getMaintenanceEstimatedEnd();
                    Instant maintenanceDeadline = MaintenanceAPI.getApi().getMaintenanceDeadline();
                    Instant now = Instant.now();

                    MaintenanceAPI.getApi().disableMaintenance();
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Die aktuelle Wartung wurde nun ausgestaltet.", NamedTextColor.WHITE)));

                        if (maintenanceStart != null) {
                            long startDuration = Math.abs(Duration.between(maintenanceStart, now).getSeconds());
                            Component formattedStart = FormatNumbers.formatDuration(startDuration);
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Die Wartung hat vor ", NamedTextColor.GRAY))
                                    .append(formattedStart)
                                    .append(Component.text(" begonnen.", NamedTextColor.GRAY)));
                        }

                        if (maintenanceEnd != null) {
                            long endDuration = Duration.between(now, maintenanceEnd).getSeconds();
                            Component formattedEnd = FormatNumbers.formatDuration(Math.abs(endDuration));

                            if (endDuration > 0) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Die Wartung sollte voraussichtlich noch ", NamedTextColor.BLUE))
                                        .append(formattedEnd)
                                        .append(Component.text(" brauchen.", NamedTextColor.BLUE)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Die Wartung sollte voraussichtlich bereits vor ", NamedTextColor.BLUE))
                                        .append(formattedEnd)
                                        .append(Component.text(" zu Ende sein.", NamedTextColor.BLUE)));
                            }
                        }

                        if (maintenanceDeadline != null) {
                            long deadlineDuration = Duration.between(now, maintenanceDeadline).getSeconds();
                            Component formattedDeadline = FormatNumbers.formatDuration(Math.abs(deadlineDuration));

                            if (deadlineDuration > 0) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Die Wartung hätte in ", NamedTextColor.GOLD))
                                        .append(formattedDeadline)
                                        .append(Component.text(" automatisch beendet werden sollen.", NamedTextColor.GOLD)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Die Wartung hätte bereits vor ", NamedTextColor.GOLD))
                                        .append(formattedDeadline)
                                        .append(Component.text(" automatisch beendet werden sollen.", NamedTextColor.GOLD)));
                            }
                        }

                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Alle Spieler können wieder beitreten und Discord Status Nachrichten werden gesendet!", NamedTextColor.YELLOW)));

                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The current maintenance has now been disabled.", NamedTextColor.WHITE)));

                        if (maintenanceStart != null) {
                            long startDuration = Math.abs(Duration.between(maintenanceStart, now).getSeconds());
                            Component formattedStart = FormatNumbers.formatDuration(startDuration);
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("The maintenance started ", NamedTextColor.GRAY))
                                    .append(formattedStart)
                                    .append(Component.text(" ago.", NamedTextColor.GRAY)));
                        }

                        if (maintenanceEnd != null) {
                            long endDuration = Duration.between(now, maintenanceEnd).getSeconds();
                            Component formattedEnd = FormatNumbers.formatDuration(Math.abs(endDuration));

                            if (endDuration > 0) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("The maintenance was estimated to take ", NamedTextColor.BLUE))
                                        .append(formattedEnd)
                                        .append(Component.text(" more.", NamedTextColor.BLUE)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("The maintenance was estimated to have finished ", NamedTextColor.BLUE))
                                        .append(formattedEnd)
                                        .append(Component.text(" ago.", NamedTextColor.BLUE)));
                            }
                        }

                        if (maintenanceDeadline != null) {
                            long deadlineDuration = Duration.between(now, maintenanceDeadline).getSeconds();
                            Component formattedDeadline = FormatNumbers.formatDuration(Math.abs(deadlineDuration));

                            if (deadlineDuration > 0) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("The maintenance would have been automatically disabled in ", NamedTextColor.GOLD))
                                        .append(formattedDeadline)
                                        .append(Component.text(".", NamedTextColor.GOLD)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("The maintenance would have been automatically disabled ", NamedTextColor.GOLD))
                                        .append(formattedDeadline)
                                        .append(Component.text(" ago.", NamedTextColor.GOLD)));
                            }
                        }

                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("All players can join again and Discord status messages will be sent!", NamedTextColor.YELLOW)));
                    }
                }
                break;
            }
            case "starttime": {
                if (args.length < 2) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Component.text("Verwendung").color(NamedTextColor.GRAY)
                                .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                                .append(Component.text("/maintenance ").color(NamedTextColor.BLUE))
                                .append(Component.text("startTime <Zeit>", NamedTextColor.BLUE)));
                    } else {
                        player.sendMessage(Component.text("Use").color(NamedTextColor.GRAY)
                                .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                                .append(Component.text("/maintenance ").color(NamedTextColor.BLUE))
                                .append(Component.text("startTime <time>", NamedTextColor.BLUE)));
                    }
                    return;
                }

                Instant startTime = TimeUtil.parseTimeToInstant(args[1]);
                if (startTime == null) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Ungültiges Zeitformat! Erwartetes Format: Zahl gefolgt von s/m/h/d (z. B. '30s', '5m', '2h', '7d')", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Invalid time format! Expected format: number followed by s/m/h/d (e.g., '30s', '5m', '2h', '7d')", NamedTextColor.RED)));
                    }
                    break;
                }
                MaintenanceAPI.getApi().setMaintenanceStart(startTime);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Die Wartung wurde erfolgreich geplant!", NamedTextColor.GREEN)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("The maintenance was successfully planned!", NamedTextColor.GREEN)));
                }
                break;
            }
            case "endtime": {
                if (args.length < 2) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Component.text("Verwendung").color(NamedTextColor.GRAY)
                                .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                                .append(Component.text("/maintenance ").color(NamedTextColor.BLUE))
                                .append(Component.text("endTime <Zeit>", NamedTextColor.BLUE)));
                    } else {
                        player.sendMessage(Component.text("Use").color(NamedTextColor.GRAY)
                                .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                                .append(Component.text("/maintenance ").color(NamedTextColor.BLUE))
                                .append(Component.text("endTime <time>", NamedTextColor.BLUE)));
                    }
                    return;
                }

                if (MaintenanceAPI.getApi().getMaintenanceStart() == null && !MaintenanceAPI.getApi().isMaintenanceEnabled()) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nichts hat sich geändert! Aktuell sind keine Wartungen geplant!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nothing changed! There is no maintenance planned!", NamedTextColor.RED)));
                    }
                    break;
                }

                Instant endTime = TimeUtil.parseTimeToInstant(args[1]);
                if (endTime == null) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Ungültiges Zeitformat! Erwartetes Format: Zahl gefolgt von s/m/h/d (z. B. '30s', '5m', '2h', '7d')", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Invalid time format! Expected format: number followed by s/m/h/d (e.g., '30s', '5m', '2h', '7d')", NamedTextColor.RED)));
                    }
                    break;
                }
                MaintenanceAPI.getApi().setMaintenanceEstimatedEnd(endTime);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Das Wartungsende wurde erfolgreich aktualisiert!", NamedTextColor.GREEN)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("The maintenance end was successfully updated!", NamedTextColor.GREEN)));
                }
                break;
            }
            case "deadline": {
                if (args.length < 2) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Component.text("Verwendung").color(NamedTextColor.GRAY)
                                .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                                .append(Component.text("/maintenance ").color(NamedTextColor.BLUE))
                                .append(Component.text("deadline <Zeit>", NamedTextColor.BLUE)));
                    } else {
                        player.sendMessage(Component.text("Use").color(NamedTextColor.GRAY)
                                .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                                .append(Component.text("/maintenance ").color(NamedTextColor.BLUE))
                                .append(Component.text("deadline <time>", NamedTextColor.BLUE)));
                    }
                    return;
                }

                if (MaintenanceAPI.getApi().getMaintenanceStart() == null && !MaintenanceAPI.getApi().isMaintenanceEnabled()) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nichts hat sich geändert! Aktuell sind keine Wartungen geplant!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nothing changed! There is no maintenance planned!", NamedTextColor.RED)));
                    }
                    break;
                }

                Instant deadlineTime = TimeUtil.parseTimeToInstant(args[1]);
                if (deadlineTime == null) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Ungültiges Zeitformat! Erwartetes Format: Zahl gefolgt von s/m/h/d (z. B. '30s', '5m', '2h', '7d')", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Invalid time format! Expected format: number followed by s/m/h/d (e.g., '30s', '5m', '2h', '7d')", NamedTextColor.RED)));
                    }
                    break;
                }
                MaintenanceAPI.getApi().setMaintenanceDeadline(deadlineTime);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Die Wartungsdeadline wurde erfolgreich aktualisiert!", NamedTextColor.GREEN)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("The maintenance deadline was successfully updated!", NamedTextColor.GREEN)));
                }
                break;
            }
            case "reason": {
                if (args.length < 2) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Component.text("Verwendung").color(NamedTextColor.GRAY)
                                .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                                .append(Component.text("/maintenance ").color(NamedTextColor.BLUE))
                                .append(Component.text("reason <Grund der Wartung>", NamedTextColor.BLUE)));
                    } else {
                        player.sendMessage(Component.text("Use").color(NamedTextColor.GRAY)
                                .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                                .append(Component.text("/maintenance ").color(NamedTextColor.BLUE))
                                .append(Component.text("reason <reason of maintenance>", NamedTextColor.BLUE)));
                    }
                    return;
                }

                if (MaintenanceAPI.getApi().getMaintenanceStart() == null && !MaintenanceAPI.getApi().isMaintenanceEnabled()) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nichts hat sich geändert! Aktuell sind keine Wartungen geplant!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nothing changed! There is no maintenance planned!", NamedTextColor.RED)));
                    }
                    break;
                }

                StringBuilder reason = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    reason.append(args[i]).append(" ");
                }
                MaintenanceAPI.getApi().setMaintenanceReasonAsync(reason.toString());
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Der Wartungsgrund wurde erfolgreich aktualisiert!", NamedTextColor.GREEN)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("The maintenance reason was successfully updated!", NamedTextColor.GREEN)));
                }
                break;
            }
            default: {
                sendUsage(player);
            }
        }
    }

    @Override
    public @NonNull Collection<String> suggest(final CommandSourceStack commandSourceStack, final String @NonNull [] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (!player.hasPermission("utilsmanager.maintenance")) return Collections.emptyList();

        if (args.length == 0) {
            return List.of(
                    "on",
                    "off",
                    "startTime",
                    "endTime",
                    "deadline",
                    "reason"
            );
        }

        if (args.length == 1) {
            return SuggestHelper.filter(args[0],
                    "on",
                    "off",
                    "startTime",
                    "endTime",
                    "deadline",
                    "reason"
            );
        }

        if (args[0].equalsIgnoreCase("startTime") ||
                args[0].equalsIgnoreCase("endTime") ||
                args[0].equalsIgnoreCase("deadline")) {
            return SuggestHelper.getTimeSuggestions(args[1]);
        }
        return Collections.emptyList();
    }

    private void sendUsage(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(Component.text("Verwendung").color(NamedTextColor.GRAY)
                    .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                    .append(Component.text("/maintenance ").color(NamedTextColor.BLUE))
                    .append(Component.text("on, off, startTime <Zeit>, endTime <Zeit>, deadline <Zeit>, reason <Grund>", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Use").color(NamedTextColor.GRAY)
                    .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                    .append(Component.text("/maintenance ").color(NamedTextColor.BLUE))
                    .append(Component.text("on, off, startTime <time>, endTime <time>, deadline <time>, reason <reason>", NamedTextColor.BLUE)));
        }
    }
}