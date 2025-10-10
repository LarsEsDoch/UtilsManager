package de.lars.utilsManager.ban;

import de.lars.apiManager.banAPI.BanAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.RankStatements;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Banhammer implements Listener {
    private String name;
    private String Itemname;
    private ItemMeta itemMeta;
    private String displayName;

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
        Main.getInstance().getDiscordBot().sendPunishmentMessage(message);
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

        if(!(RankAPI.getApi().getRankID(player) >= 9)) {
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
        Main.getInstance().getDiscordBot().sendPunishmentMessage(message);
    }
}
