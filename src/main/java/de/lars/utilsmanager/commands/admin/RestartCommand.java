package de.lars.utilsmanager.commands.admin;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RestartCommand implements BasicCommand {

    Integer delay;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (!player.hasPermission("plugin.restart")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        try {
            delay = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sendUsage(player);
            return;
        }
        
        if (delay < 0) {
            sendUsage(player);
            return;
        }

        if (delay > 120) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Component.text("Du kannst diese Nummer nicht benutzten! Max allowed is 120!", NamedTextColor.RED));
            } else {
                player.sendMessage(Component.text("Cant use this number! Der maximal zulässige Wert beträgt 120!", NamedTextColor.RED));
            }
            return;
        }

        if (!(delay == 1 || delay == 2 || delay == 3 || delay == 4 || delay == 5 || delay == 10 || delay == 30 || delay == 60 || delay == 90 || delay == 120)) {
            for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    onlinePlayer.sendMessage(Statements.getPrefix()
                            .append(Component.text("Der Server wird in ", NamedTextColor.GOLD))
                            .append(Component.text(delay, NamedTextColor.DARK_RED))
                            .append(Component.text(" Sekunden neu gestartet!", NamedTextColor.GOLD)));
                } else {
                    onlinePlayer.sendMessage(Statements.getPrefix()
                            .append(Component.text("Server will be restarting in ", NamedTextColor.GOLD))
                            .append(Component.text(delay, NamedTextColor.DARK_RED))
                            .append(Component.text(" seconds!", NamedTextColor.GOLD)));
                }
            }
            Bukkit.getConsoleSender().sendMessage(Statements.getPrefix()
                    .append(Component.text("Server will be restarting in ", NamedTextColor.GOLD))
                    .append(Component.text(delay, NamedTextColor.DARK_RED))
                    .append(Component.text(" seconds!", NamedTextColor.GOLD)));
        }

        Bukkit.getScheduler().runTaskTimer(UtilsManager.getInstance(), bukkitTask -> {
            if (delay == 120 || delay == 90 || delay == 60 || delay == 30 || delay == 10 || delay == 5 || delay == 4 || delay == 3 || delay == 2 || delay == 1) {
                for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        onlinePlayer.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Server wird in ", NamedTextColor.GOLD))
                                .append(Component.text(delay, NamedTextColor.DARK_RED))
                                .append(Component.text(" Sekunden neu gestartet!", NamedTextColor.GOLD)));
                    } else {
                        onlinePlayer.sendMessage(Statements.getPrefix()
                                .append(Component.text("Server will be restarting in ", NamedTextColor.GOLD))
                                .append(Component.text(delay, NamedTextColor.DARK_RED))
                                .append(Component.text(" seconds!", NamedTextColor.GOLD)));
                    }
                }
                Bukkit.getConsoleSender().sendMessage(Statements.getPrefix()
                        .append(Component.text("Server will be restarting in ", NamedTextColor.GOLD))
                        .append(Component.text(delay, NamedTextColor.DARK_RED))
                        .append(Component.text(" seconds!", NamedTextColor.GOLD)));
            }
            if (delay == 0) {
                for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        onlinePlayer.sendMessage(Statements.getPrefix().append(Component.text("Server wird neu gestartet...", NamedTextColor.GOLD)));
                    } else {
                        onlinePlayer.sendMessage(Statements.getPrefix().append(Component.text("Server is restarting...", NamedTextColor.GOLD)));
                    }
                    Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("Server is restarting...", NamedTextColor.GOLD)));
                    if (!(onlinePlayer == player)) {
                        Component kickMessage;
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            kickMessage = Component.text("Server wird neu gestartet...", NamedTextColor.GOLD);
                        } else {
                            kickMessage = Component.text("Server is restarting...", NamedTextColor.GOLD);
                        }
                        onlinePlayer.kick(kickMessage);
                    }
                }
                Bukkit.restart();
            }
            delay--;
        }, 20, 20);
    }

    private void sendUsage(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/serverrestart <Verzögerung>", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Use", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/serverrestart <delay>", NamedTextColor.BLUE)));
        }
    }
}