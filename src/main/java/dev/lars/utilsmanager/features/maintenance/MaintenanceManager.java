package dev.lars.utilsmanager.features.maintenance;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.FormatNumbers;
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
import java.util.List;

public class MaintenanceManager {

    List<Long> COUNTDOWN_THRESHOLDS = List.of(
        10800L,
        9000L,
        7200L,
        5400L,
        3600L,
        2700L,
        1800L,
        1200L,
        900L,
        600L,
        300L,
        180L,
        120L,
        60L,
        30L,
        10L,
        5L
    );


    public MaintenanceManager() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            Instant now = Instant.now();
            boolean maintenanceEnabled = ServerSettingsAPI.getApi().isMaintenanceEnabled();
            Instant maintenanceStart = ServerSettingsAPI.getApi().getMaintenanceStart();
            Instant maintenanceEnd = ServerSettingsAPI.getApi().getMaintenanceEstimatedEnd();
            Instant maintenanceDeadline = ServerSettingsAPI.getApi().getMaintenanceDeadline();
            if (maintenanceStart != null && maintenanceStart.isAfter(now) && !maintenanceEnabled) {
                long secondsUntilMaintenanceStart = Duration.between(now, maintenanceStart).getSeconds();
                Component formatedTime = FormatNumbers.formatDuration(secondsUntilMaintenanceStart);

                if (secondsUntilMaintenanceStart <= 0) {
                    ServerSettingsAPI.getApi().setMaintenanceEnabled(true);
                }

                if (COUNTDOWN_THRESHOLDS.contains(secondsUntilMaintenanceStart)) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Die Server Wartung beginnt in ", NamedTextColor.AQUA))
                                    .append(formatedTime)
                                    .append(Component.text("!", NamedTextColor.AQUA)));
                        } else {
                             player.sendMessage(Statements.getPrefix()
                                     .append(Component.text("Server maintenance will begin in ", NamedTextColor.AQUA))
                                     .append(formatedTime)
                                     .append(Component.text("!", NamedTextColor.AQUA)));
                        }
                    }
                    Bukkit.getConsoleSender().sendMessage(Statements.getPrefix()
                            .append(Component.text("Server maintenance will begin in ", NamedTextColor.AQUA))
                            .append(formatedTime)
                            .append(Component.text("!", NamedTextColor.AQUA)));
                }
            }
            if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.isOp() || !player.hasPermission("utilsmanager.maintenance")) {
                        Bukkit.getScheduler().runTask(UtilsManager.getInstance(), bukkitTask1 -> {
                            player.kick(kickMessage(player));
                        });
                    } else {
                        if (maintenanceEnd != null) {
                            long secondsUntilMaintenanceEnd = Duration.between(now, maintenanceEnd).getSeconds();
                            Component formatedTime = FormatNumbers.formatDuration(secondsUntilMaintenanceEnd);
                            if (now.isBefore(maintenanceEnd)) {
                                if (COUNTDOWN_THRESHOLDS.contains(secondsUntilMaintenanceEnd)) {
                                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                            player.sendMessage(Statements.getPrefix()
                                                    .append(Component.text("Die Server Wartung sollte voraussichtlich in ", NamedTextColor.AQUA))
                                                    .append(formatedTime)
                                                    .append(Component.text(" zu ende sein!", NamedTextColor.AQUA)));
                                    } else {
                                        player.sendMessage(Statements.getPrefix()
                                                .append(Component.text("Server maintenance is expected to be completed in ", NamedTextColor.AQUA))
                                                .append(formatedTime)
                                                .append(Component.text(" !", NamedTextColor.AQUA)));
                                    }
                                    Bukkit.getConsoleSender().sendMessage(Statements.getPrefix()
                                            .append(Component.text("Server maintenance is expected to be completed in ", NamedTextColor.AQUA))
                                            .append(formatedTime)
                                            .append(Component.text(" !", NamedTextColor.AQUA)));
                                }
                            } else {
                                if (secondsUntilMaintenanceEnd != 0) {
                                    if (COUNTDOWN_THRESHOLDS.contains(secondsUntilMaintenanceEnd)) {
                                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                            Component stopMaintenanceGermanCommand = Component.text("[Jetzt ausschalten]", NamedTextColor.DARK_RED)
                                                    .clickEvent(ClickEvent.runCommand("/maintenance off"))
                                                    .hoverEvent(HoverEvent.showText(Component.text("Alle Spieler können wieder beitreten und Discord Status Nachrichten werden gesendet!", NamedTextColor.YELLOW)));

                                            player.sendMessage(Statements.getPrefix().append(Component.text("Die Server Wartung sollte laut voraussichtlicher Zeit seit ", NamedTextColor.DARK_PURPLE))
                                                    .append(formatedTime.color(NamedTextColor.DARK_RED))
                                                    .append(Component.text("zu Ende sein!", NamedTextColor.DARK_PURPLE)));
                                            player.sendMessage(Statements.getPrefix().append(Component.text("Bitte beenden sie die Wartung sobald wie möglich! ", NamedTextColor.GRAY))
                                                    .append(stopMaintenanceGermanCommand));

                                        } else {
                                            Component stopMaintenanceEnglischCommand = Component.text("[Turn off now]", NamedTextColor.DARK_RED)
                                                    .clickEvent(ClickEvent.runCommand("/maintenance off"))
                                                    .hoverEvent(HoverEvent.showText(Component.text("All players can join again and Discord Status Messages will be send!", NamedTextColor.YELLOW)));

                                            player.sendMessage(Statements.getPrefix().append(Component.text("The server maintenance should be since ", NamedTextColor.DARK_PURPLE))
                                                    .append(formatedTime.color(NamedTextColor.DARK_RED))
                                                    .append(Component.text(" over according to the estimated time!", NamedTextColor.DARK_PURPLE)));
                                            player.sendMessage(Statements.getPrefix().append(Component.text("Please end the maintenance as soon as possible! ", NamedTextColor.GRAY))
                                                    .append(stopMaintenanceEnglischCommand));

                                        }
                                        Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("The server maintenance should be since ", NamedTextColor.DARK_PURPLE))
                                                .append(formatedTime.color(NamedTextColor.DARK_RED))
                                                .append(Component.text(" over according to the estimated time!", NamedTextColor.DARK_PURPLE)));
                                    }
                                } else {
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
                                }
                            }
                        }
                    }
                }
                if (ServerSettingsAPI.getApi().isMaintenanceEnabled() && maintenanceDeadline != null && maintenanceDeadline.isBefore(now)) {
                    ServerSettingsAPI.getApi().disableMaintenance();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Component.text("Die Server Wartung wurde nun automatisch beendet!", NamedTextColor.GOLD));
                        } else {
                            player.sendMessage(Component.text("Server maintenance was now automatically ended!", NamedTextColor.GOLD));
                        }
                    }
                    Bukkit.getConsoleSender().sendMessage(Statements.getPrefix()
                            .append(Component.text("Server maintenance was now automatically ended!", NamedTextColor.GOLD)));
                }
                if (ServerSettingsAPI.getApi().isMaintenanceEnabled() && maintenanceDeadline != null && maintenanceDeadline.isAfter(now) && maintenanceEnd == null
                    || ServerSettingsAPI.getApi().isMaintenanceEnabled() && maintenanceDeadline != null && maintenanceDeadline.isAfter(now) && maintenanceEnd != null && now.isAfter(maintenanceEnd)) {
                    long secondsUntilMaintenanceDeadline = Duration.between(now, maintenanceDeadline).getSeconds();
                    Component formatedTime = FormatNumbers.formatDuration(secondsUntilMaintenanceDeadline);
                    if (COUNTDOWN_THRESHOLDS.contains(secondsUntilMaintenanceDeadline)) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Die Server Wartung wird in ", NamedTextColor.AQUA))
                                        .append(formatedTime)
                                        .append(Component.text(" automatisch beendet!", NamedTextColor.AQUA)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Server maintenance will automatically end in ", NamedTextColor.AQUA))
                                        .append(formatedTime)
                                        .append(Component.text(" !", NamedTextColor.AQUA)));
                            }
                            Bukkit.getConsoleSender().sendMessage(Statements.getPrefix()
                                    .append(Component.text("Server maintenance will automatically end in ", NamedTextColor.AQUA))
                                    .append(formatedTime)
                                    .append(Component.text(" !", NamedTextColor.AQUA)));
                        }
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