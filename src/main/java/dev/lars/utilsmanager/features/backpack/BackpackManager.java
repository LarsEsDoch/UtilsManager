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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class BackpackManager implements Listener {

    private final Map<UUID, Set<Player>> openBackpacks = new HashMap<>();

    public void openBackpack(Player player) {
        openBackpack(player, player);
    }

    public void openOfflineBackpack(OfflinePlayer offlinePlayer, Player viewer) {
        openBackpack(viewer, offlinePlayer);
    }

    private void openBackpack(Player viewer, OfflinePlayer owner) {
        BackpackAPI.getApi().getBackpackAsync(owner).thenAccept(data -> {
            Bukkit.getScheduler().runTask(UtilsManager.getInstance(), () -> {
                Inventory inv = Bukkit.createInventory(viewer, LimitAPI.getApi().getBackpackSlots(owner), Component.text("§6Backpack"));

                if (data != null && !data.isEmpty()) {
                    ItemStack[] items = deserializeItems(data);
                    inv.setContents(items);
                }

                viewer.openInventory(inv);

                openBackpacks.computeIfAbsent(owner.getUniqueId(), k -> new HashSet<>()).add(viewer);
            });
        });
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().title().equals(Component.text("§6Backpack"))) {
            Player viewer = (Player) event.getWhoClicked();
            UUID ownerId = openBackpacks.entrySet().stream()
                    .filter(e -> e.getValue().contains(viewer))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            if (ownerId != null) {
                Inventory inv = event.getInventory();
                String data = serializeItems(inv.getContents());

                OfflinePlayer owner = Bukkit.getOfflinePlayer(ownerId);
                BackpackAPI.getApi().setBackpackAsync(owner, data);

                for (Player otherViewer : openBackpacks.get(ownerId)) {
                    if (otherViewer.equals(viewer)) continue;
                    otherViewer.getOpenInventory().getTopInventory().setContents(inv.getContents());
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player viewer = (Player) event.getPlayer();
        UUID ownerId = openBackpacks.entrySet().stream()
                .filter(e -> e.getValue().contains(viewer))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (ownerId != null) {
            Inventory inv = event.getInventory();
            String data = serializeItems(inv.getContents());

            OfflinePlayer owner = Bukkit.getOfflinePlayer(ownerId);
            BackpackAPI.getApi().setBackpackAsync(owner, data);

            openBackpacks.get(ownerId).remove(viewer);
            if (openBackpacks.get(ownerId).isEmpty()) {
                openBackpacks.remove(ownerId);
            }
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