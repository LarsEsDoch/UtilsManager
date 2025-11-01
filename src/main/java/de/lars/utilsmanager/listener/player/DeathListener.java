package de.lars.utilsmanager.listener.player;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.util.RankStatements;
import de.lars.utilsmanager.util.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class DeathListener implements Listener {

    private Integer X;
    private Integer Y;
    private Integer Z;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        if (event.getEntity().getKiller() != null) {
            Player killer = event.getEntity().getKiller();
            Player p = event.getEntity();

            X = Math.toIntExact(Math.round(p.getLocation().getX()));
            Y = Math.toIntExact(Math.round(p.getLocation().getY()));
            Z = Math.toIntExact(Math.round(p.getLocation().getZ()));

            if (LanguageAPI.getApi().getLanguage(killer) == 2) {
                killer.sendMessage(
                        Component.text()
                                .append(Statements.getPrefix())
                                .append(Component.text("Du hast ", NamedTextColor.DARK_RED))
                                .append(p.displayName().color(NamedTextColor.YELLOW))
                                .append(Component.text(" getötet!", NamedTextColor.DARK_RED)));
            } else {
                killer.sendMessage(
                        Component.text()
                                .append(Statements.getPrefix())
                                .append(Component.text("You have killed ", NamedTextColor.DARK_RED))
                                .append(p.displayName().color(NamedTextColor.YELLOW))
                                .append(Component.text("!", NamedTextColor.DARK_RED)));
            }

            if (LanguageAPI.getApi().getLanguage(p) == 2) {
                p.sendMessage(
                        Component.text()
                                .append(Statements.getPrefix())
                                .append(Component.text("Du wurdest von ", NamedTextColor.RED))
                                .append(killer.displayName().color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD))
                                .append(Component.text(" bei den Koordinaten ", NamedTextColor.BLUE))
                                .append(Component.text(X + " " + Y + " " + Z, NamedTextColor.BLUE))
                                .append(Component.text(" getötet.", NamedTextColor.RED)));
            } else {
                p.sendMessage(
                        Component.text()
                                .append(Statements.getPrefix())
                                .append(Component.text("You were killed by ", NamedTextColor.RED))
                                .append(killer.displayName().color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD))
                                .append(Component.text(" on coordinate ", NamedTextColor.BLUE))
                                .append(Component.text(X + " " + Y + " " + Z, NamedTextColor.BLUE))
                                .append(Component.text(".", NamedTextColor.RED)));
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(
                            Component.text()
                                    .append(Statements.getPrefix())
                                    .append(p.displayName().color(NamedTextColor.RED).decorate(TextDecoration.BOLD))
                                    .append(Component.text(" wurde von "))
                                    .append(killer.displayName().color(NamedTextColor.DARK_RED).decorate(TextDecoration.BOLD))
                                    .append(Component.text(" getötet!")));
                } else {
                    player.sendMessage(
                            Component.text()
                                    .append(Statements.getPrefix())
                                    .append(p.displayName().color(NamedTextColor.RED).decorate(TextDecoration.BOLD))
                                    .append(Component.text(" was killed by "))
                                    .append(killer.displayName().color(NamedTextColor.DARK_RED).decorate(TextDecoration.BOLD)));
                }

            }
        } else {
            if (event.getEntity().getKiller().getName().contains("❤")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String monsterType = event.getEntity().getKiller().getType().name();

                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(
                                Component.text()
                                        .append(Statements.getPrefix())
                                        .append(RankStatements.getRank(player))
                                        .append(Component.text(" wurde von einem/r ", NamedTextColor.WHITE))
                                        .append(Component.text(monsterType, NamedTextColor.RED))
                                        .append(Component.text(" getötet!", NamedTextColor.WHITE))
                                        .build()
                        );
                    } else {
                        player.sendMessage(
                                Component.text()
                                        .append(Statements.getPrefix())
                                        .append(RankStatements.getRank(player))
                                        .append(Component.text(" was slain by ", NamedTextColor.WHITE))
                                        .append(Component.text(monsterType, NamedTextColor.RED))
                                        .append(Component.text("!", NamedTextColor.WHITE))
                                        .build()
                        );
                    }
                }
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String monsterType = event.getEntity().getKiller().getName();

                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(
                                Component.text()
                                        .append(Statements.getPrefix())
                                        .append(RankStatements.getRank(player))
                                        .append(Component.text(" wurde von einem/r ", NamedTextColor.WHITE))
                                        .append(Component.text(monsterType, NamedTextColor.RED))
                                        .append(Component.text(" getötet!", NamedTextColor.WHITE))
                                        .build()
                        );
                    } else {
                        player.sendMessage(
                                Component.text()
                                        .append(Statements.getPrefix())
                                        .append(RankStatements.getRank(player))
                                        .append(Component.text(" was slain by ", NamedTextColor.WHITE))
                                        .append(Component.text(monsterType, NamedTextColor.RED))
                                        .append(Component.text("!", NamedTextColor.WHITE))
                                        .build()
                        );
                    }
                }
            }

        }
        event.deathMessage(Component.text(""));
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Monster && entity.customName() != null && Objects.requireNonNull(entity.customName()).contains(Component.text("❤"))) {
            entity.customName(Component.text(""));
            entity.setCustomNameVisible(false);
            /*event.setCancelled(true);
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), bukkitTask ->  {
                event.setCancelled(false);
            }, 1);

             */
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Monster) {
            Monster monster = (Monster) entity;
            double currentHealth = monster.getHealth();
            double damage = event.getDamage();
            if (damage >= currentHealth) {
                entity.setCustomName("");
                entity.setCustomNameVisible(false);
                event.setCancelled(true);
                ((Monster) entity).setHealth(0);
            }
        }
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (event.getDamager() instanceof Monster) {
                double currentHealth = player.getHealth();
                double damage = event.getDamage();
                if (damage >= currentHealth) {
                    event.getDamager().setCustomName(event.getDamager().getType().name());
                }
            }
        }
    }
}
