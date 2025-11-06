package de.lars.utilsmanager.features.court;

import de.lars.apimanager.apis.courtAPI.CourtAPI;
import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.utils.RankStatements;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReportCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player sendplayer = (Player) stack.getSender();
        if (args.length == 0 || args.length == 1) {
            sendUsage(sendplayer);
            return;
        }
        for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
            if(CourtAPI.getApi().getStatus(onlinePlayer) != 0) {
                if(LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                    onlinePlayer.sendMessage(Statements.getPrefix().append(Component.text("Es wurde bereits jemand angeklagt!", NamedTextColor.RED)));
                } else {
                    onlinePlayer.sendMessage(Statements.getPrefix().append(Component.text("Somebody is already accused!", NamedTextColor.RED)));
                }
                return;
            }
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (!Bukkit.getOnlinePlayers().contains(player) || player == null) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                sendplayer.sendMessage(Component.text("Der Spieler existiert nicht!", NamedTextColor.RED));
            } else {
                sendplayer.sendMessage(Component.text("The Player doesn't exist!", NamedTextColor.RED));
            }
            return;
        }
        String reason = "";
        for (int i = 1; i < args.length; i++) {
            reason += args[i] + " ";
        }
        if(LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                    .append(RankStatements.getRank(player).append(Component.text(player.getName())))
                    .append(Component.text(" angeklagt.", NamedTextColor.WHITE)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You accused ", NamedTextColor.WHITE))
                    .append(RankStatements.getRank(player).append(Component.text(player.getName())))
                    .append(Component.text(".", NamedTextColor.WHITE)));
        }
        CourtAPI.getApi().setStatus(player, 1);
        CourtAPI.getApi().setReason(player, reason);
        CourtAPI.getApi().setProsecutor(player, sendplayer);
    }

    private void sendUsage(Player sender) {
        if (LanguageAPI.getApi().getLanguage(sender) == 2) {
            sender.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/criminal <Spieler> <Grund>");
        } else {
            sender.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/criminal <player> <reason>");
        }
    }

}
