package dev.lars.utilsmanager.features.freecam;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FreeCamLeaveCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String @NotNull [] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (!player.hasPermission("utilsmanager.feature.freecam")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        FreeCamManager freeCamManager = UtilsManager.getInstance().getFreeCamManager();
        if (!freeCamManager.exitFreeCam(player)) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du bist gar nicht im Kamera Modus!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You aren't in freecam mode!", NamedTextColor.RED)));
            }
        } else {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du bist nun zurück im Überlebens Modus.", NamedTextColor.GREEN)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You're now back in Survival mode.", NamedTextColor.GREEN)));
            }
        }
    }
}