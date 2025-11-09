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
import java.util.Objects;

public class WalletCommand implements BasicCommand {

    private int coins;
    private OfflinePlayer player;
    private Player sendplayer;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player sendplayer = (Player) stack.getSender();

        if (!(sendplayer.hasPermission("plugin.wallet"))) {
            sendplayer.sendMessage(Statements.getNotAllowed(sendplayer));
            return;
        }
        if (args.length == 0) {

            coins = EconomyAPI.getApi().getBalance(sendplayer);
            DecimalFormat formatter = new DecimalFormat("#,###");
            String formatierteZahl = formatter.format(coins);
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                sendplayer.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.AQUA))
                        .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                        .append(Component.text("$ auf deinem Konto.", NamedTextColor.AQUA)));
            } else {
                sendplayer.sendMessage(Statements.getPrefix().append(Component.text("You have ", NamedTextColor.AQUA))
                        .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                        .append(Component.text("$ in your wallet.", NamedTextColor.AQUA)));
            }
            return;
        }

        player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                sendplayer.sendMessage(NamedTextColor.RED + "Der Spieler existiert nicht!");
            } else {
                sendplayer.sendMessage(NamedTextColor.RED + "The Player dosen't exist!");
            }
            return;
        }
        coins = EconomyAPI.getApi().getBalance((Player) player);
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatierteZahl = formatter.format(coins);
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sendplayer.sendMessage(Statements.getPrefix().append(Component.text(Objects.requireNonNull(player.getName()), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text(" hat ", NamedTextColor.AQUA))
                    .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                    .append(Component.text("$ auf seinem Konto.", NamedTextColor.AQUA)));
        } else {
            sendplayer.sendMessage(Statements.getPrefix().append(Component.text(Objects.requireNonNull(player.getName()), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text(" has ", NamedTextColor.AQUA))
                    .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                    .append(Component.text("$ in his Wallet", NamedTextColor.AQUA)));
        }
    }

    private void sendUsage(CommandSender sender) {
        Player sendplayer = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sender.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/wallet (<Spieler>)");
        } else {
            sender.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/wallet (<Player>)");
        }
    }
}
