package de.lars.utilsManager.ban;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.RankStatements;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class KickCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player player = (Player) stack.getSender();
        int time = 0;
        if (!(player.hasPermission("plugin.ban"))) {
            player.sendMessage(Statements.getNotAllowed(player));
        }
        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        Player kickPlayer = Bukkit.getPlayer(args[0]);

        if (kickPlayer == null) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(NamedTextColor.RED + "Der Spieler ist nicht online!");
            } else {
                player.sendMessage(NamedTextColor.RED + "The Player isn't online!");
            }
            return;
        }

        try {
            if (args.length > 1) {
                time = Integer.parseInt(args[args.length - 1]);
            }
        } catch (NumberFormatException ignored) {

        }

        int messageEndIndex = args.length;
        if (time > 0) messageEndIndex--;

        StringBuilder reason = new StringBuilder();
        for (int i = 1; i < messageEndIndex; i++) {
            reason.append(args[i]).append(" ");
        }

        Component reasonComponent;
        if (reason.toString().isEmpty()) {
            reasonComponent = Component.text("Kicked by ", NamedTextColor.DARK_RED)
                    .append(RankStatements.getRank(player));
        } else {
            reasonComponent = Component.text("Kicked by ", NamedTextColor.DARK_RED)
                    .append(RankStatements.getRank(player))
                    .append(Component.text(" Reason:\n", NamedTextColor.DARK_RED))
                    .append(Component.text(reason.toString(), NamedTextColor.RED));
        }

        if (RankAPI.getApi().getRankID(kickPlayer) > RankAPI.getApi().getRankID(player)) {
            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du darfst diesen Spieler nicht kicken!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You aren't allowed to kick this player!", NamedTextColor.RED)));
            }
            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                kickPlayer.sendMessage(Statements.getPrefix().append(Component.text("Achtung! Der Spieler ", NamedTextColor.RED))
                        .append(RankStatements.getRank(player))
                        .append(Component.text(player.getName()))
                        .append(Component.text(" hat versucht dich zu kicken!", NamedTextColor.RED)));
            } else {
                kickPlayer.sendMessage(Statements.getPrefix().append(Component.text("Warning! The player ", NamedTextColor.RED))
                        .append(RankStatements.getRank(player))
                        .append(Component.text(player.getName()))
                        .append(Component.text(" has tried to kick you!", NamedTextColor.RED)));
            }
            return;
        }

        if (time == 0) {
            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast den Spieler", NamedTextColor.WHITE))
                        .append(RankStatements.getRank(kickPlayer))
                        .append(Component.text(kickPlayer.getName()))
                        .append(Component.text(" gekickt!", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You kicked the player ", NamedTextColor.WHITE))
                        .append(RankStatements.getRank(kickPlayer))
                        .append(Component.text(kickPlayer.getName()))
                        .append(Component.text("!", NamedTextColor.WHITE)));
            }
            kickPlayer.kick(reasonComponent);
        } else {
            Main.getInstance().getKickManager().setKicked(kickPlayer, reason.toString(), time);
            int seconds = time % 60;
            int minutes = (time / 60) % 60;
            int hours = (time / 3600);
            String formatedTime = String.format("%02dh %02dm %02ds", hours, minutes, seconds);
            kickPlayer.kick(reasonComponent.append(Component.text("\n Time to wait: " + formatedTime + "!", NamedTextColor.GOLD)));
            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast den Spieler ", NamedTextColor.WHITE))
                        .append(RankStatements.getRank(kickPlayer))
                        .append(Component.text(kickPlayer.getName()))
                        .append(Component.text(" für ", NamedTextColor.WHITE))
                        .append(Component.text(time, NamedTextColor.RED))
                        .append(Component.text( " Sekunden gekickt!", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You kicked the player", NamedTextColor.WHITE))
                        .append(RankStatements.getRank(kickPlayer))
                        .append(Component.text(kickPlayer.getName()))
                        .append(Component.text(" for ", NamedTextColor.WHITE))
                        .append(Component.text(time, NamedTextColor.RED))
                        .append(Component.text( " seconds!", NamedTextColor.WHITE)));
            }
        }
        if (reason.toString().isEmpty()) {
            sendKickMessage(kickPlayer, time, null);
        } else {
            sendKickMessage(kickPlayer, time, reason.toString());
        }
    }

    private void sendKickMessage(Player player, Integer time, String reason) {
        String reasonString;
        if (reason == null) {
            reasonString = "";
        } else {
            reasonString = "mit dem Grund " + reason + " ";
        }

        String timeString;
        if (time == 0) {
            timeString = "";
        } else {
            int seconds = time % 60;
            int minutes = (time / 60) % 60;
            int hours = (time / 3600);
            String formatedTime = String.format("%02d Stunden %02d Minuten %02d Sekunden", hours, minutes, seconds);
            timeString = "für " + formatedTime + " ";
        }

        String message = "Der Spieler " + RankStatements.getUnformattedRank(player) + player.getName() + " wurde " + timeString + reasonString + "gekickt !";
        Main.getInstance().getDiscordBot().sendPunishmentMessage(message);
    }

    private void sendUsage(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/kick <Spieler> (<Grund>) (<Länge in Sec>)");
        } else {
            sender.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/kick <Player> (<Reason>) (<Period in sec>)");
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
