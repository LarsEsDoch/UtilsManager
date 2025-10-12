package de.lars.utilsmanager.features.moderation;

import de.lars.apiManager.banAPI.BanAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsmanager.Main;
import de.lars.utilsmanager.util.RankStatements;
import de.lars.utilsmanager.util.Statements;
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

public class BanCommand implements BasicCommand {
    Player player;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player sendplayer = (Player) stack.getSender();
        if (!(sendplayer.hasPermission("plugin.ban"))) {
            sendplayer.sendMessage(Statements.getNotAllowed(sendplayer));
            return;
        }

        if (args.length < 3) {
            sendUsage(sendplayer);
            return;
        }

        player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                sendplayer.sendMessage(NamedTextColor.RED + "Der Spieler existiert nicht!");
            } else {
                sendplayer.sendMessage(NamedTextColor.RED + "The Player dosent exist!");
            }
            return;
        }
        if (!BanAPI.getApi().doesUserExist(player)) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                sendplayer.sendMessage(NamedTextColor.RED + "Der Spieler existiert nicht!");
            } else {
                sendplayer.sendMessage(NamedTextColor.RED + "The Player dosent exist!");
            }
            return;
        }
        for (String arg : args) {
            try {
                Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sendUsage(sendplayer);
                return;
            }
        }

        Integer time = Integer.parseInt(args[1]);
        if (time <= 0) {
            sendUsage(sendplayer);
            return;
        }
        StringBuilder reason = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }
        if(RankAPI.getApi().getRankID(sendplayer) == 8) {
            if(RankAPI.getApi().getRankID(player) > 8) {
                if(LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                    sendplayer.sendMessage(Statements.getPrefix().append(Component.text("Du darfst diesen Spieler nicht bannen!", NamedTextColor.RED)));
                } else {
                    sendplayer.sendMessage(Statements.getPrefix().append(Component.text("You aren't allowed to ban this player!", NamedTextColor.RED)));
                }
                if(LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Achtung! Der Spieler ", NamedTextColor.RED))
                            .append(Component.text(RankStatements.getRank(sendplayer) + sendplayer.getName()))
                            .append(Component.text(" hat versucht dich zu bannen!", NamedTextColor.RED)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Warning! The player ", NamedTextColor.RED))
                            .append(Component.text(RankStatements.getRank(sendplayer) + sendplayer.getName()))
                            .append(Component.text(" has tried to ban you!", NamedTextColor.RED)));
                }
                return;
            }
        }
        if(RankAPI.getApi().getRankID(sendplayer) == 9) {
            if(RankAPI.getApi().getRankID(player) > 9) {
                if(LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                    sendplayer.sendMessage(Statements.getPrefix().append(Component.text("Du darfst diesen Spieler nicht bannen!", NamedTextColor.RED)));
                } else {
                    sendplayer.sendMessage(Statements.getPrefix().append(Component.text("You aren't allowed to ban this player!", NamedTextColor.RED)));
                }
                if(LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Achtung! Der Spieler ", NamedTextColor.RED))
                            .append(Component.text(RankStatements.getRank(sendplayer) + sendplayer.getName()))
                            .append(Component.text(" hat versucht dich zu bannen!", NamedTextColor.RED)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Warning! The player ", NamedTextColor.RED))
                            .append(Component.text(RankStatements.getRank(sendplayer) + sendplayer.getName()))
                            .append(Component.text(" has tried to ban you!", NamedTextColor.RED)));
                }
                return;
            }
        }
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sendplayer.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.YELLOW))
                    .append(Component.text(RankStatements.getRank(player) + player.getName()))
                    .append(Component.text(" für ", NamedTextColor.YELLOW))
                    .append(Component.text(time, NamedTextColor.BLUE))
                    .append(Component.text(" Tage gebannt mit dem Grund ", NamedTextColor.YELLOW))
                    .append(Component.text(reason.toString(), NamedTextColor.GREEN))
                    .append(Component.text(".", NamedTextColor.YELLOW)));
        } else {
            sendplayer.sendMessage(Statements.getPrefix().append(Component.text("You have banned ", NamedTextColor.YELLOW))
                    .append(Component.text(RankStatements.getRank(player) + player.getName()))
                    .append(Component.text(" for ", NamedTextColor.YELLOW))
                    .append(Component.text(time, NamedTextColor.BLUE))
                    .append(Component.text(" days with the reason ", NamedTextColor.YELLOW))
                    .append(Component.text(reason.toString(), NamedTextColor.GREEN))
                    .append(Component.text(".", NamedTextColor.YELLOW)));
        }
        BanAPI.getApi().setBanned(player, reason.toString(), time);
        String message = "Der Spieler " + RankStatements.getUnformattedRank(player) + player.getName() + " wurde von" + RankStatements.getUnformattedRank(player) + player.getName() + " mit dem Grund " + reason + " für 7 Tage gebannt!";
        Main.getInstance().getDiscordBot().sendPunishmentMessage(message);
    }

    private void sendUsage(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/ban <Spieler> <Zeit> <Grund>", NamedTextColor.BLUE)));
        } else {
            player.sendMessage(Component.text("Use", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/ban <Player> <Time> <Reason>", NamedTextColor.BLUE)));
        }
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (RankAPI.getApi().getRankID(player) < 9) return Collections.emptyList();
        if (args.length == 1 || args.length == 0) {
            List<String> names = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                names.add(p.getName());
            }

            return names;
        }
        return Collections.emptyList();
    }
}
