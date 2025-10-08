package de.lars.utilsManager.commands;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LanguageCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player player = (Player) stack.getSender();

        if (args.length == 0) {
            sendUsage(player);
            return;
        }
        if (!(player.hasPermission("plugin.language"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        switch (args[0].toLowerCase()) {
            case "deutsch", "german": {
                LanguageAPI.getApi().setLanguage(player, 2);
                player.sendMessage(Statements.getPrefix().append(Component.text("Der Server ist für dich nun ", NamedTextColor.GRAY))
                        .append(Component.text("Deutsch", NamedTextColor.YELLOW)));
                break;
            }
            case "english", "englisch": {
                LanguageAPI.getApi().setLanguage(player, 1);
                player.sendMessage(Statements.getPrefix().append(Component.text("The Server is now for you ", NamedTextColor.GRAY))
                        .append(Component.text("english!", NamedTextColor.BLUE)));
                break;
            }
            default:
                sendUsage(player);
                break;
        }
    }

    private void sendUsage(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/language deutsch/englisch", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Use", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/language german/english", NamedTextColor.BLUE)));
        }
    }
}
