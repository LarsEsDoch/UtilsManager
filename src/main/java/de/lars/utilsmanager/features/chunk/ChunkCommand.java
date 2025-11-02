
package de.lars.utilsmanager.features.chunk;

import de.lars.apimanager.apis.chunkAPI.ChunkAPI;
import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.apimanager.apis.limitAPI.LimitAPI;
import de.lars.apimanager.apis.rankAPI.RankAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.util.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ChunkCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        Chunk chunk = player.getLocation().getChunk();
        Location loc = player.getLocation();

        String subCommand = args[0].toLowerCase();
        
        switch (subCommand) {
            case "claimspawnchunks" -> handleClaimSpawnChunks(player, loc);
            case "claim" -> handleClaim(player, chunk, loc);
            case "sell" -> handleSell(player, chunk, loc, args);
            case "list" -> handleList(player);
            case "friend" -> handleFriend(player, chunk, loc, args);
            case "free" -> handleFree(player);
            case "info" -> handleInfo(player, chunk, loc);
            case "flag" -> handleFlag(player, chunk, loc, args);
            default -> sendUsage(player);
        }
    }

    private void handleClaimSpawnChunks(Player player, Location loc) {
        if (RankAPI.getApi().getRankId(player) != 10) {
            return;
        }

        int minX = 2, minZ = -11, maxX = 38, maxZ = 18;
        World world = Bukkit.getWorld("world");
        
        Bukkit.getScheduler().runTaskAsynchronously(UtilsManager.getInstance(), task -> {
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if (world != null) {
                        Chunk spawnChunk = world.getChunkAt(x, z);
                        ChunkAPI.getApi().claimChunk(player, spawnChunk);
                        
                        sendMessage(player, 
                            "Du hast den Chunk " + formatChunkCoords(spawnChunk, loc) + " beansprucht!",
                            "You claimed the Chunk " + formatChunkCoords(spawnChunk, loc) + " !",
                            NamedTextColor.WHITE);
                    }
                }
            }
        });
    }

    private void handleClaim(Player player, Chunk chunk, Location loc) {
        UUID chunkOwner = ChunkAPI.getApi().getChunkOwner(chunk);

        if (chunkOwner != null) {
            if (chunkOwner.equals(player.getUniqueId())) {
                sendMessage(player,
                    "Das ist bereits dein Chunk!" + formatChunkCoords(chunk, loc),
                    "This is already your chunk!" + formatChunkCoords(chunk, loc),
                    NamedTextColor.RED);
                return;
            }

            if (RankAPI.getApi().getRankId(player) > 8) {
                claimChunkForPlayer(player, chunk, loc);
                OfflinePlayer owner = Bukkit.getOfflinePlayer(chunkOwner);
                sendMessage(player,
                    "Dieser hat " + owner.getName() + " davor gehört! Wenn das ein aus Versehen war, melde dich bei den SQL Managern oder Besitzern des Servers!",
                    "This one belonged to " + owner.getName() + " before! If this was an accident, report it to the SQL managers or owners of the server!",
                    NamedTextColor.DARK_RED);
                return;
            }

            OfflinePlayer owner = Bukkit.getOfflinePlayer(chunkOwner);
            sendMessage(player,
                "Der Chunk gehört bereits " + owner.getName() + " !" + formatChunkCoords(chunk, loc),
                "This chunk already belongs to " + owner.getName() + " !" + formatChunkCoords(chunk, loc),
                NamedTextColor.RED);
            return;
        }

        if (LimitAPI.getApi().getChunkLimit(player) == 0) {
            sendMessage(player,
                "Du kannst nun keine Chunks mehr beanspruchen! Du must zum Notar gehen und dir dort neue kaufen!",
                "You can´t claim more chunks. You have to go to the notary and buy new ones there!",
                NamedTextColor.RED);
            return;
        }

        claimChunkForPlayer(player, chunk, loc);
    }

    private void claimChunkForPlayer(Player player, Chunk chunk, Location loc) {
        ChunkAPI.getApi().claimChunk(player, chunk);
        LimitAPI.getApi().increaseChunkLimit(player, 1);
        
        sendMessage(player,
            "Du hast den Chunk " + formatChunkCoords(chunk, loc) + " beansprucht!",
            "You claimed the Chunk " + formatChunkCoords(chunk, loc) + " !",
            NamedTextColor.WHITE);
        
        if (RankAPI.getApi().getRankId(player) <= 8) {
            int remaining = LimitAPI.getApi().getChunkLimit(player) - 1;
            sendMessage(player,
                "Du kannst nun noch " + remaining + " Chunks beanspruchen. Wenn du mehr brauchst kannst du diese beim Notar kaufen.",
                "Now you can claim " + remaining + " chunks. If you need more chunks you can buy them at the notary.",
                NamedTextColor.WHITE);
        }
    }

    private void handleSell(Player player, Chunk chunk, Location loc, String[] args) {
        if (args.length > 1 && args[1].equals("*")) {
            List<Chunk> chunks = ChunkAPI.getApi().getChunks(player);
            for (Chunk sellChunk : chunks) {
                ChunkAPI.getApi().unclaimChunk(player, sellChunk);
                sendMessage(player,
                    "Du hast den Chunk: " + formatChunkCoords(sellChunk, loc) + " verkauft.",
                    "You sold the chunk: " + formatChunkCoords(sellChunk, loc) + ".",
                    NamedTextColor.WHITE);
            }
            return;
        }
        
        UUID chunkOwner = ChunkAPI.getApi().getChunkOwner(chunk);
        
        if (chunkOwner == null) {
            sendMessage(player,
                "Der Chunk hat keinen Besitzer! " + formatChunkCoords(chunk, loc),
                "This chunk has no owner!" + formatChunkCoords(chunk, loc),
                NamedTextColor.RED);
            return;
        }
        
        if (!chunkOwner.equals(player.getUniqueId())) {
            OfflinePlayer owner = Bukkit.getOfflinePlayer(chunkOwner);
            sendMessage(player,
                "Der Chunk gehört " + owner.getName() + " !" + formatChunkCoords(chunk, loc),
                "This chunk owns " + owner.getName() + " !" + formatChunkCoords(chunk, loc),
                NamedTextColor.RED);
            return;
        }

        ChunkAPI.getApi().unclaimChunk(player, chunk);
        if (RankAPI.getApi().getRankId(player) <= 8) {
            LimitAPI.getApi().decreaseChunkLimit(player, 1);
        }
        
        sendMessage(player,
            "Du hast den Chunk " + formatChunkCoords(chunk, loc) + " verkauft!",
            "You sold the Chunk " + formatChunkCoords(chunk, loc) + " !",
            NamedTextColor.WHITE);
        
        if (RankAPI.getApi().getRankId(player) <= 8) {
            int remaining = LimitAPI.getApi().getChunkLimit(player) + 1;
            sendMessage(player,
                "Du kannst nun noch " + remaining + " Chunks beanspruchen. Wenn du mehr brauchst kannst du diese beim Notar kaufen.",
                "Now you can claim " + remaining + " chunks. If you need more chunks you can buy them at the notary.",
                NamedTextColor.WHITE);
        }
    }

    private void handleList(Player player) {
        List<Chunk> chunks = ChunkAPI.getApi().getChunks(player);
        String chunkList = chunks.isEmpty() ? "-" : String.join(", ", 
            chunks.stream().map(c -> "(" + c.getX() + "," + c.getZ() + ")").toList());
        
        sendMessage(player,
            "Das sind deine Chunks: " + chunkList,
            "Your Chunks: " + chunkList,
            NamedTextColor.AQUA);
    }

    private void handleFriend(Player player, Chunk chunk, Location loc, String[] args) {
        if (args.length == 1) {
            sendUsageFriends(player);
            return;
        }
        
        String friendCommand = args[1].toLowerCase();
        
        switch (friendCommand) {
            case "all" -> handleFriendAll(player, chunk, loc, args);
            case "list" -> handleFriendList(player, chunk, loc);
            case "add" -> handleFriendAdd(player, chunk, loc, args);
            case "remove" -> handleFriendRemove(player, chunk, loc, args);
            default -> sendUsageFriends(player);
        }
    }

    private void handleFriendAll(Player player, Chunk chunk, Location loc, String[] args) {
        if (args.length < 3) {
            sendUsageAllFriends(player);
            return;
        }
        
        String action = args[2].toLowerCase();
        if (!action.equals("add") && !action.equals("remove")) {
            sendUsageAllFriends(player);
            return;
        }
        
        if (args.length < 4) {
            sendUsageAllFriends(player);
            return;
        }
        
        boolean addAll = "*".equals(args[3]);
        OfflinePlayer friend = addAll ? null : Bukkit.getOfflinePlayer(args[3]);
        
        if (friend != null && friend.getName() != null && friend.getName().equals(player.getName())) {
            sendMessage(player,
                "Du besitzt diesen Chunk!",
                "You own this chunk!",
                NamedTextColor.RED);
            return;
        }
        
        boolean isAdd = action.equals("add");
        OfflinePlayer finalFriend = friend;
        
        Bukkit.getScheduler().runTaskAsynchronously(UtilsManager.getInstance(), task -> {
            for (Chunk friendChunk : ChunkAPI.getApi().getChunks(player)) {
                if (addAll) {
                    if (isAdd && !ChunkAPI.getApi().getFriends(friendChunk).contains("*")) {
                        ChunkAPI.getApi().addFriend(friendChunk, null);
                        sendChunkFriendMessage(player, friendChunk, loc, "alle Spieler", "all players", isAdd);
                    } else if (!isAdd) {
                        ChunkAPI.getApi().removeFriend(friendChunk, null);
                        sendChunkFriendMessage(player, friendChunk, loc, "alle Spieler", "all players", isAdd);
                    }
                } else {
                    List<String> friends = ChunkAPI.getApi().getFriends(friendChunk);
                    String friendUUID = finalFriend.getUniqueId().toString();
                    
                    if (isAdd && !friends.contains(friendUUID)) {
                        ChunkAPI.getApi().addFriend(friendChunk, finalFriend);
                        sendChunkFriendMessage(player, friendChunk, loc, finalFriend.getName(), finalFriend.getName(), isAdd);
                    } else if (!isAdd && friends.contains(friendUUID)) {
                        ChunkAPI.getApi().removeFriend(friendChunk, finalFriend);
                        sendChunkFriendMessage(player, friendChunk, loc, finalFriend.getName(), finalFriend.getName(), isAdd);
                    }
                }
            }
        });
    }

    private void handleFriendList(Player player, Chunk chunk, Location loc) {
        if (!validateChunkOwnership(player, chunk, loc)) {
            return;
        }
        
        List<String> friendList = ChunkAPI.getApi().getFriends(chunk);
        Component friends = formatFriendList(friendList, player);
        player.sendMessage(friends);
    }

    private void handleFriendAdd(Player player, Chunk chunk, Location loc, String[] args) {
        if (!validateChunkOwnership(player, chunk, loc)) {
            return;
        }
        
        if (args.length == 2) {
            sendUsageFriends(player);
            return;
        }
        
        boolean addAll = "*".equals(args[2]);
        OfflinePlayer friend = addAll ? null : Bukkit.getOfflinePlayer(args[2]);
        
        if (friend != null && friend.getName() != null && friend.getName().equals(player.getName())) {
            sendMessage(player,
                "Du besitzt diesen Chunk!",
                "You own this chunk!",
                NamedTextColor.RED);
            return;
        }
        
        List<String> friends = ChunkAPI.getApi().getFriends(chunk);
        
        if (friends.contains("*")) {
            sendMessage(player,
                "Jeder ist vertraut und kann hier bauen!",
                "Everyone is trusted and can build here!",
                NamedTextColor.RED);
            return;
        }
        
        if (friend != null && friends.contains(friend.getUniqueId().toString())) {
            sendMessage(player,
                "Dieser Spieler ist bereits ein Freund, der hier bauen kann!",
                "This player is already a friend, which can build here!",
                NamedTextColor.RED);
            return;
        }
        
        ChunkAPI.getApi().addFriend(chunk, friend);
        
        String playerName = addAll ? "alle Spieler" : friend.getName();
        String playerNameEn = addAll ? "all players" : friend.getName();
        
        sendMessage(player,
            "Du hast " + playerName + " zu den Freunden dieses Chunks hinzugefügt. Chunk: " + formatChunkCoords(chunk, loc),
            "You add " + playerNameEn + " to the friends of this chunk. Chunk: " + formatChunkCoords(chunk, loc),
            NamedTextColor.WHITE);
    }

    private void handleFriendRemove(Player player, Chunk chunk, Location loc, String[] args) {
        if (!validateChunkOwnership(player, chunk, loc)) {
            return;
        }
        
        if (args.length == 2) {
            sendUsageFriends(player);
            return;
        }
        
        boolean removeAll = "*".equals(args[2]);
        OfflinePlayer friend = removeAll ? null : Bukkit.getOfflinePlayer(args[2]);
        
        if (friend != null && friend.getName() != null && friend.getName().equals(player.getName())) {
            sendMessage(player,
                "Du besitzt diesen Chunk!",
                "You own this chunk!",
                NamedTextColor.RED);
            return;
        }
        
        if (!removeAll && !ChunkAPI.getApi().getFriends(chunk).contains(friend.getUniqueId().toString())) {
            sendMessage(player,
                "Dieser Spieler ist gar nicht ein Freund, der hier bauen kann!",
                "This player isn´t a friend, which can build here!",
                NamedTextColor.RED);
            return;
        }
        
        ChunkAPI.getApi().removeFriend(chunk, friend);
        
        String playerName = removeAll ? "alle Spieler" : friend.getName();
        String playerNameEn = removeAll ? "all players" : friend.getName();
        
        sendMessage(player,
            "Du hast " + playerName + " von den Freunden dieses Chunks entfernt. Chunk: " + formatChunkCoords(chunk, loc),
            "You removed " + playerNameEn + " from the friends of this chunk. Chunk: " + formatChunkCoords(chunk, loc),
            NamedTextColor.WHITE);
    }

    private void handleFree(Player player) {
        if (RankAPI.getApi().getRankId(player) > 8) {
            sendMessage(player,
                "Du kannst nun noch ∞ Chunks beantragen. Da du ein Admin/Owner bist!",
                "You can claim ∞ chunks. Because you´re an Admin/Owner!",
                NamedTextColor.WHITE);
            return;
        }
        
        int limit = LimitAPI.getApi().getChunkLimit(player);
        
        if (limit == 0) {
            sendMessage(player,
                "Du kannst nun keine Chunks mehr beanspruchen! Du must zum Notar gehen und dir dort neue kaufen!",
                "You can´t claim more chunks. You have to go to the notary and buy new ones there!",
                NamedTextColor.RED);
            return;
        }
        
        sendMessage(player,
            "Du kannst nun noch " + limit + " Chunks beantragen. Wenn du mehr brauchst kannst du diese beim Notar kaufen.",
            "You can claim " + limit + " chunks. If you need more chunks you can buy them at the notary.",
            NamedTextColor.WHITE);
    }

    private void handleInfo(Player player, Chunk chunk, Location loc) {
        UUID chunkOwnerId = ChunkAPI.getApi().getChunkOwner(chunk);
        String owner = chunkOwnerId == null ? "---" : Bukkit.getOfflinePlayer(chunkOwnerId).getName();
        String chunkCoords = formatChunkCoords(chunk, loc);
        Component friends = chunkOwnerId == null ? Component.text("---", NamedTextColor.GRAY) 
            : formatFriendListInfo(ChunkAPI.getApi().getFriends(chunk));
        String flags = "EntitySpawning: " + (isGerman(player) ? "Aktiviert" : "Activated");
        
        Component title = Component.text("                   ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH)
        .append(Component.text("[ ", NamedTextColor.DARK_GRAY))
        .append(Component.text("Chunk Info", NamedTextColor.LIGHT_PURPLE))
        .append(Component.text(" ]", NamedTextColor.DARK_GRAY))
        .append(Component.text("                   ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH));
        
        player.sendMessage(Statements.getPrefix().append(title));
        player.sendMessage(Statements.getPrefix());
        
        player.sendMessage(Statements.getPrefix()
            .append(Component.text(isGerman(player) ? " Besitzer: " : " Owner: ", NamedTextColor.DARK_PURPLE))
            .append(Component.text(owner, NamedTextColor.GREEN)));
        
        player.sendMessage(Statements.getPrefix());
        
        player.sendMessage(Statements.getPrefix()
            .append(Component.text(" Chunk: ", NamedTextColor.LIGHT_PURPLE))
            .append(Component.text(chunkCoords, NamedTextColor.BLUE)));
        
        player.sendMessage(Statements.getPrefix());
        
        player.sendMessage(Statements.getPrefix()
            .append(Component.text(isGerman(player) ? " Freunde/Vertraute: " : " Friends/Trusted: ", NamedTextColor.AQUA))
            .append(friends));
        
        player.sendMessage(Statements.getPrefix());
        
        player.sendMessage(Statements.getPrefix()
            .append(Component.text(" Flags: ", NamedTextColor.GOLD))
            .append(Component.text(flags, NamedTextColor.DARK_AQUA)));
        
        player.sendMessage(Statements.getPrefix());
        player.sendMessage(Statements.getPrefix().append(title));
    }

    private void handleFlag(Player player, Chunk chunk, Location loc, String[] args) {
        if (args.length == 1) {
            sendUsageFlag(player);
            return;
        }
        
        String flagCommand = args[1].toLowerCase();
        
        if (flagCommand.equals("info")) {
            String available = isGerman(player) ? "Verfügbare Flags: " : "Available Flags: ";
            String activated = isGerman(player) ? "Aktivierte Flags: " : "Activated Flags: ";
            
            player.sendMessage(Statements.getPrefix()
                .append(Component.text(available, NamedTextColor.WHITE))
                .append(Component.text("EntitySpawning", NamedTextColor.BLUE)));
            
            player.sendMessage(Statements.getPrefix()
                .append(Component.text(activated, NamedTextColor.WHITE))
                .append(Component.text("EntitySpawning", NamedTextColor.GREEN)));
            return;
        }
        
        if (!flagCommand.equals("set") || args.length < 4) {
            sendUsageFlag(player);
            return;
        }
        
        if (!args[2].equalsIgnoreCase("entityspawning")) {
            sendUsageFlag(player);
            return;
        }
        
        if (!validateChunkOwnership(player, chunk, loc)) {
            return;
        }
        
        boolean enableFlag = args[3].equalsIgnoreCase("true");
        String status = enableFlag ? 
            (isGerman(player) ? "aktiviert" : "activated") : 
            (isGerman(player) ? "deaktiviert" : "deactivated");
        NamedTextColor statusColor = enableFlag ? NamedTextColor.GREEN : NamedTextColor.RED;
        
        ChunkAPI.getApi().setFlags(chunk, "{\"entityspawning\": " + enableFlag + "}");
        
        player.sendMessage(Statements.getPrefix()
            .append(Component.text(isGerman(player) ? "Du hast den Flag: " : "You ", NamedTextColor.WHITE))
            .append(Component.text(enableFlag ? "" : status + " ", statusColor))
            .append(Component.text(isGerman(player) ? "" : "the flag: ", NamedTextColor.WHITE))
            .append(Component.text("EntitySpawning ", NamedTextColor.GOLD))
            .append(Component.text(enableFlag ? status + "! " : "! ", enableFlag ? statusColor : NamedTextColor.WHITE))
            .append(Component.text("Chunk: ", NamedTextColor.WHITE))
            .append(Component.text(formatChunkCoords(chunk, loc), NamedTextColor.DARK_BLUE)));
    }
    
    private boolean validateChunkOwnership(Player player, Chunk chunk, Location loc) {
        UUID chunkOwner = ChunkAPI.getApi().getChunkOwner(chunk);
        
        if (chunkOwner == null) {
            sendMessage(player,
                "Der Chunk hat keinen Besitzer! " + formatChunkCoords(chunk, loc),
                "This chunk has no owner!" + formatChunkCoords(chunk, loc),
                NamedTextColor.RED);
            return false;
        }
        
        if (!chunkOwner.equals(player.getUniqueId())) {
            OfflinePlayer owner = Bukkit.getOfflinePlayer(chunkOwner);
            sendMessage(player,
                "Der Chunk gehört nicht dir! Besitzer: " + owner.getName() + "!" + formatChunkCoords(chunk, loc),
                "This chunk isn´t yours! Owner: " + owner.getName() + "!" + formatChunkCoords(chunk, loc),
                NamedTextColor.RED);
            return false;
        }
        
        return true;
    }

    private void sendChunkFriendMessage(Player player, Chunk chunk, Location loc, String playerNameDe, String playerNameEn, boolean isAdd) {
        String actionDe = isAdd ? "hinzugefügt" : "entfernt";
        String actionEn = isAdd ? "add" : "removed";
        String prepositionDe = isAdd ? "zu" : "von";
        String prepositionEn = isAdd ? "to" : "from";
        
        sendMessage(player,
            "Du hast " + playerNameDe + " " + prepositionDe + " den Freunden dieses Chunks " + actionDe + ". Chunk: " + formatChunkCoords(chunk, loc),
            "You " + actionEn + " " + playerNameEn + " " + prepositionEn + " the friends of this chunk. Chunk: " + formatChunkCoords(chunk, loc),
            NamedTextColor.WHITE);
    }

    private String formatChunkCoords(Chunk chunk, Location loc) {
        return "(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")";
    }

    private boolean isGerman(Player player) {
        return LanguageAPI.getApi().getLanguage(player) == 2;
    }

    private void sendMessage(Player player, String germanText, String englishText, NamedTextColor color) {
        String message = isGerman(player) ? germanText : englishText;
        player.sendMessage(Statements.getPrefix().append(Component.text(message, color)));
    }

    private Component formatFriendList(List<String> friendList, Player player) {
        String prefix = isGerman(player) ? 
            "Das sind deine Freunde, die hier bauen können: " : 
            "That are your friends, which can build here: ";
        
        return Statements.getPrefix()
            .append(Component.text(prefix, NamedTextColor.WHITE))
            .append(formatFriendListInfo(friendList));
    }

    private Component formatFriendListInfo(List<String> friendList) {
        if (friendList.isEmpty()) {
            return Component.text("---", NamedTextColor.GRAY);
        }
        
        if (friendList.contains("*")) {
            return Component.text("*", NamedTextColor.LIGHT_PURPLE);
        }
        
        List<String> names = new ArrayList<>();
        for (String uuid : friendList) {
            OfflinePlayer friend = getOfflinePlayerByUUID(uuid);
            if (friend.getName() != null) {
                names.add(friend.getName());
            }
        }
        
        return Component.text(String.join(", ", names), NamedTextColor.LIGHT_PURPLE);
    }

    private OfflinePlayer getOfflinePlayerByUUID(String uuid) {
        try {
            UUID playerUUID = UUID.fromString(uuid);
            Player onlinePlayer = Bukkit.getPlayer(playerUUID);
            return onlinePlayer != null ? onlinePlayer : Bukkit.getOfflinePlayer(playerUUID);
        } catch (IllegalArgumentException e) {
            return Bukkit.getOfflinePlayer(uuid);
        }
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        if (args.length <= 1) {
            return List.of("claim", "sell", "friend", "list", "info", "free", "flag");
        }
        
        if (args[0].equalsIgnoreCase("friend")) {
            if (args.length == 2) {
                return List.of("add", "remove", "list", "all");
            }
            if (args.length == 3 && args[1].equalsIgnoreCase("all")) {
                return List.of("add", "remove");
            }
        }
        
        if (args[0].equalsIgnoreCase("flag")) {
            if (args.length == 2) {
                return List.of("set", "info");
            }
            if (args.length == 3 && args[1].equalsIgnoreCase("set")) {
                return List.of("entityspawning");
            }
            if (args.length == 4 && args[1].equalsIgnoreCase("set")) {
                return List.of("true", "false");
            }
        }
        
        return Collections.emptyList();
    }

    private void sendUsage(CommandSender sender) {
        Player player = (Player) sender;
        String usage = isGerman(player) ?
            "/chunk claim, sell, friend (add <Spieler>, remove <Spieler>, list), list, info, free, flag" :
            "/chunk claim, sell, friend (add <player>, remove <player>, list), list, info, free, flag";
        
        sender.sendMessage(Component.text(isGerman(player) ? "Verwendung" : "Use", NamedTextColor.GRAY)
            .append(Component.text(": ", NamedTextColor.DARK_GRAY))
            .append(Component.text(usage, NamedTextColor.BLUE)));
    }

    private void sendUsageFriends(CommandSender sender) {
        Player player = (Player) sender;
        String usage = isGerman(player) ?
            "/chunk friend add <Spieler>, remove <Spieler>, list" :
            "/chunk friend add <player>, remove <player>, list";
        
        sender.sendMessage(Component.text(isGerman(player) ? "Verwendung" : "Use", NamedTextColor.GRAY)
            .append(Component.text(": ", NamedTextColor.DARK_GRAY))
            .append(Component.text(usage, NamedTextColor.BLUE)));
    }

    private void sendUsageAllFriends(CommandSender sender) {
        Player player = (Player) sender;
        String usage = isGerman(player) ?
            "/chunk friend all add <Spieler>, remove <Spieler>" :
            "/chunk friend all add <player>, remove <player>";
        
        sender.sendMessage(Component.text(isGerman(player) ? "Verwendung" : "Use", NamedTextColor.GRAY)
            .append(Component.text(": ", NamedTextColor.DARK_GRAY))
            .append(Component.text(usage, NamedTextColor.BLUE)));
    }

    private void sendUsageFlag(CommandSender sender) {
        Player player = (Player) sender;
        String usage = isGerman(player) ?
            "/chunk flag set <Flags(EntitySpawning)> <true/false>, info" :
            "/chunk flag set <flags(EntitySpawning)> <true/false>, info";
        
        sender.sendMessage(Component.text(isGerman(player) ? "Verwendung" : "Use", NamedTextColor.GRAY)
            .append(Component.text(": ", NamedTextColor.DARK_GRAY))
            .append(Component.text(usage, NamedTextColor.BLUE)));
    }
}