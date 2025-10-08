package de.lars.utilsManager.backpack;

import de.lars.apiManager.backpackAPI.BackpackAPI;
import de.lars.utilsManager.utils.Base64;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.UUID;

public class Backpack {

    public static int backpackslots;
    private final UUID uuid;
    private final Inventory inventory;

    public Backpack(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        backpackslots = BackpackAPI.getApi().getSlots(player);

        if (backpackslots == 0) {
            backpackslots = 9;
        }

        this.uuid = uuid;
        this.inventory = Bukkit.createInventory(null, backpackslots, Component.text("              Backpack", NamedTextColor.DARK_GREEN));
    }

    public Backpack(UUID uuid, String base64) throws IOException {

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        backpackslots = BackpackAPI.getApi().getSlots(player);

        if (backpackslots == 0) {
            backpackslots = 9;
        }

        this.uuid = uuid;
        this.inventory = Bukkit.createInventory(null, backpackslots, Component.text("              Backpack", NamedTextColor.DARK_GREEN));
        this.inventory.setContents(Base64.itemStackArrayFromBase64(base64));
    }

    public UUID getUuid() {
        return uuid;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String toBase64() {
        return Base64.itemStackArrayToBase64(inventory.getContents());
    }
}