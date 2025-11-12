package dev.lars.utilsmanager.commands.teleport.spawn;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.playerSettingsAPI.PlayerSettingsAPI;
import dev.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleBedCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String @NotNull [] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

        if (!(player.hasPermission("utilsmanager.togglebed"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (PlayerSettingsAPI.getApi().getBedToggle(player)) {
            PlayerSettingsAPI.getApi().setBedToggle(player, false);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du Spawnst nun nicht mehr am Spawn!", NamedTextColor.GRAY)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You aren´t longer spawning on the spawn!", NamedTextColor.GRAY)));
            }

        } else {
            PlayerSettingsAPI.getApi().setBedToggle(player, true);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du Spawnst nun am Spawn!", NamedTextColor.GRAY)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You are now spawning on the spawn!", NamedTextColor.GRAY)));
            }
        }
    }
}
