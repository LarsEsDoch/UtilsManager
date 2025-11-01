package de.lars.utilsmanager.listener.misc;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.util.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FunListeners implements Listener {

    private ItemMeta itemMeta;
    private Map<Player, Long> cooldowns = new HashMap<>();
    private long cooldownTimeMillis = 1000;

    private Map<Player, Entity> entityList = new HashMap<Player, Entity>();

    private Map<Player, Integer> tntList = new HashMap<>();

    public FunListeners() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            if (java.time.LocalDateTime.now().getMinute() == 0 && java.time.LocalDateTime.now().getSecond() == 0) {
                tntList.replaceAll((player, value) -> 0);
            }
        }, 20, 20);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {

        if(!(event.getEntity() instanceof Creeper)) {
            return;
        }

        event.blockList().clear();
    }

    @EventHandler
    public void onTntPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() == org.bukkit.GameMode.CREATIVE) return;
        if (event.getBlockPlaced().getType() == Material.TNT) {
            Player player = event.getPlayer();
            if (tntList.containsKey(player)) {
                if (tntList.get(player) >= 2) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Warte bis zu Ende der aktuellen Stunde um mehr zu sprengen!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Wait until the end of the current hour to explode more!", NamedTextColor.RED)));
                    }

                    event.setCancelled(true);
                } else {
                    tntList.replace(player, tntList.get(player) + 1);
                }
            } else {
                tntList.put(player, 1);
            }
        }
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        event.getEntity().getWorld().spawnParticle(Particle.FLAME, event.getEntity().getLocation(), 1000);
        event.getEntity().getWorld().spawnParticle(Particle.SMALL_FLAME, event.getEntity().getLocation(), 500);
        event.getEntity().getWorld().spawnParticle(Particle.ASH, event.getEntity().getLocation(), 1000);
        event.getEntity().getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, event.getEntity().getLocation().add(0, 1, 0), 5, 0.3, 0.5, 0.3, 0.01);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {

        if(!(event.getBlockPlaced().getType().equals(Material.TNT))) {
            return;
        }

        itemMeta = event.getItemInHand().getItemMeta();
        if (itemMeta == null) {
            return;
        }

        if(!(Objects.equals(itemMeta.displayName(), "Instant TNT"))) {
            return;
        }

        event.getBlockPlaced().setType(Material.AIR);

        TNTPrimed tnt = (TNTPrimed) event.getBlockPlaced().getWorld().spawnEntity(event.getBlockPlaced().getLocation(), EntityType.TNT);
        tnt.setFuseTicks(0);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        itemMeta = Objects.requireNonNull(event.getPlayer().getInventory().getItemInMainHand()).getItemMeta();
        if (itemMeta == null) {
            return;
        }
        String displayName = itemMeta.getDisplayName();

        if(Objects.requireNonNull(event.getPlayer().getInventory().getItemInMainHand()).getType().equals(Material.ECHO_SHARD)) {
            if(Objects.equals(displayName, "Pet-teleporter")) {
                if (checkCooldown(event.getPlayer())) return;
                Player player = event.getPlayer();
                if (entityList.containsKey(player)) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast bereits die Entity ", NamedTextColor.RED))
                                .append(Component.text( entityList.get(player).getType().name(), NamedTextColor.GREEN))
                                .append(Component.text(" gefangen!", NamedTextColor.RED)));
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Zum freilassen, klicke mit dem Pet-teleporter auf ein Block.", NamedTextColor.AQUA)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You have already caught the Entity ", NamedTextColor.RED))
                                .append(Component.text( entityList.get(player).getType().name(), NamedTextColor.GREEN))
                                .append(Component.text("!", NamedTextColor.RED)));
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("To release it, click on a block with the pet-teleporter.", NamedTextColor.AQUA)));
                    }
                    cooldowns.put(player, System.currentTimeMillis());
                    return;
                }
                Entity entity = event.getRightClicked();
                if (!(entity instanceof LivingEntity livingEntity)) return;
                if (entity instanceof EnderDragon || entity instanceof Wither || entity instanceof Warden || entity instanceof Guardian) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Du kannst diesen Mob nicht fangen!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix().append(Component.text("You can't catch this mob!", NamedTextColor.RED)));
                    }
                    return;
                }
                entityList.put(player, entity);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast erfolgreich die Entity ", NamedTextColor.WHITE))
                            .append(Component.text(entity.getType().name(), NamedTextColor.GREEN))
                            .append(Component.text(" gefangen.", NamedTextColor.WHITE)));
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Zum freilassen, klicke mit dem Pet-teleporter auf ein Block.", NamedTextColor.AQUA)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You have successfully caught the Entity ", NamedTextColor.WHITE))
                            .append(Component.text(entity.getType().name(), NamedTextColor.GREEN))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("To release it, click on a block with the pet-teleporter.", NamedTextColor.AQUA)));
                }
                event.setCancelled(true);
                Bukkit.getWorld("world").loadChunk(-1, 1);
                entity.teleport(new Location(Bukkit.getWorld("world"), -1, 128.5, 31));
                Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
                    if (!entityList.containsKey(player)) bukkitTask.cancel();
                    Objects.requireNonNull(Bukkit.getWorld("world")).loadChunk(-1, 1);
                }, 20, 20);
                entity.setSilent(true);
                livingEntity.setAI(false);
                cooldowns.put(player, System.currentTimeMillis());
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(!event.hasItem()) {
            return;
        }

        if(!event.hasBlock()) {
            return;
        }

        itemMeta = event.getItem().getItemMeta();
        if (itemMeta == null) {
            return;
        }
        String displayName = itemMeta.getDisplayName();

        if(Objects.requireNonNull(event.getItem()).getType().equals(Material.ECHO_SHARD)) {
            if(Objects.equals(displayName, "Pet-teleporter")) {
                if (checkCooldown(event.getPlayer())) return;
                Block block = event.getClickedBlock();
                String blockName = "X: " + block.getX() + " Y:" + block.getY() + " Z:" + block.getZ();
                Player player = event.getPlayer();
                if (entityList.containsKey(player)) {
                    Entity entity = entityList.get(player);
                    entity.teleport(block.getLocation().add(0.5, 1, 0.5));
                    entity.setSilent(false);
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.setAI(true);
                    entityList.remove(player);
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast erfolgreich die Entity ", NamedTextColor.WHITE))
                                .append(Component.text(entity.getType().name(), NamedTextColor.GREEN))
                                .append(Component.text(" freigelassen auf dem Block "))
                                .append(Component.text(blockName, NamedTextColor.BLUE))
                                .append(Component.text(".", NamedTextColor.WHITE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You have successfully released the entity ", NamedTextColor.WHITE))
                                .append(Component.text(entity.getType().name(), NamedTextColor.GREEN))
                                .append(Component.text(" on block "))
                                .append(Component.text(blockName, NamedTextColor.BLUE))
                                .append(Component.text(".", NamedTextColor.WHITE)));
                    }

                    ItemStack itemInHand = player.getInventory().getItemInMainHand();
                    itemInHand.setAmount(itemInHand.getAmount() - 1);
                    player.getInventory().setItemInMainHand(itemInHand);

                    cooldowns.put(player, System.currentTimeMillis());
                }
            }
        }

        if(!event.getPlayer().isOp()) {
            return;
        }

        if(!Objects.requireNonNull(event.getItem()).getType().equals(Material.FIRE_CHARGE)) {
            return;
        }

        if(!(Objects.equals(displayName, "Boom"))) {
            return;
        }

        TNTPrimed tnt = (TNTPrimed) Objects.requireNonNull(event.getClickedBlock()).getWorld().spawnEntity(event.getClickedBlock().getLocation(), EntityType.TNT);
        tnt.setIsIncendiary(false);
        tnt.setFuseTicks(0);
        tnt.setYield(400);
        World world = event.getClickedBlock().getLocation().getWorld();
        Vector center = event.getClickedBlock().getLocation().toVector();
        int radius = 50;
        Bukkit.getScheduler().runTaskLaterAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Vector offset = new Vector(x, y, z);
                        if (offset.length() <= radius) {
                            int blockX = center.getBlockX() + x;
                            int blockY = center.getBlockY() + y;
                            int blockZ = center.getBlockZ() + z;

                            if (blockY >= world.getMinHeight() && blockY <= world.getMaxHeight()) {
                                if (world.getBlockAt(blockX, blockY, blockZ).getType() != Material.AIR) {
                                    world.getBlockAt(blockX, blockY, blockZ).setType(Material.AIR);
                                }
                            }
                        }
                    }
                }
            }
        }, 1);
    }

    public boolean checkCooldown(Player player) {
        if (cooldowns.containsKey(player)) {
            long lastActionTime = cooldowns.get(player);
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastActionTime;
            if (elapsedTime < cooldownTimeMillis) {
                return true;
            }
        }
        return false;
    }
}











