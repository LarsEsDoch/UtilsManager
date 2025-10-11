package de.lars.utilsManager.scoreboard;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.toggleAPI.ToggleAPI;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleScoreboardCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Component.text("Only player can send Messages.", NamedTextColor.RED));
            return;
        }
        if (ToggleAPI.getApi().getScoreboardToggle(player)) {
            ToggleAPI.getApi().setScoreboardToggle(player, false);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.kick(Statements.getPrefix().append(Component.text("Du hast nun ab dem nächsten join kein Scoreboard!", NamedTextColor.GRAY)));
            } else {
                player.kick(Statements.getPrefix().append(Component.text("You haven´t after you next join a scoreboard!", NamedTextColor.GRAY)));
            }

        } else {
            ToggleAPI.getApi().setScoreboardToggle(player,true);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.kick(Statements.getPrefix().append(Component.text("Du hast nun ab dem nächsten join ein Scoreboard!", NamedTextColor.GRAY)));
            } else {
                player.kick(Statements.getPrefix().append(Component.text("You have after you next join a scoreboard!", NamedTextColor.GRAY)));
            }
        }
    }
}
