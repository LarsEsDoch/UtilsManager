package de.lars.utilsManager.commands;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VanishCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player player = (Player) stack.getSender();
        if (!player.hasPermission("plugin.vanish")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if (player.isInvisible()) {
            player.setInvisible(false);
            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du bist nun für alle wieder ", NamedTextColor.WHITE))
                        .append(Component.text("sichtbar ", NamedTextColor.GRAY)).append(Component.text("!", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You're now ", NamedTextColor.WHITE))
                                .append(Component.text("visible ", NamedTextColor.GRAY)).append(Component.text("again for everyone!", NamedTextColor.WHITE)));
            }
        } else {
            player.setInvisible(true);
            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du bist nun für alle ", NamedTextColor.WHITE))
                                .append(Component.text("unsichtbar ", NamedTextColor.GRAY)).append(Component.text("!", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You're now ", NamedTextColor.WHITE))
                                .append(Component.text("invisible ", NamedTextColor.GRAY)).append(Component.text("for everyone!", NamedTextColor.WHITE)));
            }
        }
    }
}
