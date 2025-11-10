package dev.lars.utilsmanager.features.realtime;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RealTimeCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        RealTime realTime = UtilsManager.getInstance().getRealTime();
        if (!(player.hasPermission("plugin.realtime"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if(realTime == null) {
            player.sendMessage(NamedTextColor.DARK_RED + "ERROR");
        }

        if (!ServerSettingsAPI.getApi().isRealTimeEnabled()) {
            ServerSettingsAPI.getApi().setRealTimeEnabled(true);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Component.text("Die echte Zeit ist nun aktiviert.", NamedTextColor.GRAY));
            } else {
                player.sendMessage(Component.text("The real Time is now enabled.", NamedTextColor.GRAY));
            }
        } else {
            ServerSettingsAPI.getApi().setRealTimeEnabled(false);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Component.text("Die echte Zeit ist nun deaktiviert.", NamedTextColor.RED));
            } else {
                player.sendMessage(Component.text("The real Time is now disabled.", NamedTextColor.RED));
            }
        }
    }
}
