package dev.lars.utilsmanager.commands.economy;

import dev.lars.apimanager.apis.economyAPI.EconomyAPI;
import dev.lars.apimanager.apis.economyAPI.Gift;
import dev.lars.apimanager.apis.languageAPI.Language;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.utils.FormatNumbers;
import dev.lars.utilsmanager.utils.Statements;
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

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

        if (EconomyAPI.getApi().getGifts(player).isEmpty()) {
            if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
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

        for (Gift gift : EconomyAPI.getApi().getGifts(player)) {
            Component giftValue = FormatNumbers.formatMoney(gift.value());
            Component giftClickText = Component.text("[Anfordern]", NamedTextColor.GREEN).clickEvent(ClickEvent.runCommand("/claimgift " + gift.id()));
            Component giftClickTextE = Component.text("[Claim]", NamedTextColor.GREEN).clickEvent(ClickEvent.runCommand("/claimgift " + gift.id()));
            Component giftText = Statements.getPrefix().append(Component.text(">> ", NamedTextColor.GOLD).append(giftValue));
            if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                giftText = giftText.append(giftClickText);
            } else {
                giftText = giftText.append(giftClickTextE);
            }
            player.sendMessage(giftText);
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