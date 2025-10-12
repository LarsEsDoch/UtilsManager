package de.lars.utilsmanager.features.backpack;

import de.lars.apiManager.backpackAPI.BackpackAPI;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.*;

public class BackpackManager {

    private final Map<UUID, Backpack> map;

    public BackpackManager() {
        map = new HashMap<>();

        load();
    }

    public Backpack getBackpack(UUID uuid) {
        if(map.containsKey(uuid)) {
            return map.get(uuid);
        }

        Backpack backpack = new Backpack(uuid);
        map.put(uuid, backpack);
        return backpack;
    }

    public void setBackpack(UUID uuid, Backpack backpack) {
        map.put(uuid, backpack);
    }

    private void load() {
        List<String> uuids = BackpackAPI.getApi().getUUIDs();


        uuids.forEach(s -> {
            UUID uuid = UUID.fromString(s);
            String base64 = BackpackAPI.getApi().getBackpack(Bukkit.getOfflinePlayer(uuid));
            if (Objects.equals("", base64)) {
                map.put(uuid, new Backpack(uuid));
                return;
            }


            try {
                map.put(uuid, new Backpack(uuid, base64));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void save() {

        List<String> uuids = new ArrayList<>();

        for (UUID uuid : map.keySet()) {
            uuids.add(uuid.toString());
            BackpackAPI.getApi().setBackpack(Bukkit.getOfflinePlayer(uuid), getBackpack(uuid).toBase64());
        }
    }
}