package de.lars.utilsManager.coins;

import de.lars.apiManager.coinAPI.CoinAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class GiftCommand implements BasicCommand {

    DecimalFormat formatter = new DecimalFormat("#,###");

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player player = (Player) stack.getSender();

        if (CoinAPI.getApi().getGifts(player).isEmpty()) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast keine Geschenke!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You don't have any gifts!", NamedTextColor.RED)));

            }
            return;
        }
        player.sendMessage(Statements.getPrefix().append(Component.text("                    ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                .append(Component.text("[ ", NamedTextColor.DARK_GRAY))
                .append(Component.text("Gifts", NamedTextColor.GREEN))
                .append(Component.text(" ]", NamedTextColor.DARK_GRAY))
                .append(Component.text("                    ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH)));
        player.sendMessage(" ");
        for (String gift: CoinAPI.getApi().getGifts(player)) {
            String giftString = formatter.format(Integer.parseInt(gift));
            Component GiftClickText = Component.text("[Anfordern]", NamedTextColor.GREEN).clickEvent(ClickEvent.runCommand("/claimgift " + gift));
            Component GiftClickTextE = Component.text("[Claim]", NamedTextColor.GREEN).clickEvent(ClickEvent.runCommand("/claimgift " + gift));
            ComponentBuilder GiftText = Component.text().append(Statements.getPrefix().append(Component.text(">> " + giftString + "$ ", NamedTextColor.GOLD)));
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                GiftText.append(GiftClickText);
            } else {
                GiftText.append(GiftClickTextE);
            }
            player.sendMessage(GiftText);
            player.sendMessage("");
        }
        player.sendMessage(Statements.getPrefix().append(Component.text("                    ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                .append(Component.text("[ ", NamedTextColor.DARK_GRAY))
                .append(Component.text("Gifts", NamedTextColor.GREEN))
                .append(Component.text(" ]", NamedTextColor.DARK_GRAY))
                .append(Component.text("                    ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH)));
        player.sendMessage(" ");
    }
}
