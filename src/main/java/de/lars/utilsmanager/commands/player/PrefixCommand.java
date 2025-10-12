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
            HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();
            if (RankAPI.getApi().getRankID(player) == 10) {
                integerItemStackHashMap.put(10, new ItemBuilder(Material.BARRIER).setDisplayname(Component.text("Dark Red", NamedTextColor.DARK_RED)).setLocalizedName(Component.text("fail")).build());
            } else {
                integerItemStackHashMap.put(10, new ItemBuilder(Material.RED_CONCRETE).setDisplayname(Component.text("Dark Red", NamedTextColor.DARK_RED)).setLocalizedName(Component.text("dark_red")).build());
            }
            if ((RankAPI.getApi().getRankID(player) >= 9)) {
                integerItemStackHashMap.put(11, new ItemBuilder(Material.BARRIER).setDisplayname(Component.text("Red", NamedTextColor.RED)).setLocalizedName(Component.text("fail")).build());
            } else {
                integerItemStackHashMap.put(11, new ItemBuilder(Material.RED_TERRACOTTA).setDisplayname(Component.text("Red", NamedTextColor.RED)).setLocalizedName(Component.text("red")).build());
            }
            integerItemStackHashMap.put(12, new ItemBuilder(Material.ORANGE_CONCRETE).setDisplayname(Component.text("Gold", NamedTextColor.GOLD)).setLocalizedName(Component.text("gold")).build());
            if ((RankAPI.getApi().getRankID(player) >= 6)) {
                integerItemStackHashMap.put(13, new ItemBuilder(Material.BARRIER).setDisplayname(Component.text("Yellow", NamedTextColor.YELLOW)).setLocalizedName(Component.text("fail")).build());
            } else {
                integerItemStackHashMap.put(13, new ItemBuilder(Material.YELLOW_CONCRETE).setDisplayname(Component.text("Yellow", NamedTextColor.YELLOW)).setLocalizedName(Component.text("yellow")).build());
            }
            integerItemStackHashMap.put(14, new ItemBuilder(Material.GREEN_CONCRETE).setDisplayname(Component.text("Dark Green", NamedTextColor.DARK_GREEN)).setLocalizedName(Component.text("dark_green")).build());
            integerItemStackHashMap.put(15, new ItemBuilder(Material.LIME_CONCRETE).setDisplayname(Component.text("Green", NamedTextColor.GREEN)).setLocalizedName(Component.text("green")).build());
            integerItemStackHashMap.put(16, new ItemBuilder(Material.LIGHT_BLUE_CONCRETE).setDisplayname(Component.text("Aqua", NamedTextColor.AQUA)).setLocalizedName(Component.text("aqua")).build());

            integerItemStackHashMap.put(19, new ItemBuilder(Material.CYAN_CONCRETE).setDisplayname(Component.text("Dark Aqua", NamedTextColor.DARK_AQUA)).setLocalizedName(Component.text("dark_aqua")).build());
            if ((RankAPI.getApi().getRankID(player) >= 8)) {
                integerItemStackHashMap.put(20, new ItemBuilder(Material.BARRIER).setDisplayname(Component.text("Dark Blue", NamedTextColor.DARK_BLUE)).setLocalizedName(Component.text("fail")).build());
            } else {
                integerItemStackHashMap.put(20, new ItemBuilder(Material.BLUE_CONCRETE).setDisplayname(Component.text("Dark Blue", NamedTextColor.DARK_BLUE)).setLocalizedName(Component.text("dark_blue")).build());
            }
            integerItemStackHashMap.put(21, new ItemBuilder(Material.LIGHT_BLUE_CONCRETE).setDisplayname(Component.text("Blue", NamedTextColor.BLUE)).setLocalizedName(Component.text("blue")).build());
            integerItemStackHashMap.put(22, new ItemBuilder(Material.MAGENTA_CONCRETE).setDisplayname(Component.text("Light Purple", NamedTextColor.LIGHT_PURPLE)).setLocalizedName(Component.text("light_purple")).build());
            if ((RankAPI.getApi().getRankID(player) >= 7)) {
                integerItemStackHashMap.put(23, new ItemBuilder(Material.BARRIER).setDisplayname(Component.text("Dark Purple", NamedTextColor.DARK_PURPLE)).setLocalizedName(Component.text("fail")).build());
            } else {
                integerItemStackHashMap.put(23, new ItemBuilder(Material.PURPLE_CONCRETE).setDisplayname(Component.text("Dark Purple", NamedTextColor.DARK_PURPLE)).setLocalizedName(Component.text("dark_purple")).build());
            }
            integerItemStackHashMap.put(24, new ItemBuilder(Material.LIGHT_GRAY_CONCRETE).setDisplayname(Component.text("Gray", NamedTextColor.GRAY)).setLocalizedName(Component.text("gray")).build());
            integerItemStackHashMap.put(25, new ItemBuilder(Material.GRAY_CONCRETE).setDisplayname(Component.text("Dark Gray", NamedTextColor.DARK_GRAY)).setLocalizedName(Component.text("dark_gray")).build());

            integerItemStackHashMap.put(30, new ItemBuilder(Material.WHITE_CONCRETE).setDisplayname(Component.text("White", NamedTextColor.WHITE)).setLocalizedName(Component.text("white")).build());
            integerItemStackHashMap.put(31, new ItemBuilder(Material.TNT).setDisplayname(Component.text("Reset Prefix", NamedTextColor.RED, TextDecoration.BOLD)).setLocalizedName(Component.text("reset")).build());
            integerItemStackHashMap.put(32, new ItemBuilder(Material.BLACK_CONCRETE).setDisplayname(Component.text("Black", NamedTextColor.BLACK)).setLocalizedName(Component.text("black")).build());

            integerItemStackHashMap.put(2, new ItemBuilder(Material.PAPER).setDisplayname(Component.text("Bold", NamedTextColor.WHITE, TextDecoration.BOLD)).setLocalizedName(Component.text("bold")).setCustomModelData(2).build());
            integerItemStackHashMap.put(3, new ItemBuilder(Material.PAPER).setDisplayname(Component.text("Italic", NamedTextColor.WHITE, TextDecoration.ITALIC)).setLocalizedName(Component.text("italic")).setCustomModelData(3).build());
            integerItemStackHashMap.put(4, new ItemBuilder(Material.PAPER).setDisplayname(Component.text("Magic", NamedTextColor.WHITE, TextDecoration.OBFUSCATED)).setLocalizedName(Component.text("magic")).setCustomModelData(4).build());
            integerItemStackHashMap.put(5, new ItemBuilder(Material.PAPER).setDisplayname(Component.text("Strikethrough", NamedTextColor.WHITE, TextDecoration.STRIKETHROUGH)).setLocalizedName(Component.text("strikethrough")).setCustomModelData(5).build());
            integerItemStackHashMap.put(6, new ItemBuilder(Material.PAPER).setDisplayname(Component.text("Underline", NamedTextColor.WHITE, TextDecoration.UNDERLINED)).setLocalizedName(Component.text("underline")).setCustomModelData(6).build());

            Inventory i = Bukkit.createInventory(null, 4 * 9, Component.text("             Prefix", NamedTextColor.DARK_GREEN, TextDecoration.BOLD));
            for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
                i.setItem(integerItemStackEntry.getKey() , integerItemStackEntry.getValue());
            }
            player.openInventory(i);
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
