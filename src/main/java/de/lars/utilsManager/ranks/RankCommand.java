package de.lars.utilsManager.ranks;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.utils.RankStatements;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

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
            time = Integer.parseInt(args[2]);
            if (time <= 0) {
                sendUsage(sendplayer);
                return ;
            }
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (!Bukkit.getOnlinePlayers().contains(player) || player == null || !RankAPI.getApi().doesUserExist(player)) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                player.sendMessage(Component.text("Der Spieler existiert nicht!", NamedTextColor.RED));
            } else {
                player.sendMessage(Component.text("The Player dosen't exist!", NamedTextColor.RED));
            }
            return ;
        }
        int rankID = 0;
        switch (args[1].toLowerCase()) {
            case "player": {
                rankID = 1;
                RankAPI.getApi().setPrefix(player, 7);
                break;
            }
            case "premium": {
                rankID = 2;
                RankAPI.getApi().setPrefix(player, 10);
                break;
            }
            case "supreme": {
                rankID = 3;
                RankAPI.getApi().setPrefix(player, 11);
                break;
            }
            case "titan": {
                rankID = 4;
                RankAPI.getApi().setPrefix(player, 9);
                break;
            }
            case "matrix": {
                rankID = 5;
                RankAPI.getApi().setPrefix(player, 6);
                break;
            }
            case "builder": {
                rankID = 6;
                RankAPI.getApi().setPrefix(player, 14);
                break;
            }
            case "developer": {
                rankID = 7;
                RankAPI.getApi().setPrefix(player, 5);
                break;
            }
            case "team": {
                rankID = 8;
                RankAPI.getApi().setPrefix(player, 1);
                break;
            }
            case "admin": {
                rankID = 9;
                RankAPI.getApi().setPrefix(player, 12);
                break;
            }
            case "owner": {
                rankID = 10;
                RankAPI.getApi().setPrefix(player, 4);
                break;
            }
            default:
                sendUsage(sendplayer);
                break;
        }

        RankAPI.getApi().setRankID(player, rankID, time, Calendar.getInstance());
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
        if (args.length == 0 || args.length == 1) {
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
        if (LanguageAPI.getApi().getLanguage(player) == 1) {
            player.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/setrank <Spieler> <Rang> <Zeit>");
        } else {
            player.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/setrank <player> <rank> <time>");
        }
    }
}
