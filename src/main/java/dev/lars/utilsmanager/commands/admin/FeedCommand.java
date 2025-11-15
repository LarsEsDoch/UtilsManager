package dev.lars.utilsmanager.commands.admin;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.utils.CheckPlayers;
import dev.lars.utilsmanager.utils.RankStatements;
import dev.lars.utilsmanager.utils.Statements;
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

public class FeedCommand implements BasicCommand {

    Player sendplayer;
    Player player;
    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        sendplayer = (Player) stack.getSender();
        if (!sendplayer.hasPermission("utilsmanager.feed")) {
            sendplayer.sendMessage(Statements.getNotAllowed(sendplayer));
            return;
        }

        if (args.length == 0) {
            sendUsage(sendplayer);
            return;
        }

        player = Bukkit.getPlayer(args[0]);
        if (CheckPlayers.checkPlayer(sendplayer, player)) return;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest gefüttert!", NamedTextColor.GREEN)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You were feed!", NamedTextColor.GREEN)));
        }
        player.setFoodLevel(20);
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sendplayer.sendMessage(Statements.getPrefix().append(Component.text("Du hast den Spieler ", NamedTextColor.GREEN).append(RankStatements.getRank(player)).append(Component.text(player.getName(), NamedTextColor.GREEN)))
                    .append(Component.text(" gefüttert!", NamedTextColor.GREEN)));
        } else {
            sendplayer.sendMessage(Statements.getPrefix().append(Component.text("You feed the player ", NamedTextColor.GREEN).append(RankStatements.getRank(player)).append(Component.text(player.getName(), NamedTextColor.GREEN)))
                    .append(Component.text("!", NamedTextColor.GREEN)));
        }
    }

    @Override
    public @NotNull Collection<String> suggest(final CommandSourceStack commandSourceStack, final String @NotNull [] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (!player.hasPermission("utilsmanager.feed")) return Collections.emptyList();
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
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sender.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/invsee <Spieler>");
        } else {
            sender.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/invsee <player>");
        }
    }
}
