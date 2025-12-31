package dev.lars.utilsmanager.entity;

import dev.lars.utilsmanager.UtilsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class HeartDisplayManager {

    private static final int VIEW_RADIUS = 20;
    private static final long INITIAL_DELAY = 10L;
    private static final long INTERVAL = 10L;

    private final NamespacedKey heartsKey;

    public HeartDisplayManager() {
        this.heartsKey = new NamespacedKey(UtilsManager.getInstance(), "displayed_hearts");
    }

    public void start() {
        Bukkit.getScheduler().runTaskTimer(UtilsManager.getInstance(), this::tick, INITIAL_DELAY, INTERVAL);
    }

    private void tick() {
        PlainTextComponentSerializer plain = PlainTextComponentSerializer.plainText();

        for (World world : Bukkit.getWorlds()) {
            for (Monster monster : world.getEntitiesByClass(Monster.class)) {

                if (monster.isDead()) continue;

                Component custom = monster.customName();
                String plainName = custom != null ? plain.serialize(custom) : null;
                boolean hasHeartLabel = plainName != null && plainName.contains("❤");

                if (plainName != null && !hasHeartLabel) continue;

                int hearts = (int) Math.ceil(monster.getHealth() / 2.0);

                boolean playerNearby = monster.getNearbyEntities(VIEW_RADIUS, VIEW_RADIUS, VIEW_RADIUS)
                                             .stream().anyMatch(e -> e instanceof Player);

                if (!playerNearby) {
                    if (monster.getPersistentDataContainer().has(heartsKey, PersistentDataType.INTEGER)) {
                        monster.setCustomNameVisible(false);
                    }
                    continue;
                }

                int previous = -1;
                if (monster.getPersistentDataContainer().has(heartsKey, PersistentDataType.INTEGER)) {
                    previous = monster.getPersistentDataContainer().get(heartsKey, PersistentDataType.INTEGER);
                }

                if (previous != hearts || !hasHeartLabel) {
                    monster.customName(Component.text(hearts + "❤"));
                    monster.setCustomNameVisible(true);

                    monster.getPersistentDataContainer().set(heartsKey, PersistentDataType.INTEGER, hearts);
                }
            }
        }
    }
}