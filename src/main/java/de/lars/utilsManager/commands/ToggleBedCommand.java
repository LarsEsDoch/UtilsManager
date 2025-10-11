package de.lars.utilsManager.commands;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.toggleAPI.ToggleAPI;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleBedCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Component.text("Only player can send Messages.", NamedTextColor.RED));
            return;
        }
        if (!(player.hasPermission("plugin.togglebed"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if (ToggleAPI.getApi().getBedToggle(player)) {
            ToggleAPI.getApi().setBedToggle(player, false);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du Spawnst nun nicht mehr am Spawn!", NamedTextColor.GRAY)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You aren´t longer spawning on the spawn!", NamedTextColor.GRAY)));
            }

        } else {
            ToggleAPI.getApi().setBedToggle(player, true);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du Spawnst nun am Spawn!", NamedTextColor.GRAY)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You are now spawning on the spawn!", NamedTextColor.GRAY)));
            }

        }
    }
}
