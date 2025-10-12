package de.lars.utilsmanager.commands.player;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsmanager.Main;
import de.lars.utilsmanager.util.ItemBuilder;
import de.lars.utilsmanager.util.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PrefixCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (!player.hasPermission("plugin.prefix")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if (args.length == 0) {
            NamespacedKey prefixKey = new NamespacedKey("utilsmanager", "prefix_id");

            HashMap<Integer, ItemStack> items = new HashMap<>();

            if (RankAPI.getApi().getRankID(player) == 10) {
                items.put(10, new ItemBuilder(Material.BARRIER)
                        .setDisplayName(Component.text("Dark Red", NamedTextColor.DARK_RED))
                        .setCustomId(prefixKey, "fail")
                        .build());
            } else {
                items.put(10, new ItemBuilder(Material.RED_CONCRETE)
                        .setDisplayName(Component.text("Dark Red", NamedTextColor.DARK_RED))
                        .setCustomId(prefixKey, "dark_red")
                        .build());
            }

            if (RankAPI.getApi().getRankID(player) >= 9) {
                items.put(11, new ItemBuilder(Material.BARRIER)
                        .setDisplayName(Component.text("Red", NamedTextColor.RED))
                        .setCustomId(prefixKey, "fail")
                        .build());
            } else {
                items.put(11, new ItemBuilder(Material.RED_TERRACOTTA)
                        .setDisplayName(Component.text("Red", NamedTextColor.RED))
                        .setCustomId(prefixKey, "red")
                        .build());
            }

            items.put(12, new ItemBuilder(Material.ORANGE_CONCRETE)
                    .setDisplayName(Component.text("Gold", NamedTextColor.GOLD))
                    .setCustomId(prefixKey, "gold")
                    .build());

            if (RankAPI.getApi().getRankID(player) >= 6) {
                items.put(13, new ItemBuilder(Material.BARRIER)
                        .setDisplayName(Component.text("Yellow", NamedTextColor.YELLOW))
                        .setCustomId(prefixKey, "fail")
                        .build());
            } else {
                items.put(13, new ItemBuilder(Material.YELLOW_CONCRETE)
                        .setDisplayName(Component.text("Yellow", NamedTextColor.YELLOW))
                        .setCustomId(prefixKey, "yellow")
                        .build());
            }

            items.put(14, new ItemBuilder(Material.GREEN_CONCRETE)
                    .setDisplayName(Component.text("Dark Green", NamedTextColor.DARK_GREEN))
                    .setCustomId(prefixKey, "dark_green")
                    .build());

            items.put(15, new ItemBuilder(Material.LIME_CONCRETE)
                    .setDisplayName(Component.text("Green", NamedTextColor.GREEN))
                    .setCustomId(prefixKey, "green")
                    .build());

            items.put(16, new ItemBuilder(Material.LIGHT_BLUE_CONCRETE)
                    .setDisplayName(Component.text("Aqua", NamedTextColor.AQUA))
                    .setCustomId(prefixKey, "aqua")
                    .build());

            items.put(19, new ItemBuilder(Material.CYAN_CONCRETE)
                    .setDisplayName(Component.text("Dark Aqua", NamedTextColor.DARK_AQUA))
                    .setCustomId(prefixKey, "dark_aqua")
                    .build());

            if (RankAPI.getApi().getRankID(player) >= 8) {
                items.put(20, new ItemBuilder(Material.BARRIER)
                        .setDisplayName(Component.text("Dark Blue", NamedTextColor.DARK_BLUE))
                        .setCustomId(prefixKey, "fail")
                        .build());
            } else {
                items.put(20, new ItemBuilder(Material.BLUE_CONCRETE)
                        .setDisplayName(Component.text("Dark Blue", NamedTextColor.DARK_BLUE))
                        .setCustomId(prefixKey, "dark_blue")
                        .build());
            }

            items.put(21, new ItemBuilder(Material.LIGHT_BLUE_CONCRETE)
                    .setDisplayName(Component.text("Blue", NamedTextColor.BLUE))
                    .setCustomId(prefixKey, "blue")
                    .build());

            items.put(22, new ItemBuilder(Material.MAGENTA_CONCRETE)
                    .setDisplayName(Component.text("Light Purple", NamedTextColor.LIGHT_PURPLE))
                    .setCustomId(prefixKey, "light_purple")
                    .build());

            if (RankAPI.getApi().getRankID(player) >= 7) {
                items.put(23, new ItemBuilder(Material.BARRIER)
                        .setDisplayName(Component.text("Dark Purple", NamedTextColor.DARK_PURPLE))
                        .setCustomId(prefixKey, "fail")
                        .build());
            } else {
                items.put(23, new ItemBuilder(Material.PURPLE_CONCRETE)
                        .setDisplayName(Component.text("Dark Purple", NamedTextColor.DARK_PURPLE))
                        .setCustomId(prefixKey, "dark_purple")
                        .build());
            }

            items.put(24, new ItemBuilder(Material.LIGHT_GRAY_CONCRETE)
                    .setDisplayName(Component.text("Gray", NamedTextColor.GRAY))
                    .setCustomId(prefixKey, "gray")
                    .build());

            items.put(25, new ItemBuilder(Material.GRAY_CONCRETE)
                    .setDisplayName(Component.text("Dark Gray", NamedTextColor.DARK_GRAY))
                    .setCustomId(prefixKey, "dark_gray")
                    .build());

            items.put(30, new ItemBuilder(Material.WHITE_CONCRETE)
                    .setDisplayName(Component.text("White", NamedTextColor.WHITE))
                    .setCustomId(prefixKey, "white")
                    .build());

            items.put(31, new ItemBuilder(Material.TNT)
                    .setDisplayName(Component.text("Reset Prefix", NamedTextColor.RED, TextDecoration.BOLD))
                    .setCustomId(prefixKey, "reset")
                    .build());

            items.put(32, new ItemBuilder(Material.BLACK_CONCRETE)
                    .setDisplayName(Component.text("Black", NamedTextColor.BLACK))
                    .setCustomId(prefixKey, "black")
                    .build());

            items.put(2, new ItemBuilder(Material.PAPER)
                    .setDisplayName(Component.text("Bold", NamedTextColor.WHITE, TextDecoration.BOLD))
                    .setCustomId(prefixKey, "bold")
                    .setCustomModelData(2)
                    .build());

            items.put(3, new ItemBuilder(Material.PAPER)
                    .setDisplayName(Component.text("Italic", NamedTextColor.WHITE, TextDecoration.ITALIC))
                    .setCustomId(prefixKey, "italic")
                    .setCustomModelData(3)
                    .build());

            items.put(4, new ItemBuilder(Material.PAPER)
                    .setDisplayName(Component.text("Magic", NamedTextColor.WHITE, TextDecoration.OBFUSCATED))
                    .setCustomId(prefixKey, "magic")
                    .setCustomModelData(4)
                    .build());

            items.put(5, new ItemBuilder(Material.PAPER)
                    .setDisplayName(Component.text("Strikethrough", NamedTextColor.WHITE, TextDecoration.STRIKETHROUGH))
                    .setCustomId(prefixKey, "strikethrough")
                    .setCustomModelData(5)
                    .build());

            items.put(6, new ItemBuilder(Material.PAPER)
                    .setDisplayName(Component.text("Underline", NamedTextColor.WHITE, TextDecoration.UNDERLINED))
                    .setCustomId(prefixKey, "underline")
                    .setCustomModelData(6)
                    .build());

            Inventory inv = Bukkit.createInventory(null, 4 * 9, Component.text("             Prefix", NamedTextColor.DARK_GREEN, TextDecoration.BOLD));
            items.forEach(inv::setItem);
            player.openInventory(inv);
            return;
        }
        switch (args[0].toLowerCase()) {
            case "black": {
                RankAPI.getApi().setPrefix(player, 0);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("Schwarz", NamedTextColor.BLACK))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You´ve set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("Black", NamedTextColor.BLACK))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }
            case "dark_blue": {
                RankAPI.getApi().setPrefix(player, 1);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("dunkel Blau", NamedTextColor.DARK_BLUE))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You´ve set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("dark blue", NamedTextColor.DARK_BLUE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }
            case "dark_green": {
                RankAPI.getApi().setPrefix(player, 2);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("dunkel Grün", NamedTextColor.DARK_GREEN))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You´ve set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("dark green", NamedTextColor.DARK_GREEN))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }
            case "dark_aqua": {
                RankAPI.getApi().setPrefix(player, 3);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("dunkel Türkis", NamedTextColor.DARK_AQUA))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You´ve set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("dark aqua", NamedTextColor.DARK_AQUA))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }
            case "dark_red": {
                RankAPI.getApi().setPrefix(player, 4);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("dunkel Rot", NamedTextColor.DARK_RED))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You´ve set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("dark red", NamedTextColor.DARK_RED))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }
            case "dark_purple": {
                RankAPI.getApi().setPrefix(player, 5);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("dunkel lila", NamedTextColor.DARK_PURPLE))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You´ve set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("dark purple", NamedTextColor.DARK_PURPLE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }
            case "gold": {
                RankAPI.getApi().setPrefix(player, 6);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("Gold", NamedTextColor.GOLD))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You´ve set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("gold", NamedTextColor.GOLD))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }
            case "gray": {
                RankAPI.getApi().setPrefix(player, 7);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("Grau", NamedTextColor.GRAY))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You´ve set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("gray", NamedTextColor.GRAY))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }
            case "dark_gray": {
                RankAPI.getApi().setPrefix(player, 8);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("dunkel Grau", NamedTextColor.DARK_GRAY))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You´ve set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("dark gray", NamedTextColor.DARK_GRAY))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }
            case "blue": {
                RankAPI.getApi().setPrefix(player, 9);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("Blau", NamedTextColor.BLUE))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You´ve set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("blue", NamedTextColor.BLUE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }
            case "green": {
                RankAPI.getApi().setPrefix(player, 10);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("Grün", NamedTextColor.GREEN))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You´ve set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("green", NamedTextColor.GREEN))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }
            case "aqua": {
                RankAPI.getApi().setPrefix(player, 11);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("Türkis", NamedTextColor.AQUA))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You´ve set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("aqua", NamedTextColor.AQUA))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }
            case "red": {
                RankAPI.getApi().setPrefix(player, 12);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("Rot", NamedTextColor.RED))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You've set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("red", NamedTextColor.RED))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }

            case "light_purple": {
                RankAPI.getApi().setPrefix(player, 13);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("hell Lila", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You've set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("light purple", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }

            case "yellow": {
                RankAPI.getApi().setPrefix(player, 14);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("Gelb", NamedTextColor.YELLOW))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You've set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("yellow", NamedTextColor.YELLOW))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }

            case "white": {
                RankAPI.getApi().setPrefix(player, 15);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast deinen Prefix auf ", NamedTextColor.WHITE))
                            .append(Component.text("Weiß", NamedTextColor.WHITE))
                            .append(Component.text(" gesetzt.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You've set your prefix on ", NamedTextColor.WHITE))
                            .append(Component.text("white", NamedTextColor.WHITE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }

            case "bold": {
                RankAPI.getApi().setPrefixType(player, 1);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den Prefix Effekt ", NamedTextColor.WHITE))
                            .append(Component.text("Fett", NamedTextColor.WHITE, TextDecoration.BOLD))
                            .append(Component.text(" aktiviert.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You've set the prefix effect ", NamedTextColor.WHITE))
                            .append(Component.text("bold", NamedTextColor.WHITE, TextDecoration.BOLD))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }

            case "italic": {
                RankAPI.getApi().setPrefixType(player, 2);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den Prefix Effekt ", NamedTextColor.WHITE))
                            .append(Component.text("Kursiv", NamedTextColor.WHITE, TextDecoration.ITALIC))
                            .append(Component.text(" aktiviert.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You've set the prefix effect ", NamedTextColor.WHITE))
                            .append(Component.text("italic", NamedTextColor.WHITE, TextDecoration.ITALIC))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }

            case "magic": {
                RankAPI.getApi().setPrefixType(player, 3);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den Prefix Effekt ", NamedTextColor.WHITE))
                            .append(Component.text("Magie", NamedTextColor.WHITE, TextDecoration.OBFUSCATED))
                            .append(Component.text(" aktiviert.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You've set the prefix effect ", NamedTextColor.WHITE))
                            .append(Component.text("magic", NamedTextColor.WHITE, TextDecoration.OBFUSCATED))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }

            case "strikethrough": {
                RankAPI.getApi().setPrefixType(player, 4);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den Prefix Effekt ", NamedTextColor.WHITE))
                            .append(Component.text("Durchgestrichen", NamedTextColor.WHITE, TextDecoration.STRIKETHROUGH))
                            .append(Component.text(" aktiviert.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You've set the prefix effect ", NamedTextColor.WHITE))
                            .append(Component.text("strikethrough", NamedTextColor.WHITE, TextDecoration.STRIKETHROUGH))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }

            case "underline": {
                RankAPI.getApi().setPrefixType(player, 5);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den Prefix Effekt ", NamedTextColor.WHITE))
                            .append(Component.text("Unterstrichen", NamedTextColor.WHITE, TextDecoration.UNDERLINED))
                            .append(Component.text(" aktiviert.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You've set the prefix effect ", NamedTextColor.WHITE))
                            .append(Component.text("underlined", NamedTextColor.WHITE, TextDecoration.UNDERLINED))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                break;
            }

            case "reset": {
                if (RankAPI.getApi().getRankID(player) == 5) {
                    RankAPI.getApi().setPrefix(player,6);
                    RankAPI.getApi().setPrefixType(player, 0);
                }
                if (RankAPI.getApi().getRankID(player) == 6) {
                    RankAPI.getApi().setPrefix(player,14);
                    RankAPI.getApi().setPrefixType(player, 0);
                }
                if (RankAPI.getApi().getRankID(player) == 7) {
                    RankAPI.getApi().setPrefix(player,5);
                    RankAPI.getApi().setPrefixType(player, 0);
                }
                if (RankAPI.getApi().getRankID(player) == 8) {
                    RankAPI.getApi().setPrefix(player,1);
                    RankAPI.getApi().setPrefixType(player, 0);
                }
                if (RankAPI.getApi().getRankID(player) == 9) {
                    RankAPI.getApi().setPrefix(player,12);
                    RankAPI.getApi().setPrefixType(player, 0);
                }
                if (RankAPI.getApi().getRankID(player) == 10) {
                    RankAPI.getApi().setPrefix(player, 4);
                    RankAPI.getApi().setPrefixType(player, 0);
                }
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Du hast deinen Prefix zurückgesetzt.", NamedTextColor.GOLD)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("You´ve reset your prefix.", NamedTextColor.GOLD)));
                }
                break;
            }
            default:
                sendUsage(player);
                break;
        }
        Main.getInstance().getTablistManager().setAllPlayerTeams();
    }

    @Override
    public @NotNull Collection<String> suggest(final @NotNull CommandSourceStack commandSourceStack, final String[] args) {
        if (args.length == 0 || args.length == 1) {
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

    private void sendUsage(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Component.text()
                    .append(Component.text("Verwendung", NamedTextColor.GRAY))
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/prefix <Farbe>", NamedTextColor.BLUE))
                    .build());
        } else {
            player.sendMessage(Component.text()
                    .append(Component.text("Use", NamedTextColor.GRAY))
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/prefix <Color>", NamedTextColor.BLUE))
                    .build());
        }
    }
}
