package de.lars.utilsmanager.listener.misc;

import de.lars.apiManager.coinAPI.CoinAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsmanager.Main;
import de.lars.utilsmanager.util.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ShopListener implements Listener {

    int balence;
    int price;
    int sellprice;


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryKlick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getView().title().equals(Component.text("             Shop", NamedTextColor.DARK_GREEN, TextDecoration.BOLD))) {
            event.setCancelled(true);

            Component clickedItem = event.getCurrentItem().getItemMeta().itemName();
            String buycopper = "buycopper";
            String buyamethyst = "buyamethyst";
            String buydiamond = "buydiamond";
            String buynetherite = "buynetherite";
            String buyspawner = "buyspawner";
            String sellcopper = "sellcopper";
            String sellamethyst = "sellamethyst";
            String selldiamond = "selldiamond";
            String sellnetherite = "sellnetherite";
            String sellspawner = "sellspawner";
            if (clickedItem.equals(buycopper)) {
                /* ItemStack copper = new ItemStack(Material.COPPER_INGOT);

                AnvilGUI.Builder Anvil = new AnvilGUI.Builder();
                Anvil.interactableSlots(0);
                Anvil.title("Number of Copper");
                Anvil.itemOutput(copper);
                Anvil.itemLeft(copper);
                Anvil.text("");
                Anvil.onClick((slot, stateSnapshot) -> {

                    try {
                        Integer.parseInt(stateSnapshot.getText());
                    } catch (NumberFormatException e) {
                        Anvil.text("");
                        return Arrays.asList(net.wesjd.anvilgui.AnvilGUI.ResponseAction.updateTitle("Not a Number", false));
                    }
                    if (!Objects.equals(stateSnapshot.getText(), "")) {
                        Integer number = Integer.parseInt(stateSnapshot.getText());
                        player.performCommand("buy copper " + number);
                        return Arrays.asList(
                                net.wesjd.anvilgui.AnvilGUI.ResponseAction.close()

                        );
                    } else {
                        Anvil.text("");
                        return Arrays.asList(net.wesjd.anvilgui.AnvilGUI.ResponseAction.replaceInputText(""));
                    }
                });
                Anvil.plugin(Main.getInstance());
                Anvil.open(player);
                */
                PlayerInventory inventory = player.getInventory();
                ItemStack copper = new ItemStack(Material.COPPER_INGOT);
                balence = CoinAPI.getApi().getCoins(player);
                price = 10;
                if (price > balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + (price - balence) + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + (price - balence) + "$.");
                    }
                    return;
                }
                inventory.addItem(copper);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast ", NamedTextColor.GREEN))
                            .append(Component.text("1 ", NamedTextColor.GOLD))
                            .append(Component.text("Kupfer Barren gekauft, für ", NamedTextColor.YELLOW))
                            .append(Component.text(price + "$", NamedTextColor.LIGHT_PURPLE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought ", NamedTextColor.GREEN))
                            .append(Component.text("1 ", NamedTextColor.GOLD))
                            .append(Component.text("Copper Ingot, for ", NamedTextColor.YELLOW))
                            .append(Component.text(price + "$", NamedTextColor.LIGHT_PURPLE)));
                }
                CoinAPI.getApi().removeCoins(player, price);
                return;
            }
            if (clickedItem.equals(buyamethyst)) {
                PlayerInventory inventory = player.getInventory();
                ItemStack amethyst = new ItemStack(Material.AMETHYST_SHARD);
                balence = CoinAPI.getApi().getCoins(player);
                price = 30;
                if (price > balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + (price - balence) + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + (price - balence) + "$.");
                    }
                    return;
                }
                inventory.addItem(amethyst);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast ", NamedTextColor.GREEN))
                            .append(Component.text("1 ", NamedTextColor.GOLD))
                            .append(Component.text("Amethyst Scherbe gekauft, für ", NamedTextColor.YELLOW))
                            .append(Component.text(price + "$", NamedTextColor.LIGHT_PURPLE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought ", NamedTextColor.GREEN))
                            .append(Component.text("1 ", NamedTextColor.GOLD))
                            .append(Component.text("Amethyst Shard, for ", NamedTextColor.YELLOW))
                            .append(Component.text(price + "$", NamedTextColor.LIGHT_PURPLE)));
                }
                CoinAPI.getApi().removeCoins(player, price);
                return;
            }
            if (clickedItem.equals(buydiamond)) {
                PlayerInventory inventory = player.getInventory();
                ItemStack diamond = new ItemStack(Material.DIAMOND);
                balence = CoinAPI.getApi().getCoins(player);
                price = 150;
                if (price > balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + (price - balence) + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + (price - balence) + "$.");
                    }
                    return;
                }
                inventory.addItem(diamond);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast ", NamedTextColor.GREEN))
                            .append(Component.text("1 ", NamedTextColor.GOLD))
                            .append(Component.text("Diamant gekauft, für ", NamedTextColor.YELLOW))
                            .append(Component.text(price + "$", NamedTextColor.LIGHT_PURPLE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought ", NamedTextColor.GREEN))
                            .append(Component.text("1 ", NamedTextColor.GOLD))
                            .append(Component.text("Diamond, for ", NamedTextColor.YELLOW))
                            .append(Component.text(price + "$", NamedTextColor.LIGHT_PURPLE)));
                }
                CoinAPI.getApi().removeCoins(player, price);
                return;
            }
            if (clickedItem.equals(buynetherite)) {
                PlayerInventory inventory = player.getInventory();
                ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT);
                balence = CoinAPI.getApi().getCoins(player);
                price = 1250;
                if (price > balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + (price - balence) + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + (price - balence) + "$.");
                    }
                    return;
                }
                inventory.addItem(netherite);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast ", NamedTextColor.GREEN))
                            .append(Component.text("1 ", NamedTextColor.GOLD))
                            .append(Component.text("Netherite Barren gekauft, für ", NamedTextColor.YELLOW))
                            .append(Component.text(price + "$", NamedTextColor.LIGHT_PURPLE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought ", NamedTextColor.GREEN))
                            .append(Component.text("1 ", NamedTextColor.GOLD))
                            .append(Component.text("Netherite Ingot, for ", NamedTextColor.YELLOW))
                            .append(Component.text(price + "$", NamedTextColor.LIGHT_PURPLE)));
                }
                CoinAPI.getApi().removeCoins(player, price);
                return;
            }
            if (clickedItem.equals(buyspawner)) {
                PlayerInventory inventory = player.getInventory();
                ItemStack spawner = new ItemStack(Material.SPAWNER);
                balence = CoinAPI.getApi().getCoins(player);
                price = 10000;
                if (price >= balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + (price - balence) + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + (price - balence) + "$.");
                    }
                    return;
                }
                inventory.addItem(spawner);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast ", NamedTextColor.GREEN))
                            .append(Component.text("1 ", NamedTextColor.GOLD))
                            .append(Component.text("Spawner gekauft, für ", NamedTextColor.YELLOW))
                            .append(Component.text(price + "$", NamedTextColor.LIGHT_PURPLE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought ", NamedTextColor.GREEN))
                            .append(Component.text("1 ", NamedTextColor.GOLD))
                            .append(Component.text("Spawner, for ", NamedTextColor.YELLOW))
                            .append(Component.text(price + "$", NamedTextColor.LIGHT_PURPLE)));
                }
                CoinAPI.getApi().removeCoins(player, price);
                return;
            }
            if (clickedItem.equals(sellcopper)) {
                PlayerInventory inventory = player.getInventory();
                ItemStack copper = new ItemStack(Material.COPPER_INGOT);
                int existing = inventory.all(Material.COPPER_INGOT).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                sellprice = 5;
                if (1 > existing) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Kupfer Barren! Dir fehlen " + (1 - existing) + ".");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough Copper Ingots! You miss " + (1 - existing) + ".");
                    }
                    return;
                }
                inventory.removeItem(copper);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast ", NamedTextColor.WHITE))
                            .append(Component.text("1", NamedTextColor.GOLD))
                            .append(Component.text(" Kupfer Barren verkauft, für ", NamedTextColor.WHITE))
                            .append(Component.text(sellprice + "$.", NamedTextColor.LIGHT_PURPLE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You sold ", NamedTextColor.WHITE))
                            .append(Component.text("1", NamedTextColor.GOLD))
                            .append(Component.text(" Copper Ingot, for ", NamedTextColor.WHITE))
                            .append(Component.text(sellprice + "$.", NamedTextColor.LIGHT_PURPLE)));
                }
                CoinAPI.getApi().addCoins(player, sellprice);
                return;
            }
            if (clickedItem.equals(sellamethyst)) {
                PlayerInventory inventory = player.getInventory();
                ItemStack amethyst = new ItemStack(Material.AMETHYST_SHARD);
                int existing = inventory.all(Material.AMETHYST_SHARD).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                sellprice = 20;
                if (1 > existing) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Amethyst Scherben! Dir fehlen " + (1 - existing) + ".");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough Amethyst Shards! You miss " + (1 - existing) + ".");
                    }
                    return;
                }
                inventory.removeItem(amethyst);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                            .append(Component.text("1", NamedTextColor.GOLD))
                            .append(Component.text(" Amethyst Scherbe verkauft, für ", NamedTextColor.WHITE))
                            .append(Component.text(sellprice, NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text("$.")));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("You sold ", NamedTextColor.WHITE))
                            .append(Component.text("1", NamedTextColor.GOLD))
                            .append(Component.text(" Amethyst Shard, for ", NamedTextColor.WHITE))
                            .append(Component.text(sellprice, NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text("$.")));
                }
                CoinAPI.getApi().addCoins(player, sellprice);
                return;
            }
            if (clickedItem.equals(selldiamond)) {
                PlayerInventory inventory = player.getInventory();
                ItemStack diamond = new ItemStack(Material.DIAMOND);
                int existing = inventory.all(Material.DIAMOND).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                sellprice = 100;
                if (1 > existing) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Diamanten! Dir fehlen " + (1 - existing) + ".");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough Diamonds! You miss " + (1 - existing) + ".");
                    }
                    return;
                }
                inventory.removeItem(diamond);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                            .append(Component.text("1", NamedTextColor.GOLD))
                            .append(Component.text(" Diamant verkauft, für ", NamedTextColor.WHITE))
                            .append(Component.text(sellprice, NamedTextColor.AQUA))
                            .append(Component.text("$.")));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("You sold ", NamedTextColor.WHITE))
                            .append(Component.text("1", NamedTextColor.GOLD))
                            .append(Component.text(" Diamond, for ", NamedTextColor.WHITE))
                            .append(Component.text(sellprice, NamedTextColor.AQUA))
                            .append(Component.text("$.")));
                }
                CoinAPI.getApi().addCoins(player, sellprice);
                return;
            }
            if (clickedItem.equals(sellnetherite)) {
                PlayerInventory inventory = player.getInventory();
                ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT);
                int existing = inventory.all(Material.NETHERITE_INGOT).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                sellprice = 1000;
                if (1 > existing) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Netherite Barren! Dir fehlen " + (1 - existing) + ".");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough Netherite Ingots! You miss " + (1 - existing) + ".");
                    }
                    return;
                }
                inventory.removeItem(netherite);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                            .append(Component.text("1", NamedTextColor.GOLD))
                            .append(Component.text(" Netherite Barren verkauft, für ", NamedTextColor.WHITE))
                            .append(Component.text(sellprice, NamedTextColor.DARK_GRAY))
                            .append(Component.text("$.")));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("You sold ", NamedTextColor.WHITE))
                            .append(Component.text("1", NamedTextColor.GOLD))
                            .append(Component.text(" Netherite Ingot, for ", NamedTextColor.WHITE))
                            .append(Component.text(sellprice, NamedTextColor.DARK_GRAY))
                            .append(Component.text("$.")));
                }
                CoinAPI.getApi().addCoins(player, sellprice);
                return;
            }
            if (clickedItem.equals(sellspawner)) {
                PlayerInventory inventory = player.getInventory();
                ItemStack spawner = new ItemStack(Material.SPAWNER);
                int existing = inventory.all(Material.SPAWNER).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                sellprice = 7500;
                if (1 > existing) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Spawner! Dir fehlen " + (1 - existing) + ".");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough Spawner! You miss " + (1 - existing) + ".");
                    }
                    return;
                }
                inventory.removeItem(spawner);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                            .append(Component.text("1", NamedTextColor.GOLD))
                            .append(Component.text(" Spawner verkauft, für ", NamedTextColor.WHITE))
                            .append(Component.text(sellprice, NamedTextColor.BLACK))
                            .append(Component.text("$.")));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("You sold ", NamedTextColor.WHITE))
                            .append(Component.text("1", NamedTextColor.GOLD))
                            .append(Component.text(" spawner, for ", NamedTextColor.WHITE))
                            .append(Component.text(sellprice, NamedTextColor.BLACK))
                            .append(Component.text("$.")));
                }
                CoinAPI.getApi().addCoins(player, sellprice);
                return;
            }
        }
    }

    @EventHandler
    public void onInventoryKlickPrefix(InventoryClickEvent event) {

        World world = Bukkit.getWorld("world");
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getView().title().equals(Component.text("             Prefix", NamedTextColor.DARK_GREEN, TextDecoration.BOLD))) {
            event.setCancelled(true);
            Component clickedItem = event.getCurrentItem().getItemMeta().itemName();
            Component dark_red = Component.text("dark_red");
            Component red = Component.text("red");
            Component gold = Component.text("gold");
            Component yellow = Component.text("yellow");
            Component dark_green = Component.text("dark_green");
            Component green = Component.text("green");
            Component aqua = Component.text("aqua");
            Component dark_aqua = Component.text("dark_aqua");
            Component dark_blue = Component.text("dark_blue");
            Component blue = Component.text("blue");
            Component light_purple = Component.text("light_purple");
            Component dark_purple = Component.text("dark_purple");
            Component gray = Component.text("gray");
            Component dark_gray = Component.text("dark_gray");
            Component white = Component.text("white");
            Component black = Component.text("black");
            Component reset = Component.text("reset");
            Component bold = Component.text("bold");
            Component italic = Component.text("italic");
            Component magic = Component.text("magic");
            Component strikethrough = Component.text("strikethrough");
            Component underline = Component.text("underline");

            if (clickedItem.equals(dark_red)) {
                player.performCommand("prefix dark_red");
            }
            if (clickedItem.equals(red)) {
                player.performCommand("prefix red");
            }
            if (clickedItem.equals(gold)) {
                player.performCommand("prefix gold");
            }
            if (clickedItem.equals(yellow)) {
                player.performCommand("prefix yellow");
            }
            if (clickedItem.equals(dark_green)) {
                player.performCommand("prefix dark_green");
            }
            if (clickedItem.equals(green)) {
                player.performCommand("prefix green");
            }
            if (clickedItem.equals(aqua)) {
                player.performCommand("prefix aqua");
            }
            if (clickedItem.equals(dark_aqua)) {
                player.performCommand("prefix dark_aqua");
            }
            if (clickedItem.equals(dark_blue)) {
                player.performCommand("prefix dark_blue");
            }
            if (clickedItem.equals(blue)) {
                player.performCommand("prefix blue");
            }
            if (clickedItem.equals(light_purple)) {
                player.performCommand("prefix light_purple");
            }
            if (clickedItem.equals(dark_purple)) {
                player.performCommand("prefix dark_purple");
            }
            if (clickedItem.equals(gray)) {
                player.performCommand("prefix gray");
            }
            if (clickedItem.equals(dark_gray)) {
                player.performCommand("prefix dark_gray");
            }
            if (clickedItem.equals(white)) {
                player.performCommand("prefix white");
            }
            if (clickedItem.equals(black)) {
                player.performCommand("prefix black");
            }

            if (clickedItem.equals(bold)) {
                player.performCommand("prefix bold");
            }
            if (clickedItem.equals(italic)) {
                player.performCommand("prefix italic");
            }
            if (clickedItem.equals(magic)) {
                player.performCommand("prefix magic");
            }
            if (clickedItem.equals(strikethrough)) {
                player.performCommand("prefix strikethrough");
            }
            if (clickedItem.equals(underline)) {
                player.performCommand("prefix underline");
            }

            if (clickedItem.equals(reset)) {
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
                    player.sendMessage(NamedTextColor.GOLD + "Du hast deinen Prefix zurückgesetzt.");
                } else {
                    player.sendMessage(NamedTextColor.GOLD + "You´ve reset your prefix.");
                }
                Main.getInstance().getTablistManager().setAllPlayerTeams();
            }
        }
    }

}
