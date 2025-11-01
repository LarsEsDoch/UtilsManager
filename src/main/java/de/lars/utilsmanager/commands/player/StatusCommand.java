package de.lars.utilsmanager.commands.player;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.apimanager.apis.statusAPI.StatusAPI;
import de.lars.utilsmanager.UtilsManager;
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

public class StatusCommand implements BasicCommand {

    protected Player player;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        player = (Player) stack.getSender();
        if (!(player.hasPermission("plugin.prefix"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if (args.length == 0) {
            sendUsage(player);
            return;
        }
        String status = args[0];

        if (status.equals("reset")) {
            StatusAPI.getApi().setStatus(player, null);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast deinen Status zurückgesetzt!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You've rested your status!", NamedTextColor.RED)));
            }
        } else {
            if (args.length == 1) {
                sendUsage(player);
                return;
            }
            NamedTextColor color = getColor(args[1]);
            if (color == null) {
                sendUsage(player);
                return;
            }
            StatusAPI.getApi().setStatus(player, status);
            StatusAPI.getApi().setColor(player, color);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast deinen Status auf ", NamedTextColor.WHITE))
                        .append(Component.text(status, color))
                        .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You've set your status on ", NamedTextColor.WHITE))
                        .append(Component.text(status, color))
                        .append(Component.text(".", NamedTextColor.WHITE)));
            }
        }
        UtilsManager.getInstance().getTablistManager().setAllPlayerTeams();
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        if (args.length == 0 || args.length == 1) {
            List<String> status = new ArrayList<>();
            status.add("<Status>");
            return status;
        }
        if (args.length == 2 || args.length == 3) {
            List<String> colorSuggestions = new ArrayList<>();
            colorSuggestions.add("aqua");
            colorSuggestions.add("black");
            colorSuggestions.add("blue");
            colorSuggestions.add("bold");
            colorSuggestions.add("dark_aqua");
            colorSuggestions.add("dark_blue");
            colorSuggestions.add("dark_gray");
            colorSuggestions.add("dark_green");
            colorSuggestions.add("dark_purple");
            colorSuggestions.add("dark_red");
            colorSuggestions.add("gold");
            colorSuggestions.add("gray");
            colorSuggestions.add("green");
            colorSuggestions.add("italic");
            colorSuggestions.add("light_purple");
            colorSuggestions.add("magic");
            colorSuggestions.add("red");
            colorSuggestions.add("reset");
            colorSuggestions.add("strikethrough");
            colorSuggestions.add("underline");
            colorSuggestions.add("white");
            colorSuggestions.add("yellow");

            return colorSuggestions;
        }
        return Collections.emptyList();
    }

    private NamedTextColor getColor(String colorString) {
        return switch (colorString) {
            case "black" -> NamedTextColor.BLACK;
            case "dark_blue" -> NamedTextColor.DARK_BLUE;
            case "dark_green" -> NamedTextColor.DARK_GREEN;
            case "dark_aqua" -> NamedTextColor.DARK_AQUA;
            case "dark_red" -> NamedTextColor.DARK_RED;
            case "dark_purple" -> NamedTextColor.DARK_PURPLE;
            case "gold" -> NamedTextColor.GOLD;
            case "gray" -> NamedTextColor.GRAY;
            case "dark_gray" -> NamedTextColor.DARK_GRAY;
            case "blue" -> NamedTextColor.BLUE;
            case "green" -> NamedTextColor.GREEN;
            case "aqua" -> NamedTextColor.AQUA;
            case "red" -> NamedTextColor.RED;
            case "light_purple" -> NamedTextColor.LIGHT_PURPLE;
            case "yellow" -> NamedTextColor.YELLOW;
            case "white" -> NamedTextColor.WHITE;
            default -> null;
        };
    }

    private void sendUsage(CommandSender sender) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/status <Status> <Farbe> / reset", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Use", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/status <status> <color> / reset", NamedTextColor.BLUE)));
        }
    }
}
