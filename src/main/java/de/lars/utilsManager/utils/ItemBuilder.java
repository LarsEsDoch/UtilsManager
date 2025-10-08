package de.lars.utilsManager.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {
    private ItemMeta itemMeta;
    private ItemStack itemStack;
    public ItemBuilder(Material mat){
        itemStack = new ItemStack(mat);
        itemMeta = itemStack.getItemMeta();
    }
    public ItemBuilder setDisplayname(Component s){
        itemMeta.displayName(s);
        return this;
    }
    public ItemBuilder setLocalizedName(Component s){
        itemMeta.itemName(s);
        return this;
    }
    public ItemBuilder setCustomModelData(Integer i){
        itemMeta.setCustomModelData(i);
        return this;
    }
    public ItemBuilder setLore(Component... s){
        itemMeta.lore(Arrays.asList(s));
        return this;
    }
    public ItemBuilder setUnbreakable(boolean s){
        itemMeta.setUnbreakable(s);
        return this;
    }
    public ItemBuilder addItemFlags(ItemFlag... s){
        itemMeta.addItemFlags(s);
        return this;
    }
    public ItemBuilder addEnchantment(Enchantment s, Integer l, Boolean t){
        itemMeta.addEnchant(s, l, t);
        return this;
    }

    @Override
    public String toString() {
        return "ItemBuilder{" +
                "itemMeta=" + itemMeta +
                ", itemStack=" + itemStack +
                '}';
    }
    public ItemStack build(){
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
