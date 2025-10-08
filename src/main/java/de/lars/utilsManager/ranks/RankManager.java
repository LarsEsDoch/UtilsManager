package de.lars.utilsManager.ranks;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.RankStatements;
import de.lars.utilsManager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Calendar;
import java.util.Date;

public class RankManager {

    public void setRange(Player player) {
        run(player);
    }

    public void setPerm() {
        run1();
    }

    private void run1() {
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (RankAPI.getApi().getRankID(target) == 10 || target.isOp()) {
                PermissionAttachment attachment = target.addAttachment(Main.getInstance());
                attachment.setPermission("plugin.addcoins", true);
                attachment.setPermission("plugin.setcoins", true);
                attachment.setPermission("plugin.backpack", true);
                attachment.setPermission("plugin.backpackconfig", true);
                attachment.setPermission("plugin.buy", true);
                attachment.setPermission("plugin.sell", true);
                attachment.setPermission("plugin.shop", true);
                attachment.setPermission("plugin.date", true);
                attachment.setPermission("plugin.firstcoins", true);
                attachment.setPermission("plugin.language", true);
                attachment.setPermission("plugin.pay", true);
                attachment.setPermission("plugin.price", true);
                attachment.setPermission("plugin.realtime", true);
                attachment.setPermission("plugin.setspawn", true);
                attachment.setPermission("plugin.spawn", true);
                attachment.setPermission("plugin.timer", true);
                attachment.setPermission("plugin.timer.public", true);
                attachment.setPermission("plugin.togglebed", true);
                attachment.setPermission("plugin.togglescoreboard", true);
                attachment.setPermission("plugin.wallet", true);
                attachment.setPermission("plugin.rank", true);
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
                attachment.setPermission("plugin.nick", true);
                attachment.setPermission("plugin.spawnbuild", true);
                attachment.setPermission("plugin.freeze", true);
                attachment.setPermission("plugin.inv", true);
                attachment.setPermission("plugin.prefix", true);
                attachment.setPermission("plugin.ban", true);
                attachment.setPermission("plugin.home", true);
                attachment.setPermission("plugin.heal", true);
                attachment.setPermission("plugin.announce", true);
                attachment.setPermission("plugin.clearchat", true);
                attachment.setPermission("plugin.entity", true);
                attachment.setPermission("plugin.restart", true);
                attachment.setPermission("plugin.search", true);
                attachment.setPermission("plugin.sudo", true);
                attachment.setPermission("plugin.vanish", true);
                attachment.setPermission("plugin.freecam", true);
                attachment.setPermission("plugin.maintenance", true);
            }
            if (RankAPI.getApi().getRankID(target) == 9) {
                PermissionAttachment attachment = target.addAttachment(Main.getInstance());
                attachment.setPermission("plugin.addcoins", true);
                attachment.setPermission("plugin.setcoins", true);
                attachment.setPermission("plugin.backpack", true);
                attachment.setPermission("plugin.backpackconfig", true);
                attachment.setPermission("plugin.buy", true);
                attachment.setPermission("plugin.sell", true);
                attachment.setPermission("plugin.shop", true);
                attachment.setPermission("plugin.date", true);
                attachment.setPermission("plugin.firstcoins", true);
                attachment.setPermission("plugin.language", true);
                attachment.setPermission("plugin.pay", true);
                attachment.setPermission("plugin.price", true);
                attachment.setPermission("plugin.realtime", true);
                attachment.setPermission("plugin.setspawn", true);
                attachment.setPermission("plugin.spawn", true);
                attachment.setPermission("plugin.timer", true);
                attachment.setPermission("plugin.togglebed", true);
                attachment.setPermission("plugin.togglescoreboard", true);
                attachment.setPermission("plugin.wallet", true);
                attachment.setPermission("plugin.rank", true);
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
                attachment.setPermission("plugin.nick", true);
                attachment.setPermission("plugin.spawnbuild", true);
                attachment.setPermission("plugin.freeze", true);
                attachment.setPermission("plugin.inv", true);
                attachment.setPermission("plugin.prefix", true);
                attachment.setPermission("plugin.ban", true);
                attachment.setPermission("plugin.home", true);
                attachment.setPermission("plugin.heal", true);
                attachment.setPermission("plugin.announce", true);
                attachment.setPermission("plugin.clearchat", true);
                attachment.setPermission("plugin.entity", true);
                attachment.setPermission("plugin.restart", true);
                attachment.setPermission("plugin.search", true);
                attachment.setPermission("plugin.sudo", true);
                attachment.setPermission("plugin.vanish", true);
                attachment.setPermission("plugin.freecam", true);
                attachment.setPermission("plugin.maintenance", true);
            }
            if (RankAPI.getApi().getRankID(target) == 8) {
                PermissionAttachment attachment = target.addAttachment(Main.getInstance());
                attachment.setPermission("plugin.addcoins", true);
                attachment.setPermission("plugin.setcoins", true);
                attachment.setPermission("plugin.backpack", true);
                attachment.setPermission("plugin.backpackconfig", true);
                attachment.setPermission("plugin.buy", true);
                attachment.setPermission("plugin.sell", true);
                attachment.setPermission("plugin.shop", true);
                attachment.setPermission("plugin.date", true);
                attachment.setPermission("plugin.firstcoins", true);
                attachment.setPermission("plugin.language", true);
                attachment.setPermission("plugin.pay", true);
                attachment.setPermission("plugin.price", true);
                attachment.setPermission("plugin.realtime", true);
                attachment.setPermission("plugin.setspawn", true);
                attachment.setPermission("plugin.spawn", true);
                attachment.setPermission("plugin.timer", true);
                attachment.setPermission("plugin.togglebed", true);
                attachment.setPermission("plugin.togglescoreboard", true);
                attachment.setPermission("plugin.wallet", true);
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
                attachment.setPermission("plugin.nick", true);
                attachment.setPermission("plugin.prefix", true);
                attachment.setPermission("plugin.home", true);
                attachment.setPermission("plugin.announce", true);
                attachment.setPermission("plugin.clearchat", true);
                attachment.setPermission("plugin.entity", true);
                attachment.setPermission("plugin.restart", true);
                attachment.setPermission("plugin.search", true);
                attachment.setPermission("plugin.vanish", true);
                attachment.setPermission("plugin.freecam", true);
                attachment.setPermission("plugin.maintenance", true);
            }
            if (RankAPI.getApi().getRankID(target) == 7) {
                PermissionAttachment attachment = target.addAttachment(Main.getInstance());
                attachment.setPermission("plugin.addcoins", true);
                attachment.setPermission("plugin.setcoins", true);
                attachment.setPermission("plugin.backpack", true);
                attachment.setPermission("plugin.backpackconfig", true);
                attachment.setPermission("plugin.buy", true);
                attachment.setPermission("plugin.sell", true);
                attachment.setPermission("plugin.shop", true);
                attachment.setPermission("plugin.date", true);
                attachment.setPermission("plugin.firstcoins", true);
                attachment.setPermission("plugin.language", true);
                attachment.setPermission("plugin.pay", true);
                attachment.setPermission("plugin.price", true);
                attachment.setPermission("plugin.realtime", true);
                attachment.setPermission("plugin.setspawn", true);
                attachment.setPermission("plugin.spawn", true);
                attachment.setPermission("plugin.timer", true);
                attachment.setPermission("plugin.togglebed", true);
                attachment.setPermission("plugin.togglescoreboard", true);
                attachment.setPermission("plugin.wallet", true);
                attachment.setPermission("spark", true);
                attachment.setPermission("worldedit.navigation.jumpto.command", true);
                attachment.setPermission("bukkit.command.reload",true);
                attachment.setPermission("bukkit.command.timings",true);
                attachment.setPermission("minecraft.nbt.copy",true);
                attachment.setPermission("minecraft.nbt.place",true);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("plugin.prefix", true);
                attachment.setPermission("plugin.home", true);
                attachment.setPermission("plugin.clearchat", true);
                attachment.setPermission("plugin.entity", true);
                attachment.setPermission("plugin.search", true);
                attachment.setPermission("plugin.freecam", true);
                attachment.setPermission("plugin.maintenance", true);
            }
            if (RankAPI.getApi().getRankID(target) == 6) {
                PermissionAttachment attachment = target.addAttachment(Main.getInstance());
                attachment.setPermission("plugin.backpack", true);
                attachment.setPermission("plugin.buy", true);
                attachment.setPermission("plugin.sell", true);
                attachment.setPermission("plugin.shop", true);
                attachment.setPermission("plugin.date", true);
                attachment.setPermission("plugin.firstcoins", true);
                attachment.setPermission("plugin.language", true);
                attachment.setPermission("plugin.pay", true);
                attachment.setPermission("plugin.price", true);
                attachment.setPermission("plugin.setspawn", true);
                attachment.setPermission("plugin.spawn", true);
                attachment.setPermission("plugin.timer", true);
                attachment.setPermission("plugin.togglebed", true);
                attachment.setPermission("plugin.togglescoreboard", true);
                attachment.setPermission("plugin.wallet", true);
                attachment.setPermission("worldedit.*", true);
                attachment.setPermission("bukkit.command.version",false);
                attachment.setPermission("bukkit.command.plugins",false);
                attachment.setPermission("bukkit.command.help",false);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("plugin.spawnbuild", true);
                attachment.setPermission("plugin.prefix", true);
                attachment.setPermission("plugin.home", true);
                attachment.setPermission("plugin.entity", true);
                attachment.setPermission("plugin.search", true);
                attachment.setPermission("plugin.freecam", true);
            }
            if (RankAPI.getApi().getRankID(target) == 5) {
                PermissionAttachment attachment = target.addAttachment(Main.getInstance());
                attachment.setPermission("plugin.backpack", true);
                attachment.setPermission("plugin.buy", true);
                attachment.setPermission("plugin.sell", true);
                attachment.setPermission("plugin.shop", true);
                attachment.setPermission("plugin.date", true);
                attachment.setPermission("plugin.firstcoins", true);
                attachment.setPermission("plugin.language", true);
                attachment.setPermission("plugin.pay", true);
                attachment.setPermission("plugin.price", true);
                attachment.setPermission("plugin.spawn", true);
                attachment.setPermission("plugin.timer", true);
                attachment.setPermission("plugin.togglebed", true);
                attachment.setPermission("plugin.togglescoreboard", true);
                attachment.setPermission("plugin.wallet", true);
                attachment.setPermission("spark.tps", true);
                attachment.setPermission("bukkit.command.version",false);
                attachment.setPermission("bukkit.command.plugins",false);
                attachment.setPermission("bukkit.command.help",false);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("plugin.prefix", true);
                attachment.setPermission("plugin.home", true);
                attachment.setPermission("plugin.search", true);
                attachment.setPermission("plugin.freecam", true);
            }
            if (RankAPI.getApi().getRankID(target) == 4) {
                PermissionAttachment attachment = target.addAttachment(Main.getInstance());
                attachment.setPermission("plugin.backpack", true);
                attachment.setPermission("plugin.buy", true);
                attachment.setPermission("plugin.sell", true);
                attachment.setPermission("plugin.shop", true);
                attachment.setPermission("plugin.date", true);
                attachment.setPermission("plugin.firstcoins", true);
                attachment.setPermission("plugin.language", true);
                attachment.setPermission("plugin.pay", true);
                attachment.setPermission("plugin.price", true);
                attachment.setPermission("plugin.togglebed", true);
                attachment.setPermission("plugin.togglescoreboard", true);
                attachment.setPermission("plugin.wallet", true);
                attachment.setPermission("bukkit.command.version",false);
                attachment.setPermission("bukkit.command.plugins",false);
                attachment.setPermission("bukkit.command.help",false);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("plugin.home", true);
                attachment.setPermission("plugin.search", true);

            }
            if (RankAPI.getApi().getRankID(target) == 3) {
                PermissionAttachment attachment = target.addAttachment(Main.getInstance());
                attachment.setPermission("plugin.buy", true);
                attachment.setPermission("plugin.sell", true);
                attachment.setPermission("plugin.shop", true);
                attachment.setPermission("plugin.date", true);
                attachment.setPermission("plugin.firstcoins", true);
                attachment.setPermission("plugin.language", true);
                attachment.setPermission("plugin.pay", true);
                attachment.setPermission("plugin.price", true);
                attachment.setPermission("plugin.togglescoreboard", true);
                attachment.setPermission("plugin.wallet", true);
                attachment.setPermission("bukkit.command.version",false);
                attachment.setPermission("bukkit.command.plugins",false);
                attachment.setPermission("bukkit.command.help",false);
                attachment.setPermission("minecraft.autocraft",true);
                attachment.setPermission("plugin.search", true);
            }
            if (RankAPI.getApi().getRankID(target) == 2) {
                PermissionAttachment attachment = target.addAttachment(Main.getInstance());
                attachment.setPermission("plugin.buy", true);
                attachment.setPermission("plugin.sell", true);
                attachment.setPermission("plugin.price", true);
                attachment.setPermission("plugin.date", true);
                attachment.setPermission("plugin.firstcoins", true);
                attachment.setPermission("plugin.language", true);
                attachment.setPermission("plugin.wallet", true);
                attachment.setPermission("bukkit.command.version",false);
                attachment.setPermission("bukkit.command.plugins",false);
                attachment.setPermission("bukkit.command.help",false);
                attachment.setPermission("minecraft.autocraft",true);

            }
            if (RankAPI.getApi().getRankID(target) == 1) {
                PermissionAttachment attachment = target.addAttachment(Main.getInstance());
                attachment.setPermission("plugin.firstcoins", true);
                attachment.setPermission("plugin.language", true);
                attachment.setPermission("bukkit.command.version",false);
                attachment.setPermission("bukkit.command.plugins",false);
                attachment.setPermission("bukkit.command.help",false);

            }
        }
    }
    private void run(Player player) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), bukkitTask -> {
            if (RankAPI.getApi().getRankID(player) == 5 || RankAPI.getApi().getRankID(player) == 4 || RankAPI.getApi().getRankID(player) == 3 || RankAPI.getApi().getRankID(player) == 2) {
                Date now = new Date();
                now.setTime(0);

                Date time = RankAPI.getApi().getRankDate(player).getTime();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                calendar.add(Calendar.DAY_OF_MONTH, RankAPI.getApi().getRankTime(player));
                time = calendar.getTime();
                if (time.compareTo(now) <= 0) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du bist nun kein ", NamedTextColor.RED))
                                .append(RankStatements.getCleanRank(player))
                                .append(Component.text("mehr.", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You aren´t longer a ", NamedTextColor.RED))
                                .append(RankStatements.getCleanRank(player))
                                .append(Component.text(".", NamedTextColor.RED)));
                    }
                    int rang = RankAPI.getApi().getRankID(player);
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.kick(Component.text("Du bist nun kein ", NamedTextColor.RED)
                                .append(RankStatements.getCleanRank(player))
                                .append(Component.text("mehr.", NamedTextColor.RED)));
                    } else {
                        player.kick(Component.text("You aren´t longer a ", NamedTextColor.RED)
                                .append(RankStatements.getCleanRank(player))
                                .append(Component.text(".", NamedTextColor.RED)));
                    }
                    RankAPI.getApi().setRankID(player, rang-1, 182, Calendar.getInstance());
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du bist nun ein ", NamedTextColor.GREEN))
                                .append(RankStatements.getCleanRank(player))
                                .append(Component.text(".", NamedTextColor.GREEN)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You are now a ", NamedTextColor.GREEN))
                                .append(RankStatements.getCleanRank(player))
                                .append(Component.text(".", NamedTextColor.GREEN)));
                    }
                }
            }
        }, 20, 12000);
    }
}
