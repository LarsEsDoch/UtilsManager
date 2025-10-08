package de.lars.utilsManager.chunks;

import de.lars.apiManager.chunkAPI.ChunkAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChunkOwnerListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Chunk chunk = event.getBlock().getChunk();
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        if (RankAPI.getApi().getRankID(player) >= 9) {
            return;
        }
        if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {
            /*
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix() + NamedTextColor.RED + "Der Chunk hat keinen Besitzer!" + NamedTextColor.YELLOW + "(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")");
                player.sendMessage(Statements.getPrefix() + NamedTextColor.WHITE + "Du kannst ihn beanspruchen mit" + NamedTextColor.GREEN + " /chunk claim!");
            } else {
                player.sendMessage(Statements.getPrefix() + NamedTextColor.RED + "This chunk has no owner!" + NamedTextColor.YELLOW + "(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")");
                player.sendMessage(Statements.getPrefix() + NamedTextColor.WHITE + "You can claim it with" + NamedTextColor.GREEN + " /chunk claim!");
            }*/
            return;
        }
        if (ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains("*")) {
            return;
        }
        if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString(), player.getUniqueId().toString())) {
            OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Chunk chunk = event.getBlock().getChunk();
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        if (RankAPI.getApi().getRankID(player) >= 9) {
            return;
        }
        if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {
            /*
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix() + NamedTextColor.RED + "Der Chunk hat keinen Besitzer!" + NamedTextColor.YELLOW + "(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")");
                player.sendMessage(Statements.getPrefix() + NamedTextColor.WHITE + "Du kannst ihn beanspruchen mit" + NamedTextColor.GREEN + " /chunk claim!");
            } else {
                player.sendMessage(Statements.getPrefix() + NamedTextColor.RED + "This chunk has no owner!" + NamedTextColor.YELLOW + "(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")");
                player.sendMessage(Statements.getPrefix() + NamedTextColor.WHITE + "You can claim it with" + NamedTextColor.GREEN + " /chunk claim!");
            }
            */
            return;
        }
        if (ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains("*")) {
            return;
        }
        if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString(), player.getUniqueId().toString())) {
            OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        if (event.getClickedBlock() == null) {
            return;
        }
        if (player.hasPermission("plugin.maintenance")) {
            return;
        }
        Chunk chunk = event.getClickedBlock().getChunk();
        if (checkClaimed(chunk.getX(), chunk.getZ(), chunk.getWorld().getName())) {
            /*
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix() + NamedTextColor.RED + "Der Chunk hat keinen Besitzer!" + NamedTextColor.YELLOW + "(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")");
                player.sendMessage(Statements.getPrefix() + NamedTextColor.WHITE + "Du kannst ihn beanspruchen mit" + NamedTextColor.GREEN + " /chunk claim!");
            } else {
                player.sendMessage(Statements.getPrefix() + NamedTextColor.RED + "This chunk has no owner!" + NamedTextColor.YELLOW + "(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")");
                player.sendMessage(Statements.getPrefix() + NamedTextColor.WHITE + "You can claim it with" + NamedTextColor.GREEN + " /chunk claim!");
            }
            */
            return;
        }
        if (ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains("*")) {
            return;
        }
        if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString(), player.getUniqueId().toString())) {
            OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
            }
            event.setCancelled(true);
        }
    }

    public boolean checkClaimed(int x, int z, String world) {
        String chunkString = x+ "," + z + "," + world;
        AtomicBoolean check = new AtomicBoolean(false);
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), bukkitTask -> {
            check.set(ChunkAPI.getApi().getChunkOwner(chunkString) == null);
        });
        return check.get();
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Location loc = player.getLocation();
            Chunk chunk = event.getEntity().getLocation().getChunk();
            if (RankAPI.getApi().getRankID(player) >= 9) {
                return;
            }
            if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {

                return;
            }
            if (ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains("*")) {
                return;
            }
            if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString(), player.getUniqueId().toString())) {
                OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                            .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                            .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                            .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                            .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player test = (Player) event.getEntity().getShooter();
        if (event.getEntity() instanceof Firework) {
            Firework firework = (Firework) event.getEntity();
            if (!(firework.getShooter() instanceof Player)) {
                return;
            }
            Player player = (Player) firework.getShooter();
            Location loc;
            if (event.getHitBlock() == null) {
                loc = Objects.requireNonNull(event.getHitEntity()).getLocation();
            } else {
                loc = Objects.requireNonNull(event.getHitBlock()).getLocation();
            }
            Chunk chunk = event.getEntity().getLocation().getChunk();
            if (RankAPI.getApi().getRankID(player) >= 9) {
                return;
            }
            if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {

                return;
            }
            if (ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains("*")) {
                return;
            }
            if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString(), player.getUniqueId().toString())) {
                OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                            .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                            .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                            .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                            .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
                }
                event.setCancelled(true);
            }
        }
        if (!(event.getEntity() instanceof Arrow)) {
            return;
        }
        Arrow arrow = (Arrow) event.getEntity();
        if (!(arrow.getShooter() instanceof Player)) {
            return;
        }
        Player player = (Player) arrow.getShooter();
        if (!(event.getHitBlock() == null)) {
            return;
        }
        Location loc = Objects.requireNonNull(event.getHitEntity()).getLocation();
        Chunk chunk = event.getEntity().getLocation().getChunk();
        if (RankAPI.getApi().getRankID(player) >= 9) {
            return;
        }
        if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {

            return;
        }
        if (ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains("*")) {
            return;
        }
        if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString(), player.getUniqueId().toString())) {
            OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            Location loc = player.getLocation();
            Chunk chunk = event.getEntity().getLocation().getChunk();
            if (RankAPI.getApi().getRankID(player) >= 9) {
                return;
            }
            if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {

                return;
            }
            if (ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains("*")) {
                return;
            }
            if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString(), player.getUniqueId().toString())) {
                OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                            .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                            .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                            .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                            .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW)));
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Chunk chunk = event.getEntity().getLocation().getChunk();
        Location loc = event.getLocation();
        if(!(event.getEntity() instanceof Animals)) {
            return;
        }
        /*if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {
            return;
        }
        if (!ChunkAPI.getApi().getEntitySpawning(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName())) {
            event.setCancelled(true);
        }*/
    }
}
