package dev.lars.utilsmanager.features.moderation;

import dev.lars.apimanager.apis.banAPI.BanAPI;
import dev.lars.apimanager.apis.rankAPI.RankAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.FormatNumbers;
import dev.lars.utilsmanager.utils.RankStatements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class BanListener implements Listener {

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(event.getUniqueId());
        UtilsManager.getInstance().getBanManager().checkBanned(player);
        if (BanAPI.getApi().isBanned(player)) {
            Instant now = Instant.now();
            Instant end = BanAPI.getApi().getExpiration(player);
            if (end == null) return;

            Component message = Component.text("You're banned! Reason:\n", NamedTextColor.WHITE)
                    .append(Component.text(BanAPI.getApi().getReason(player), NamedTextColor.RED))
                    .append(Component.text("\n"))
                    .append(Component.text("Time to wait: ", NamedTextColor.GOLD))
                    .append(FormatNumbers.formatDuration(Duration.between(now, end).getSeconds()))
                    .append(Component.text("!", NamedTextColor.GOLD));
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message);
        }
        if (UtilsManager.getInstance().getKickManager().isKicked(player)) {
            String reason = UtilsManager.getInstance().getKickManager().getReason(player);
            Integer time = UtilsManager.getInstance().getKickManager().getTime(player);
            Component message = Component.text("You're kicked! Reason:\n", NamedTextColor.DARK_RED)
                    .append(Component.text(reason, NamedTextColor.RED))
                    .append(Component.text("\n"))
                    .append(Component.text("Time to wait: ", NamedTextColor.GOLD))
                    .append(FormatNumbers.formatDuration(time))
                    .append(Component.text("!", NamedTextColor.GOLD));
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, message);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked().getType() != EntityType.PLAYER) {
            return;
        }

        if (!(player.hasPermission("utilsmanager.ban"))) {
            return;
        }

        if (!event.getPlayer().getItemInHand().getType().equals(Material.NETHERITE_HOE)) {
            return;
        }

        if (!Objects.equals(event.getPlayer().getItemInHand().getItemMeta().getDisplayName(), "Banhammer")) {
            return;
        }

        Player banned = (Player) event.getRightClicked();
        BanAPI.getApi().setBanned(banned, NamedTextColor.WHITE + "Banned by " + RankStatements.getRank(player) + player.getName() + NamedTextColor.WHITE + "!!!", Instant.now().plus(Duration.ofDays(7)));
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
        BanAPI.getApi().setBanned(banned, NamedTextColor.WHITE + "Banned by " + RankStatements.getRank(player) + player.getName() + NamedTextColor.WHITE + "!!!", Instant.now().plus(Duration.ofDays(7)));
        String message = "Der Spieler " + RankStatements.getUnformattedRank(banned) + banned.getName() + " wurde von" + RankStatements.getUnformattedRank(player) + player.getName() + " gebannt für 7 Tage !";
        UtilsManager.getInstance().getDiscordBot().sendPunishmentMessage(message);
    }
}