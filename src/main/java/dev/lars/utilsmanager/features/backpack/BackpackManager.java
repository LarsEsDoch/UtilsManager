package dev.lars.utilsmanager.features.backpack;

import dev.lars.apimanager.apis.backpackAPI.BackpackAPI;
import dev.lars.apimanager.apis.limitAPI.LimitAPI;
import dev.lars.utilsmanager.UtilsManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class BackpackManager implements Listener {
    public void openBackpack(Player player) {
        BackpackAPI.getApi().getBackpackAsync(player).thenAccept(data -> {
            Bukkit.getScheduler().runTask(UtilsManager.getInstance(), () -> {
                Inventory inv = Bukkit.createInventory(player, LimitAPI.getApi().getBackpackSlots(player), Component.text("§6Backpack"));

                if (data != null && !data.isEmpty()) {
                    ItemStack[] items = deserializeItems(data);
                    inv.setContents(items);
                }

                player.openInventory(inv);
            });
        });
    }

    public void openOfflineBackpack(OfflinePlayer offlinePlayer, Player player) {
        BackpackAPI.getApi().getBackpackAsync(offlinePlayer).thenAccept(data -> {
            Bukkit.getScheduler().runTask(UtilsManager.getInstance(), () -> {
                Inventory inv = Bukkit.createInventory(player, LimitAPI.getApi().getBackpackSlots(offlinePlayer), Component.text("§6Backpack"));

                if (data != null && !data.isEmpty()) {
                    ItemStack[] items = deserializeItems(data);
                    inv.setContents(items);
                }

                player.openInventory(inv);
            });
        });
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().title().equals(Component.text("§6Backpack"))) {
            Player player = (Player) event.getPlayer();
            Inventory inv = event.getInventory();

            String data = serializeItems(inv.getContents());

            BackpackAPI.getApi().setBackpackAsync(player, data)
                .exceptionally(ex -> {
                    Bukkit.getLogger().severe("Failed to save backpack for " + player.getName() + ": " + ex.getMessage());
                    return null;
                });
        }
    }

    public String serializeItems (ItemStack[]items){
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
             dataOutput.writeInt(items.length);

             for (ItemStack item : items) {
                 dataOutput.writeObject(item);
             }
             dataOutput.flush();

             return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
             throw new IllegalStateException("Failed to serialize items", e);
        }
    }
    public ItemStack[] deserializeItems (String base64){
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
             int size = dataInput.readInt();
             ItemStack[] items = new ItemStack[size];

             for (int i = 0; i < size; i++) {
                 items[i] = (ItemStack) dataInput.readObject();
             }

             return items;
        } catch (IOException | ClassNotFoundException e) {
             throw new IllegalStateException("Failed to deserialize items", e);
        }
    }
}