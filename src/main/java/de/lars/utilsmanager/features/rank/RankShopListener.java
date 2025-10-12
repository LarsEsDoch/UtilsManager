package de.lars.utilsmanager.features.rank;

import de.lars.apiManager.coinAPI.CoinAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsmanager.util.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Calendar;

public class RankShopListener implements Listener {

    @EventHandler
    public void onInventoryKlick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.isOp()) {
            return;
        }
        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getView().title().equals(Component.text("           RankShop", NamedTextColor.DARK_GREEN, TextDecoration.BOLD))) {
            event.setCancelled(true);
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            NamespacedKey rankKey = new NamespacedKey("utilsmanager", "rank_id");
            String id = clicked.getItemMeta().getPersistentDataContainer().get(rankKey, PersistentDataType.STRING);
            if (id == null) return;

            int balance = CoinAPI.getApi().getCoins(player);
            int price = switch (id) {
                case "6premium" -> 500;
                case "12premium" -> 900;
                case "6supreme" -> 1500;
                case "12supreme" -> 2800;
                case "6titan" -> 5000;
                case "12titan" -> 9500;
                case "6matrix" -> 50000;
                case "12matrix" -> 100000;
                default -> 0;
            };

            if (balance < price) {
                Component message = Component.text()
                        .append(Component.text(
                                LanguageAPI.getApi().getLanguage(player) == 2 ? "Du hast nicht genug Geld! Dir fehlen " :
                                "You haven´t got enough money! You miss ", NamedTextColor.RED))
                        .append(Component.text(price - balance, NamedTextColor.YELLOW))
                        .append(Component.text("$.", NamedTextColor.RED)).build();
                player.sendMessage(message);
                return;
            }

            int rankID;
            int durationDays;
            switch (id) {
                case "6premium" -> { rankID = 2; durationDays = 182; }
                case "12premium" -> { rankID = 2; durationDays = 365; }
                case "6supreme" -> { rankID = 3; durationDays = 182; }
                case "12supreme" -> { rankID = 3; durationDays = 365; }
                case "6titan" -> { rankID = 4; durationDays = 182; }
                case "12titan" -> { rankID = 4; durationDays = 365; }
                case "6matrix" -> { rankID = 5; durationDays = 182; }
                case "12matrix" -> { rankID = 5; durationDays = 365; }
                default -> { return; }
            }

            RankAPI.getApi().setRankID(player, rankID, durationDays, Calendar.getInstance());
            CoinAPI.getApi().removeCoins(player, price);

            String rankName = switch (id) {
                case "6premium", "12premium" -> "Premium";
                case "6supreme", "12supreme" -> "Supreme";
                case "6titan", "12titan" -> "Titan";
                case "6matrix", "12matrix" -> "Matrix";
                default -> "";
            };
            String months = id.startsWith("6") ? "6 months" : "12 months";
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix()
                        .append(Component.text("Du hast den ", NamedTextColor.WHITE))
                        .append(Component.text(rankName + " ", NamedTextColor.GREEN))
                        .append(Component.text("Rang für ", NamedTextColor.WHITE))
                        .append(Component.text(months, NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text(" gekauft.", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix()
                        .append(Component.text("You bought the ", NamedTextColor.WHITE))
                        .append(Component.text(rankName + " ", NamedTextColor.GREEN))
                        .append(Component.text("rank for ", NamedTextColor.WHITE))
                        .append(Component.text(months, NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text(".", NamedTextColor.WHITE)));
            }
        }
    }

}
