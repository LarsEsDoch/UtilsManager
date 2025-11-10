package de.lars.utilsmanager.features.rank;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.prefixAPI.PrefixAPI;
import dev.lars.apimanager.apis.rankAPI.RankAPI;
import de.lars.utilsmanager.utils.RankStatements;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RankCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player sendplayer = (Player) stack.getSender();
        if (!(sendplayer.hasPermission("plugin.rank"))) {
            sendplayer.sendMessage(Statements.getNotAllowed(sendplayer));
            return;
        }
        if (args.length == 0 || args.length == 1 || args.length == 2) {
            sendUsage(sendplayer);
            return;
        }
        for (String ignored : args) {
            try {
                Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                if (args[2].contains("permanent") || args[2].contains("*") || args[2].contains("all")) {
                    sendUsage(sendplayer);
                    return;
                }
            }
        }
        int time;
        if (args[2].contains("permanent") || args[2].contains("*") || args[2].contains("all")) {
            time = 365000;
        } else {
            try {
                time = Integer.parseInt(args[2]);
            } catch (Exception e) {
                if (LanguageAPI.getApi().getLanguage(sendplayer) == 1) {
                    sendplayer.sendMessage(Statements.getPrefix().append(Component.text("Die Zeitspanne ist zu groß! (Max: 2147483647)", NamedTextColor.RED)));
                } else {
                    sendplayer.sendMessage(Statements.getPrefix().append(Component.text("The timespan is to long! (Max: 2147483647)", NamedTextColor.RED)));
                }
                return;
            }
            if (time <= 0) {
                sendUsage(sendplayer);
                return ;
            }
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                sendplayer.sendMessage(Component.text("Der Spieler existiert nicht!", NamedTextColor.RED));
            } else {
                sendplayer.sendMessage(Component.text("The Player dosen't exist!", NamedTextColor.RED));
            }
            return ;
        }
        int rankID = 0;
        switch (args[1].toLowerCase()) {
            case "player": {
                rankID = 1;
                PrefixAPI.getApi().setColor(player, NamedTextColor.GRAY);
                break;
            }
            case "premium": {
                rankID = 2;
                PrefixAPI.getApi().setColor(player, NamedTextColor.GREEN);
                break;
            }
            case "supreme": {
                rankID = 3;
                PrefixAPI.getApi().setColor(player, NamedTextColor.AQUA);
                break;
            }
            case "titan": {
                rankID = 4;
                PrefixAPI.getApi().setColor(player, NamedTextColor.BLUE);
                break;
            }
            case "matrix": {
                rankID = 5;
                PrefixAPI.getApi().setColor(player, NamedTextColor.GOLD);
                break;
            }
            case "builder": {
                rankID = 6;
                PrefixAPI.getApi().setColor(player, NamedTextColor.YELLOW);
                break;
            }
            case "developer": {
                rankID = 7;
                PrefixAPI.getApi().setColor(player, NamedTextColor.GOLD);
                break;
            }
            case "team": {
                rankID = 8;
                PrefixAPI.getApi().setColor(player, NamedTextColor.DARK_BLUE);
                break;
            }
            case "admin": {
                rankID = 9;
                PrefixAPI.getApi().setColor(player, NamedTextColor.RED);
                break;
            }
            case "owner": {
                rankID = 10;
                PrefixAPI.getApi().setColor(player, NamedTextColor.DARK_RED);
                break;
            }
            default:
                sendUsage(sendplayer);
                break;
        }

        RankAPI.getApi().setRank(player, rankID, time);
        if (rankID >= 5) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast den Rang von ", NamedTextColor.WHITE))
                        .append(Component.text(player.getName(), NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text(" auf ", NamedTextColor.WHITE))
                        .append(RankStatements.getCleanRank(player))
                        .append(Component.text(" für ", NamedTextColor.WHITE))
                        .append(Component.text(time, NamedTextColor.GREEN))
                        .append(Component.text(" Tage gesetzt.", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You´ve set the rank of ", NamedTextColor.WHITE))
                        .append(Component.text(player.getName(), NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text(" to ", NamedTextColor.WHITE))
                        .append(RankStatements.getCleanRank(player))
                        .append(Component.text(" for ", NamedTextColor.WHITE))
                        .append(Component.text(time, NamedTextColor.GREEN))
                        .append(Component.text(" days.", NamedTextColor.WHITE)));
            }
        } else {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast den Rang von ", NamedTextColor.WHITE))
                        .append(Component.text(player.getName(), NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text(" auf ", NamedTextColor.WHITE))
                        .append(RankStatements.getCleanRank(player))
                        .append(Component.text(".", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You´ve set the rank of ", NamedTextColor.WHITE))
                        .append(Component.text(player.getName(), NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text(" to ", NamedTextColor.WHITE))
                        .append(RankStatements.getCleanRank(player))
                        .append(Component.text(".", NamedTextColor.WHITE)));
            }
        }
        player.kick(Component.text("Please rejoin! /n You're rank has changed.", NamedTextColor.GOLD));
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        if (args.length == 1 || args.length == 0) {
            List<String> names = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                names.add(p.getName());
            }

            return names;
        } else if (args.length == 2 || args.length == 3) {
            List<String> rankCommands = new ArrayList<>();
            rankCommands.add("player");
            rankCommands.add("premium");
            rankCommands.add("supreme");
            rankCommands.add("titan");
            rankCommands.add("matrix");
            rankCommands.add("builder");
            rankCommands.add("developer");
            rankCommands.add("team");
            rankCommands.add("admin");
            rankCommands.add("owner");

            return rankCommands;
        }
        return Collections.emptyList();
    }

    private void sendUsage(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/setrank <Spieler> <Rang> <Zeit>");
        } else {
            player.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/setrank <player> <rank> <time>");
        }
    }
}
