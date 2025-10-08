package de.lars.utilsManager.coins;

import de.lars.apiManager.coinAPI.CoinAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class ClaimGiftCommand implements BasicCommand {

    DecimalFormat formatter = new DecimalFormat("#,###");

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player player = (Player) stack.getSender();
        if (args.length == 0) {
            sendUsage(player);
            return;
        }
        String gift = args[0];
        if (!CoinAPI.getApi().getGifts(player).contains(gift)) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Dir steht nicht dieses Geschenk zu!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You are not entitled to this gift!", NamedTextColor.RED)));
            }
            return;
        }

        String giftString = formatter.format(Integer.parseInt(gift));
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du hast dein Geschenk im Werte von ", NamedTextColor.BLUE))
                    .append(Component.text(giftString, NamedTextColor.GOLD))
                    .append(Component.text("$ angefordert.", NamedTextColor.BLUE)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You claimed your gift worth of ", NamedTextColor.BLUE))
                    .append(Component.text(giftString, NamedTextColor.GOLD))
                    .append(Component.text("$.", NamedTextColor.BLUE)));
        }
        CoinAPI.getApi().addCoins(player, Integer.parseInt(gift));
        CoinAPI.getApi().removeGift(player,gift);
        if (!CoinAPI.getApi().getGifts(player).isEmpty()) {
            player.sendMessage(" ");
            player.performCommand("gifts");
        }
    }

    private void sendUsage(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/claimgift <Geschenkwert>");
        } else {
            sender.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/claimgift <Gift value>");
        }

    }
}
