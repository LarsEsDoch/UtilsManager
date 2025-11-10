package de.lars.utilsmanager.commands.admin;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AnnounceCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (!(player.hasPermission("plugin.announce"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        int delay = 0;
        int repeat = 1;

        try {
            if (args.length > 1) {
                repeat = Integer.parseInt(args[args.length - 1]);

                if (args.length > 2) {
                    delay = Integer.parseInt(args[args.length - 2]);
                }
            }
        } catch (NumberFormatException ignored) {

        }

        int messageEndIndex = args.length;
        if (delay > 0) messageEndIndex--;
        if (repeat > 1) messageEndIndex--;

        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 0; i < messageEndIndex; i++) {
            messageBuilder.append(args[i]).append(" ");
        }
        String message = ChatColor.translateAlternateColorCodes('&', messageBuilder.toString().trim());

        if (delay == 0 & repeat == 1) {

            for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(Statements.getPrefix()
                        .append(Component.text("Server", NamedTextColor.GOLD))
                        .append(Component.text(">: ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(message, NamedTextColor.WHITE)));
            }
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Es wurde deine Ankündigung erfolgreich jedem übermittelt!", NamedTextColor.GREEN)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("Your announcement has been successfully sent to everyone!", NamedTextColor.GREEN)));
            }
        } else if (delay != 0 & repeat == 1) {

            Bukkit.getScheduler().runTaskLaterAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
                for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage(Statements.getPrefix()
                            .append(Component.text("Server", NamedTextColor.GOLD))
                            .append(Component.text(">: ", NamedTextColor.DARK_GRAY))
                            .append(Component.text(message, NamedTextColor.WHITE)));
                }
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Es wurde deine Ankündigung erfolgreich jedem übermittelt!", NamedTextColor.GREEN)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Your announcement has been successfully sent to everyone!", NamedTextColor.GREEN)));
                }
            }, delay * 20L);

            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix()
                        .append(Component.text("Deine Ankündigung wird in ", NamedTextColor.WHITE))
                        .append(Component.text(delay, NamedTextColor.AQUA))
                        .append(Component.text(" Sekunden übermittelt.", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix()
                        .append(Component.text("Your announcement will be will be transmitted in ", NamedTextColor.WHITE))
                        .append(Component.text(delay, NamedTextColor.AQUA))
                        .append(Component.text(" seconds.", NamedTextColor.WHITE)));
            }

        } else {
            int[] timesDid = {0};

            int finalRepeat = repeat;
            Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
                for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage(Statements.getPrefix()
                            .append(Component.text("Server", NamedTextColor.GOLD))
                            .append(Component.text(">: ", NamedTextColor.DARK_GRAY))
                            .append(Component.text(message, NamedTextColor.WHITE)));
                }
                timesDid[0]++;
                if (timesDid[0] == finalRepeat) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Es wurde deine Ankündigung erfolgreich jedem mehrmals übermittelt!", NamedTextColor.GREEN)));
                    } else {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Your announcement has been successfully sent multiple times to everyone!", NamedTextColor.GREEN)));
                    }
                    bukkitTask.cancel();
                }
            }, delay * 20L, delay * 20L);

            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Deine Ankündigung wird ", NamedTextColor.WHITE))
                        .append(Component.text(repeat, NamedTextColor.DARK_AQUA))
                        .append(Component.text(" Mal jede ", NamedTextColor.WHITE))
                        .append(Component.text(delay, NamedTextColor.AQUA))
                        .append(Component.text(" Sekunden übermittelt.", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("Your announcement will be will be transmitted ", NamedTextColor.WHITE))
                        .append(Component.text(repeat, NamedTextColor.DARK_AQUA))
                        .append(Component.text(" times every ", NamedTextColor.WHITE))
                        .append(Component.text(delay, NamedTextColor.AQUA))
                        .append(Component.text(" seconds.", NamedTextColor.WHITE)));
            }
        }
    }

    private void sendUsage(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Component.text()
                    .append(Component.text("Verwendung").color(NamedTextColor.GRAY))
                    .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                    .append(Component.text("/announce ").color(NamedTextColor.BLUE))
                    .append(Component.text("<Ankündigung>").color(NamedTextColor.BLUE))
                    .append(Component.text(" (<delay> <periods>)").color(NamedTextColor.BLUE)));
        } else {
            player.sendMessage(Component.text()
                    .append(Component.text("Use").color(NamedTextColor.GRAY))
                    .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                    .append(Component.text("/announce ").color(NamedTextColor.BLUE))
                    .append(Component.text("<announcement>").color(NamedTextColor.BLUE))
                    .append(Component.text(" (<delay> <repeats>)").color(NamedTextColor.BLUE)));
        }
    }
}
