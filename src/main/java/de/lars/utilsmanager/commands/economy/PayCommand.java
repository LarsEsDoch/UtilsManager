package de.lars.utilsmanager.commands.economy;

import de.lars.apimanager.apis.economyAPI.EconomyAPI;
import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.utils.CheckPlayers;
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

public class PayCommand implements BasicCommand {

    private Player sendplayer;
    private int sendercoins;
    private int paying;
    private Player player;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (args.length == 0) {
            sendUsage(player);
            return;
        }
        if (!(player.hasPermission("plugin.pay"))) {
            player.sendMessage(Statements.getNotAllowed(sendplayer));
            return;
        }
        for (String arg : args) {
            try {
                Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sendUsage(player);
                return;
            }
        }

        paying = Integer.parseInt(args[1]);
        if (paying <= 0) {
            sendUsage(player);
            return;
        }
        sendercoins = EconomyAPI.getApi().getBalance(sendplayer);
        OfflinePlayer recipient = Bukkit.getPlayer(args[0]);
        if (CheckPlayers.checkOfflinePlayer(sendplayer, recipient)) return;
        if (paying > sendercoins) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld zum überweisen!");
            } else {
                player.sendMessage(NamedTextColor.RED + "You haven´t got enough money to pay!");
            }
            return;
        }

        EconomyAPI.getApi().decreaseBalance(sendplayer, paying);
        EconomyAPI.getApi().increaseBalance(player, paying);
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatierteZahl = formatter.format(paying);
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.GREEN))
                    .append(Component.text(player.getName(), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text(" "))
                    .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                    .append(Component.text("$ überwiesen.", NamedTextColor.GREEN)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You have paid ", NamedTextColor.GREEN))
                    .append(Component.text(player.getName(), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text(" "))
                    .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                    .append(Component.text("$.", NamedTextColor.GREEN)));
        }
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.GREEN))
                    .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                    .append(Component.text("$ von ", NamedTextColor.GREEN))
                    .append(Component.text(sendplayer.getName(), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text(" bekommen!", NamedTextColor.GREEN)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You got ", NamedTextColor.GREEN))
                    .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                    .append(Component.text("$ from ", NamedTextColor.GREEN))
                    .append(Component.text(sendplayer.getName(), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text(" !", NamedTextColor.GREEN)));
            }
        }
    private void sendUsage(CommandSender sender) {
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sender.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/pay <Spieler> <Balance>");
        } else {
            sender.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/pay <Player> <Balance>");
        }
    }
}
