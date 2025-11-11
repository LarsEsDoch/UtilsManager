package dev.lars.utilsmanager.features.realtime;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import dev.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RealTimeCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        CommandSender sender = stack.getSender();

        if (sender instanceof Player player) {
            if (!player.hasPermission("utilsmanager.realtime") && !player.isOp()) {
                player.sendMessage(Statements.getNotAllowed(player));
                return;
            }
        }

        boolean newState = !ServerSettingsAPI.getApi().isRealTimeEnabled();
        ServerSettingsAPI.getApi().setRealTimeEnabled(newState);

        if (sender instanceof Player player) {
            if (newState) {
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Die echte Zeit ist nun aktiviert.", NamedTextColor.GRAY)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("The real Time is now enabled.", NamedTextColor.GRAY)));
                }
            } else {
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Die echte Zeit ist nun deaktiviert.", NamedTextColor.RED)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("The real Time is now disabled.", NamedTextColor.RED)));
                }
            }
        } else {
            sender.sendMessage(Statements.getPrefix().append(Component.text("Real time has been " + (newState ? "enabled" : "disabled") + ".", NamedTextColor.GRAY)));
        }
    }
}