package de.lars.utilsManager.commands.homes;

import de.lars.apiManager.homeAPI.HomeAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setHomeCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Component.text("Only player can send Messages.", NamedTextColor.RED));
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
        Boolean isPublic = false;
        Component publicStringEn = Component.text("");
        Component publicStringDe = Component.text("");
        /*if (RankAPI.getApi().getRankID(player) >= 9) {
            if (args.length == 1) {
                sendUsage(player);
                return;
            }
            if (args[1].contains("true") || args[1].contains("on") || args[1].contains("ja") ||args[1].contains("yes")) {
                isPublic = true;
                publicStringEn = Component.text("public ", NamedTextColor.BLUE);
                publicStringDe = Component.text("öffentlichen ", NamedTextColor.BLUE);
            }
        }

         */

        if (args.length == 1) {
            sendUsage(player);
            return;
        }
        if (args[1].contains("true") || args[1].contains("on") || args[1].contains("ja") || args[1].contains("yes") || args[1].contains("y") || args[1].contains("1")) {
            isPublic = true;
            publicStringEn = Component.text("public ", NamedTextColor.BLUE);
            publicStringDe = Component.text("öffentlichen ", NamedTextColor.BLUE);
        }

        String HomeName = args[0];

        if (HomeAPI.getApi().doesHomeExist(player, HomeName)) {
            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Der Home ", NamedTextColor.RED))
                        .append(Component.text(HomeName, NamedTextColor.YELLOW))
                        .append(Component.text(" existiert schon!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("The home ", NamedTextColor.RED))
                        .append(Component.text(HomeName, NamedTextColor.YELLOW))
                        .append(Component.text(" exist already!", NamedTextColor.RED)));
            }
            return ;
        }
        
        String HomeLocation = player.getLocation().getX() + "," + player.getLocation().getY() + "," + player.getLocation().getZ() + "," + player.getLocation().getWorld().getName();

        HomeAPI.getApi().createHome(player, HomeName, player.getLocation(), isPublic);
        if(LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du hast den " , NamedTextColor.AQUA))
                    .append(publicStringDe)
                    .append(Component.text("Home ", NamedTextColor.AQUA))
                    .append(Component.text(HomeName, NamedTextColor.GREEN))
                    .append(Component.text(" erstellt.", NamedTextColor.AQUA)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You've created the " , NamedTextColor.AQUA))
                    .append(publicStringEn)
                    .append(Component.text("home ", NamedTextColor.AQUA))
                    .append(Component.text(HomeName, NamedTextColor.GREEN))
                    .append(Component.text(".", NamedTextColor.AQUA)));
        }
    }

    private void sendUsage(Player sender) {
        /*if (RankAPI.getApi().getRankID(sender) >= 9) {
            if (LanguageAPI.getApi().getLanguage(sender) == 2) {
                sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                        .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                        .append(Component.text("/sethome <Name> <Öffentlich>", NamedTextColor.BLUE)));
            } else {
                sender.sendMessage(Component.text("Use", NamedTextColor.GRAY)
                        .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                        .append(Component.text("/sethome <name> <public>", NamedTextColor.BLUE)));
            }
        } else {
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

         */
        if (LanguageAPI.getApi().getLanguage(sender) == 2) {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/sethome <Name> <Öffentlich>", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Use", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/sethome <name> <public>", NamedTextColor.BLUE)));
        }
    }
}
