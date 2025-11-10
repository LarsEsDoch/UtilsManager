package dev.lars.utilsmanager.features.chunk;

import dev.lars.apimanager.apis.chunkAPI.ChunkAPI;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.rankAPI.RankAPI;
import dev.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

public class ChunkOwnerListener implements Listener {

    private static final Set<Material> SAFE_INTERACTABLES = EnumSet.of(
        Material.OAK_DOOR, Material.SPRUCE_DOOR, Material.BIRCH_DOOR, Material.JUNGLE_DOOR,
        Material.ACACIA_DOOR, Material.DARK_OAK_DOOR, Material.CRIMSON_DOOR, Material.WARPED_DOOR,
        Material.IRON_DOOR,
        Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR, Material.BIRCH_TRAPDOOR,
        Material.JUNGLE_TRAPDOOR, Material.ACACIA_TRAPDOOR, Material.DARK_OAK_TRAPDOOR,
        Material.CRIMSON_TRAPDOOR, Material.WARPED_TRAPDOOR, Material.IRON_TRAPDOOR,
        Material.OAK_FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.BIRCH_FENCE_GATE,
        Material.JUNGLE_FENCE_GATE, Material.ACACIA_FENCE_GATE, Material.DARK_OAK_FENCE_GATE,
        Material.CRIMSON_FENCE_GATE, Material.WARPED_FENCE_GATE,

        Material.LEVER,
        Material.STONE_BUTTON, Material.OAK_BUTTON, Material.SPRUCE_BUTTON, Material.BIRCH_BUTTON,
        Material.JUNGLE_BUTTON, Material.ACACIA_BUTTON, Material.DARK_OAK_BUTTON,
        Material.CRIMSON_BUTTON, Material.WARPED_BUTTON,

        Material.BELL, Material.CAULDRON, Material.WATER_CAULDRON,
        Material.LAVA_CAULDRON, Material.POWDER_SNOW_CAULDRON
    );

    private static final Set<Material> ITEM_STORAGE_BLOCKS = EnumSet.of(
        Material.CHEST, Material.TRAPPED_CHEST, Material.BARREL,
        Material.SHULKER_BOX, Material.WHITE_SHULKER_BOX, Material.ORANGE_SHULKER_BOX,
        Material.MAGENTA_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX,
        Material.LIME_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.GRAY_SHULKER_BOX,
        Material.LIGHT_GRAY_SHULKER_BOX, Material.CYAN_SHULKER_BOX, Material.PURPLE_SHULKER_BOX,
        Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.GREEN_SHULKER_BOX,
        Material.RED_SHULKER_BOX, Material.BLACK_SHULKER_BOX,
        Material.HOPPER, Material.DROPPER, Material.DISPENSER,
        Material.BLAST_FURNACE, Material.FURNACE, Material.SMOKER,
        Material.BREWING_STAND, Material.LECTERN, Material.COMPOSTER
    );

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
        Player player = event.getPlayer();

        if (event.getClickedBlock() == null) {
            return;
        }
        if (RankAPI.getApi().getRankId(player) >= 9) {
            return;
        }

        Block block = event.getClickedBlock();
        Chunk chunk = block.getChunk();

        if (ChunkAPI.getApi().getChunkOwner(chunk) == null) {
            return;
        }

        if (ChunkAPI.getApi().getFriends(chunk).contains(player.getUniqueId().toString()) || ChunkAPI.getApi().getFriends(chunk).contains("*")) {
            return;
        }

        if (Objects.equals(ChunkAPI.getApi().getChunkOwner(chunk).toString(), player.getUniqueId().toString())) {
            return;
        }

        Material type = block.getType();

        if (ITEM_STORAGE_BLOCKS.contains(type)) {
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
            return;
        }

        if (isSafeBlock(block)) {
            return;
        }

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

    private boolean isSafeBlock(Block block) {
        Material mat = block.getType();
        BlockData data = block.getBlockData();

        if (SAFE_INTERACTABLES.contains(mat)) return true;
        if (data instanceof Openable) return true;
        if (data instanceof Switch) return true;
        if (data instanceof Bed) return true;
        return false;
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
