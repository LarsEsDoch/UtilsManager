package de.lars.utilsmanager.commands.economy;

import de.lars.apimanager.apis.economyAPI.EconomyAPI;
import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class SetCoinsCommand implements BasicCommand {

    private int setcoins;
    private OfflinePlayer player;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player sendplayer = (Player) stack.getSender();

        if (args.length == 0) {
            sendUsage(sendplayer);
            return;
        }
        if (args.length == 1) {
            sendUsage(sendplayer);
            return;
        }

        if (!(sendplayer.hasPermission("plugin.setcoins"))) {
            sendplayer.sendMessage(Statements.getNotAllowed(sendplayer));
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

        setcoins = Integer.parseInt(args[1]);
        player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                sendplayer.sendMessage(Component.text("Der Spieler existiert nicht!", NamedTextColor.RED));
            } else {
                sendplayer.sendMessage(Component.text("This player dosen't exits!", NamedTextColor.RED));
            }
            return;
        }
        EconomyAPI.getApi().setBalance((Player) player, setcoins);
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatierteZahl = formatter.format(setcoins);
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sendplayer.sendMessage(Statements.getPrefix().append(Component.text("Du hast den Kontostand von ", NamedTextColor.YELLOW))
                    .append(Component.text(player.getName(), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text(" auf ", NamedTextColor.YELLOW))
                    .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                    .append(Component.text("$ gesetzt.", NamedTextColor.YELLOW)));
        } else {
            sendplayer.sendMessage(Statements.getPrefix().append(Component.text("You set the account balance of ", NamedTextColor.YELLOW))
                    .append(Component.text(player.getName(), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text(" to ", NamedTextColor.YELLOW))
                    .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                    .append(Component.text("$.", NamedTextColor.YELLOW)));
        }
        Player player1 = (Player) player;
        if (LanguageAPI.getApi().getLanguage((Player) player) == 2) {
            player1.sendMessage(Statements.getPrefix().append(Component.text("Dein Kontostand wurde auf ", NamedTextColor.YELLOW))
                    .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                    .append(Component.text("$ gesetzt.", NamedTextColor.YELLOW)));
        } else {
            player1.sendMessage(Statements.getPrefix().append(Component.text("Your account balance has been set to ", NamedTextColor.YELLOW))
                    .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                    .append(Component.text("$.", NamedTextColor.YELLOW)));
        }
        return;
    }

    private void sendUsage(CommandSender sender) {
        if (LanguageAPI.getApi().getLanguage((Player) sender) == 2) {
            sender.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/setcoins <Spieler> <Balance>");
        } else {
            sender.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/setcoins <Player> <Balance>");
        }
    }
}
