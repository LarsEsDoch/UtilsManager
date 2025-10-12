package de.lars.utilsmanager.commands.admin;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsmanager.util.RankStatements;
import de.lars.utilsmanager.util.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FeedCommand implements BasicCommand {

    Player sendplayer;
    Player player;
    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        sendplayer = (Player) stack.getSender();
        if (!sendplayer.hasPermission("plugin.heal")) {
            sendplayer.sendMessage(Statements.getNotAllowed(sendplayer));
            return;
        }

        if (args.length == 0) {
            sendUsage(sendplayer);
            return;
        }

        player = Bukkit.getPlayer(args[0]);
        if (!Bukkit.getOnlinePlayers().contains(player) || player == null) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                sendplayer.sendMessage(Component.text("Der Spieler existiert nicht!", NamedTextColor.RED));
            } else {
                sendplayer.sendMessage(Component.text("The Player dosent exist!", NamedTextColor.RED));
            }
            return;
        }
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest gefüttert!", NamedTextColor.GREEN)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You were feed!", NamedTextColor.GREEN)));
        }
        player.setFoodLevel(20);
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sendplayer.sendMessage(Statements.getPrefix().append(Component.text("Du hast den Spieler " + RankStatements.getRank(player) + player.getName(), NamedTextColor.GREEN))
                    .append(Component.text(" gefüttert!", NamedTextColor.GREEN)));
        } else {
            sendplayer.sendMessage(Statements.getPrefix().append(Component.text("You feed the player " + RankStatements.getRank(player) + player.getName(), NamedTextColor.GREEN))
                    .append(Component.text("!", NamedTextColor.GREEN)));
        }
    }

    private void sendUsage(CommandSender sender) {
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sender.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/invsee <Spieler>");
        } else {
            sender.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/invsee <player>");
        }
    }
}
