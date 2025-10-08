package de.lars.utilsManager.maintenance;

import de.lars.apiManager.dataAPI.DataAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getOnlinePlayers;

public class MaintenanceCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player player = (Player) stack.getSender();

        if (!player.hasPermission("plugin.maintenance")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        switch (args[0]) {
            case "on": {
                if (DataAPI.getApi().isMaintenanceActive()) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nichts hat sich geändert! Aktuell ist die Wartung schon aktiviert!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nothing changed! Maintenance is currently activated!", NamedTextColor.RED)));
                    }
                } else {
                    if (args.length < 3) {
                        sendUsage(player);
                        return;
                    }
                    try {
                        Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        sendUsage(player);
                        return;
                    }

                    int time = Integer.parseInt(args[1]);
                    if (time <= 0) {
                        sendUsage(player);
                        return;
                    }
                    StringBuilder reason = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        reason.append(args[i]).append(" ");
                    }
                    DataAPI.getApi().activateMaintenance(reason.toString(), time);
                    int seconds = time % 60;
                    int minutes = (time / 60) % 60;
                    int hours = (time / 3600) % 24;
                    int days = time / 86400;

                    ComponentLike formatedTime;
                    if ((time / 86400) != 0) {
                        formatedTime = Component.text(String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, seconds), NamedTextColor.GOLD);
                    } else {
                        if ((time / 3600) == 0) {
                            formatedTime = Component.text(String.format("%02dm %02ds", minutes, seconds), NamedTextColor.GOLD);
                        } else {
                            formatedTime = Component.text(String.format("%02dh %02dm %02ds", hours, minutes, seconds), NamedTextColor.GOLD);
                        }
                    }

                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Wartungs Modus wurde aktiviert.", NamedTextColor.WHITE)));
                        player.sendMessage(Statements.getPrefix().append(Component.text("Die Wartung sollte voraussichtlich ", NamedTextColor.BLUE))
                                .append(formatedTime)
                                .append(Component.text(" beanspruchen,", NamedTextColor.BLUE)));
                        player.sendMessage(Statements.getPrefix().append(Component.text("")));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The current maintenance has now been disabled.", NamedTextColor.WHITE)));
                        player.sendMessage(Statements.getPrefix().append(Component.text("The maintenance is supposed to take ", NamedTextColor.BLUE))
                                .append(formatedTime)
                                .append(Component.text(".", NamedTextColor.BLUE)));
                    }
                    for (Player onlinePlayer : getOnlinePlayers()) {
                        if (onlinePlayer.isOp() || RankAPI.getApi().getRankID(onlinePlayer) > 7) continue;
                        if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                            onlinePlayer.kick(Component.text("Der Server ist im Wartungs Modus! Bitte warten Sie, bis der Modus deaktiviert ist."));
                        } else {
                            onlinePlayer.kick(Component.text("The server is in maintenance mode! Please wait until the mode is disabled."));
                        }
                    }
                }
                break;
            }

            case "off": {
                if (!DataAPI.getApi().isMaintenanceActive()) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nichts hat sich geändert! Aktuell sind keine Wartungen!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nothing changed! There is currently no maintenance!", NamedTextColor.RED)));
                    }
                } else {
                    int maintenanceTime = DataAPI.getApi().getMaintenanceTime();
                    if (DataAPI.getApi().getMaintenanceTime() < 0) {
                        maintenanceTime = DataAPI.getApi().getMaintenanceTime()*-1;
                    }
                    int seconds = maintenanceTime % 60;
                    int minutes = (maintenanceTime / 60) % 60;
                    int hours = (maintenanceTime / 3600) % 24;
                    int days = maintenanceTime / 86400;

                    ComponentLike formatedTime;
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        if ((maintenanceTime / 86400) != 0) {
                            formatedTime = Component.text(String.format("%02d Tage %02d Stunden %02d Minuten %02d Sekunden", days, hours, minutes, seconds), NamedTextColor.GOLD);
                        } else {
                            if ((maintenanceTime / 3600) == 0) {
                                formatedTime = Component.text(String.format("%02d Minuten %02d Sekunden", minutes, seconds), NamedTextColor.GOLD);
                            } else {
                                formatedTime = Component.text(String.format("%02d Stunden %02d Minuten %02d Sekunden", hours, minutes, seconds), NamedTextColor.GOLD);
                            }
                        }
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Die aktuelle Wartung wurde nun ausgestaltet.", NamedTextColor.WHITE)));
                        if (DataAPI.getApi().getMaintenanceTime() > 0) {
                            player.sendMessage(Statements.getPrefix().append(Component.text("Die Wartung sollte voraussichtlich noch ", NamedTextColor.BLUE))
                                    .append(formatedTime)
                                    .append(Component.text(" brauchen.", NamedTextColor.BLUE)));
                        } else {
                            player.sendMessage(Statements.getPrefix().append(Component.text("Die Wartung sollte voraussichtlich schon vor ", NamedTextColor.BLUE))
                                    .append(formatedTime)
                                    .append(Component.text(" zu Ende sein.", NamedTextColor.BLUE)));
                        }
                        player.sendMessage(Statements.getPrefix().append(Component.text("Alle Spieler können wieder beitreten und Discord Status Nachrichten werden gesendet!", NamedTextColor.YELLOW)));
                    } else {
                        if ((maintenanceTime / 86400) != 0) {
                            formatedTime = Component.text(String.format("%02d days %02d hours %02d minutes %02d seconds", days, hours, minutes, seconds), NamedTextColor.GOLD);
                        } else {
                            if ((maintenanceTime / 3600) == 0) {
                                formatedTime = Component.text(String.format("%02d minutes %02d seconds", minutes, seconds), NamedTextColor.GOLD);
                            } else {
                                formatedTime = Component.text(String.format("%02d hours %02d minutes %02d seconds", hours, minutes, seconds), NamedTextColor.GOLD);
                            }
                        }
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The current maintenance has now been disabled.", NamedTextColor.WHITE)));
                        if (DataAPI.getApi().getMaintenanceTime() > 0) {
                            player.sendMessage(Statements.getPrefix().append(Component.text("The maintenance was supposed to take ", NamedTextColor.BLUE))
                                    .append(formatedTime)
                                    .append(Component.text(" more.", NamedTextColor.BLUE)));
                        } else {
                            player.sendMessage(Statements.getPrefix().append(Component.text("The maintenance was supposed to take ", NamedTextColor.BLUE))
                                    .append(formatedTime)
                                    .append(Component.text(" less.", NamedTextColor.BLUE)));
                        }
                        player.sendMessage(Statements.getPrefix().append(Component.text("All players can join again and Discord Status Messages will be send!", NamedTextColor.YELLOW)));
                    }
                }
                break;
            }

            case "time": {

            }

            case "reason": {

            }
        }
    }

    private void sendUsage(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(Component.text("Verwendung").color(NamedTextColor.GRAY)
                    .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                    .append(Component.text("/maintenance ").color(NamedTextColor.BLUE))
                    .append(Component.text("on <Zeit> <Grund>/off/time (<Zeitänderung>)/reason (<Grundänderung>)", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Use").color(NamedTextColor.GRAY)
                    .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                    .append(Component.text("/maintenance ").color(NamedTextColor.BLUE))
                    .append(Component.text("on <time> <reason>/off/time (<Time change>)/reason (<Reason change>)", NamedTextColor.BLUE)));
        }
    }
}
