package de.lars.utilsmanager.features.maintenance;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.apimanager.apis.rankAPI.RankAPI;
import de.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import de.lars.utilsmanager.util.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;

import static org.bukkit.Bukkit.getOnlinePlayers;

public class MaintenanceCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

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
                if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) {
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

                    Instant end = Instant.now().plusSeconds(time);

                    ServerSettingsAPI.getApi().enableMaintenance(reason.toString().trim(), null, end, null);

                    Component formattedTime = Statements.formatDuration(time);

                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Wartungs Modus wurde aktiviert.", NamedTextColor.WHITE)));
                        player.sendMessage(Statements.getPrefix().append(Component.text("Die Wartung sollte voraussichtlich ", NamedTextColor.BLUE))
                                .append(formattedTime)
                                .append(Component.text(" beanspruchen,", NamedTextColor.BLUE)));
                        player.sendMessage(Statements.getPrefix().append(Component.text("")));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The current maintenance has now been disabled.", NamedTextColor.WHITE)));
                        player.sendMessage(Statements.getPrefix().append(Component.text("The maintenance is supposed to take ", NamedTextColor.BLUE))
                                .append(formattedTime)
                                .append(Component.text(".", NamedTextColor.BLUE)));
                    }
                    for (Player onlinePlayer : getOnlinePlayers()) {
                        if (onlinePlayer.isOp() || RankAPI.getApi().getRankId(onlinePlayer) > 7) continue;
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
                if (!ServerSettingsAPI.getApi().isMaintenanceEnabled()) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nichts hat sich geändert! Aktuell sind keine Wartungen!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Nothing changed! There is currently no maintenance!", NamedTextColor.RED)));
                    }
                } else {
                    Instant maintenanceEnd = ServerSettingsAPI.getApi().getMaintenanceEnd();
                    Instant now = Instant.now();

                    long maintenanceTime = Duration.between(now, maintenanceEnd).getSeconds();

                    if (maintenanceTime < 0) maintenanceTime *= -1;

                    Component formattedTime = Statements.formatDuration((int) maintenanceTime);
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {

                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Die aktuelle Wartung wurde nun ausgestaltet.", NamedTextColor.WHITE)));
                        if (maintenanceTime > 0) {
                            player.sendMessage(Statements.getPrefix().append(Component.text("Die Wartung sollte voraussichtlich noch ", NamedTextColor.BLUE))
                                    .append(formattedTime)
                                    .append(Component.text(" brauchen.", NamedTextColor.BLUE)));
                        } else {
                            player.sendMessage(Statements.getPrefix().append(Component.text("Die Wartung sollte voraussichtlich schon vor ", NamedTextColor.BLUE))
                                    .append(formattedTime)
                                    .append(Component.text(" zu Ende sein.", NamedTextColor.BLUE)));
                        }
                        player.sendMessage(Statements.getPrefix().append(Component.text("Alle Spieler können wieder beitreten und Discord Status Nachrichten werden gesendet!", NamedTextColor.YELLOW)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("The current maintenance has now been disabled.", NamedTextColor.WHITE)));
                        if (maintenanceTime > 0) {
                            player.sendMessage(Statements.getPrefix().append(Component.text("The maintenance was supposed to take ", NamedTextColor.BLUE))
                                    .append(formattedTime)
                                    .append(Component.text(" more.", NamedTextColor.BLUE)));
                        } else {
                            player.sendMessage(Statements.getPrefix().append(Component.text("The maintenance was supposed to take ", NamedTextColor.BLUE))
                                    .append(formattedTime)
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
