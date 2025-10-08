package de.lars.utilsManager.maintenance;

import de.lars.apiManager.dataAPI.DataAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class MaintenanceListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("plugin.maintenance")) return;
        if (player.isOp()) return;
        if (RankAPI.getApi().getRankID(player) >= 8) return;

        if (DataAPI.getApi().isMaintenanceActive()) {

            int maintenanceTime = DataAPI.getApi().getMaintenanceTime();
            if (DataAPI.getApi().getMaintenanceTime() < 0) {
                maintenanceTime = DataAPI.getApi().getMaintenanceTime()*-1;
            }
            int seconds = maintenanceTime % 60;
            int minutes = (maintenanceTime / 60) % 60;
            int hours = (maintenanceTime / 3600) % 24;
            int days = maintenanceTime / 86400;
            Component formatedTime = Component.text(String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, seconds), NamedTextColor.GOLD);
            Component formatedTimeNoDay = Component.text(String.format("%02dh %02dm %02ds", hours, minutes, seconds), NamedTextColor.GOLD);
            Component formatedTimeNoHour = Component.text(String.format("%02dm %02ds", minutes, seconds), NamedTextColor.GOLD);

            if (DataAPI.getApi().getMaintenanceTime() > 0) {
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    if ((maintenanceTime / 86400) != 0) {
                        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Wir entschuldigen uns aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                .append(Component.text("Voraussichtlich Verbliebende Zeit: ", NamedTextColor.WHITE))
                                .append(formatedTime));
                    } else {
                        if ((maintenanceTime / 3600) == 0) {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Wir entschuldigen uns aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                    .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                    .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Voraussichtlich Verbliebende Zeit: ", NamedTextColor.WHITE))
                                    .append(formatedTimeNoHour));
                        } else {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Wir entschuldigen uns aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                    .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                    .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Voraussichtlich Verbliebende Zeit: ", NamedTextColor.WHITE))
                                    .append(formatedTimeNoDay));
                        }
                    }
                } else {
                    if ((maintenanceTime / 86400) != 0) {
                        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                .append(Component.text("Estimated Remaining Time: ", NamedTextColor.WHITE))
                                .append(formatedTime));
                    } else {
                        if ((maintenanceTime / 3600) == 0) {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                    .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                    .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Estimated Remaining Time: ", NamedTextColor.WHITE))
                                    .append(formatedTimeNoHour));
                        } else {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                    .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                    .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Estimated Remaining Time: ", NamedTextColor.WHITE))
                                    .append(formatedTimeNoDay));
                        }
                    }
                }
            } else {
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    if ((maintenanceTime / 86400) != 0) {
                        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Wir entschuldigen uns aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                .append(Component.text("Überschrittene Voraussichtliche Zeit: ", NamedTextColor.WHITE))
                                .append(formatedTime));
                    } else {
                        if ((maintenanceTime / 3600) == 0) {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Wir entschuldigen uns aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                    .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                    .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Überschrittene Voraussichtliche Zeit: ", NamedTextColor.WHITE))
                                    .append(formatedTimeNoHour));
                        } else {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Wir entschuldigen uns aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                    .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                    .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Überschrittene Voraussichtliche Zeit: ", NamedTextColor.WHITE))
                                    .append(formatedTimeNoDay));
                        }
                    }
                } else {
                    if ((maintenanceTime / 86400) != 0) {
                        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                .append(Component.text("Exceeded Estimated Time: ", NamedTextColor.WHITE))
                                .append(formatedTime));
                    } else {
                        if ((maintenanceTime / 3600) == 0) {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                    .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                    .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Exceeded Estimated Time: ", NamedTextColor.WHITE))
                                    .append(formatedTimeNoHour));
                        } else {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                    .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                    .append(Component.text(DataAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Exceeded Estimated Time: ", NamedTextColor.WHITE))
                                    .append(formatedTimeNoDay));
                        }
                    }
                }
            }
        }
    }

}
