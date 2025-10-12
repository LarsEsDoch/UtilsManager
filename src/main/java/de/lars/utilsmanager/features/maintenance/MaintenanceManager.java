package de.lars.utilsmanager.features.maintenance;

import de.lars.apiManager.Main;
import de.lars.apiManager.dataAPI.DataAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsmanager.util.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.Month;

public class MaintenanceManager {

    public void maintenanceManager() {
        run();
    }

    private void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), bukkitTask -> {
            if (DataAPI.getApi().isMaintenanceActive()) {
                int remainingTime = DataAPI.getApi().getMaintenanceTime();

                int seconds = remainingTime % 60;
                int minutes = (remainingTime / 60) % 60;
                int hours = (remainingTime / 3600) % 24;
                int days = remainingTime / 86400;
                Component formatedTime = Component.text(String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, seconds), NamedTextColor.GOLD);
                Component formatedTimeNoDay = Component.text(String.format("%02dh %02dm %02ds", hours, minutes, seconds), NamedTextColor.GOLD);
                Component formatedTimeNoHour = Component.text(String.format("%02dm %02ds", minutes, seconds), NamedTextColor.GOLD);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.isOp() || !player.hasPermission("plugin.maintenance")) {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            if ((remainingTime / 86400) != 0) {
                                Bukkit.getScheduler().runTaskLater(Main.getInstance(), bukkitTask1 -> {
                                    player.kick(Component.text("Wir entschuldigen uns, aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                            .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                            .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                            .append(Component.text("Voraussichtlich Verbliebende Zeit: ", NamedTextColor.WHITE))
                                            .append(formatedTime));
                                }, 1);
                            } else {
                                if ((remainingTime / 3600) == 0) {
                                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), bukkitTask1 -> {
                                        player.kick(Component.text("Wir entschuldigen uns, aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                                .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                                .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                                .append(Component.text("Voraussichtlich Verbliebende Zeit: ", NamedTextColor.WHITE))
                                                .append(formatedTimeNoHour));
                                    }, 1);
                                } else {
                                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), bukkitTask1 -> {
                                        player.kick(Component.text("Wir entschuldigen uns, aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                                .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                                .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                                .append(Component.text("Voraussichtlich Verbliebende Zeit: ", NamedTextColor.WHITE))
                                                .append(formatedTimeNoDay));
                                    }, 1);
                                }
                            }
                        } else {
                            if ((remainingTime / 86400) != 0) {
                                Bukkit.getScheduler().runTaskLater(Main.getInstance(), bukkitTask1 -> {
                                    player.kick(Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                            .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                            .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                            .append(Component.text("Estimated Remaining Time: ", NamedTextColor.WHITE))
                                            .append(formatedTime));
                                }, 1);
                            } else {
                                if ((remainingTime / 3600) == 0) {
                                    player.kick(Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                            .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                            .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                            .append(Component.text("Estimated Remaining Time ", NamedTextColor.WHITE))
                                            .append(formatedTimeNoHour));
                                } else {
                                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), bukkitTask1 -> {
                                        player.kick(Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                                .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                                .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                                .append(Component.text("Estimated Remaining Time: ", NamedTextColor.WHITE))
                                                .append(formatedTimeNoDay));
                                    }, 1);
                                }
                            }
                        }
                    } else {
                        switch (remainingTime) {
                            case 9000, 7200, 5400 ,3600, 1800, 1200, 600, 120, 90, 60: {
                                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                    player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("Die Server Wartung sollte voraussichtlich in ", NamedTextColor.AQUA))
                                            .append(Component.text(remainingTime / 60, NamedTextColor.GOLD))
                                            .append(Component.text(" Minuten zu ende sein!", NamedTextColor.AQUA)));
                                } else {
                                    player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("Server maintenance is expected to be completed in ", NamedTextColor.AQUA))
                                            .append(Component.text(remainingTime / 60, NamedTextColor.GOLD))
                                            .append(Component.text(" minutes!", NamedTextColor.AQUA)));
                                }
                                Bukkit.getConsoleSender().sendMessage(Statements.getPrefix()
                                        .append(Component.text("Server maintenance is expected to be completed in ", NamedTextColor.AQUA))
                                        .append(Component.text(remainingTime / 60, NamedTextColor.GOLD))
                                        .append(Component.text(" minutes!", NamedTextColor.AQUA)));
                                break;
                            }
                            case 30, 10, 5: {
                                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                    player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("Die Server Wartung sollte voraussichtlich in ", NamedTextColor.AQUA))
                                            .append(Component.text(remainingTime, NamedTextColor.GOLD))
                                            .append(Component.text(" Sekunden zu ende sein!", NamedTextColor.AQUA)));
                                } else {
                                    player.sendMessage(Statements.getPrefix()
                                            .append(Component.text("Server maintenance is expected to be completed in ", NamedTextColor.AQUA))
                                            .append(Component.text(remainingTime, NamedTextColor.GOLD))
                                            .append(Component.text(" seconds!", NamedTextColor.AQUA)));
                                }
                                Bukkit.getConsoleSender().sendMessage(Statements.getPrefix()
                                        .append(Component.text("Server maintenance is expected to be completed in ", NamedTextColor.AQUA))
                                        .append(Component.text(remainingTime, NamedTextColor.GOLD))
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
                                            .append(Component.text(remainingTime/-60 + " Minuten", NamedTextColor.DARK_RED))
                                            .append(Component.text("zu Ende sein!", NamedTextColor.DARK_PURPLE)));
                                    player.sendMessage(Statements.getPrefix().append(Component.text("Bitte beenden sie die Wartung sobald wie möglich! ", NamedTextColor.GRAY))
                                            .append(stopMaintenanceGermanCommand));

                                } else {
                                    Component stopMaintenanceEnglischCommand = Component.text("[Turn off now]", NamedTextColor.DARK_RED)
                                            .clickEvent(ClickEvent.runCommand("/maintenance off"))
                                            .hoverEvent(HoverEvent.showText(Component.text("All players can join again and Discord Status Messages will be send!", NamedTextColor.YELLOW)));

                                    player.sendMessage(Statements.getPrefix().append(Component.text("The server maintenance should be since ", NamedTextColor.DARK_PURPLE))
                                            .append(Component.text(remainingTime/-60 + " minutes", NamedTextColor.DARK_RED))
                                            .append(Component.text(" over according to the estimated time!", NamedTextColor.DARK_PURPLE)));
                                    player.sendMessage(Statements.getPrefix().append(Component.text("Please end the maintenance as soon as possible! ", NamedTextColor.GRAY))
                                            .append(stopMaintenanceEnglischCommand));

                                }
                                Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("The server maintenance should be since ", NamedTextColor.DARK_PURPLE))
                                        .append(Component.text(remainingTime/-60 + " minutes", NamedTextColor.DARK_RED))
                                        .append(Component.text(" over according to the estimated time!", NamedTextColor.DARK_PURPLE)));
                                break;
                            }

                            default:
                                break;
                        }
                    }
                }
                DataAPI.getApi().setMaintenanceTime(remainingTime - 1);
                if (DataAPI.getApi().isMaintenanceActive() && remainingTime <= 0) {
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime target = LocalDateTime.of(2025, Month.APRIL, 16, 15, 0);
                    if (now.isBefore(target)) {
                        DataAPI.getApi().deactivateMaintenance();
                    }
                }
            }
        }, 20, 20);
    }
}
