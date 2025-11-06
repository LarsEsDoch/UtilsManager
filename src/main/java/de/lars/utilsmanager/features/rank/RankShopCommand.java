package de.lars.utilsmanager.features.rank;

import de.lars.utilsmanager.utils.ItemBuilder;
import de.lars.utilsmanager.utils.Statements;
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
import java.util.function.BiFunction;

public class RankShopCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

        NamespacedKey rankKey = new NamespacedKey("utilsmanager", "rank_id");
        Map<Integer, ItemStack> items = new HashMap<>();

        BiFunction<Material, String, ItemBuilder> item = (mat, id) ->
                new ItemBuilder(mat).setCustomStringData(rankKey, id);

        items.put(10, item.apply(Material.EMERALD, "6premium")
                .setDisplayName(Component.text("6 months", NamedTextColor.YELLOW)
                        .append(Component.text(" Premium", NamedTextColor.GREEN)))
                .setLore(
                        Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 500$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("- Buy and Sell", NamedTextColor.WHITE),
                        Component.text("- Wallet", NamedTextColor.WHITE),
                        Component.text("- Craftingtable recipes", NamedTextColor.WHITE)
                ).build());

        items.put(19, item.apply(Material.EMERALD_BLOCK, "12premium")
                .setDisplayName(Component.text("12 months", NamedTextColor.GOLD)
                        .append(Component.text(" Premium", NamedTextColor.GREEN)))
                .setLore(
                        Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 900$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("- Buy and Sell", NamedTextColor.WHITE),
                        Component.text("- Wallet", NamedTextColor.WHITE),
                        Component.text("- Craftingtable recipes", NamedTextColor.WHITE)
                ).build());

        items.put(12, item.apply(Material.DIAMOND, "6supreme")
                .setDisplayName(Component.text("6 months", NamedTextColor.YELLOW)
                        .append(Component.text(" Supreme", NamedTextColor.AQUA)))
                .setLore(
                        Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 1.500$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("- Buy, Sell and Shop", NamedTextColor.WHITE),
                        Component.text("- Wallet", NamedTextColor.WHITE),
                        Component.text("- Pay", NamedTextColor.WHITE),
                        Component.text("- Craftingtable recipes", NamedTextColor.WHITE),
                        Component.text("- Toggle the Scoreboard", NamedTextColor.WHITE)
                ).build());

        items.put(21, item.apply(Material.DIAMOND_BLOCK, "12supreme")
                .setDisplayName(Component.text("12 months", NamedTextColor.GOLD)
                        .append(Component.text(" Supreme", NamedTextColor.AQUA)))
                .setLore(
                        Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 2.800$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("- Buy, Sell and Shop", NamedTextColor.WHITE),
                        Component.text("- Wallet", NamedTextColor.WHITE),
                        Component.text("- Pay", NamedTextColor.WHITE),
                        Component.text("- Craftingtable recipes", NamedTextColor.WHITE),
                        Component.text("- Toggle the Scoreboard", NamedTextColor.WHITE)
                ).build());

        items.put(14, item.apply(Material.LAPIS_LAZULI, "6titan")
                .setDisplayName(Component.text("6 months", NamedTextColor.YELLOW)
                        .append(Component.text(" Titan", NamedTextColor.BLUE)))
                .setLore(
                        Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 5.000$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("- Buy, Sell and Shop", NamedTextColor.WHITE),
                        Component.text("- Wallet", NamedTextColor.WHITE),
                        Component.text("- Pay", NamedTextColor.WHITE),
                        Component.text("- Craftingtable recipes", NamedTextColor.WHITE),
                        Component.text("- Toggle the Scoreboard", NamedTextColor.WHITE),
                        Component.text("- Togglebed", NamedTextColor.WHITE),
                        Component.text("- Backpack", NamedTextColor.WHITE)
                ).build());

        items.put(23, item.apply(Material.LAPIS_BLOCK, "12titan")
                .setDisplayName(Component.text("12 months", NamedTextColor.GOLD)
                        .append(Component.text(" Titan", NamedTextColor.BLUE)))
                .setLore(
                        Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 9.500$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("- Buy, Sell and Shop", NamedTextColor.WHITE),
                        Component.text("- Wallet", NamedTextColor.WHITE),
                        Component.text("- Pay", NamedTextColor.WHITE),
                        Component.text("- Craftingtable recipes", NamedTextColor.WHITE),
                        Component.text("- Toggle the Scoreboard", NamedTextColor.WHITE),
                        Component.text("- Togglebed", NamedTextColor.WHITE),
                        Component.text("- Backpack", NamedTextColor.WHITE)
                ).build());

        items.put(16, item.apply(Material.GOLD_INGOT, "6matrix")
                .setDisplayName(Component.text("6 months", NamedTextColor.YELLOW)
                        .append(Component.text(" Matrix", NamedTextColor.GOLD)))
                .setLore(
                        Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 50.000$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("- Buy, Sell and Shop", NamedTextColor.WHITE),
                        Component.text("- Wallet", NamedTextColor.WHITE),
                        Component.text("- Pay", NamedTextColor.WHITE),
                        Component.text("- Craftingtable recipes", NamedTextColor.WHITE),
                        Component.text("- Toggle the Scoreboard", NamedTextColor.WHITE),
                        Component.text("- Togglebed", NamedTextColor.WHITE),
                        Component.text("- Backpack", NamedTextColor.WHITE),
                        Component.text("- Teleport to Spawn", NamedTextColor.WHITE),
                        Component.text("- Control the Timer", NamedTextColor.WHITE),
                        Component.text("- Check TPS", NamedTextColor.WHITE),
                        Component.text("- Jumpto something", NamedTextColor.WHITE)
                ).build());

        items.put(25, item.apply(Material.GOLD_BLOCK, "12matrix")
                .setDisplayName(Component.text("12 months", NamedTextColor.GOLD)
                        .append(Component.text(" Matrix", NamedTextColor.GOLD)))
                .setLore(
                        Component.text(" "),
                        Component.text("Price:", NamedTextColor.WHITE, TextDecoration.BOLD),
                        Component.text(" 100.000$", NamedTextColor.DARK_AQUA),
                        Component.text(" "),
                        Component.text("Benefits:", NamedTextColor.GREEN, TextDecoration.BOLD),
                        Component.text("- Buy, Sell and Shop", NamedTextColor.WHITE),
                        Component.text("- Prefix Changer", NamedTextColor.WHITE),
                        Component.text("- Wallet", NamedTextColor.WHITE),
                        Component.text("- Pay", NamedTextColor.WHITE),
                        Component.text("- Craftingtable recipes", NamedTextColor.WHITE),
                        Component.text("- Toggle the Scoreboard", NamedTextColor.WHITE),
                        Component.text("- Togglebed", NamedTextColor.WHITE),
                        Component.text("- Backpack", NamedTextColor.WHITE),
                        Component.text("- Teleport to Spawn", NamedTextColor.WHITE),
                        Component.text("- Control the Timer", NamedTextColor.WHITE),
                        Component.text("- Check TPS", NamedTextColor.WHITE),
                        Component.text("- Jumpto something", NamedTextColor.WHITE)
                ).build());

        Inventory gui = Bukkit.createInventory(null, 4 * 9, Component.text("           RankShop", NamedTextColor.DARK_GREEN, TextDecoration.BOLD));
        items.forEach(gui::setItem);
        player.openInventory(gui);
    }
}
