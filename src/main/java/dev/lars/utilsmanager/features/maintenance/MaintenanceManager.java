package dev.lars.utilsmanager.features.maintenance;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;

public class MaintenanceManager {

    public MaintenanceManager() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            if (Bukkit.getOnlinePlayers().isEmpty()) return;
            if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) {
                Instant maintenanceEnd = ServerSettingsAPI.getApi().getMaintenanceEstimatedEnd();

                Instant now = Instant.now();
                long maintenanceTime = Duration.between(now, maintenanceEnd).getSeconds();

                maintenanceTime = Math.abs(maintenanceTime);

                int seconds = (int) (maintenanceTime % 60);
                int minutes = (int) ((maintenanceTime / 60) % 60);
                int hours = (int) ((maintenanceTime / 3600) % 24);
                int days = (int) (maintenanceTime / 86400);

                Component formatedTime = Component.text(String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, seconds), NamedTextColor.GOLD);
                Component formatedTimeNoDay = Component.text(String.format("%02dh %02dm %02ds", hours, minutes, seconds), NamedTextColor.GOLD);
                Component formatedTimeNoHour = Component.text(String.format("%02dm %02ds", minutes, seconds), NamedTextColor.GOLD);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.isOp() || !player.hasPermission("utilsmanager.maintenance")) {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            if ((maintenanceTime/ 86400) != 0) {
                                Bukkit.getScheduler().runTaskLater(UtilsManager.getInstance(), bukkitTask1 -> {
                                    player.kick(Component.text("Wir entschuldigen uns, aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                            .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                            .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                            .append(Component.text("Voraussichtlich Verbliebende Zeit: ", NamedTextColor.WHITE))
                                            .append(formatedTime));
                                }, 1);
                            } else {
                                if ((maintenanceTime/ 3600) == 0) {
                                    Bukkit.getScheduler().runTaskLater(UtilsManager.getInstance(), bukkitTask1 -> {
                                        player.kick(Component.text("Wir entschuldigen uns, aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                                .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                                .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                                .append(Component.text("Voraussichtlich Verbliebende Zeit: ", NamedTextColor.WHITE))
                                                .append(formatedTimeNoHour));
                                    }, 1);
                                } else {
                                    Bukkit.getScheduler().runTaskLater(UtilsManager.getInstance(), bukkitTask1 -> {
                                        player.kick(Component.text("Wir entschuldigen uns, aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                                .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                                .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                                .append(Component.text("Voraussichtlich Verbliebende Zeit: ", NamedTextColor.WHITE))
                                                .append(formatedTimeNoDay));
                                    }, 1);
                                }
                            }
                        } else {
                            if ((maintenanceTime/ 86400) != 0) {
                                Bukkit.getScheduler().runTaskLater(UtilsManager.getInstance(), bukkitTask1 -> {
                                    player.kick(Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                            .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                            .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                            .append(Component.text("Estimated Remaining Time: ", NamedTextColor.WHITE))
                                            .append(formatedTime));
                                }, 1);
                            } else {
                                if ((maintenanceTime/ 3600) == 0) {
                                    player.kick(Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                            .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                            .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                            .append(Component.text("Estimated Remaining Time ", NamedTextColor.WHITE))
                                            .append(formatedTimeNoHour));
                                } else {
                                    Bukkit.getScheduler().runTaskLater(UtilsManager.getInstance(), bukkitTask1 -> {
                                        player.kick(Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                                .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                                .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                                .append(Component.text("Estimated Remaining Time: ", NamedTextColor.WHITE))
                                                .append(formatedTimeNoDay));
                                    }, 1);
                                }
                            }
                        }
                    } else {
                        switch ((int) maintenanceTime) {
                            case 9000, 7200, 5400 ,3600, 1800, 1200, 600, 120, 90, 60: {
                                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                    player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("Die Server Wartung sollte voraussichtlich in ", NamedTextColor.AQUA))
                                            .append(Component.text(maintenanceTime/ 60, NamedTextColor.GOLD))
                                            .append(Component.text(" Minuten zu ende sein!", NamedTextColor.AQUA)));
                                } else {
                                    player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("Server maintenance is expected to be completed in ", NamedTextColor.AQUA))
                                            .append(Component.text(maintenanceTime/ 60, NamedTextColor.GOLD))
                                            .append(Component.text(" minutes!", NamedTextColor.AQUA)));
                                }
                                Bukkit.getConsoleSender().sendMessage(Statements.getPrefix()
                                        .append(Component.text("Server maintenance is expected to be completed in ", NamedTextColor.AQUA))
                                        .append(Component.text(maintenanceTime/ 60, NamedTextColor.GOLD))
                                        .append(Component.text(" minutes!", NamedTextColor.AQUA)));
                                break;
                            }
                            case 30, 10, 5: {
                                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                    player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("Die Server Wartung sollte voraussichtlich in ", NamedTextColor.AQUA))
                                            .append(Component.text(maintenanceTime, NamedTextColor.GOLD))
                                            .append(Component.text(" Sekunden zu ende sein!", NamedTextColor.AQUA)));
                                } else {
                                    player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("Server maintenance is expected to be completed in ", NamedTextColor.AQUA))
                                            .append(Component.text(maintenanceTime, NamedTextColor.GOLD))
                                            .append(Component.text(" seconds!", NamedTextColor.AQUA)));
                                }
                                Bukkit.getConsoleSender().sendMessage(Statements.getPrefix()
                                        .append(Component.text("Server maintenance is expected to be completed in ", NamedTextColor.AQUA))
                                        .append(Component.text(maintenanceTime, NamedTextColor.GOLD))
                                        .append(Component.text(" seconds!", NamedTextColor.AQUA)));
                                break;
                            }
                            case 0: {
                                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                    Component stopMaintenanceGermanCommand = Component.text("[Jetzt ausschalten]", NamedTextColor.DARK_RED)
                                            .clickEvent(ClickEvent.runCommand("/maintenance off"))
                                            .hoverEvent(HoverEvent.showText(Component.text("Alle Spieler können wieder beitreten und Discord Status Nachrichten werden gesendet!", NamedTextColor.YELLOW)));

                                    player.sendMessage(Statements.getPrefix().append(Component.text("Die Server Wartung sollte laut voraussichtlicher Zeit", NamedTextColor.DARK_PURPLE))
                                            .append(Component.text(" jetzt ", NamedTextColor.DARK_RED))
                                            .append(Component.text("zu Ende sein!", NamedTextColor.DARK_PURPLE)));
                                    player.sendMessage(Statements.getPrefix().append(Component.text("Bitte beenden sie die Wartung sobald wie möglich! ", NamedTextColor.GRAY))
                                            .append(stopMaintenanceGermanCommand));

                                } else {
                                    Component stopMaintenanceEnglischCommand = Component.text("[Turn off now]", NamedTextColor.DARK_RED)
                                            .clickEvent(ClickEvent.runCommand("/maintenance off"))
                                            .hoverEvent(HoverEvent.showText(Component.text("All players can join again and Discord Status Messages will be send!", NamedTextColor.YELLOW)));

                                    player.sendMessage(Statements.getPrefix().append(Component.text("The server maintenance should be", NamedTextColor.DARK_PURPLE))
                                            .append(Component.text(" now ", NamedTextColor.DARK_RED))
                                            .append(Component.text("over according to the estimated time!", NamedTextColor.DARK_PURPLE)));
                                    player.sendMessage(Statements.getPrefix().append(Component.text("Please end the maintenance as soon as possible! ", NamedTextColor.GRAY))
                                            .append(stopMaintenanceEnglischCommand));

                                }
                                Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("The server maintenance should ", NamedTextColor.DARK_PURPLE))
                                        .append(Component.text(" now ", NamedTextColor.DARK_RED))
                                        .append(Component.text("be over according to the estimated time!", NamedTextColor.DARK_PURPLE)));
                                break;
                            }
                            case -60, -120, -300, -600, -1200, -1800, -3600, -5400, -7200, -9000: {
                                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                    Component stopMaintenanceGermanCommand = Component.text("[Jetzt ausschalten]", NamedTextColor.DARK_RED)
                                            .clickEvent(ClickEvent.runCommand("/maintenance off"))
                                            .hoverEvent(HoverEvent.showText(Component.text("Alle Spieler können wieder beitreten und Discord Status Nachrichten werden gesendet!", NamedTextColor.YELLOW)));

                                    player.sendMessage(Statements.getPrefix().append(Component.text("Die Server Wartung sollte laut voraussichtlicher Zeit seit ", NamedTextColor.DARK_PURPLE))
                                            .append(Component.text(maintenanceTime/-60 + " Minuten", NamedTextColor.DARK_RED))
                                            .append(Component.text("zu Ende sein!", NamedTextColor.DARK_PURPLE)));
                                    player.sendMessage(Statements.getPrefix().append(Component.text("Bitte beenden sie die Wartung sobald wie möglich! ", NamedTextColor.GRAY))
                                            .append(stopMaintenanceGermanCommand));

                                } else {
                                    Component stopMaintenanceEnglischCommand = Component.text("[Turn off now]", NamedTextColor.DARK_RED)
                                            .clickEvent(ClickEvent.runCommand("/maintenance off"))
                                            .hoverEvent(HoverEvent.showText(Component.text("All players can join again and Discord Status Messages will be send!", NamedTextColor.YELLOW)));

                                    player.sendMessage(Statements.getPrefix().append(Component.text("The server maintenance should be since ", NamedTextColor.DARK_PURPLE))
                                            .append(Component.text(maintenanceTime/-60 + " minutes", NamedTextColor.DARK_RED))
                                            .append(Component.text(" over according to the estimated time!", NamedTextColor.DARK_PURPLE)));
                                    player.sendMessage(Statements.getPrefix().append(Component.text("Please end the maintenance as soon as possible! ", NamedTextColor.GRAY))
                                            .append(stopMaintenanceEnglischCommand));

                                }
                                Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("The server maintenance should be since ", NamedTextColor.DARK_PURPLE))
                                        .append(Component.text(maintenanceTime/-60 + " minutes", NamedTextColor.DARK_RED))
                                        .append(Component.text(" over according to the estimated time!", NamedTextColor.DARK_PURPLE)));
                                break;
                            }

                            default:
                                break;
                        }
                    }
                }
                if (ServerSettingsAPI.getApi().isMaintenanceEnabled() && maintenanceTime<= 0) {
                    LocalDateTime nowDate = LocalDateTime.now();
                    LocalDateTime target = LocalDateTime.of(2025, Month.OCTOBER, 31, 18, 0);
                    if (nowDate.isBefore(target)) {
                        ServerSettingsAPI.getApi().disableMaintenance();
                    }
                }
            }
        }, 20, 20);
    }

    public static Component kickMessage(Player player) {
        boolean german = LanguageAPI.getApi().getLanguage(player) == 2;

        Component message = Component.text(
                german
                        ? "Wir entschuldigen uns, aber der Server ist derzeit in Wartung!\n"
                        : "We apologize but the server is currently under maintenance!\n",
                NamedTextColor.RED
        );

        String reason = ServerSettingsAPI.getApi().getMaintenanceReason();
        if (reason != null && !reason.isBlank()) {
            message = message
                    .append(Component.text(
                            german ? "Grund: " : "Reason: ",
                            NamedTextColor.WHITE
                    ))
                    .append(Component.text(reason + "\n", NamedTextColor.BLUE));
        }

        Instant now = Instant.now();
        Instant estimatedEndTime = ServerSettingsAPI.getApi().getMaintenanceEstimatedEnd();
        Instant deadline = ServerSettingsAPI.getApi().getMaintenanceDeadline();
        Component timeComponent = null;

        if (estimatedEndTime != null && estimatedEndTime.isAfter(now)) {
            long seconds = Duration.between(now, estimatedEndTime).getSeconds();
            timeComponent = FormatNumbers.formatDuration(seconds);

            message = message.append(Component.text(
                    german
                            ? "Voraussichtlich verbleibende Zeit: "
                            : "Estimated remaining time: ",
                    NamedTextColor.WHITE
            ));
        } else if (deadline != null && deadline.isAfter(now)) {
            long seconds = Duration.between(now, deadline).getSeconds();
            timeComponent = FormatNumbers.formatDuration(seconds);

            message = message.append(Component.text(
                    german
                            ? "Endet in: "
                            : "Ends in: ",
                    NamedTextColor.WHITE
            ));
        }

        if (timeComponent != null) {
            message = message.append(timeComponent);
        }

        return message;
    }
}