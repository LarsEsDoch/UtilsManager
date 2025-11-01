package de.lars.utilsmanager.features.moderation;

import de.lars.apimanager.apis.banAPI.BanAPI;
import de.lars.apimanager.apis.rankAPI.RankAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.util.RankStatements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class BanListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        UtilsManager.getInstance().getBanManager().checkBanned(player);
        if (BanAPI.getApi().isBanned(player)) {
            Instant now = Instant.now();
            Instant end = BanAPI.getApi().getEnd(player);
            if (end == null) return;

            String remaining = getTimeDifference(now, end);


            event.disallow(PlayerLoginEvent.Result.KICK_OTHER,  Component.text("You're banned! Reason:\n", NamedTextColor.WHITE)
                    .append(Component.text(BanAPI.getApi().getReason(player), NamedTextColor.RED))
                    .append(Component.text("\n"))
                    .append(Component.text("Time to wait: ", NamedTextColor.GOLD))
                    .append(Component.text(remaining + "!", NamedTextColor.GOLD)));
        }
        if (UtilsManager.getInstance().getKickManager().isKicked(player)) {
            String reason = UtilsManager.getInstance().getKickManager().getReason(player);
            Integer time = UtilsManager.getInstance().getKickManager().getTime(player);
            int seconds = time % 60;
            int minutes = (time / 60) % 60;
            int hours = (time / 3600);
            String formatedTime = String.format("%02dh %02dm %02ds", hours, minutes, seconds);
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER,  Component.text("You're kicked! Reason:\n", NamedTextColor.DARK_RED)
                    .append(Component.text(reason, NamedTextColor.RED))
                    .append(Component.text("\n"))
                    .append(Component.text("Time to wait: ", NamedTextColor.GOLD))
                    .append(Component.text(formatedTime + "!", NamedTextColor.GOLD)));
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked().getType() != EntityType.PLAYER) {
            return;
        }

        if (!(player.hasPermission("plugin.ban"))) {
            return;
        }

        if (!event.getPlayer().getItemInHand().getType().equals(Material.NETHERITE_HOE)) {
            return;
        }

        if (!Objects.equals(event.getPlayer().getItemInHand().getItemMeta().getDisplayName(), "Banhammer")) {
            return;
        }

        Player banned = (Player) event.getRightClicked();
        BanAPI.getApi().setBanned(banned, NamedTextColor.WHITE + "Banned by " + RankStatements.getRank(player) + player.getName() + NamedTextColor.WHITE + "!!!", 7);
        String message = "Der Spieler " + RankStatements.getUnformattedRank(banned) + banned.getName() + " wurde von" + RankStatements.getRank(player) + player.getName() + " gebannt für 7 Tage !";
        UtilsManager.getInstance().getDiscordBot().sendPunishmentMessage(message);
    }

    @EventHandler
    public void onPlayerInteractEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER) {
            return;
        }
        if (event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) event.getDamager();

        if(!(RankAPI.getApi().getRankId(player) >= 9)) {
            return;
        }

        if(!player.getItemInHand().getType().equals(Material.NETHERITE_HOE)) {
            return;
        }

        if(!Objects.equals(player.getItemInHand().getItemMeta().getDisplayName(), "Banhammer")) {
            return;
        }

        Player banned = (Player) event.getEntity();
        BanAPI.getApi().setBanned(banned, NamedTextColor.WHITE + "Banned by " + RankStatements.getRank(player) + player.getName() + NamedTextColor.WHITE + "!!!", 7);
        String message = "Der Spieler " + RankStatements.getUnformattedRank(banned) + banned.getName() + " wurde von" + RankStatements.getUnformattedRank(player) + player.getName() + " gebannt für 7 Tage !";
        UtilsManager.getInstance().getDiscordBot().sendPunishmentMessage(message);
    }

    public static String getTimeDifference(Instant from, Instant to) {
    Duration duration = Duration.between(from, to);
    long seconds = duration.getSeconds();
    if (seconds < 0) seconds = 0;

    long days = seconds / 86400;
    long hours = (seconds % 86400) / 3600;
    long minutes = (seconds % 3600) / 60;
    long secs = seconds % 60;

    if (days > 0)
        return String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, secs);
    else if (hours > 0)
        return String.format("%02dh %02dm %02ds", hours, minutes, secs);
    else
        return String.format("%02dm %02ds", minutes, secs);
    }
}
