package de.lars.utilsmanager.features.rank;

import de.lars.utilsmanager.util.ItemBuilder;
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

public class RankShopCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

        HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();
        integerItemStackHashMap.put(10, new ItemBuilder(Material.EMERALD)
                .setDisplayname(Component.text("6 months", NamedTextColor.YELLOW)
                        .append(Component.text(" Premium", NamedTextColor.GREEN)))
                .setLocalizedName(Component.text("6premium"))
                .setLore(Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 500$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("-Buy and Sell", NamedTextColor.WHITE),
                        Component.text("-Wallet", NamedTextColor.WHITE),
                        Component.text("-Craftingtable recipes", NamedTextColor.WHITE))
                .build());

        integerItemStackHashMap.put(19, new ItemBuilder(Material.EMERALD_BLOCK)
                .setDisplayname(Component.text("12 months", NamedTextColor.GOLD)
                        .append(Component.text(" ", NamedTextColor.GREEN)))
                .setLocalizedName(Component.text("12premium"))
                .setLore(Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 900$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("-Buy and Sell", NamedTextColor.WHITE),
                        Component.text("-Wallet", NamedTextColor.WHITE),
                        Component.text("-Craftingtable recipes", NamedTextColor.WHITE))
                .build());

        integerItemStackHashMap.put(12, new ItemBuilder(Material.DIAMOND)
                .setDisplayname(Component.text("6 months", NamedTextColor.YELLOW)
                        .append(Component.text(" Supreme", NamedTextColor.AQUA)))
                .setLocalizedName(Component.text("6supreme"))
                .setLore(Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 1.500$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("-Buy, Sell and Shop", NamedTextColor.WHITE),
                        Component.text("-Wallet", NamedTextColor.WHITE),
                        Component.text("-Pay", NamedTextColor.WHITE),
                        Component.text("-Craftingtable recipes", NamedTextColor.WHITE),
                        Component.text("-Toggle the Scoreboard", NamedTextColor.WHITE))
                .build());

        integerItemStackHashMap.put(21, new ItemBuilder(Material.DIAMOND_BLOCK)
                .setDisplayname(Component.text("12 months", NamedTextColor.GOLD)
                        .append(Component.text(" Supreme", NamedTextColor.AQUA)))
                .setLocalizedName(Component.text("12supreme"))
                .setLore(Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 2.800$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("-Buy, Sell and Shop", NamedTextColor.WHITE),
                        Component.text("-Wallet", NamedTextColor.WHITE),
                        Component.text("-Pay", NamedTextColor.WHITE),
                        Component.text("-Craftingtable recipes", NamedTextColor.WHITE),
                        Component.text("-Toggle the Scoreboard", NamedTextColor.WHITE))
                .build());

        integerItemStackHashMap.put(14, new ItemBuilder(Material.LAPIS_LAZULI)
                .setDisplayname(Component.text("6 months", NamedTextColor.YELLOW)
                        .append(Component.text(" Titan", NamedTextColor.BLUE)))
                .setLocalizedName(Component.text("6titan"))
                .setLore(Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 5.000$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("-Buy, Sell and Shop", NamedTextColor.WHITE),
                        Component.text("-Wallet", NamedTextColor.WHITE),
                        Component.text("-Pay", NamedTextColor.WHITE),
                        Component.text("-Craftingtable recipes", NamedTextColor.WHITE),
                        Component.text("-Toggle the Scoreboard", NamedTextColor.WHITE),
                        Component.text("-Togglebed", NamedTextColor.WHITE),
                        Component.text("-Backpack", NamedTextColor.WHITE))
                .build());

        integerItemStackHashMap.put(23, new ItemBuilder(Material.LAPIS_BLOCK)
                .setDisplayname(Component.text("12 months", NamedTextColor.GOLD)
                        .append(Component.text(" Titan", NamedTextColor.BLUE)))
                .setLocalizedName(Component.text("12titan"))
                .setLore(Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 9.500$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("-Buy, Sell and Shop", NamedTextColor.WHITE),
                        Component.text("-Wallet", NamedTextColor.WHITE),
                        Component.text("-Pay", NamedTextColor.WHITE),
                        Component.text("-Craftingtable recipes", NamedTextColor.WHITE),
                        Component.text("-Toggle the Scoreboard", NamedTextColor.WHITE),
                        Component.text("-Togglebed", NamedTextColor.WHITE),
                        Component.text("-Backpack", NamedTextColor.WHITE))
                .build());

        integerItemStackHashMap.put(16, new ItemBuilder(Material.GOLD_INGOT)
                .setDisplayname(Component.text("6 months", NamedTextColor.YELLOW)
                        .append(Component.text(" Matrix", NamedTextColor.GOLD)))
                .setLocalizedName(Component.text("6matrix"))
                .setLore(Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 50.000$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("-Buy, Sell and Shop", NamedTextColor.WHITE),
                        Component.text("-Wallet", NamedTextColor.WHITE),
                        Component.text("-Pay", NamedTextColor.WHITE),
                        Component.text("-Craftingtable recipes", NamedTextColor.WHITE),
                        Component.text("-Toggle the Scoreboard", NamedTextColor.WHITE),
                        Component.text("-Togglebed", NamedTextColor.WHITE),
                        Component.text("-Backpack", NamedTextColor.WHITE),
                        Component.text("-Teleport to the Spawn", NamedTextColor.WHITE),
                        Component.text("-Control the Timer", NamedTextColor.WHITE),
                        Component.text("-You can Check the Tps", NamedTextColor.WHITE),
                        Component.text("-You can Jumpto something", NamedTextColor.WHITE))
                .build());

        integerItemStackHashMap.put(25, new ItemBuilder(Material.GOLD_BLOCK)
                .setDisplayname(Component.text("12 months", NamedTextColor.GOLD)
                        .append(Component.text(" Matrix", NamedTextColor.GOLD)))
                .setLocalizedName(Component.text("12matrix"))
                .setLore(Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 100.000$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("-Buy, Sell and Shop", NamedTextColor.WHITE),
                        Component.text("Prefix Changer", NamedTextColor.WHITE),
                        Component.text("-Wallet", NamedTextColor.WHITE),
                        Component.text("-Pay", NamedTextColor.WHITE),
                        Component.text("-Craftingtable recipes", NamedTextColor.WHITE),
                        Component.text("-Toggle the Scoreboard", NamedTextColor.WHITE),
                        Component.text("-Togglebed", NamedTextColor.WHITE),
                        Component.text("-Backpack", NamedTextColor.WHITE),
                        Component.text("-Teleport to the Spawn", NamedTextColor.WHITE),
                        Component.text("-Control the Timer", NamedTextColor.WHITE),
                        Component.text("-You can Check the Tps", NamedTextColor.WHITE),
                        Component.text("-You can Jumpto something", NamedTextColor.WHITE))
                .build());

        Inventory i = Bukkit.createInventory(null, 4*9, Component.text("           RankShop", NamedTextColor.DARK_GREEN, TextDecoration.BOLD));
        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            i.setItem(integerItemStackEntry.getKey() , integerItemStackEntry.getValue());
        }
        player.openInventory(i);
    }
}
