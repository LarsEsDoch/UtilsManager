package de.lars.utilsManager.chunks;

import de.lars.apiManager.chunkAPI.ChunkAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
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
            stack.getSender().sendMessage(Component.text("Only player can send Messages.", NamedTextColor.RED));
            return;
        }
        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        Location loc = player.getLocation();
        Chunk chunk = player.getLocation().getChunk();
        Component chunkComponent = Component.text(" (" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW);
        Component chunkComponentClean = Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.DARK_BLUE);
        if (args[0].equalsIgnoreCase("claimspawnchunks")) {
            if (RankAPI.getApi().getRankID(player) == 10) {
                int minX = 2;
                int minZ = -11;
                int maxX = 38;
                int maxZ = 18;
                World world = Bukkit.getWorld("world");
                Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), bukkitTask -> {
                    for (int x = minX; x <= maxX; x++) {
                        for (int z = minZ; z <= maxZ; z++) {
                            assert world != null;
                            Chunk spawnChunk = world.getChunkAt(x, z);
                            ChunkAPI.getApi().claimChunk(player, player.getName(), spawnChunk.getX() + "," + spawnChunk.getZ() + "," + loc.getWorld().getName(), "");
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Du hast den Chunk ", NamedTextColor.WHITE))
                                        .append(Component.text(spawnChunk.getX() + "," + spawnChunk.getZ() + "," + loc.getWorld().getName(), NamedTextColor.DARK_BLUE))
                                        .append(Component.text(" beansprucht!", NamedTextColor.WHITE)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("You claimed the Chunk ", NamedTextColor.WHITE))
                                        .append(Component.text(spawnChunk.getX() + "," + spawnChunk.getZ() + "," + loc.getWorld().getName(), NamedTextColor.DARK_BLUE))
                                        .append(Component.text(" !", NamedTextColor.WHITE)));
                            }
                        }
                    }
                });
            }
            return;
        }
        switch (args[0].toLowerCase()) {
            case "claim": {
                if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {
                    if (ChunkAPI.getApi().getFreeChunks(player) == 0) {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Du kannst nun keine Chunks mehr beanspruchen! Du must zum Notar gehen und dir dort neue kaufen!", NamedTextColor.RED)));
                        } else {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("You can´t claim more chunks. You have to go to the notary and buy new ones there!", NamedTextColor.RED)));
                        }
                        return;
                    }
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast den Chunk ", NamedTextColor.WHITE))
                                .append(Component.text(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), NamedTextColor.DARK_BLUE))
                                .append(Component.text(" beansprucht!", NamedTextColor.WHITE)));
                        if (!(RankAPI.getApi().getRankID(player) > 8)) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Du kannst nun noch ", NamedTextColor.WHITE))
                                    .append(Component.text((ChunkAPI.getApi().getFreeChunks(player) - 1), NamedTextColor.GREEN))
                                    .append(Component.text(" Chunks beanspruchen. Wenn du mehr brauchst kannst du diese beim Notar kaufen.", NamedTextColor.WHITE)));
                        }
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You claimed the Chunk ", NamedTextColor.WHITE))
                                .append(Component.text(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), NamedTextColor.DARK_BLUE))
                                .append(Component.text(" !", NamedTextColor.WHITE)));
                        if (!(RankAPI.getApi().getRankID(player) > 8)) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Now you can claim ", NamedTextColor.WHITE))
                                    .append(Component.text((ChunkAPI.getApi().getFreeChunks(player) - 1), NamedTextColor.GREEN))
                                    .append(Component.text(" chunks. If you need more chunks you can buy them at the notary.", NamedTextColor.WHITE)));
                        }
                    }
                    if (RankAPI.getApi().getRankID(player) > 8) {
                        ChunkAPI.getApi().addFree(player, 1);
                    }
                    ChunkAPI.getApi().claimChunk(player, player.getName(), chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), "");
                    break;
                }
                if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString().contains(player.getUniqueId().toString())) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(
                                Component.text()
                                        .append(Statements.getPrefix())
                                        .append(Component.text("Das ist bereits dein Chunk!", NamedTextColor.RED))
                                        .append(Component.text(" (", NamedTextColor.YELLOW))
                                        .append(Component.text(String.valueOf(chunk.getX()), NamedTextColor.YELLOW))
                                        .append(Component.text(",", NamedTextColor.YELLOW))
                                        .append(Component.text(String.valueOf(chunk.getZ()), NamedTextColor.YELLOW))
                                        .append(Component.text(",", NamedTextColor.YELLOW))
                                        .append(Component.text(loc.getWorld().getName(), NamedTextColor.YELLOW))
                                        .append(Component.text(")", NamedTextColor.YELLOW))
                                        .build()
                        );
                    } else {
                        player.sendMessage(
                                Component.text()
                                        .append(Statements.getPrefix())
                                        .append(Component.text("This is already your chunk!", NamedTextColor.RED))
                                        .append(Component.text(" (", NamedTextColor.YELLOW))
                                        .append(Component.text(String.valueOf(chunk.getX()), NamedTextColor.YELLOW))
                                        .append(Component.text(",", NamedTextColor.YELLOW))
                                        .append(Component.text(String.valueOf(chunk.getZ()), NamedTextColor.YELLOW))
                                        .append(Component.text(",", NamedTextColor.YELLOW))
                                        .append(Component.text(loc.getWorld().getName(), NamedTextColor.YELLOW))
                                        .append(Component.text(")", NamedTextColor.YELLOW))
                                        .build()
                        );
                    }

                    return;
                }
                if (ChunkAPI.getApi().doesChunkHaveOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName())) {
                    OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
                    if (RankAPI.getApi().getRankID(player) > 8) {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(
                                    Component.text()
                                            .append(Statements.getPrefix())
                                            .append(Component.text("Du hast den Chunk ", NamedTextColor.WHITE))
                                            .append(Component.text(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), NamedTextColor.DARK_BLUE))
                                            .append(Component.text(" beansprucht!", NamedTextColor.WHITE))
                                            .build()
                            );
                            player.sendMessage(
                                    Component.text()
                                            .append(Statements.getPrefix())
                                            .append(Component.text("Dieser hat ", NamedTextColor.DARK_RED))
                                            .append(Component.text(Objects.requireNonNull(owner.getName()), NamedTextColor.DARK_PURPLE))
                                            .append(Component.text(" davor gehört!", NamedTextColor.RED))
                                            .append(Component.text(" Wenn das ein aus Versehen war, melde dich bei den SQL Managern oder Besitzern des Servers!", NamedTextColor.RED, TextDecoration.BOLD))
                                            .build()
                            );
                        } else {
                            player.sendMessage(
                                    Component.text()
                                            .append(Statements.getPrefix())
                                            .append(Component.text("You claimed the Chunk ", NamedTextColor.WHITE))
                                            .append(Component.text(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), NamedTextColor.DARK_BLUE))
                                            .append(Component.text(" !", NamedTextColor.WHITE))
                                            .build()
                            );
                            player.sendMessage(
                                    Component.text()
                                            .append(Statements.getPrefix())
                                            .append(Component.text("This one belonged to ", NamedTextColor.DARK_RED))
                                            .append(Component.text(Objects.requireNonNull(owner.getName()), NamedTextColor.DARK_PURPLE))
                                            .append(Component.text(" before!", NamedTextColor.DARK_RED))
                                            .append(Component.text(" If this was an accident, report it to the SQL managers or owners of the server!", NamedTextColor.DARK_RED, TextDecoration.BOLD))
                                            .build()
                            );
                        }
                        if (RankAPI.getApi().getRankID(player) > 8) {
                            ChunkAPI.getApi().addFree(player, 1);
                        }
                        ChunkAPI.getApi().claimChunk(player, player.getName(), chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), "");
                        break;
                    }
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(
                                Component.text()
                                        .append(Statements.getPrefix())
                                        .append(Component.text("Der Chunk gehört bereits ", NamedTextColor.RED))
                                        .append(Component.text(Objects.requireNonNull(owner.getName()), NamedTextColor.DARK_PURPLE))
                                        .append(Component.text(" !", NamedTextColor.RED))
                                        .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW))
                                        .build()
                        );
                    } else {
                        player.sendMessage(
                                Component.text()
                                        .append(Statements.getPrefix())
                                        .append(Component.text("This chunk already belongs to ", NamedTextColor.RED))
                                        .append(Component.text(Objects.requireNonNull(owner.getName()), NamedTextColor.DARK_PURPLE))
                                        .append(Component.text(" !", NamedTextColor.RED))
                                        .append(Component.text("(" + chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName() + ")", NamedTextColor.YELLOW))
                                        .build()
                        );
                    }

                    return;
                }
                if (ChunkAPI.getApi().getFreeChunks(player) == 0) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(
                                Component.text()
                                        .append(Statements.getPrefix())
                                        .append(Component.text("Du kannst nun keine Chunks mehr beanspruchen! Du musst zum Notar gehen und dir dort neue kaufen!", NamedTextColor.RED))
                                        .build()
                        );
                    } else {
                        player.sendMessage(
                                Component.text()
                                        .append(Statements.getPrefix())
                                        .append(Component.text("You can´t claim more chunks. You have to go to the notary and buy new ones there!", NamedTextColor.RED))
                                        .build()
                        );
                    }

                    return;
                }
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den Chunk ", NamedTextColor.WHITE))
                            .append(Component.text(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), NamedTextColor.DARK_BLUE))
                            .append(Component.text(" beansprucht!", NamedTextColor.WHITE)));
                    if (!(RankAPI.getApi().getRankID(player) > 8)) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du kannst nun noch ", NamedTextColor.WHITE))
                                .append(Component.text((ChunkAPI.getApi().getFreeChunks(player) - 1), NamedTextColor.GREEN))
                                .append(Component.text(" Chunks beanspruchen. Wenn du mehr brauchst kannst du diese beim Notar kaufen.", NamedTextColor.WHITE)));
                    }
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You claimed the Chunk ", NamedTextColor.WHITE))
                            .append(Component.text(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), NamedTextColor.DARK_BLUE))
                            .append(Component.text(" !", NamedTextColor.WHITE)));
                    if (!(RankAPI.getApi().getRankID(player) > 8)) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Now you can claim ", NamedTextColor.WHITE))
                                .append(Component.text((ChunkAPI.getApi().getFreeChunks(player) - 1), NamedTextColor.GREEN))
                                .append(Component.text(" chunks. If you need more chunks you can buy them at the notary.", NamedTextColor.WHITE)));
                    }
                }
                if (RankAPI.getApi().getRankID(player) > 8) {
                    ChunkAPI.getApi().addFree(player, 1);
                }
                ChunkAPI.getApi().claimChunk(player, player.getName(), chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), "");
                break;
            }
            case "sell": {
                if (args.length > 1) {
                    if (args[1].equals("*")) {
                        for (String chunkName : ChunkAPI.getApi().getChunks(player)) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Du hast den Chunk: ", NamedTextColor.WHITE))
                                        .append(Component.text(chunkName, NamedTextColor.DARK_BLUE))
                                        .append(Component.text(" verkauft.", NamedTextColor.WHITE)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("You sold the chunk: ", NamedTextColor.WHITE))
                                        .append(Component.text(chunkName, NamedTextColor.DARK_BLUE))
                                        .append(Component.text(".", NamedTextColor.WHITE)));
                            }
                            ChunkAPI.getApi().sellChunk(player, chunkName);
                        }
                    }
                }

                if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Chunk hat keinen Besitzer! ", NamedTextColor.RED))
                                .append(chunkComponent));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("This chunk has no owner!", NamedTextColor.RED))
                                .append(chunkComponent));
                    }
                    return;
                }
                if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString().contains(player.getUniqueId().toString())) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast den Chunk ", NamedTextColor.WHITE))
                                .append(chunkComponentClean)
                                .append(Component.text(" verkauft!", NamedTextColor.WHITE)));
                        if (!(RankAPI.getApi().getRankID(player) > 8)) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Du kannst nun noch ", NamedTextColor.WHITE))
                                    .append(Component.text((ChunkAPI.getApi().getFreeChunks(player) + 1), NamedTextColor.GREEN))
                                    .append(Component.text(" Chunks beanspruchen. Wenn du mehr brauchst kannst du diese beim Notar kaufen.", NamedTextColor.WHITE)));
                        }
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You sold the Chunk ", NamedTextColor.WHITE))
                                .append(chunkComponentClean)
                                .append(Component.text(" !", NamedTextColor.WHITE)));
                        if (!(RankAPI.getApi().getRankID(player) > 8)) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Now you can claim ", NamedTextColor.WHITE))
                                    .append(Component.text((ChunkAPI.getApi().getFreeChunks(player) + 1), NamedTextColor.GREEN))
                                    .append(Component.text(" chunks. If you need more chunks you can buy them at the notary.", NamedTextColor.WHITE)));
                        }
                    }
                    if (RankAPI.getApi().getRankID(player) > 8) {
                        ChunkAPI.getApi().removeFree(player, 1);
                    }
                    ChunkAPI.getApi().sellChunk(player, chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName());
                    break;
                }
                if (ChunkAPI.getApi().doesChunkHaveOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName())) {
                    OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Der Chunk gehört ", NamedTextColor.RED))
                                .append(Component.text(owner.getName() + " !", NamedTextColor.DARK_PURPLE))
                                .append(chunkComponent));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("This chunk owns ", NamedTextColor.RED))
                                .append(Component.text(owner.getName() + " !", NamedTextColor.DARK_PURPLE))
                                .append(chunkComponent));
                    }
                    return;
                }
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den Chunk ", NamedTextColor.WHITE))
                            .append(chunkComponentClean)
                            .append(Component.text(" verkauft!", NamedTextColor.WHITE)));
                    if (!(RankAPI.getApi().getRankID(player) > 8)) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du kannst nun noch ", NamedTextColor.WHITE))
                                .append(Component.text((ChunkAPI.getApi().getFreeChunks(player) + 1), NamedTextColor.GREEN))
                                .append(Component.text(" Chunks beanspruchen. Wenn du mehr brauchst kannst du diese beim Notar kaufen.", NamedTextColor.WHITE)));
                    }
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You sold the Chunk ", NamedTextColor.WHITE))
                            .append(chunkComponentClean)
                            .append(Component.text(" !", NamedTextColor.WHITE)));
                    if (!(RankAPI.getApi().getRankID(player) > 8)) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Now you can claim ", NamedTextColor.WHITE))
                                .append(Component.text((ChunkAPI.getApi().getFreeChunks(player) + 1), NamedTextColor.GREEN))
                                .append(Component.text(" chunks. If you need more chunks you can buy them at the notary.", NamedTextColor.WHITE)));
                    }
                }
                if (RankAPI.getApi().getRankID(player) > 8) {
                    ChunkAPI.getApi().removeFree(player, 1);
                }
                ChunkAPI.getApi().sellChunk(player, chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName());
                break;
            }
            case "list": {
                ArrayList chunks = (ArrayList) ChunkAPI.getApi().getChunks(player);
                String stringshunk = String.join(", ", chunks);;

                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Das sind deine Chunks: ", NamedTextColor.AQUA))
                            .append(Component.text(stringshunk, NamedTextColor.LIGHT_PURPLE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Your Chunks: ", NamedTextColor.AQUA))
                            .append(Component.text(stringshunk, NamedTextColor.LIGHT_PURPLE)));
                }
                break;
            }
            case "friend": {
                if (args.length == 1) {
                    sendUsageFriends(player);
                    return;
                }
                switch (args[1].toLowerCase()) {
                    case "all": {
                        switch (args[2].toLowerCase()) {
                            case "add": {
                                if (args.length == 3) {
                                    sendUsageAllFriends(player);
                                    return;
                                }
                                OfflinePlayer friend = Bukkit.getOfflinePlayer("Test123");
                                boolean all;
                                if (Objects.equals("*", args[3])) {
                                    all = true;
                                } else {
                                    all = false;
                                    friend = Bukkit.getOfflinePlayer(args[3]);
                                }
                                if (Objects.requireNonNull(friend.getName()).contains(player.getName())) {
                                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                        player.sendMessage(Statements.getPrefix().append(Component.text("Du besitzt diesen Chunk!", NamedTextColor.RED)));
                                    } else {
                                        player.sendMessage(Statements.getPrefix().append(Component.text("You own this chunk!", NamedTextColor.RED)));
                                    }
                                    return;
                                }
                                OfflinePlayer finalFriend = friend;
                                Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), bukkitTask -> {
                                    for (String chunkName : ChunkAPI.getApi().getChunks(player)) {
                                        if(all) {
                                            if(!ChunkAPI.getApi().getFriends(chunkName).contains("*")) {
                                                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                                    player.sendMessage(Statements.getPrefix()
                                                            .append(Component.text("Du hast alle Spieler zu den Freunden des Chunks: ", NamedTextColor.WHITE))
                                                            .append(Component.text(chunkName, NamedTextColor.DARK_BLUE))
                                                            .append(Component.text(" hinzugefügt.", NamedTextColor.WHITE)));
                                                } else {
                                                    player.sendMessage(Statements.getPrefix()
                                                            .append(Component.text("You add all players to the friends of the chunk: ", NamedTextColor.WHITE))
                                                            .append(Component.text(chunkName, NamedTextColor.DARK_BLUE))
                                                            .append(Component.text(" .", NamedTextColor.WHITE)));
                                                }
                                                ChunkAPI.getApi().addFriend(player, chunkName, "*");
                                            }
                                        } else if(!ChunkAPI.getApi().getFriends(chunkName).contains(finalFriend.getUniqueId().toString())) {
                                            ChunkAPI.getApi().addFriend(player, chunkName, String.valueOf(finalFriend.getUniqueId()));
                                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                                player.sendMessage(Statements.getPrefix()
                                                        .append(Component.text("Du hast den Spieler ", NamedTextColor.WHITE))
                                                        .append(Component.text(finalFriend.getName(), NamedTextColor.GREEN))
                                                        .append(Component.text(" zu den Freunden dieses Chunks hinzugefügt. Chunk: ", NamedTextColor.WHITE))
                                                        .append(Component.text(chunkName, NamedTextColor.DARK_BLUE)));
                                            } else {
                                                player.sendMessage(Statements.getPrefix()
                                                        .append(Component.text("You add the player ", NamedTextColor.WHITE))
                                                        .append(Component.text(finalFriend.getName(), NamedTextColor.GREEN))
                                                        .append(Component.text(" to the friends of this chunk. Chunk:  ", NamedTextColor.WHITE))
                                                        .append(Component.text(chunkName, NamedTextColor.DARK_BLUE)));
                                            }
                                        }
                                    }
                                });
                                break;
                            }
                            case "remove": {
                                if (args.length == 3) {
                                    sendUsageFriends(player);
                                    return;
                                }
                                OfflinePlayer friend = Bukkit.getOfflinePlayer("Test123");
                                Boolean all;
                                if (Objects.equals("*", args[3])) {
                                    all = true;
                                } else {
                                    all = false;
                                    friend = Bukkit.getOfflinePlayer(args[3]);
                                }
                                if (Objects.requireNonNull(friend.getName()).contains(player.getName())) {
                                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                        player.sendMessage(Statements.getPrefix().append(Component.text("Du besitzt diesen Chunk!", NamedTextColor.RED)));
                                    } else {
                                        player.sendMessage(Statements.getPrefix().append(Component.text("You own this chunk!", NamedTextColor.RED)));
                                    }
                                    return;
                                }

                                OfflinePlayer finalFriend = friend;
                                Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), bukkitTask -> {
                                    for (String chunkName : ChunkAPI.getApi().getChunks(player)) {
                                        if(all) {
                                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                                player.sendMessage(Statements.getPrefix()
                                                        .append(Component.text("Du hast alle Spieler von den Freunden des Chunks: ", NamedTextColor.WHITE))
                                                        .append(Component.text(chunkName, NamedTextColor.DARK_BLUE))
                                                        .append(Component.text(" entfernt.", NamedTextColor.WHITE)));
                                            } else {
                                                player.sendMessage(Statements.getPrefix()
                                                        .append(Component.text("You removed all players from the friends of the chunk: ", NamedTextColor.WHITE))
                                                        .append(Component.text(chunkName, NamedTextColor.DARK_BLUE))
                                                        .append(Component.text(" .", NamedTextColor.WHITE)));
                                            }
                                            ChunkAPI.getApi().removeFriend(player, chunkName, "*");
                                        } else if(ChunkAPI.getApi().getFriends(chunkName).contains(finalFriend.getUniqueId().toString())) {
                                            ChunkAPI.getApi().removeFriend(player, chunkName, String.valueOf(finalFriend.getUniqueId()));
                                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                                player.sendMessage(Statements.getPrefix()
                                                        .append(Component.text("Du hast den Spieler ", NamedTextColor.WHITE))
                                                        .append(Component.text(finalFriend.getName(), NamedTextColor.GREEN))
                                                        .append(Component.text(" von den Freunden dieses Chunks entfernt. Chunk: ", NamedTextColor.WHITE))
                                                        .append(Component.text(chunkName, NamedTextColor.DARK_BLUE)));
                                            } else {
                                                player.sendMessage(Statements.getPrefix()
                                                        .append(Component.text("You removed the player ", NamedTextColor.WHITE))
                                                        .append(Component.text(finalFriend.getName(), NamedTextColor.GREEN))
                                                        .append(Component.text(" from the friends of this chunk. Chunk:  ", NamedTextColor.WHITE))
                                                        .append(Component.text(chunkName, NamedTextColor.DARK_BLUE)));
                                            }
                                        }
                                    }
                                });
                                break;
                            }
                            default:
                                sendUsageAllFriends(player);
                                break;
                        }
                        break;
                    }
                    case "list": {
                        if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Der Chunk hat keinen Besitzer! ", NamedTextColor.RED))
                                        .append(chunkComponent));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("This chunk has no owner!", NamedTextColor.RED))
                                        .append(chunkComponent));
                            }
                            return;
                        }
                        if (!ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString().contains(player.getUniqueId().toString())) {
                            OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Der Chunk gehört nicht dir! Besitzer: ", NamedTextColor.RED))
                                        .append(Component.text(owner.getName(), NamedTextColor.DARK_PURPLE))
                                        .append(Component.text("!", NamedTextColor.RED))
                                        .append(chunkComponent));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("This chunk isn´t yours! Owner: ", NamedTextColor.RED))
                                        .append(Component.text(owner.getName(), NamedTextColor.DARK_PURPLE))
                                        .append(Component.text("!", NamedTextColor.RED))
                                        .append(chunkComponent));
                            }
                            return;
                        }
                        List<String> friendList = ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName());
                        Component friends = formatFriendList(friendList, player);
                        player.sendMessage(friends);
                        break;
                    }
                    case "add": {
                        if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Der Chunk hat keinen Besitzer! ", NamedTextColor.RED))
                                        .append(chunkComponent));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("This chunk has no owner!", NamedTextColor.RED))
                                        .append(chunkComponent));
                            }
                            return;
                        }
                        if (!ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString().contains(player.getUniqueId().toString())) {
                            OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Der Chunk gehört nicht dir! Besitzer: ", NamedTextColor.RED))
                                        .append(Component.text(owner.getName(), NamedTextColor.DARK_PURPLE))
                                        .append(Component.text("!", NamedTextColor.RED))
                                        .append(chunkComponent));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("This chunk isn´t yours! Owner: ", NamedTextColor.RED))
                                        .append(Component.text(owner.getName(), NamedTextColor.DARK_PURPLE))
                                        .append(Component.text("!", NamedTextColor.RED))
                                        .append(chunkComponent));
                            }
                            return;
                        }
                        if (args.length == 2) {
                            sendUsageFriends(player);
                            return;
                        }
                        OfflinePlayer friend = Bukkit.getOfflinePlayer("Test123");
                        Boolean all = false;
                        if (Objects.equals("*", args[2])) {
                            all = true;
                        } else {
                            friend = Bukkit.getOfflinePlayer(args[2]);
                        }
                        if (Objects.requireNonNull(friend.getName()).contains(player.getName())) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du besitzt diesen Chunk!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You own this chunk!", NamedTextColor.RED)));
                            }
                            return;
                        }
                        if(ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains("*")) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Jeder ist vertraut und kann hier bauen!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Everyone is trusted and can build here!", NamedTextColor.RED)));
                            }
                            return;
                        }
                        if(ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains(friend.getUniqueId().toString())) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Dieser Spieler ist bereits ein Freund, der hier bauen kann!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("This player is already a friend, wich can build here!", NamedTextColor.RED)));
                            }
                            return;
                        }
                        if(all) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Du hast alle Spieler zu den Freunden dieses Chunks hinzugefügt. Chunk: ", NamedTextColor.WHITE))
                                        .append(chunkComponentClean));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("You add all players to the friends of this chunk. Chunk: ", NamedTextColor.WHITE))
                                        .append(chunkComponentClean));
                            }
                            ChunkAPI.getApi().addFriend(player, chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), "*");
                            return;
                        }
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Du hast den Spieler ", NamedTextColor.WHITE))
                                    .append(Component.text(friend.getName(), NamedTextColor.GREEN))
                                    .append(Component.text(" zu den Freunden dieses Chunks hinzugefügt. Chunk: ", NamedTextColor.WHITE))
                                    .append(chunkComponentClean));
                        } else {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("You add the player ", NamedTextColor.WHITE))
                                    .append(Component.text(friend.getName(), NamedTextColor.GREEN))
                                    .append(Component.text(" to the friends of this chunk. Chunk: ", NamedTextColor.WHITE))
                                    .append(chunkComponentClean));
                        }
                        ChunkAPI.getApi().addFriend(player, chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), String.valueOf(friend.getUniqueId()));
                        break;
                    }
                    case "remove": {
                        if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Der Chunk hat keinen Besitzer! ", NamedTextColor.RED))
                                        .append(chunkComponent));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("This chunk has no owner!", NamedTextColor.RED))
                                        .append(chunkComponent));
                            }
                            return;
                        }
                        if (!ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString().contains(player.getUniqueId().toString())) {
                            OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Der Chunk gehört nicht dir! Besitzer: ", NamedTextColor.RED))
                                        .append(Component.text(owner.getName(), NamedTextColor.DARK_PURPLE))
                                        .append(Component.text("!", NamedTextColor.RED))
                                        .append(chunkComponent));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("This chunk isn´t yours! Owner: ", NamedTextColor.RED))
                                        .append(Component.text(owner.getName(), NamedTextColor.DARK_PURPLE))
                                        .append(Component.text("!", NamedTextColor.RED))
                                        .append(chunkComponent));
                            }
                            return;
                        }

                        if (args.length == 2) {
                            sendUsageFriends(player);
                            return;
                        }
                        OfflinePlayer friend = Bukkit.getOfflinePlayer("Test123");
                        boolean all = false;
                        if (Objects.equals("*", args[2])) {
                            all = true;
                        } else {
                            friend = Bukkit.getOfflinePlayer(args[2]);
                        }
                        if (Objects.requireNonNull(friend.getName()).contains(player.getName())) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du besitzt diesen Chunk!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You own this chunk!", NamedTextColor.RED)));
                            }
                            return;
                        }
                        if(all) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Du hast alle Spieler von den Freunden dieses Chunks entfernt. Chunk: ", NamedTextColor.WHITE))
                                        .append(chunkComponentClean));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("You removed all players from the friends of this chunk. Chunk: ", NamedTextColor.WHITE))
                                        .append(chunkComponentClean));
                            }
                            ChunkAPI.getApi().removeFriend(player, chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), "*");
                            return;
                        }
                        if(!ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).contains(friend.getUniqueId().toString())) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Dieser Spieler ist gar nicht ein Freund, der hier bauen kann!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix()
                                        .append(Component.text("This player isn´t a friend, wich can build here!", NamedTextColor.RED)));
                            }
                            return;
                        }
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Du hast den Spieler ", NamedTextColor.WHITE))
                                    .append(Component.text(friend.getName(), NamedTextColor.GREEN))
                                    .append(Component.text(" von den Freunden dieses Chunks entfernt. Chunk: ", NamedTextColor.WHITE))
                                    .append(chunkComponentClean));
                        } else {
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("You removed the player ", NamedTextColor.WHITE))
                                    .append(Component.text(friend.getName(), NamedTextColor.GREEN))
                                    .append(Component.text(" from the friends of this chunk. Chunk: ", NamedTextColor.WHITE))
                                    .append(chunkComponentClean));
                        }
                        ChunkAPI.getApi().removeFriend(player, chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), String.valueOf(friend.getUniqueId()));
                        break;
                    }
                    default:
                        sendUsageFriends(player);
                        break;
                }
                break;
            }
            case "free": {
                if (RankAPI.getApi().getRankID(player) > 8) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du kannst nun noch ", NamedTextColor.WHITE))
                                .append(Component.text("∞", NamedTextColor.GREEN))
                                .append(Component.text(" Chunks beantragen. Da du ein Admin/Owner bist!", NamedTextColor.WHITE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You can claim ", NamedTextColor.WHITE))
                                .append(Component.text("∞", NamedTextColor.GREEN))
                                .append(Component.text(" chunks. Because you´re an Admin/Owner!", NamedTextColor.WHITE)));
                    }
                    return;
                }
                if (ChunkAPI.getApi().getFreeChunks(player) == 0) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du kannst nun keine Chunks mehr beanspruchen! Du must zum Notar gehen und dir dort neue kaufen!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix().append(Component.text("You can´t claim more chunks. You have to go to the notary and buy new ones there!", NamedTextColor.RED)));
                    }
                    return;
                }
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du kannst nun noch ", NamedTextColor.WHITE))
                            .append(Component.text((ChunkAPI.getApi().getFreeChunks(player)), NamedTextColor.GREEN))
                            .append(Component.text(" Chunks beantragen. Wenn du mehr brauchst kannst du diese beim Notar kaufen.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You can claim ", NamedTextColor.WHITE))
                            .append(Component.text((ChunkAPI.getApi().getFreeChunks(player)), NamedTextColor.GREEN))
                            .append(Component.text(" chunks. If you need more chunks you can buy them at the notary.", NamedTextColor.WHITE)));
                }
                break;
            }
            case "info": {
                String owner;
                String chunkname;
                Component friends;
                String flags;
                if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {
                    owner = "---";
                    chunkname = chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName();
                    friends = Component.text("---", NamedTextColor.GRAY);
                    flags = "---";
                } else {
                    owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName())).getName();
                    chunkname = chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName();
                    List<String> friendList = ChunkAPI.getApi().getFriends(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName());
                    friends = formatFriendListInfo(friendList);
                    if (ChunkAPI.getApi().getEntitySpawning(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName())) {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            flags = "EntitySpawning: Aktiviert";
                        } else {
                            flags = "EntitySpawning: Activated";
                        }
                    } else {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            flags = "EntitySpawning: Deaktiviert";
                        } else {
                            flags = "EntitySpawning: Deactivated";
                        }
                    }
                }
                if (Objects.equals(friends, "")) {
                    friends = Component.text("---", NamedTextColor.GRAY);
                }

                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Component.text()
                            .append(Statements.getPrefix())
                            .append(Component.text("                   ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                            .append(Component.text("[ ", NamedTextColor.DARK_GRAY))
                            .append(Component.text("Chunk Info", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(" ]", NamedTextColor.DARK_GRAY))
                            .append(Component.text("                   ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                            .build());

                    player.sendMessage(Statements.getPrefix());

                    player.sendMessage(Component.text()
                            .append(Statements.getPrefix())
                            .append(Component.text(" Besitzer: ", NamedTextColor.DARK_PURPLE))
                            .append(Component.text(owner, NamedTextColor.GREEN))
                            .build());

                    player.sendMessage(Statements.getPrefix());

                    player.sendMessage(Component.text()
                            .append(Statements.getPrefix())
                            .append(Component.text(" Chunk: ", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(chunkname, NamedTextColor.BLUE))
                            .build());

                    player.sendMessage(Statements.getPrefix());

                    player.sendMessage(Component.text()
                            .append(Statements.getPrefix())
                            .append(Component.text(" Freunde/Vertraute: ", NamedTextColor.AQUA))
                            .append(friends)
                            .build());

                    player.sendMessage(Statements.getPrefix());

                    player.sendMessage(Component.text()
                            .append(Statements.getPrefix())
                            .append(Component.text(" Flags: ", NamedTextColor.GOLD))
                            .append(Component.text(flags, NamedTextColor.DARK_AQUA))
                            .build());

                    player.sendMessage(Statements.getPrefix());

                    player.sendMessage(Component.text()
                            .append(Statements.getPrefix())
                            .append(Component.text("                   ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                            .append(Component.text("[ ", NamedTextColor.DARK_GRAY))
                            .append(Component.text("Chunk Info", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(" ]", NamedTextColor.DARK_GRAY))
                            .append(Component.text("                   ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                            .build());
                } else {
                    player.sendMessage(Component.text()
                            .append(Statements.getPrefix())
                            .append(Component.text("                   ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                            .append(Component.text("[ ", NamedTextColor.DARK_GRAY))
                            .append(Component.text("Chunk Info", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(" ]", NamedTextColor.DARK_GRAY))
                            .append(Component.text("                   ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                            .build());

                    player.sendMessage(Statements.getPrefix());

                    player.sendMessage(Component.text()
                            .append(Statements.getPrefix())
                            .append(Component.text(" Owner: ", NamedTextColor.DARK_PURPLE))
                            .append(Component.text(owner, NamedTextColor.GREEN))
                            .build());

                    player.sendMessage(Statements.getPrefix());

                    player.sendMessage(Component.text()
                            .append(Statements.getPrefix())
                            .append(Component.text(" Chunk: ", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(chunkname, NamedTextColor.BLUE))
                            .build());

                    player.sendMessage(Statements.getPrefix());

                    player.sendMessage(Component.text()
                            .append(Statements.getPrefix())
                            .append(Component.text(" Friends/Trusted: ", NamedTextColor.AQUA))
                            .append(friends)
                            .build());

                    player.sendMessage(Statements.getPrefix());

                    player.sendMessage(Component.text()
                            .append(Statements.getPrefix())
                            .append(Component.text(" Flags: ", NamedTextColor.GOLD))
                            .append(Component.text(flags, NamedTextColor.DARK_AQUA))
                            .build());

                    player.sendMessage(Statements.getPrefix());

                    player.sendMessage(Component.text()
                            .append(Statements.getPrefix())
                            .append(Component.text("                   ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                            .append(Component.text("[ ", NamedTextColor.DARK_GRAY))
                            .append(Component.text("Chunk Info", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(" ]", NamedTextColor.DARK_GRAY))
                            .append(Component.text("                   ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                            .build());
                }
                break;
            }
            case "flag": {
                if (args.length == 1) {
                    sendUsageFlag(player);
                    return;
                }
                switch (args[1].toLowerCase()) {
                    case "set": {
                        if (args.length == 2) {
                            sendUsageFlag(player);
                            return;
                        }
                        switch (args[2].toLowerCase()) {
                            case "entityspawning": {
                                if (args.length == 3) {
                                    sendUsageFlag(player);
                                    return;
                                }
                                if (Objects.equals(args[3], "true")) {
                                    if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {
                                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                            player.sendMessage(Statements.getPrefix()
                                                    .append(Component.text("Der Chunk hat keinen Besitzer! ", NamedTextColor.RED))
                                                    .append(chunkComponent));
                                        } else {
                                            player.sendMessage(Statements.getPrefix()
                                                    .append(Component.text("This chunk has no owner!", NamedTextColor.RED))
                                                    .append(chunkComponent));
                                        }
                                        return;
                                    }
                                    if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString().contains(player.getUniqueId().toString())) {
                                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                            player.sendMessage(Component.text()
                                                    .append(Statements.getPrefix())
                                                    .append(Component.text("Du hast den Flag: ", NamedTextColor.WHITE))
                                                    .append(Component.text("EntitySpawning ", NamedTextColor.GOLD))
                                                    .append(Component.text("aktiviert! ", NamedTextColor.GREEN))
                                                    .append(Component.text("Chunk: ", NamedTextColor.WHITE))
                                                    .append(chunkComponentClean));
                                        } else {
                                            player.sendMessage(Component.text()
                                                    .append(Statements.getPrefix())
                                                    .append(Component.text("You ", NamedTextColor.WHITE))
                                                    .append(Component.text("activated ", NamedTextColor.GREEN))
                                                    .append(Component.text("the flag: ", NamedTextColor.WHITE))
                                                    .append(Component.text("EntitySpawning! ", NamedTextColor.GOLD))
                                                    .append(Component.text("Chunk: ", NamedTextColor.WHITE))
                                                    .append(chunkComponentClean));
                                        }

                                        ChunkAPI.getApi().setEntitySpawning(player, chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), true);
                                        break;
                                    }
                                    if (ChunkAPI.getApi().doesChunkHaveOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName())) {
                                        OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
                                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                            player.sendMessage(Statements.getPrefix()
                                                    .append(Component.text("Der Chunk gehört nicht dir! Besitzer: ", NamedTextColor.RED))
                                                    .append(Component.text(owner.getName(), NamedTextColor.DARK_PURPLE))
                                                    .append(Component.text("!", NamedTextColor.RED))
                                                    .append(chunkComponent));
                                        } else {
                                            player.sendMessage(Statements.getPrefix()
                                                    .append(Component.text("This chunk isn´t yours! Owner: ", NamedTextColor.RED))
                                                    .append(Component.text(owner.getName(), NamedTextColor.DARK_PURPLE))
                                                    .append(Component.text("!", NamedTextColor.RED))
                                                    .append(chunkComponent));
                                        }
                                        return;
                                    }
                                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                        player.sendMessage(Component.text()
                                                .append(Statements.getPrefix())
                                                .append(Component.text("Du hast den Flag: ", NamedTextColor.WHITE))
                                                .append(Component.text("EntitySpawning ", NamedTextColor.GOLD))
                                                .append(Component.text("aktiviert! ", NamedTextColor.GREEN))
                                                .append(Component.text("Chunk: ", NamedTextColor.WHITE))
                                                .append(chunkComponentClean));
                                    } else {
                                        player.sendMessage(Component.text()
                                                .append(Statements.getPrefix())
                                                .append(Component.text("You ", NamedTextColor.WHITE))
                                                .append(Component.text("activated ", NamedTextColor.GREEN))
                                                .append(Component.text("the flag: ", NamedTextColor.WHITE))
                                                .append(Component.text("EntitySpawning! ", NamedTextColor.GOLD))
                                                .append(Component.text("Chunk: ", NamedTextColor.WHITE))
                                                .append(chunkComponentClean));
                                    }

                                    ChunkAPI.getApi().setEntitySpawning(player, chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), true);
                                    break;
                                } else {
                                    if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()) == null) {
                                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                            player.sendMessage(Statements.getPrefix()
                                                    .append(Component.text("Der Chunk hat keinen Besitzer! ", NamedTextColor.RED))
                                                    .append(chunkComponent));
                                        } else {
                                            player.sendMessage(Statements.getPrefix()
                                                    .append(Component.text("This chunk has no owner!", NamedTextColor.RED))
                                                    .append(chunkComponent));
                                        }
                                        return;
                                    }
                                    if (ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()).toString().contains(player.getUniqueId().toString())) {
                                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                            player.sendMessage(Component.text()
                                                    .append(Statements.getPrefix())
                                                    .append(Component.text("Du hast den Flag: ", NamedTextColor.WHITE))
                                                    .append(Component.text("EntitySpawning ", NamedTextColor.GOLD))
                                                    .append(Component.text("deaktiviert! ", NamedTextColor.RED))
                                                    .append(Component.text("Chunk: ", NamedTextColor.WHITE))
                                                    .append(chunkComponentClean));
                                        } else {
                                            player.sendMessage(Component.text()
                                                    .append(Statements.getPrefix())
                                                    .append(Component.text("You ", NamedTextColor.WHITE))
                                                    .append(Component.text("deactivated ", NamedTextColor.RED))
                                                    .append(Component.text("the flag: ", NamedTextColor.WHITE))
                                                    .append(Component.text("EntitySpawning! ", NamedTextColor.GOLD))
                                                    .append(Component.text("Chunk: ", NamedTextColor.WHITE))
                                                    .append(chunkComponentClean));
                                        }

                                        ChunkAPI.getApi().setEntitySpawning(player, chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), false);
                                        break;
                                    }
                                    if (ChunkAPI.getApi().doesChunkHaveOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName())) {
                                        OfflinePlayer owner = Bukkit.getOfflinePlayer(ChunkAPI.getApi().getChunkOwner(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName()));
                                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                            player.sendMessage(Statements.getPrefix()
                                                    .append(Component.text("Der Chunk gehört nicht dir! Besitzer: ", NamedTextColor.RED))
                                                    .append(Component.text(owner.getName(), NamedTextColor.DARK_PURPLE))
                                                    .append(Component.text("!", NamedTextColor.RED))
                                                    .append(chunkComponent));
                                        } else {
                                            player.sendMessage(Statements.getPrefix()
                                                    .append(Component.text("This chunk isn´t yours! Owner: ", NamedTextColor.RED))
                                                    .append(Component.text(owner.getName(), NamedTextColor.DARK_PURPLE))
                                                    .append(Component.text("!", NamedTextColor.RED))
                                                    .append(chunkComponent));
                                        }
                                        return;
                                    }
                                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                        player.sendMessage(Component.text()
                                                .append(Statements.getPrefix())
                                                .append(Component.text("Du hast den Flag: ", NamedTextColor.WHITE))
                                                .append(Component.text("EntitySpawning ", NamedTextColor.GOLD))
                                                .append(Component.text("deaktiviert! ", NamedTextColor.RED))
                                                .append(Component.text("Chunk: ", NamedTextColor.WHITE))
                                                .append(chunkComponentClean));
                                    } else {
                                        player.sendMessage(Component.text()
                                                .append(Statements.getPrefix())
                                                .append(Component.text("You ", NamedTextColor.WHITE))
                                                .append(Component.text("deactivated ", NamedTextColor.RED))
                                                .append(Component.text("the flag: ", NamedTextColor.WHITE))
                                                .append(Component.text("EntitySpawning! ", NamedTextColor.GOLD))
                                                .append(Component.text("Chunk: ", NamedTextColor.WHITE))
                                                .append(chunkComponentClean));
                                    }

                                    ChunkAPI.getApi().setEntitySpawning(player, chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName(), false);
                                    break;
                                }
                            }
                            default:
                                sendUsageFlag(player);
                                return;
                        }
                        break;
                    }
                    case "info": {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            String entitySpawning = "";
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Verfügbare Flags: ", NamedTextColor.WHITE))
                                    .append(Component.text("EntitySpawning", NamedTextColor.BLUE)));
                            if(ChunkAPI.getApi().getEntitySpawning(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName())) {
                                entitySpawning = "EntitySpawning";
                            }
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Aktivierte Flags: ", NamedTextColor.WHITE))
                                    .append(Component.text(entitySpawning, NamedTextColor.GREEN)));
                        } else {
                            String entitySpawning = "";
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Available Flags:", NamedTextColor.WHITE))
                                    .append(Component.text("EntitySpawning", NamedTextColor.BLUE)));
                            if(ChunkAPI.getApi().getEntitySpawning(chunk.getX() + "," + chunk.getZ() + "," + loc.getWorld().getName())) {
                                entitySpawning = "EntitySpawning";
                            }
                            player.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Activated Flags: ", NamedTextColor.WHITE))
                                    .append(Component.text(entitySpawning, NamedTextColor.GREEN)));
                        }
                        break;
                    }
                    default:
                        sendUsageFlag(player);
                        return;
                }
                break;
            }
            default:
                sendUsage(player);
        }

    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        Player player = (Player) commandSourceStack.getSender();

        if (args.length == 0 || args.length == 1) {
            List<String> chunkArgs = new ArrayList<>();
            chunkArgs.add("claim");
            chunkArgs.add("sell");
            chunkArgs.add("friend");
            chunkArgs.add("list");
            chunkArgs.add("info");
            chunkArgs.add("free");
            chunkArgs.add("flag");
            return chunkArgs;
        }
        if (args.length == 2  && args[0].equals("friend")) {
            List<String> chunkArgs = new ArrayList<>();
            chunkArgs.add("add");
            chunkArgs.add("remove");
            chunkArgs.add("list");
            chunkArgs.add("all");
            return chunkArgs;
        }
        if (args.length == 3 && args[0].equals("friend") && args[1].equals("all")) {
            List<String> chunkArgs = new ArrayList<>();
            chunkArgs.add("add");
            chunkArgs.add("remove");
            return chunkArgs;
        }
        return Collections.emptyList();
    }

    private void sendUsage(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/chunk claim, sell, friend (add <Spieler>, remove <Spieler>, list), list, info, free, flag", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Use", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/chunk claim, sell, friend (add <player>, remove <player>, list), list, info, free, flag", NamedTextColor.BLUE)));
        }
    }

    private void sendUsageFriends(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/chunk friend add <Spieler>, remove <Spieler>, list", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/chunk friend add <player>, remove <player>, list", NamedTextColor.BLUE)));
        }
    }

    private void sendUsageAllFriends(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/chunk friend all add <Spieler>, remove <Spieler>", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/chunk friend all add <player>, remove <player>", NamedTextColor.BLUE)));
        }
    }

    private void sendUsageFlag(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/chunk flag set <Flags(EntitySpawning)> <true/false>, info", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/chunk flag set <flags(EntitySpawning)> <true/false>, info", NamedTextColor.BLUE)));
        }
    }

    public Component formatFriendList(List<String> friendList, Player languagehelp) {
        ComponentBuilder componentBuilder = Component.text("").toBuilder();
        if (LanguageAPI.getApi().getLanguage(languagehelp) == 2) {
            componentBuilder.append(Statements.getPrefix().append(Component.text("Das sind deine Freunde, die hier bauen können: ", NamedTextColor.WHITE)));
        } else {
            componentBuilder.append(Statements.getPrefix().append(Component.text("That are your friends, wich can build here: ", NamedTextColor.WHITE)));
        }
        if(friendList.contains("*")) {
            componentBuilder.append(Component.text("*", NamedTextColor.LIGHT_PURPLE));
            return componentBuilder.build();
        }

        for (int i = 0; i < friendList.size(); i++) {
            OfflinePlayer friend = getOfflinePlayerByUUID(friendList.get(i));
            componentBuilder.append(Component.text(Objects.requireNonNull(friend.getName()), NamedTextColor.LIGHT_PURPLE));

            if (i < friendList.size() - 1) {
                componentBuilder.append(Component.text(", ", NamedTextColor.GREEN));
            }
        }

        return componentBuilder.build();
    }

    public Component formatFriendListInfo(List<String> friendList) {
        ComponentBuilder<TextComponent, TextComponent.Builder> componentBuilder = Component.text("").toBuilder();
        if (friendList.contains("*")) {
            componentBuilder.append(Component.text("*", NamedTextColor.LIGHT_PURPLE));
            return componentBuilder.build();
        }
        for (int i = 0; i < friendList.size(); i++) {
            OfflinePlayer friend = getOfflinePlayerByUUID(friendList.get(i));
            componentBuilder.append(Component.text(friend.getName(), NamedTextColor.LIGHT_PURPLE));

            if (i < friendList.size() - 1) {
                componentBuilder.append(Component.text(", ", NamedTextColor.GRAY));
            }
        }

        return componentBuilder.build();
    }

    private OfflinePlayer getOfflinePlayerByUUID(String uuid) {
        Player onlinePlayer = Bukkit.getPlayer(UUID.fromString(uuid));
        if (onlinePlayer != null) {
            return onlinePlayer;
        }

        return Bukkit.getOfflinePlayer(UUID.fromString(uuid));
    }
}
