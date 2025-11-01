package de.lars.utilsmanager.recipes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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

        NamespacedKey lightKey = new NamespacedKey("utils_manager", "magic_light");

        ShapedRecipe lightRecipe = new ShapedRecipe(lightKey, light);
        lightRecipe.shape("GGG", "GTG", "GGG");
        lightRecipe.setIngredient('G', Material.GLASS);
        lightRecipe.setIngredient('T', Material.TORCH);

        ItemStack invisibleItemFrame = new ItemStack(Material.ITEM_FRAME);
        ItemMeta meta = invisibleItemFrame.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text("Magic Item Frame", NamedTextColor.LIGHT_PURPLE));
            meta.setCustomModelData(1);
            NamespacedKey key = new NamespacedKey("utils_manager", "invisible_frame");
            meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
            invisibleItemFrame.setItemMeta(meta);
        }

        NamespacedKey recipeKey = new NamespacedKey("utils_manager", "invisible_itemframe");
        ShapedRecipe itemFrameRecipe = new ShapedRecipe(recipeKey, invisibleItemFrame);
        itemFrameRecipe.shape("GGG", "GSG", "GGG");
        itemFrameRecipe.setIngredient('G', Material.GLASS);
        itemFrameRecipe.setIngredient('S', Material.ITEM_FRAME);

        Bukkit.addRecipe(lightRecipe);
        Bukkit.addRecipe(itemFrameRecipe);
    }

    @EventHandler
    public void onItemFramePlace(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof ItemFrame frame)) return;

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item.getItemMeta() == null) return;

        NamespacedKey key = new NamespacedKey("utils_manager", "invisible_frame");
        if (item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.BYTE)) {
            frame.setVisible(false);
            frame.setFixed(true);
        }
    }

}