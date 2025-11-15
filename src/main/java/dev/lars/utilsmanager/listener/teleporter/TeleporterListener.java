package dev.lars.utilsmanager.listener.teleporter;

import dev.lars.apimanager.ApiManager;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import dev.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class TeleporterListener implements Listener {
    Map<Player, Integer> data = new HashMap<>();

    public TeleporterListener() {
        Bukkit.getScheduler().runTaskTimer(ApiManager.getInstance(), bukkitTask -> {
            if (Bukkit.getOnlinePlayers().isEmpty()) return;
            Location loc = ServerSettingsAPI.getApi().getSpawnLocation();
            if (loc == null) return;
            for (Player player: Bukkit.getOnlinePlayers()) {
                if (!(player.hasPermission("utilsmanager.feature.spawn"))) {
                    return;
                }
                if (player.getLocation().getBlockX() == loc.getBlockX() && player.getLocation().getBlockZ() == loc.getBlockZ() && player.getLocation().getBlock().getType() == Material.SCULK_SENSOR) {
                    if (!data.containsKey(player)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 130, 0));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 170, 255));
                        data.put(player, 6);
                    }
                    data.put(player, data.get(player) - 1);
                    switch (data.get(player)) {
                        case 5: {
                            player.showTitle(Title.title(
                                Component.text("5", NamedTextColor.DARK_RED),
                                Component.empty(),
                                10, 20, 10
                            ));
                            break;
                        }
                        case 4: {
                            player.showTitle(Title.title(
                                Component.text("4", NamedTextColor.RED),
                                Component.empty(),
                                10, 20, 10
                            ));
                            break;
                        }
                        case 3: {
                            player.showTitle(Title.title(
                                Component.text("3", NamedTextColor.GOLD),
                                Component.empty(),
                                10, 20, 10
                            ));
                            break;
                        }
                        case 2: {
                            player.showTitle(Title.title(
                                Component.text("2", NamedTextColor.YELLOW),
                                Component.empty(),
                                10, 20, 10
                            ));
                            break;
                        }
                        case 1: {
                            player.showTitle(Title.title(
                                Component.text("1", NamedTextColor.GREEN),
                                Component.empty(),
                                10, 50, 10
                            ));
                            break;
                        }
                        case 0: {
                            player.showTitle(Title.title(
                                Component.text("Spawn!", NamedTextColor.LIGHT_PURPLE),
                                Component.empty(),
                                10, 10, 10
                            ));
                            player.teleport(loc);
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest zum Spawn teleportiert.", NamedTextColor.GREEN)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You have been teleported to the spawn.", NamedTextColor.GREEN)));
                            }
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
                            data.remove(player);
                            break;
                        }
                        default:
                            break;
                    }
                    return;
                }
                if (!data.containsKey(player)) {
                    return;
                }
                Component canceledMessage;
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    canceledMessage = Component.text("Abgebrochen!", NamedTextColor.RED);
                } else {
                    canceledMessage = Component.text("Canceled!", NamedTextColor.RED);
                }
                player.showTitle(Title.title(
                        Component.empty(),
                        canceledMessage,
                        10, 10, 5
                ));
                player.removePotionEffect(PotionEffectType.DARKNESS);
                player.removePotionEffect(PotionEffectType.NAUSEA);
                data.remove(player);
                return;
            }
        }, 20, 20);
    }
}