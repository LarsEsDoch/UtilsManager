package de.lars.utilsmanager.commands.economy;

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

import java.util.HashMap;
import java.util.Map;

public class ShopCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (!(player.hasPermission("plugin.shop"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        NamespacedKey shopKey = new NamespacedKey("utilsmanager", "shop_id");

        HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();
        integerItemStackHashMap.put(0, new ItemBuilder(Material.COPPER_INGOT).setDisplayName(Component.text("Buy Copper", NamedTextColor.YELLOW)).setCustomId(shopKey,"buycopper").build());
        integerItemStackHashMap.put(9, new ItemBuilder(Material.OAK_SIGN).setDisplayName(Component.text("Price: 10$", NamedTextColor.GRAY)).setCustomId(shopKey,"pricecopper").build());
        integerItemStackHashMap.put(18, new ItemBuilder(Material.OAK_SIGN).setDisplayName(Component.text("Sellprice: 5$", NamedTextColor.GRAY)).setCustomId(shopKey,"sellpricecopper").build());
        integerItemStackHashMap.put(27, new ItemBuilder(Material.COPPER_INGOT).setDisplayName(Component.text("Sell Copper", NamedTextColor.YELLOW)).setCustomId(shopKey,"sellcopper").build());

        integerItemStackHashMap.put(2, new ItemBuilder(Material.AMETHYST_SHARD).setDisplayName(Component.text("Buy Amethyst Shard", NamedTextColor.LIGHT_PURPLE)).setCustomId(shopKey,"buyamethyst").build());
        integerItemStackHashMap.put(11, new ItemBuilder(Material.OAK_SIGN).setDisplayName(Component.text("Price: 30$", NamedTextColor.GRAY)).setCustomId(shopKey,"priceamethyst").build());
        integerItemStackHashMap.put(20, new ItemBuilder(Material.OAK_SIGN).setDisplayName(Component.text("Sellprice: 20$", NamedTextColor.GRAY)).setCustomId(shopKey,"sellpriceamethyst").build());
        integerItemStackHashMap.put(29, new ItemBuilder(Material.AMETHYST_SHARD).setDisplayName(Component.text("Sell Amethyst Shard", NamedTextColor.LIGHT_PURPLE)).setCustomId(shopKey,"sellamethyst").build());

        integerItemStackHashMap.put(4, new ItemBuilder(Material.DIAMOND).setDisplayName(Component.text("Buy Diamond", NamedTextColor.AQUA)).setCustomId(shopKey,"buydiamond").build());
        integerItemStackHashMap.put(13, new ItemBuilder(Material.OAK_SIGN).setDisplayName(Component.text("Price: 150$", NamedTextColor.GRAY)).setCustomId(shopKey,"pricediamond").build());
        integerItemStackHashMap.put(22, new ItemBuilder(Material.OAK_SIGN).setDisplayName(Component.text("Sellprice: 100$", NamedTextColor.GRAY)).setCustomId(shopKey,"sellpricediamond").build());
        integerItemStackHashMap.put(31, new ItemBuilder(Material.DIAMOND).setDisplayName(Component.text("Sell Diamond", NamedTextColor.AQUA)).setCustomId(shopKey,"selldiamond").build());

        integerItemStackHashMap.put(6, new ItemBuilder(Material.NETHERITE_INGOT).setDisplayName(Component.text("Buy Netherite Ingot", NamedTextColor.RED)).setCustomId(shopKey,"buynetherite").build());
        integerItemStackHashMap.put(15, new ItemBuilder(Material.OAK_SIGN).setDisplayName(Component.text("Price: 1.250$", NamedTextColor.GRAY)).setCustomId(shopKey,"pricenetherite").build());
        integerItemStackHashMap.put(24, new ItemBuilder(Material.OAK_SIGN).setDisplayName(Component.text("Sellprice: 1.000$", NamedTextColor.GRAY)).setCustomId(shopKey,"sellpricenetherite").build());
        integerItemStackHashMap.put(33, new ItemBuilder(Material.NETHERITE_INGOT).setDisplayName(Component.text("Sell Netherite Ingot", NamedTextColor.RED)).setCustomId(shopKey,"sellnetherite").build());

        integerItemStackHashMap.put(8, new ItemBuilder(Material.SPAWNER).setDisplayName(Component.text("Buy Spawner", NamedTextColor.DARK_GRAY)).setCustomId(shopKey,"buyspawner").build());
        integerItemStackHashMap.put(17, new ItemBuilder(Material.OAK_SIGN).setDisplayName(Component.text("Price: 10.000$", NamedTextColor.GRAY)).setCustomId(shopKey,"pricespawner").build());
        integerItemStackHashMap.put(26, new ItemBuilder(Material.OAK_SIGN).setDisplayName(Component.text("Sellprice: 7.500$", NamedTextColor.GRAY)).setCustomId(shopKey,"sellpricespawner").build());
        integerItemStackHashMap.put(35, new ItemBuilder(Material.SPAWNER).setDisplayName(Component.text("Sell Spawner", NamedTextColor.DARK_GRAY)).setCustomId(shopKey,"sellspawner").build());
        Inventory i = Bukkit.createInventory(null, 4*9, Component.text("             Shop", NamedTextColor.DARK_GREEN, TextDecoration.BOLD));
        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            i.setItem(integerItemStackEntry.getKey() , integerItemStackEntry.getValue());
        }
        player.openInventory(i);
    }
}
