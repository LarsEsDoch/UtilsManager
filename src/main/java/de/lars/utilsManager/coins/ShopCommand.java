package de.lars.utilsManager.coins;

import de.lars.utilsManager.utils.ItemBuilder;
import de.lars.utilsManager.utils.Statements;
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

import java.util.HashMap;
import java.util.Map;

public class ShopCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player player = (Player) stack.getSender();
        if (!(player.hasPermission("plugin.shop"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();
        integerItemStackHashMap.put(0, new ItemBuilder(Material.COPPER_INGOT).setDisplayname(Component.text("Buy Copper", NamedTextColor.YELLOW)).setLocalizedName(Component.text("buycopper")).build());
        integerItemStackHashMap.put(9, new ItemBuilder(Material.OAK_SIGN).setDisplayname(Component.text("Price: 10$", NamedTextColor.GRAY)).setLocalizedName(Component.text("pricecopper")).build());
        integerItemStackHashMap.put(18, new ItemBuilder(Material.OAK_SIGN).setDisplayname(Component.text("Sellprice: 5$", NamedTextColor.GRAY)).setLocalizedName(Component.text("sellpricecopper")).build());
        integerItemStackHashMap.put(27, new ItemBuilder(Material.COPPER_INGOT).setDisplayname(Component.text("Sell Copper", NamedTextColor.YELLOW)).setLocalizedName(Component.text("sellcopper")).build());

        integerItemStackHashMap.put(2, new ItemBuilder(Material.AMETHYST_SHARD).setDisplayname(Component.text("Buy Amethyst Shard", NamedTextColor.LIGHT_PURPLE)).setLocalizedName(Component.text("buyamethyst")).build());
        integerItemStackHashMap.put(11, new ItemBuilder(Material.OAK_SIGN).setDisplayname(Component.text("Price: 30$", NamedTextColor.GRAY)).setLocalizedName(Component.text("priceamethyst")).build());
        integerItemStackHashMap.put(20, new ItemBuilder(Material.OAK_SIGN).setDisplayname(Component.text("Sellprice: 20$", NamedTextColor.GRAY)).setLocalizedName(Component.text("sellpriceamethyst")).build());
        integerItemStackHashMap.put(29, new ItemBuilder(Material.AMETHYST_SHARD).setDisplayname(Component.text("Sell Amethyst Shard", NamedTextColor.LIGHT_PURPLE)).setLocalizedName(Component.text("sellamethyst")).build());

        integerItemStackHashMap.put(4, new ItemBuilder(Material.DIAMOND).setDisplayname(Component.text("Buy Diamond", NamedTextColor.AQUA)).setLocalizedName(Component.text("buydiamond")).build());
        integerItemStackHashMap.put(13, new ItemBuilder(Material.OAK_SIGN).setDisplayname(Component.text("Price: 150$", NamedTextColor.GRAY)).setLocalizedName(Component.text("pricediamond")).build());
        integerItemStackHashMap.put(22, new ItemBuilder(Material.OAK_SIGN).setDisplayname(Component.text("Sellprice: 100$", NamedTextColor.GRAY)).setLocalizedName(Component.text("sellpricediamond")).build());
        integerItemStackHashMap.put(31, new ItemBuilder(Material.DIAMOND).setDisplayname(Component.text("Sell Diamond", NamedTextColor.AQUA)).setLocalizedName(Component.text("selldiamond")).build());

        integerItemStackHashMap.put(6, new ItemBuilder(Material.NETHERITE_INGOT).setDisplayname(Component.text("Buy Netherite Ingot", NamedTextColor.RED)).setLocalizedName(Component.text("buynetherite")).build());
        integerItemStackHashMap.put(15, new ItemBuilder(Material.OAK_SIGN).setDisplayname(Component.text("Price: 1.250$", NamedTextColor.GRAY)).setLocalizedName(Component.text("pricenetherite")).build());
        integerItemStackHashMap.put(24, new ItemBuilder(Material.OAK_SIGN).setDisplayname(Component.text("Sellprice: 1.000$", NamedTextColor.GRAY)).setLocalizedName(Component.text("sellpricenetherite")).build());
        integerItemStackHashMap.put(33, new ItemBuilder(Material.NETHERITE_INGOT).setDisplayname(Component.text("Sell Netherite Ingot", NamedTextColor.RED)).setLocalizedName(Component.text("sellnetherite")).build());

        integerItemStackHashMap.put(8, new ItemBuilder(Material.SPAWNER).setDisplayname(Component.text("Buy Spawner", NamedTextColor.DARK_GRAY)).setLocalizedName(Component.text("buyspawner")).build());
        integerItemStackHashMap.put(17, new ItemBuilder(Material.OAK_SIGN).setDisplayname(Component.text("Price: 10.000$", NamedTextColor.GRAY)).setLocalizedName(Component.text("pricespawner")).build());
        integerItemStackHashMap.put(26, new ItemBuilder(Material.OAK_SIGN).setDisplayname(Component.text("Sellprice: 7.500$", NamedTextColor.GRAY)).setLocalizedName(Component.text("sellpricespawner")).build());
        integerItemStackHashMap.put(35, new ItemBuilder(Material.SPAWNER).setDisplayname(Component.text("Sell Spawner", NamedTextColor.DARK_GRAY)).setLocalizedName(Component.text("sellspawner")).build());
        Inventory i = Bukkit.createInventory(null, 4*9, Component.text("             Shop", NamedTextColor.DARK_GREEN, TextDecoration.BOLD));
        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            i.setItem(integerItemStackEntry.getKey() , integerItemStackEntry.getValue());
        }
        player.openInventory(i);
    }
}
