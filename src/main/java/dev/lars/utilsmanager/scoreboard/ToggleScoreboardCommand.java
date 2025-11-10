package dev.lars.utilsmanager.scoreboard;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.playerSettingsAPI.PlayerSettingsAPI;
import dev.lars.utilsmanager.utils.Statements;
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
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (PlayerSettingsAPI.getApi().getScoreboardToggle(player)) {
            PlayerSettingsAPI.getApi().setScoreboardToggle(player, false);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.kick(Statements.getPrefix().append(Component.text("Du hast nun ab dem nächsten join kein Scoreboard!", NamedTextColor.GRAY)));
            } else {
                player.kick(Statements.getPrefix().append(Component.text("You haven´t after you next join a scoreboard!", NamedTextColor.GRAY)));
            }

        } else {
            PlayerSettingsAPI.getApi().setScoreboardToggle(player,true);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.kick(Statements.getPrefix().append(Component.text("Du hast nun ab dem nächsten join ein Scoreboard!", NamedTextColor.GRAY)));
            } else {
                player.kick(Statements.getPrefix().append(Component.text("You have after you next join a scoreboard!", NamedTextColor.GRAY)));
            }
        }
    }
}