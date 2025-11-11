package dev.lars.utilsmanager.features.maintenance;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.rankAPI.RankAPI;
import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.time.Instant;

public class MaintenanceListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("utilsmanager.maintenance")) return;
        if (player.isOp()) return;
        if (RankAPI.getApi().getRankId(player) >= 8) return;

        if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) {

            Instant maintenanceEnd = ServerSettingsAPI.getApi().getMaintenanceEstimatedEnd();

            if (maintenanceEnd == null) {
                Component noEnd = Component.text("There is no ending available");
            }
            long now = System.currentTimeMillis();

            long maintenanceTime = (maintenanceEnd.compareTo(Instant.now())) / 1000;

            if (maintenanceTime < 0) maintenanceTime *= -1;

            int seconds = (int) (maintenanceTime % 60);
            int minutes = (int) ((maintenanceTime / 60) % 60);
            int hours = (int) ((maintenanceTime / 3600) % 24);
            int days = (int) (maintenanceTime / 86400);

            Component formattedTime = Component.text(
                String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, seconds),
                NamedTextColor.GOLD
            );
            Component formattedTimeNoDay = Component.text(
                String.format("%02dh %02dm %02ds", hours, minutes, seconds),
                NamedTextColor.GOLD
            );
            Component formattedTimeNoHour = Component.text(
                String.format("%02dm %02ds", minutes, seconds),
                NamedTextColor.GOLD
            );

            if (maintenanceTime > 0) {
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    if ((maintenanceTime / 86400) != 0) {
                        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Wir entschuldigen uns aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                .append(Component.text("Voraussichtlich Verbliebende Zeit: ", NamedTextColor.WHITE))
                                .append(formattedTime));
                    } else {
                        if ((maintenanceTime / 3600) == 0) {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Wir entschuldigen uns aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                    .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                    .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Voraussichtlich Verbliebende Zeit: ", NamedTextColor.WHITE))
                                    .append(formattedTimeNoHour));
                        } else {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Wir entschuldigen uns aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                    .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                    .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Voraussichtlich Verbliebende Zeit: ", NamedTextColor.WHITE))
                                    .append(formattedTimeNoDay));
                        }
                    }
                } else {
                    if ((maintenanceTime / 86400) != 0) {
                        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                .append(Component.text("Estimated Remaining Time: ", NamedTextColor.WHITE))
                                .append(formattedTime));
                    } else {
                        if ((maintenanceTime / 3600) == 0) {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                    .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                    .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Estimated Remaining Time: ", NamedTextColor.WHITE))
                                    .append(formattedTimeNoHour));
                        } else {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                    .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                    .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Estimated Remaining Time: ", NamedTextColor.WHITE))
                                    .append(formattedTimeNoDay));
                        }
                    }
                }
            } else {
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    if ((maintenanceTime / 86400) != 0) {
                        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Wir entschuldigen uns aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                .append(Component.text("Überschrittene Voraussichtliche Zeit: ", NamedTextColor.WHITE))
                                .append(formattedTime));
                    } else {
                        if ((maintenanceTime / 3600) == 0) {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Wir entschuldigen uns aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                    .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                    .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Überschrittene Voraussichtliche Zeit: ", NamedTextColor.WHITE))
                                    .append(formattedTimeNoHour));
                        } else {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Wir entschuldigen uns aber der Server ist derzeit in Wartung! \n", NamedTextColor.RED)
                                    .append(Component.text("Grund: ", NamedTextColor.WHITE))
                                    .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Überschrittene Voraussichtliche Zeit: ", NamedTextColor.WHITE))
                                    .append(formattedTimeNoDay));
                        }
                    }
                } else {
                    if ((maintenanceTime / 86400) != 0) {
                        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                .append(Component.text("Exceeded Estimated Time: ", NamedTextColor.WHITE))
                                .append(formattedTime));
                    } else {
                        if ((maintenanceTime / 3600) == 0) {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                    .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                    .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Exceeded Estimated Time: ", NamedTextColor.WHITE))
                                    .append(formattedTimeNoHour));
                        } else {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("We apologize but the server is currently under maintenance! \n", NamedTextColor.RED)
                                    .append(Component.text("Reason: ", NamedTextColor.WHITE))
                                    .append(Component.text(ServerSettingsAPI.getApi().getMaintenanceReason() + "\n", NamedTextColor.BLUE))
                                    .append(Component.text("Exceeded Estimated Time: ", NamedTextColor.WHITE))
                                    .append(formattedTimeNoDay));
                        }
                    }
                }
            }
        }
    }

}
