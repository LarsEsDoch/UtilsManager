package dev.lars.utilsmanager.commands.teleport.home;

import dev.lars.apimanager.apis.homeAPI.HomeAPI;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.utils.Statements;
import dev.lars.utilsmanager.utils.SuggestHelper;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class DeleteHomeCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String @NonNull [] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

        if (!(player.hasPermission("utilsmanager.home"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        String homeName = args[0];
        boolean isAdmin = player.hasPermission("utilsmanager.admin");
        boolean homeExists = isAdmin
                ? HomeAPI.getApi().doesHomeExist(homeName)
                : HomeAPI.getApi().doesOwnHomeExist(player, homeName);

        if (!homeExists) {
            boolean german = LanguageAPI.getApi().getLanguage(player) == 2;

            Component message = Statements.getPrefix()
                    .append(Component.text(
                            german ? "Der Home " : "The home ",
                            NamedTextColor.RED))
                    .append(Component.text(homeName, NamedTextColor.YELLOW))
                    .append(Component.text(
                            german ? " existiert nicht!" : " does not exist!",
                            NamedTextColor.RED));

            player.sendMessage(message);
            return;
        }

        HomeAPI.getApi().deleteHome(HomeAPI.getApi().getHomeId(player, homeName));
        if(LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du hast den Home ", NamedTextColor.YELLOW))
                    .append(Component.text(homeName, NamedTextColor.RED))
                    .append(Component.text(" gelöscht!", NamedTextColor.YELLOW)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You've deleted the home ", NamedTextColor.YELLOW))
                    .append(Component.text(homeName, NamedTextColor.RED))
                    .append(Component.text("!", NamedTextColor.YELLOW)));
        }
    }

    @Override
    public @NonNull Collection<String> suggest(final CommandSourceStack commandSourceStack, final String @NonNull [] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (player.hasPermission("utilsmanager.admin")) {
            Collection<String> homes = Objects.requireNonNullElse(HomeAPI.getApi().getAllHomes(), Collections.emptyList());

            if (args.length == 0) {
                return homes;
            }
            if (args.length == 1) {
                return SuggestHelper.filter(args[0], homes.toArray(new String[0]));
            }
        } else {
           Collection<String> homes = Objects.requireNonNullElse(HomeAPI.getApi().getOwnHomes(player), Collections.emptyList());

            if (args.length == 0) {
                return homes;
            }
            if (args.length == 1) {
                return SuggestHelper.filter(args[0], homes.toArray(new String[0]));
            }
        }
        return Collections.emptyList();
    }

    private void sendUsage(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getUsage(player)
                    .append(Component.text("/deletehome <Name>", NamedTextColor.BLUE)));
        } else {
            player.sendMessage(Statements.getUsage(player)
                    .append(Component.text("/deletehome <name>", NamedTextColor.BLUE)));
        }
    }
}