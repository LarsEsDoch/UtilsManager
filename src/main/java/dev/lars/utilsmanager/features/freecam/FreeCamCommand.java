package dev.lars.utilsmanager.features.freecam;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FreeCamCommand implements BasicCommand {

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
        if (!freeCamManager.enterFreeCam(player)) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du bist bereits im Kamera Modus!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You're already in the Freecam mode!", NamedTextColor.RED)));
            }
        } else {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du befindest dich nun im Kamera Modus. Benutzte ", NamedTextColor.GREEN))
                        .append(Component.text("/freecamleave", NamedTextColor.AQUA).clickEvent(ClickEvent.runCommand("/freecamleave")))
                        .append(Component.text(" zum verlassen.", NamedTextColor.GREEN)));
                player.sendActionBar(Component.text("Mache einen Mausklick um den Freecam-Modus zu verlassen.", NamedTextColor.AQUA));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You're now in the cam mode. Use ", NamedTextColor.GREEN))
                        .append(Component.text("/freecamleave", NamedTextColor.AQUA).clickEvent(ClickEvent.runCommand("/freecamleave")))
                        .append(Component.text(" to leave.", NamedTextColor.GREEN)));
                player.sendActionBar(Component.text("Click with the mouse to leave the freecam mode.", NamedTextColor.AQUA));
            }
        }
    }
}