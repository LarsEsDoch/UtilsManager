package de.lars.utilsmanager.commands.teleport.home;

import dev.lars.apimanager.apis.courtAPI.CourtAPI;
import dev.lars.apimanager.apis.homeAPI.HomeAPI;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HomeCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }


        if (!(player.hasPermission("plugin.home"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if (args.length == 0) {
            sendUsage(player);
            return;
        }
        if (CourtAPI.getApi().getStatus(player) == 5) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        String HomeName = args[0];
        if (!HomeAPI.getApi().doesHomeExist(HomeName)) {
            if(LanguageAPI.getApi().getLanguage(player) == 2) {
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
        Integer id = HomeAPI.getApi().getHomeId(player, HomeName);
        Location loc = HomeAPI.getApi().getHomeLocation(id);
        player.teleport(loc);
        if(LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest zum Homepunkt ", NamedTextColor.AQUA))
                    .append(Component.text(HomeName, NamedTextColor.GREEN))
                    .append(Component.text(" teleportiert.", NamedTextColor.AQUA)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You've been teleported to the home ", NamedTextColor.AQUA))
                    .append(Component.text(HomeName, NamedTextColor.GREEN))
                    .append(Component.text(".", NamedTextColor.AQUA)));
        }
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        if (args.length == 0 || args.length == 1) {
            List<String> homes = new ArrayList<>();
            for (String string : HomeAPI.getApi().getHomes((Player) commandSourceStack.getSender())) {
                homes.add(string.toLowerCase());
            }
            return homes;
        }
        return Collections.emptyList();
    }

    private void sendUsage(Player sender){
        if (LanguageAPI.getApi().getLanguage(sender) == 2) {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/home <Name>", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Use", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/home <name>", NamedTextColor.BLUE)));
        }
    }
}
