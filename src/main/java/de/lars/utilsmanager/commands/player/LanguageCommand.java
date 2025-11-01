package de.lars.utilsmanager.commands.player;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.util.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LanguageCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

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

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        if (args.length == 0 || args.length == 1) {
            List<String> languages = new ArrayList<>();
            languages.add("deutsch");
            languages.add("english");

            return languages;
        }
        return Collections.emptyList();
    }
}
