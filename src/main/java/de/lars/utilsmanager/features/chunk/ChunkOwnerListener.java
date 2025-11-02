package de.lars.utilsmanager.features.chunk;

import de.lars.apimanager.apis.chunkAPI.ChunkAPI;
import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.apimanager.apis.rankAPI.RankAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.util.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChunkOwnerListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Chunk chunk = event.getBlock().getChunk();
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        if (RankAPI.getApi().getRankId(player) >= 9) {
            return;
        }
        if (ChunkAPI.getApi().getChunkOwner(chunk) == null) {
            return;
        }
        if (ChunkAPI.getApi().getFriends(chunk).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk).contains("*")) {
            return;
        }
        if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk).toString(), player.getUniqueId().toString())) {
            OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk));
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Chunk chunk = event.getBlock().getChunk();
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        if (RankAPI.getApi().getRankId(player) >= 9) {
            return;
        }
        if (ChunkAPI.getApi().getChunkOwner(chunk) == null) {
            return;
        }
        if (ChunkAPI.getApi().getFriends(chunk).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk).contains("*")) {
            return;
        }
        if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk).toString(), player.getUniqueId().toString())) {
            OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk));
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;

        Player player = event.getPlayer();
        Block clicked = event.getClickedBlock();

        if (!isContainer(clicked)) return;

        if (player.hasPermission("plugin.maintenance")) return;

        Chunk chunk = clicked.getChunk();
        UUID owner = ChunkAPI.getApi().getChunkOwner(chunk);
        if (owner == null) return;

        String uuidStr = player.getUniqueId().toString();
        List<String> friends = ChunkAPI.getApi().getFriends(chunk);

        if (friends.contains(uuidStr) || friends.contains("*")) return;
        if (owner.toString().equals(uuidStr)) return;

        event.setCancelled(true);
        OfflinePlayer offOwner = Bukkit.getOfflinePlayer(owner);
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix()
                .append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                .append(Component.text(offOwner.getName() + "!", NamedTextColor.DARK_PURPLE))
                .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
        } else {
            player.sendMessage(Statements.getPrefix()
                .append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                .append(Component.text(offOwner.getName() + "!", NamedTextColor.DARK_PURPLE))
                .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        List<org.bukkit.block.Block> blocks = event.blockList();

        Iterator<Block> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            org.bukkit.block.Block block = iterator.next();
            Chunk chunk = block.getChunk();
            UUID owner = ChunkAPI.getApi().getChunkOwner(chunk);

            if (owner != null) {
                iterator.remove();
            }
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        List<org.bukkit.block.Block> blocks = event.blockList();

        Iterator<org.bukkit.block.Block> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            org.bukkit.block.Block block = iterator.next();
            Chunk chunk = block.getChunk();
            UUID owner = ChunkAPI.getApi().getChunkOwner(chunk);

            if (owner != null) {
                iterator.remove();
            }
        }
    }

    public static boolean isContainer(Block block) {
        if (block == null) return false;
        Material m = block.getType();
        return m == Material.CHEST
            || m == Material.TRAPPED_CHEST
            || m == Material.BARREL
            || m == Material.SHULKER_BOX
            || m == Material.WHITE_SHULKER_BOX
            || m == Material.LECTERN;
    }

    /*@EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Location loc = player.getLocation();
            Chunk chunk = event.getEntity().getLocation().getChunk();
            if (RankAPI.getApi().getRankId(player) >= 9) {
                return;
            }
            if (ChunkAPI.getApi().getChunkOwner(chunk) == null) {

                return;
            }
            if (ChunkAPI.getApi().getFriends(chunk).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk).contains("*")) {
                return;
            }
            if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk).toString(), player.getUniqueId().toString())) {
                OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk));
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                            .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                            .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                            .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                            .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
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
            if (RankAPI.getApi().getRankId(player) >= 9) {
                return;
            }
            if (ChunkAPI.getApi().getChunkOwner(chunk) == null) {

                return;
            }
            if (ChunkAPI.getApi().getFriends(chunk).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk).contains("*")) {
                return;
            }
            if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk).toString(), player.getUniqueId().toString())) {
                OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk));
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                            .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                            .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                            .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                            .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
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
        if (RankAPI.getApi().getRankId(player) >= 9) {
            return;
        }
        if (ChunkAPI.getApi().getChunkOwner(chunk) == null) {

            return;
        }
        if (ChunkAPI.getApi().getFriends(chunk).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk).contains("*")) {
            return;
        }
        if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk).toString(), player.getUniqueId().toString())) {
            OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk));
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                        .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                        .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
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
            if (RankAPI.getApi().getRankId(player) >= 9) {
                return;
            }
            if (ChunkAPI.getApi().getChunkOwner(chunk) == null) {

                return;
            }
            if (ChunkAPI.getApi().getFriends(chunk).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk).contains("*")) {
                return;
            }
            if (!Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk).toString(), player.getUniqueId().toString())) {
                OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk));
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Der Chunk gehört nicht dir und du bist auch kein Freund oder Vertrauter! Besitzer: ", NamedTextColor.RED))
                            .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                            .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("This chunk isn´t yours and you aren´t a friend or trusted! Owner: ", NamedTextColor.RED))
                            .append(Component.text(owner.getName() + "!", NamedTextColor.DARK_PURPLE))
                            .append(Component.text("(" + chunk + ")", NamedTextColor.YELLOW)));
                }
                event.setCancelled(true);
            }
        }
    }
     */

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Chunk chunk = event.getEntity().getLocation().getChunk();
        Location loc = event.getLocation();
        if(!(event.getEntity() instanceof Animals)) {
            return;
        }
        /*if (ChunkAPI.getApi().getChunkOwner(chunk) == null) {
            return;
        }
        if (!ChunkAPI.getApi().getEntitySpawning(chunk)) {
            event.setCancelled(true);
        }*/
    }
}
