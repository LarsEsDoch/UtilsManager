package de.lars.utilsManager.commands.homes;

import de.lars.apiManager.homeAPI.HomeAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class deleteHomeCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player player = (Player) stack.getSender();

        if (!(player.hasPermission("plugin.home"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        String HomeName = args[0];
        if (!HomeAPI.getApi().doesOwnHomeExist(player, HomeName)) {
            if(LanguageAPI.getApi().getLanguage(player) == 1) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Der Home ", NamedTextColor.RED))
                        .append(Component.text(HomeName, NamedTextColor.YELLOW))
                        .append(Component.text(" existiert nicht!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("The home ", NamedTextColor.RED))
                        .append(Component.text(HomeName, NamedTextColor.YELLOW))
                        .append(Component.text(" does not exist!", NamedTextColor.RED)));
            }
            return;
        }

        if(LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du hast den Homepunkt ", NamedTextColor.YELLOW))
                    .append(Component.text(HomeName, NamedTextColor.RED))
                    .append(Component.text(" gelöscht!", NamedTextColor.YELLOW)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You've deleted the homepoint ", NamedTextColor.YELLOW))
                    .append(Component.text(HomeName, NamedTextColor.RED))
                    .append(Component.text("!", NamedTextColor.YELLOW)));
        }
        HomeAPI.getApi().deleteHome(player, HomeAPI.getApi().getHomeId(player, HomeName));
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (args.length == 0 || args.length == 1) {
            List<String> homes = new ArrayList<>();
            for (String string : HomeAPI.getApi().getOwnHomes(player)) {
                homes.add(string);
            }
            return homes;
        }
        return Collections.emptyList();
    }

    private void sendUsage(Player sender) {
        if (LanguageAPI.getApi().getLanguage(sender) == 2) {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/sethome <Name>", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Use", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/sethome <name>", NamedTextColor.BLUE)));
        }
    }
}
