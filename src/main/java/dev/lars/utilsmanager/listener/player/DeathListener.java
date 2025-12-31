package dev.lars.utilsmanager.listener.player;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.RankStatements;
import dev.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();

        Entity killerEntity = victim.getKiller();
        if (killerEntity == null) {
            EntityDamageEvent last = victim.getLastDamageCause();
            if (last instanceof EntityDamageByEntityEvent edbee) {
                killerEntity = edbee.getDamager();
            }
        }

        if (killerEntity == null) {
            event.deathMessage(Component.empty());
            return;
        }

        if (killerEntity instanceof Player killer) {
            int x = victim.getLocation().getBlockX();
            int y = victim.getLocation().getBlockY();
            int z = victim.getLocation().getBlockZ();

            boolean killerGerman = LanguageAPI.getApi().getLanguage(killer) == 2;
            Component killerMessage = Component.text()
                    .append(Statements.getPrefix())
                    .append(Component.text(killerGerman ? "Du hast " : "You have killed ", NamedTextColor.DARK_RED))
                    .append(victim.displayName().color(NamedTextColor.YELLOW))
                    .append(Component.text(killerGerman ? " getötet!" : "!", NamedTextColor.DARK_RED)).build();
            killer.sendMessage(killerMessage);

            boolean victimGerman = LanguageAPI.getApi().getLanguage(victim) == 2;
            Component victimMessage = Component.text()
                    .append(Statements.getPrefix())
                    .append(Component.text(victimGerman ? "Du wurdest von " : "You were killed by ", NamedTextColor.RED))
                    .append(killer.displayName().color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD))
                    .append(Component.text(victimGerman ? " bei den Koordinaten " : " on coordinates ", NamedTextColor.BLUE))
                    .append(Component.text(x + " " + y + " " + z, NamedTextColor.BLUE))
                    .append(Component.text(victimGerman ? " getötet." : ".", NamedTextColor.RED)).build();
            victim.sendMessage(victimMessage);

            for (Player online : Bukkit.getOnlinePlayers()) {
                boolean langGerman = LanguageAPI.getApi().getLanguage(online) == 2;
                Component broadcast = Component.text()
                        .append(Statements.getPrefix())
                        .append(victim.displayName().color(NamedTextColor.RED).decorate(TextDecoration.BOLD))
                        .append(Component.text(langGerman ? " wurde von " : " was killed by ", NamedTextColor.WHITE))
                        .append(killer.displayName().color(NamedTextColor.DARK_RED).decorate(TextDecoration.BOLD)).build();
                online.sendMessage(broadcast);
            }

        } else {
            Component customName = killerEntity.customName();
            String monsterName;

            if (customName != null) {
                String plainName = PlainTextComponentSerializer.plainText().serialize(customName);

                if (plainName.contains("❤")) {
                    monsterName = killerEntity.getType().name();
                } else {
                    monsterName = plainName;
                }
            } else {
                monsterName = killerEntity.getType().name();
            }
            monsterName = monsterName.replace('_', ' ');

            for (Player online : Bukkit.getOnlinePlayers()) {
                boolean langGerman = LanguageAPI.getApi().getLanguage(online) == 2;
                Component msg = Component.text()
                        .append(Statements.getPrefix())
                        .append(RankStatements.getRank(online))
                        .append(Component.text(langGerman ? " wurde von einem/r " : " was slain by ", NamedTextColor.WHITE))
                        .append(Component.text(monsterName, NamedTextColor.RED))
                        .append(Component.text(langGerman ? " getötet!" : "!", NamedTextColor.WHITE)).build();
                online.sendMessage(msg);
            }
        }

        event.deathMessage(Component.empty());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        NamespacedKey heartsKey = new NamespacedKey(UtilsManager.getInstance(), "displayed_hearts");
        Entity ent = event.getEntity();

        if (!(ent instanceof Monster monster)) return;

        if (monster.isDead()) return;

        double damage;
        try {
            damage = event.getFinalDamage();
        } catch (NoSuchMethodError e) {
            damage = event.getDamage();
        }

        double healthAfter = monster.getHealth() - damage;
        if (healthAfter > 0.0001) return;

        Component comp = monster.customName();
        if (comp == null) return;

        String plain = PlainTextComponentSerializer.plainText().serialize(comp);

        if (!plain.contains("❤")) return;

        monster.customName(null);
        monster.setCustomNameVisible(false);

        if (monster.getPersistentDataContainer().has(heartsKey, PersistentDataType.INTEGER)) {
            monster.getPersistentDataContainer().remove(heartsKey);
        }
    }
}