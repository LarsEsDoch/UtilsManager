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
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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
            NamespacedKey prefixKey = new NamespacedKey("utilsmanager", "shop_id");

            ItemStack clickedItem = event.getCurrentItem();
            ItemMeta meta = clickedItem.getItemMeta();
            if (meta == null) return;

            PersistentDataContainer data = meta.getPersistentDataContainer();
            String id = data.get(prefixKey, PersistentDataType.STRING);
            if (id == null) return;

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
            if (id.equalsIgnoreCase(buycopper)) {
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
            if (id.equalsIgnoreCase(buyamethyst)) {
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
            if (id.equalsIgnoreCase(buydiamond)) {
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
            if (id.equalsIgnoreCase(buynetherite)) {
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
            if (id.equalsIgnoreCase(buyspawner)) {
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
            if (id.equalsIgnoreCase(sellcopper)) {
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
            if (id.equalsIgnoreCase(sellamethyst)) {
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
            if (id.equalsIgnoreCase(selldiamond)) {
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
            if (id.equalsIgnoreCase(sellnetherite)) {
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
            if (id.equalsIgnoreCase(sellspawner)) {
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
            NamespacedKey prefixKey = new NamespacedKey("utilsmanager", "prefix_id");

            ItemStack clickedItem = event.getCurrentItem();
            ItemMeta meta = clickedItem.getItemMeta();
            if (meta == null) return;

            PersistentDataContainer data = meta.getPersistentDataContainer();
            String id = data.get(prefixKey, PersistentDataType.STRING);
            if (id == null) return;

            if (id.equalsIgnoreCase("reset")) {
                int rankId = RankAPI.getApi().getRankID(player);

                switch (rankId) {
                    case 5 -> RankAPI.getApi().setPrefix(player, 6);
                    case 6 -> RankAPI.getApi().setPrefix(player, 14);
                    case 7 -> RankAPI.getApi().setPrefix(player, 5);
                    case 8 -> RankAPI.getApi().setPrefix(player, 1);
                    case 9 -> RankAPI.getApi().setPrefix(player, 12);
                    case 10 -> RankAPI.getApi().setPrefix(player, 4);
                }

                RankAPI.getApi().setPrefixType(player, 0);

                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Component.text("Du hast deinen Prefix zurückgesetzt.", NamedTextColor.GOLD));
                } else {
                    player.sendMessage(Component.text("You’ve reset your prefix.", NamedTextColor.GOLD));
                }

                Main.getInstance().getTablistManager().setAllPlayerTeams();
                return;
            }

            if (id.equalsIgnoreCase("fail")) {
                player.sendMessage(Component.text("You cannot use this prefix!", NamedTextColor.RED));
                return;
            }

            player.performCommand("prefix " + id);
        }
    }
}
