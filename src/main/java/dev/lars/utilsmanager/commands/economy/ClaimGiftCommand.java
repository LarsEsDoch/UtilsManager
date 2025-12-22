package dev.lars.utilsmanager.commands.economy;

import dev.lars.apimanager.apis.economyAPI.EconomyAPI;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.utils.Statements;
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
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (args.length == 0) {
            sendUsage(player);
            return;
        }
        String gift = args[0];
        try {
            Integer.parseInt(gift);
        } catch (Exception e) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Geschenkwert muss eine Zahl sein!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("Gift value must be a number!", NamedTextColor.RED)));
            }
            return;
        }
        if (!EconomyAPI.getApi().getGifts(player).contains(Integer.parseInt(gift))) {
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
        EconomyAPI.getApi().increaseBalance(player, Integer.parseInt(gift));
        EconomyAPI.getApi().removeGift(player, Integer.parseInt(gift));
        if (!EconomyAPI.getApi().getGifts(player).isEmpty()) {
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
