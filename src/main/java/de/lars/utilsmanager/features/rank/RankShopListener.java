package de.lars.utilsmanager.features.rank;

import de.lars.apiManager.coinAPI.CoinAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsmanager.util.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Calendar;

public class RankShopListener implements Listener {

    private int price;
    private int balence;

    @EventHandler
    public void onInventoryKlick(InventoryClickEvent event) {
        World world = Bukkit.getWorld("world");
        Player player = (Player) event.getWhoClicked();
        if (player.isOp()) {
            return;
        }
        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getView().title().equals(Component.text("           RankShop", NamedTextColor.DARK_GREEN, TextDecoration.BOLD))) {
            event.setCancelled(true);
            String clickedItem = event.getCurrentItem().getItemMeta().getLocalizedName();
            String premium6 = "6premium";
            String premium12 = "12premium";
            String supreme6 = "6supreme";
            String supreme12 = "12supreme";
            String titan6 = "6titan";
            String titan12 = "12titan";
            String matrix6 = "6matrix";
            String matrix12 = "12matrix";
            if (clickedItem.equals(premium6)) {
                
                balence = CoinAPI.getApi().getCoins(player);
                price = 500;
                if (price > balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Component.text()
                                .append(Component.text("Du hast nicht genug Geld! Dir fehlen ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Component.text()
                                .append(Component.text("You haven´t got enough money! You miss ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    }

                    return;
                }

                RankAPI.getApi().setRankID(player, 2, 182, Calendar.getInstance());
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den", NamedTextColor.WHITE))
                            .append(Component.text(" Premium ", NamedTextColor.GREEN))
                            .append(Component.text("Rang für ", NamedTextColor.WHITE))
                            .append(Component.text("6 Monate", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text("gekauft.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought the", NamedTextColor.WHITE))
                            .append(Component.text(" Premium ", NamedTextColor.GREEN))
                            .append(Component.text("rank for ", NamedTextColor.WHITE))
                            .append(Component.text("6 months", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                CoinAPI.getApi().removeCoins(player, price);
                return;
            }
            if (clickedItem.equals(premium12)) {

                balence = CoinAPI.getApi().getCoins(player);
                price = 900;
                if (price > balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Component.text()
                                .append(Component.text("Du hast nicht genug Geld! Dir fehlen ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Component.text()
                                .append(Component.text("You haven´t got enough money! You miss ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    }

                    return;
                }

                RankAPI.getApi().setRankID(player, 2, 365, Calendar.getInstance());
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den", NamedTextColor.WHITE))
                            .append(Component.text(" Premium ", NamedTextColor.GREEN))
                            .append(Component.text("Rang für ", NamedTextColor.WHITE))
                            .append(Component.text("12 Monate", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text("gekauft.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought the", NamedTextColor.WHITE))
                            .append(Component.text(" Premium ", NamedTextColor.GREEN))
                            .append(Component.text("rank for ", NamedTextColor.WHITE))
                            .append(Component.text("12 months", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                CoinAPI.getApi().removeCoins(player, price);
                return;
            }
            if (clickedItem.equals(supreme6)) {

                balence = CoinAPI.getApi().getCoins(player);
                price = 1500;
                if (price > balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Component.text()
                                .append(Component.text("Du hast nicht genug Geld! Dir fehlen ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Component.text()
                                .append(Component.text("You haven´t got enough money! You miss ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    }

                    return;
                }

                RankAPI.getApi().setRankID(player, 3, 182, Calendar.getInstance());
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den", NamedTextColor.WHITE))
                            .append(Component.text(" Supreme ", NamedTextColor.AQUA))
                            .append(Component.text("Rang für ", NamedTextColor.WHITE))
                            .append(Component.text("6 Monate", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text("gekauft.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought the", NamedTextColor.WHITE))
                            .append(Component.text(" Supreme ", NamedTextColor.AQUA))
                            .append(Component.text("rank for ", NamedTextColor.WHITE))
                            .append(Component.text("6 months", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }
                CoinAPI.getApi().removeCoins(player, price);
                return;
            }
            if (clickedItem.equals(supreme12)) {

                balence = CoinAPI.getApi().getCoins(player);
                price = 2800;
                if (price > balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Component.text()
                                .append(Component.text("Du hast nicht genug Geld! Dir fehlen ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Component.text()
                                .append(Component.text("You haven´t got enough money! You miss ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    }

                    return;
                }

                RankAPI.getApi().setRankID(player, 3, 365, Calendar.getInstance());
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den", NamedTextColor.WHITE))
                            .append(Component.text(" Supreme ", NamedTextColor.AQUA))
                            .append(Component.text("Rang für ", NamedTextColor.WHITE))
                            .append(Component.text("12 Monate", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text("gekauft.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought the", NamedTextColor.WHITE))
                            .append(Component.text(" Supreme ", NamedTextColor.AQUA))
                            .append(Component.text("rank for ", NamedTextColor.WHITE))
                            .append(Component.text("12 months", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }

                CoinAPI.getApi().removeCoins(player, price);
                return;
            }
            if (clickedItem.equals(titan6)) {
                balence = CoinAPI.getApi().getCoins(player);
                price = 5000;
                if (price >= balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Component.text()
                                .append(Component.text("Du hast nicht genug Geld! Dir fehlen ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Component.text()
                                .append(Component.text("You haven´t got enough money! You miss ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    }

                    return;
                }

                RankAPI.getApi().setRankID(player, 4, 182, Calendar.getInstance());
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den", NamedTextColor.WHITE))
                            .append(Component.text(" Titan ", NamedTextColor.BLUE))
                            .append(Component.text("Rang für ", NamedTextColor.WHITE))
                            .append(Component.text("6 Monate", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text("gekauft.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought the", NamedTextColor.WHITE))
                            .append(Component.text(" Titan ", NamedTextColor.BLUE))
                            .append(Component.text("rank for ", NamedTextColor.WHITE))
                            .append(Component.text("6 months", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }

                CoinAPI.getApi().removeCoins(player, price);
                return;
            }
            if (clickedItem.equals(titan12)) {
                balence = CoinAPI.getApi().getCoins(player);
                price = 9500;
                if (price >= balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Component.text()
                                .append(Component.text("Du hast nicht genug Geld! Dir fehlen ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Component.text()
                                .append(Component.text("You haven´t got enough money! You miss ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    }


                    return;
                }

                RankAPI.getApi().setRankID(player, 4, 365, Calendar.getInstance());
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den", NamedTextColor.WHITE))
                            .append(Component.text(" Titan ", NamedTextColor.BLUE))
                            .append(Component.text("Rang für ", NamedTextColor.WHITE))
                            .append(Component.text("12 Monate", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text("gekauft.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought the", NamedTextColor.WHITE))
                            .append(Component.text(" Titan ", NamedTextColor.BLUE))
                            .append(Component.text("rank for ", NamedTextColor.WHITE))
                            .append(Component.text("12 months", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }

                CoinAPI.getApi().removeCoins(player, price);
                return;
            }
            if (clickedItem.equals(matrix6)) {
                balence = CoinAPI.getApi().getCoins(player);
                price = 50000;
                if (price >= balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Component.text()
                                .append(Component.text("Du hast nicht genug Geld! Dir fehlen ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Component.text()
                                .append(Component.text("You haven´t got enough money! You miss ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    }

                    return;
                }

                RankAPI.getApi().setRankID(player, 5, 182, Calendar.getInstance());
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den", NamedTextColor.WHITE))
                            .append(Component.text(" Matrix ", NamedTextColor.GOLD))
                            .append(Component.text("Rang für ", NamedTextColor.WHITE))
                            .append(Component.text("6 Monate", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text("gekauft.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought the", NamedTextColor.WHITE))
                            .append(Component.text(" Matrix ", NamedTextColor.GOLD))
                            .append(Component.text("rank for ", NamedTextColor.WHITE))
                            .append(Component.text("6 months", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }

                CoinAPI.getApi().removeCoins(player, price);
                return;
            }
            if (clickedItem.equals(matrix12)) {
                balence = CoinAPI.getApi().getCoins(player);
                price = 100000;
                if (price >= balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Component.text()
                                .append(Component.text("Du hast nicht genug Geld! Dir fehlen ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Component.text()
                                .append(Component.text("You haven´t got enough money! You miss ", NamedTextColor.RED))
                                .append(Component.text(price - balence, NamedTextColor.YELLOW))
                                .append(Component.text("$.", NamedTextColor.RED)));
                    }

                    return;
                }

                RankAPI.getApi().setRankID(player, 5, 365, Calendar.getInstance());
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast den", NamedTextColor.WHITE))
                            .append(Component.text(" Matrix ", NamedTextColor.GOLD))
                            .append(Component.text("Rang für ", NamedTextColor.WHITE))
                            .append(Component.text("12 Monate", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text("gekauft.", NamedTextColor.WHITE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought the", NamedTextColor.WHITE))
                            .append(Component.text(" Matrix ", NamedTextColor.GOLD))
                            .append(Component.text("rank for ", NamedTextColor.WHITE))
                            .append(Component.text("12 months", NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(".", NamedTextColor.WHITE)));
                }

                CoinAPI.getApi().removeCoins(player, price);
            }
        }


    }

}
