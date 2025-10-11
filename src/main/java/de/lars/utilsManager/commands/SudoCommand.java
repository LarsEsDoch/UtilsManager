package de.lars.utilsManager.commands;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.utils.RankStatements;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SudoCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Component.text("Only player can send Messages.", NamedTextColor.RED));
            return;
        }
        if (!player.hasPermission("plugin.sudo")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if (args.length == 1 || args.length == 0) {
            sendUsage(player);
            return;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(NamedTextColor.RED + "Der Spieler existiert nicht!");
            } else {
                player.sendMessage(NamedTextColor.RED + "The Player dosent exist!");
            }
            return;
        }
        Player sudo = Bukkit.getPlayer(args[0]);
        assert sudo != null;
        if (args[1].equals("message")) {
            StringBuilder sudoMessage = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                sudoMessage.append(args[i]).append(" ");
            }
            for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(RankStatements.getRank(sudo).append(Component.text(">: ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(sudoMessage.toString(), NamedTextColor.WHITE)));
            }
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Der Spieler ", NamedTextColor.WHITE))
                        .append(RankStatements.getRank(sudo))
                        .append(Component.text(" hat die Nachricht ", NamedTextColor.WHITE))
                        .append(Component.text(sudoMessage.toString(), NamedTextColor.DARK_RED))
                        .append(Component.text("geschickt.", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("The player ", NamedTextColor.WHITE))
                        .append(RankStatements.getRank(sudo))
                        .append(Component.text(" send the message ", NamedTextColor.WHITE))
                        .append(Component.text(sudoMessage.toString(), NamedTextColor.DARK_RED))
                        .append(Component.text("", NamedTextColor.WHITE)));
            }
        } else {
            String sudoArgs = "";
            for (int i = 1; i < args.length; i++) {
                sudoArgs += args[i] + " ";
            }
            sudo.performCommand(sudoArgs);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Der Spieler ", NamedTextColor.WHITE))
                        .append(RankStatements.getRank(sudo))
                        .append(Component.text(" hat den Befehl ", NamedTextColor.WHITE))
                        .append(Component.text(sudoArgs, NamedTextColor.DARK_RED))
                        .append(Component.text(" ausgeführt.", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("The player ", NamedTextColor.WHITE))
                        .append(RankStatements.getRank(sudo))
                        .append(Component.text(" executed the command ", NamedTextColor.WHITE))
                        .append(Component.text(sudoArgs, NamedTextColor.DARK_RED))
                        .append(Component.text("", NamedTextColor.WHITE)));
            }
        }
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (!player.hasPermission("plugin.sudo")) return Collections.emptyList();
        if (args.length == 1 || args.length == 0) {
            List<String> names = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                names.add(p.getName());
            }
            return names;
        }
        return Collections.emptyList();
    }

    private void sendUsage(CommandSender sender) {
        if (LanguageAPI.getApi().getLanguage((Player) sender) == 2) {
            sender.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/sudo <Spieler> <Befehl> <Argumente>");
        } else {
            sender.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/sudo <player> <arguments>");
        }
    }
}
