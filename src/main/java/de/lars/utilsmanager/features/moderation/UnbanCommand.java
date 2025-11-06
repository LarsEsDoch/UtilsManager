package de.lars.utilsmanager.features.moderation;

import de.lars.apimanager.apis.banAPI.BanAPI;
import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

public class UnbanCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player sendplayer = (Player) stack.getSender();
        if (args.length == 0) {
            sendUsage(sendplayer);
            return;
        }

        if (!(sendplayer.hasPermission("plugin.ban"))) {
            sendplayer.sendMessage(Statements.getNotAllowed(sendplayer));
            return;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        if (offlinePlayer == null) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                sendplayer.sendMessage(Statements.getPrefix().append(Component.text("Der Spieler existiert nicht!", NamedTextColor.RED)));
            } else {
                sendplayer.sendMessage(Statements.getPrefix().append(Component.text("The player doesn't exists!", NamedTextColor.RED)));
            }
            return;
        }
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sendplayer.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                    .append(Component.text(offlinePlayer.getName(), NamedTextColor.GREEN))
                    .append(Component.text(" entbannt.", NamedTextColor.WHITE)));
        } else {
            sendplayer.sendMessage(Statements.getPrefix().append(Component.text("You have unbanned", NamedTextColor.WHITE))
                    .append(Component.text(offlinePlayer.getName(), NamedTextColor.GREEN))
                    .append(Component.text(".", NamedTextColor.WHITE)));
        }
        BanAPI.getApi().setUnBanned(offlinePlayer);
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (!(player.hasPermission("plugin.ban"))) {
            return Collections.emptyList();
        }
        /*List<String> players = BanAPI.getApi().getBannedPlayer();
        if (args.length == 0 || args.length == 1) {

            return players;
        } */
        return Collections.emptyList();
    }

    private void sendUsage(Player sendplayer) {
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sendplayer.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/unban <Spieler>", NamedTextColor.BLUE)));
        } else {
            sendplayer.sendMessage(Component.text("Use", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/unban <Player>", NamedTextColor.BLUE)));
        }
    }
}
