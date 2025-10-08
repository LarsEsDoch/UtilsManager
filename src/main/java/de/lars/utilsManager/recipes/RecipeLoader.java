package de.lars.utilsManager.recipes;

import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class RecipeLoader implements Listener {

    public void registerRecipes() {
        ItemStack light = new ItemStack(Material.LIGHT);
        ItemMeta itemMetaLight = light.getItemMeta();
        itemMetaLight.displayName(Component.text("Light", NamedTextColor.LIGHT_PURPLE));
        light.setItemMeta(itemMetaLight);

        NamespacedKey lightKey = new NamespacedKey(Main.getInstance(), "magic_light");

        ShapedRecipe lightRecipe = new ShapedRecipe(lightKey, light);
        lightRecipe.shape("GGG", "GTG", "GGG");
        lightRecipe.setIngredient('G', Material.GLASS);
        lightRecipe.setIngredient('T', Material.TORCH);

        ItemStack invisibleItemFrame = new ItemBuilder(Material.ITEM_FRAME)
                .setLocalizedName(Component.text("invisible_itemframe"))
                .setDisplayname(Component.text("Magic Item Frame", NamedTextColor.LIGHT_PURPLE))
                .build();


        NamespacedKey key = new NamespacedKey(Main.getInstance(), "invisible_frame");

        ShapedRecipe itemFrameRecipe = new ShapedRecipe(key, invisibleItemFrame);
        itemFrameRecipe.shape("GGG", "GSG", "GGG");
        itemFrameRecipe.setIngredient('G', Material.GLASS);
        itemFrameRecipe.setIngredient('S', Material.ITEM_FRAME);

        Bukkit.addRecipe(lightRecipe);
        Bukkit.addRecipe(itemFrameRecipe);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.ITEM_FRAME) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        if (!(meta.itemName().equals(Component.text("invisible_itemframe")))) return;

        event.setCancelled(true);

        Block clicked = event.getClickedBlock();
        BlockFace face = event.getBlockFace();
        Location loc = clicked.getRelative(face).getLocation().add(0.5, 0.5, 0.5);

        ItemFrame frame = clicked.getWorld().spawn(loc, ItemFrame.class);
        frame.setVisible(false);
        frame.setFacingDirection(face);

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            item.setAmount(item.getAmount() - 1);
        }
    }
}
