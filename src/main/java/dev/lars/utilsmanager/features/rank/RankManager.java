package dev.lars.utilsmanager.features.rank;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.rankAPI.RankAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.RankStatements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.time.Instant;

public class RankManager {

    public void checkRanks() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Instant now = Instant.now();
                Instant expiresAt = RankAPI.getApi().getExpiresAt(player);
                if (expiresAt == null) continue;
                if (expiresAt.compareTo(now) <= 0) {
                    int rankId = RankAPI.getApi().getRankId(player);
                    RankAPI.getApi().setRank(player, rankId-1, 182);
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.kick(Component.text("Du bist nun ein ", NamedTextColor.RED)
                                .append(RankStatements.getCleanRank(player))
                                .append(Component.text("mehr!", NamedTextColor.RED)));
                    } else {
                        player.kick(Component.text("You are now a ", NamedTextColor.RED)
                                .append(RankStatements.getCleanRank(player))
                                .append(Component.text("!", NamedTextColor.RED)));
                    }
                }
            }
        }, 0, 60 * 20);
    }

    public void setPermissions(Player player) {
        PermissionAttachment attachment = player.addAttachment(UtilsManager.getInstance());
        Integer rankId = RankAPI.getApi().getRankId(player);
        if (rankId == null) {
            rankId = 1;
        }
        if (player.isOp()) {
            rankId = 10;
        }
        switch (rankId) {
            case 10: {
                attachment.setPermission("utilsmanager.addcoins", true);
                attachment.setPermission("utilsmanager.setcoins", true);
                attachment.setPermission("utilsmanager.backpack", true);
                attachment.setPermission("utilsmanager.backpackconfig", true);
                attachment.setPermission("utilsmanager.buy", true);
                attachment.setPermission("utilsmanager.sell", true);
                attachment.setPermission("utilsmanager.shop", true);
                attachment.setPermission("utilsmanager.date", true);
                attachment.setPermission("utilsmanager.firstcoins", true);
                attachment.setPermission("utilsmanager.language", true);
                attachment.setPermission("utilsmanager.pay", true);
                attachment.setPermission("utilsmanager.price", true);
                attachment.setPermission("utilsmanager.realtime", true);
                attachment.setPermission("utilsmanager.feature.spawn", true);
                attachment.setPermission("utilsmanager.timer", true);
                attachment.setPermission("utilsmanager.timer.public", true);
                attachment.setPermission("utilsmanager.togglebed", true);
                attachment.setPermission("utilsmanager.togglescoreboard", true);
                attachment.setPermission("utilsmanager.wallet", true);
                attachment.setPermission("utilsmanager.rank", true);
                attachment.setPermission("spark", true);
                attachment.setPermission("worldedit.*", true);
                attachment.setPermission("minecraft.login.bypass-player-limit", true);
                attachment.setPermission("minecraft.login.bypass-whitelist", true);
                attachment.setPermission("bukkit.command.reload",true);
                attachment.setPermission("bukkit.command.timings",true);
                attachment.setPermission("troll.*",true);
                attachment.setPermission("sv",true);
                attachment.setPermission("sv.*",true);
                attachment.setPermission("sv.use",true);
                attachment.setPermission("sv.see",true);
                attachment.setPermission("sv.silentchest",true);
                attachment.setPermission("minecraft.admin.command_feedback",true);
                attachment.setPermission("minecraft.nbt.copy",true);
                attachment.setPermission("minecraft.nbt.place",true);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("minecraft.debugstick", true);
                attachment.setPermission("minecraft.debugstick.always", true);
                attachment.setPermission("utilsmanager.nick", true);
                attachment.setPermission("utilsmanager.spawnbuild", true);
                attachment.setPermission("utilsmanager.freeze", true);
                attachment.setPermission("utilsmanager.inv", true);
                attachment.setPermission("utilsmanager.prefix", true);
                attachment.setPermission("utilsmanager.ban", true);
                attachment.setPermission("utilsmanager.home", true);
                attachment.setPermission("utilsmanager.heal", true);
                attachment.setPermission("utilsmanager.announce", true);
                attachment.setPermission("utilsmanager.clearchat", true);
                attachment.setPermission("utilsmanager.entity", true);
                attachment.setPermission("utilsmanager.restart", true);
                attachment.setPermission("utilsmanager.search", true);
                attachment.setPermission("utilsmanager.sudo", true);
                attachment.setPermission("utilsmanager.vanish", true);
                attachment.setPermission("utilsmanager.feature.freecam", true);
                attachment.setPermission("utilsmanager.maintenance", true);
                attachment.setPermission("utilsmanager.feature.spawn.setspawn", true);
            }
            case 9: {
                attachment.setPermission("utilsmanager.addcoins", true);
                attachment.setPermission("utilsmanager.setcoins", true);
                attachment.setPermission("utilsmanager.backpack", true);
                attachment.setPermission("utilsmanager.backpackconfig", true);
                attachment.setPermission("utilsmanager.buy", true);
                attachment.setPermission("utilsmanager.sell", true);
                attachment.setPermission("utilsmanager.shop", true);
                attachment.setPermission("utilsmanager.date", true);
                attachment.setPermission("utilsmanager.firstcoins", true);
                attachment.setPermission("utilsmanager.language", true);
                attachment.setPermission("utilsmanager.pay", true);
                attachment.setPermission("utilsmanager.price", true);
                attachment.setPermission("utilsmanager.realtime", true);
                attachment.setPermission("utilsmanager.feature.spawn", true);
                attachment.setPermission("utilsmanager.timer", true);
                attachment.setPermission("utilsmanager.togglebed", true);
                attachment.setPermission("utilsmanager.togglescoreboard", true);
                attachment.setPermission("utilsmanager.wallet", true);
                attachment.setPermission("utilsmanager.rank", true);
                attachment.setPermission("spark", true);
                attachment.setPermission("worldedit.*", true);
                attachment.setPermission("minecraft.login.bypass-player-limit", true);
                attachment.setPermission("minecraft.login.bypass-whitelist", true);
                attachment.setPermission("bukkit.command.reload",true);
                attachment.setPermission("bukkit.command.timings",true);
                attachment.setPermission("troll.*",true);
                attachment.setPermission("sv.use",true);
                attachment.setPermission("sv.silentchest",true);
                attachment.setPermission("minecraft.admin.command_feedback",true);
                attachment.setPermission("minecraft.nbt.copy",true);
                attachment.setPermission("minecraft.nbt.place",true);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("minecraft.debugstick", true);
                attachment.setPermission("minecraft.debugstick.always", true);
                attachment.setPermission("utilsmanager.nick", true);
                attachment.setPermission("utilsmanager.spawnbuild", true);
                attachment.setPermission("utilsmanager.freeze", true);
                attachment.setPermission("utilsmanager.inv", true);
                attachment.setPermission("utilsmanager.prefix", true);
                attachment.setPermission("utilsmanager.ban", true);
                attachment.setPermission("utilsmanager.home", true);
                attachment.setPermission("utilsmanager.heal", true);
                attachment.setPermission("utilsmanager.announce", true);
                attachment.setPermission("utilsmanager.clearchat", true);
                attachment.setPermission("utilsmanager.entity", true);
                attachment.setPermission("utilsmanager.restart", true);
                attachment.setPermission("utilsmanager.search", true);
                attachment.setPermission("utilsmanager.sudo", true);
                attachment.setPermission("utilsmanager.vanish", true);
                attachment.setPermission("utilsmanager.feature.freecam", true);
                attachment.setPermission("utilsmanager.maintenance", true);
                attachment.setPermission("utilsmanager.feature.spawn.setspawn", true);
            }
            case 8: {
                attachment.setPermission("utilsmanager.addcoins", true);
                attachment.setPermission("utilsmanager.setcoins", true);
                attachment.setPermission("utilsmanager.backpack", true);
                attachment.setPermission("utilsmanager.backpackconfig", true);
                attachment.setPermission("utilsmanager.buy", true);
                attachment.setPermission("utilsmanager.sell", true);
                attachment.setPermission("utilsmanager.shop", true);
                attachment.setPermission("utilsmanager.date", true);
                attachment.setPermission("utilsmanager.firstcoins", true);
                attachment.setPermission("utilsmanager.language", true);
                attachment.setPermission("utilsmanager.pay", true);
                attachment.setPermission("utilsmanager.price", true);
                attachment.setPermission("utilsmanager.realtime", true);
                attachment.setPermission("utilsmanager.feature.spawn", true);
                attachment.setPermission("utilsmanager.timer", true);
                attachment.setPermission("utilsmanager.togglebed", true);
                attachment.setPermission("utilsmanager.togglescoreboard", true);
                attachment.setPermission("utilsmanager.wallet", true);
                attachment.setPermission("spark", true);
                attachment.setPermission("worldedit.*", true);
                attachment.setPermission("bukkit.command.reload",true);
                attachment.setPermission("bukkit.command.timings",true);
                attachment.setPermission("troll.*",true);
                attachment.setPermission("minecraft.nbt.copy",true);
                attachment.setPermission("minecraft.nbt.place",true);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("minecraft", true);
                attachment.setPermission("minecraft.*", true);
                attachment.setPermission("minecraft.command.*", true);
                attachment.setPermission("utilsmanager.nick", true);
                attachment.setPermission("utilsmanager.prefix", true);
                attachment.setPermission("utilsmanager.home", true);
                attachment.setPermission("utilsmanager.announce", true);
                attachment.setPermission("utilsmanager.clearchat", true);
                attachment.setPermission("utilsmanager.entity", true);
                attachment.setPermission("utilsmanager.restart", true);
                attachment.setPermission("utilsmanager.search", true);
                attachment.setPermission("utilsmanager.vanish", true);
                attachment.setPermission("utilsmanager.feature.freecam", true);
                attachment.setPermission("utilsmanager.maintenance", true);
            }
            case 7: {
                attachment.setPermission("utilsmanager.addcoins", true);
                attachment.setPermission("utilsmanager.setcoins", true);
                attachment.setPermission("utilsmanager.backpack", true);
                attachment.setPermission("utilsmanager.backpackconfig", true);
                attachment.setPermission("utilsmanager.buy", true);
                attachment.setPermission("utilsmanager.sell", true);
                attachment.setPermission("utilsmanager.shop", true);
                attachment.setPermission("utilsmanager.date", true);
                attachment.setPermission("utilsmanager.firstcoins", true);
                attachment.setPermission("utilsmanager.language", true);
                attachment.setPermission("utilsmanager.pay", true);
                attachment.setPermission("utilsmanager.price", true);
                attachment.setPermission("utilsmanager.realtime", true);
                attachment.setPermission("utilsmanager.feature.spawn", true);
                attachment.setPermission("utilsmanager.timer", true);
                attachment.setPermission("utilsmanager.togglebed", true);
                attachment.setPermission("utilsmanager.togglescoreboard", true);
                attachment.setPermission("utilsmanager.wallet", true);
                attachment.setPermission("spark", true);
                attachment.setPermission("worldedit.navigation.jumpto.command", true);
                attachment.setPermission("bukkit.command.reload",true);
                attachment.setPermission("bukkit.command.timings",true);
                attachment.setPermission("minecraft.nbt.copy",true);
                attachment.setPermission("minecraft.nbt.place",true);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("utilsmanager.prefix", true);
                attachment.setPermission("utilsmanager.home", true);
                attachment.setPermission("utilsmanager.clearchat", true);
                attachment.setPermission("utilsmanager.entity", true);
                attachment.setPermission("utilsmanager.search", true);
                attachment.setPermission("utilsmanager.feature.freecam", true);
                attachment.setPermission("utilsmanager.maintenance", true);
            }
            case 6: {
                attachment.setPermission("utilsmanager.backpack", true);
                attachment.setPermission("utilsmanager.buy", true);
                attachment.setPermission("utilsmanager.sell", true);
                attachment.setPermission("utilsmanager.shop", true);
                attachment.setPermission("utilsmanager.date", true);
                attachment.setPermission("utilsmanager.firstcoins", true);
                attachment.setPermission("utilsmanager.language", true);
                attachment.setPermission("utilsmanager.pay", true);
                attachment.setPermission("utilsmanager.price", true);
                attachment.setPermission("utilsmanager.feature.spawn", true);
                attachment.setPermission("utilsmanager.timer", true);
                attachment.setPermission("utilsmanager.togglebed", true);
                attachment.setPermission("utilsmanager.togglescoreboard", true);
                attachment.setPermission("utilsmanager.wallet", true);
                attachment.setPermission("worldedit.*", true);
                attachment.setPermission("bukkit.command.version",false);
                attachment.setPermission("bukkit.command.plugins",false);
                attachment.setPermission("bukkit.command.help",false);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("utilsmanager.spawnbuild", true);
                attachment.setPermission("utilsmanager.prefix", true);
                attachment.setPermission("utilsmanager.home", true);
                attachment.setPermission("utilsmanager.entity", true);
                attachment.setPermission("utilsmanager.search", true);
                attachment.setPermission("utilsmanager.feature.freecam", true);
            }
            case 5: {
                attachment.setPermission("utilsmanager.backpack", true);
                attachment.setPermission("utilsmanager.buy", true);
                attachment.setPermission("utilsmanager.sell", true);
                attachment.setPermission("utilsmanager.shop", true);
                attachment.setPermission("utilsmanager.date", true);
                attachment.setPermission("utilsmanager.firstcoins", true);
                attachment.setPermission("utilsmanager.language", true);
                attachment.setPermission("utilsmanager.pay", true);
                attachment.setPermission("utilsmanager.price", true);
                attachment.setPermission("utilsmanager.feature.spawn", true);
                attachment.setPermission("utilsmanager.timer", true);
                attachment.setPermission("utilsmanager.togglebed", true);
                attachment.setPermission("utilsmanager.togglescoreboard", true);
                attachment.setPermission("utilsmanager.wallet", true);
                attachment.setPermission("spark.tps", true);
                attachment.setPermission("bukkit.command.version",false);
                attachment.setPermission("bukkit.command.plugins",false);
                attachment.setPermission("bukkit.command.help",false);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("utilsmanager.prefix", true);
                attachment.setPermission("utilsmanager.home", true);
                attachment.setPermission("utilsmanager.search", true);
                attachment.setPermission("utilsmanager.feature.freecam", true);
                attachment.setPermission("minecraft.debugstick", true);
                attachment.setPermission("minecraft.debugstick.always", true);
            }
            case 4: {
                attachment.setPermission("utilsmanager.backpack", true);
                attachment.setPermission("utilsmanager.buy", true);
                attachment.setPermission("utilsmanager.sell", true);
                attachment.setPermission("utilsmanager.shop", true);
                attachment.setPermission("utilsmanager.date", true);
                attachment.setPermission("utilsmanager.firstcoins", true);
                attachment.setPermission("utilsmanager.language", true);
                attachment.setPermission("utilsmanager.pay", true);
                attachment.setPermission("utilsmanager.price", true);
                attachment.setPermission("utilsmanager.togglebed", true);
                attachment.setPermission("utilsmanager.togglescoreboard", true);
                attachment.setPermission("utilsmanager.wallet", true);
                attachment.setPermission("bukkit.command.version",false);
                attachment.setPermission("bukkit.command.plugins",false);
                attachment.setPermission("bukkit.command.help",false);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("utilsmanager.home", true);
                attachment.setPermission("utilsmanager.search", true);
            }
            case 3: {
                attachment.setPermission("utilsmanager.buy", true);
                attachment.setPermission("utilsmanager.sell", true);
                attachment.setPermission("utilsmanager.shop", true);
                attachment.setPermission("utilsmanager.date", true);
                attachment.setPermission("utilsmanager.firstcoins", true);
                attachment.setPermission("utilsmanager.language", true);
                attachment.setPermission("utilsmanager.pay", true);
                attachment.setPermission("utilsmanager.price", true);
                attachment.setPermission("utilsmanager.togglescoreboard", true);
                attachment.setPermission("utilsmanager.wallet", true);
                attachment.setPermission("bukkit.command.version",false);
                attachment.setPermission("bukkit.command.plugins",false);
                attachment.setPermission("bukkit.command.help",false);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("utilsmanager.search", true);
            }
            case 2: {
                attachment.setPermission("utilsmanager.buy", true);
                attachment.setPermission("utilsmanager.sell", true);
                attachment.setPermission("utilsmanager.price", true);
                attachment.setPermission("utilsmanager.date", true);
                attachment.setPermission("utilsmanager.firstcoins", true);
                attachment.setPermission("utilsmanager.language", true);
                attachment.setPermission("utilsmanager.wallet", true);
                attachment.setPermission("bukkit.command.version",false);
                attachment.setPermission("bukkit.command.plugins",false);
                attachment.setPermission("bukkit.command.help",false);
                attachment.setPermission("minecraft.autocraft",true);
            }
            default: {
                attachment.setPermission("utilsmanager.firstcoins", true);
                attachment.setPermission("utilsmanager.language", true);
                attachment.setPermission("bukkit.command.version",false);
                attachment.setPermission("bukkit.command.plugins",false);
                attachment.setPermission("bukkit.command.help",false);
            }
        }
    }
}
