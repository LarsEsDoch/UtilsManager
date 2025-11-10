package dev.lars.utilsmanager.listener.teleporter;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class TeleporterListener implements Listener {
    Map<Player, Integer> data = new HashMap<>();
    Location loc = new Location(Bukkit.getWorld("world"), -1.5, 139.0, 24.5, 90, 0);

    public void checkTeleportTime() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player: Bukkit.getOnlinePlayers()) {
                    if (!(player.hasPermission("plugin.spawn"))) {
                        return;
                    }
                    if (player.getLocation().getBlockX() == -2 && player.getLocation().getBlockZ() == 24 && player.getLocation().getBlock().getType() == Material.SCULK_SENSOR) {
                        if (!data.containsKey(player)) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 150, 0));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 150, 255));
                            data.put(player, 6);
                        }
                        data.put(player, data.get(player) - 1);
                        switch (data.get(player)) {
                            case 5: {
                                player.sendTitle(NamedTextColor.DARK_RED + "5", "", 10, 20, 10);
                                break;
                            }
                            case 4: {
                                player.sendTitle(NamedTextColor.RED + "4", "", 10, 20, 10);
                                break;
                            }
                            case 3: {
                                player.sendTitle(NamedTextColor.GOLD + "3", "", 10, 20, 10);
                                break;
                            }
                            case 2: {
                                player.sendTitle(NamedTextColor.YELLOW + "2", "", 10, 20, 10);
                                break;
                            }
                            case 1: {
                                player.sendTitle(NamedTextColor.GREEN+ "1", "", 10, 20, 10);
                                break;
                            }
                            case 0: {
                                player.sendTitle(NamedTextColor.LIGHT_PURPLE + "Spawn!", "", 10, 30, 10);
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
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Abgebrochen!");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "Canceled!");
                    }
                    player.sendTitle(" ", "", 0, 10, 0);
                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                    player.removePotionEffect(PotionEffectType.NAUSEA);
                    data.remove(player);
                    return;
                }
            }
        }.runTaskTimer(UtilsManager.getInstance(), 20,20);
    }
}
