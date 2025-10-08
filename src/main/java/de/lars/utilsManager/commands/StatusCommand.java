package de.lars.utilsManager.commands;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.Statements;
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
            RankAPI.getApi().setStatus(player, "00-00");
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
            int color = getColor(args[1]);
            if (color == 404) {
                sendUsage(player);
                return;
            }
            if (color < 0 || color > 15) {
                sendUsage(player);
                return;
            }
            RankAPI.getApi().setStatus(player, status + "," + color);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast deinen Status auf ", NamedTextColor.WHITE))
                        .append(Component.text(status, getNamedTextColor(color)))
                        .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You've set your status on ", NamedTextColor.WHITE))
                        .append(Component.text(status, getNamedTextColor(color)))
                        .append(Component.text(".", NamedTextColor.WHITE)));
            }
        }
        Main.getInstance().getTablistManager().setAllPlayerTeams();
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (args.length == 1 || args.length == 0) {
            List<String> rangcommands = new ArrayList<>();
            rangcommands.add("<Status>");
            return rangcommands;
        }
        if (args.length == 2) {
            List<String> rangcommands = new ArrayList<>();
            rangcommands.add("aqua");
            rangcommands.add("black");
            rangcommands.add("blue");
            rangcommands.add("bold");
            rangcommands.add("dark_aqua");
            rangcommands.add("dark_blue");
            rangcommands.add("dark_gray");
            rangcommands.add("dark_green");
            rangcommands.add("dark_purple");
            rangcommands.add("dark_red");
            rangcommands.add("gold");
            rangcommands.add("gray");
            rangcommands.add("green");
            rangcommands.add("italic");
            rangcommands.add("light_purple");
            rangcommands.add("magic");
            rangcommands.add("red");
            rangcommands.add("reset");
            rangcommands.add("strikethrough");
            rangcommands.add("underline");
            rangcommands.add("white");
            rangcommands.add("yellow");

            return rangcommands;
        }
        return Collections.emptyList();
    }

    private static NamedTextColor getNamedTextColor(Integer prefixID) {
        return switch (prefixID) {
            case 0 -> NamedTextColor.BLACK;
            case 1 -> NamedTextColor.DARK_BLUE;
            case 2 -> NamedTextColor.DARK_GREEN;
            case 3 -> NamedTextColor.DARK_AQUA;
            case 4 -> NamedTextColor.DARK_RED;
            case 5 -> NamedTextColor.DARK_PURPLE;
            case 6 -> NamedTextColor.GOLD;
            case 7 -> NamedTextColor.GRAY;
            case 8 -> NamedTextColor.DARK_GRAY;
            case 9 -> NamedTextColor.BLUE;
            case 10 -> NamedTextColor.GREEN;
            case 11 -> NamedTextColor.AQUA;
            case 12 -> NamedTextColor.RED;
            case 13 -> NamedTextColor.LIGHT_PURPLE;
            case 14 -> NamedTextColor.YELLOW;
            default -> NamedTextColor.WHITE;
        };
    }

    private Integer getColor(String colorString) {
        return switch (colorString) {
            case "black" -> 0;
            case "dark_blue" -> 1;
            case "dark_green" -> 2;
            case "dark_aqua" -> 3;
            case "dark_red" -> 4;
            case "dark_purple" -> 5;
            case "gold" -> 6;
            case "gray" -> 7;
            case "dark_gray" -> 8;
            case "blue" -> 9;
            case "green" -> 10;
            case "aqua" -> 11;
            case "red" -> 12;
            case "light_purple" -> 13;
            case "yellow" -> 14;
            case "white" -> 15;
            default -> 404;
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
