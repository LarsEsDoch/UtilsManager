package de.lars.utilsManager.coins;

import de.lars.apiManager.coinAPI.CoinAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BuyCommand implements BasicCommand {

    private int price;
    private int balence;
    private int nummber;
    private int missing;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player player = (Player) stack.getSender();

        if (!(player.hasPermission("plugin.buy"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        if (args.length == 1) {
            sendUsage(player);
            return;
        }

        for (String arg : args) {
            try {
                Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sendUsage(player);
                return;
            }
        }

        nummber = Integer.parseInt(args[1]);
        if (nummber <= 0) {
            sendUsage(player);
            return;
        }
        if (nummber >= 100001) {
            sendUsage(player);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "copper": {
                PlayerInventory inventory = player.getInventory();
                ItemStack copper = new ItemStack(Material.COPPER_INGOT, nummber);
                balence = CoinAPI.getApi().getCoins(player);
                price = 10 * nummber;
                missing = price - balence;
                DecimalFormat formatterm = new DecimalFormat("#,###");
                String formatierteMissing = formatterm.format(missing);
                if (price > balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 1) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + formatierteMissing + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + formatierteMissing + "$.");
                    }
                    break;
                }
                inventory.addItem(copper);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(price);
                if (nummber >= 1) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Kupfer Barren gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Copper Ingots, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                } else {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Kupfer Barren gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Copper Ingot, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                }

                CoinAPI.getApi().removeCoins(player, price);
                break;
            }
            case "amethyst": {
                PlayerInventory inventory = player.getInventory();
                ItemStack amethyst = new ItemStack(Material.AMETHYST_SHARD, nummber);
                balence = CoinAPI.getApi().getCoins(player);
                price = 30 * nummber;
                missing = price - balence;
                DecimalFormat formatterm = new DecimalFormat("#,###");
                String formatierteMissing = formatterm.format(missing);
                if (price > balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + formatierteMissing + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + formatierteMissing + "$.");
                    }
                    break;
                }
                inventory.addItem(amethyst);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(price);
                if (nummber >= 1) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Amethyst Scherben gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Amethyst Shards, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                } else {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Amethyst Scherbe gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Amethyst Shard, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                }
                CoinAPI.getApi().removeCoins(player, price);
                break;
            }
            case "diamond": {
                PlayerInventory inventory = player.getInventory();
                ItemStack diamond = new ItemStack(Material.DIAMOND, nummber);
                balence = CoinAPI.getApi().getCoins(player);
                price = 150 * nummber;
                missing = price - balence;
                DecimalFormat formatterm = new DecimalFormat("#,###");
                String formatierteMissing = formatterm.format(missing);
                if (price > balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + formatierteMissing + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + formatierteMissing + "$.");
                    }
                    break;
                }
                inventory.addItem(diamond);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(price);
                if (nummber >= 1) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Diamanten gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Diamonds, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                } else {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Diamant gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Diamond, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                }

                CoinAPI.getApi().removeCoins(player, price);
                break;
            }
            case "netherite": {
                PlayerInventory inventory = player.getInventory();
                ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT, nummber);
                balence = CoinAPI.getApi().getCoins(player);
                price = 1250 * nummber;
                missing = price - balence;
                DecimalFormat formatterm = new DecimalFormat("#,###");
                String formatierteMissing = formatterm.format(missing);
                if (price > balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + formatierteMissing + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + formatierteMissing + "$.");
                    }
                    break;
                }
                inventory.addItem(netherite);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(price);
                if (nummber >= 1) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Netherite Barren gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Netherite Ingots, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                } else {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Netherite Barren gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                                .append(Component.text("Netherite Ingot, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                }

                CoinAPI.getApi().removeCoins(player, price);
                break;
            }
            case "spawner": {
                PlayerInventory inventory = player.getInventory();
                ItemStack spawner = new ItemStack(Material.SPAWNER, nummber);
                balence = CoinAPI.getApi().getCoins(player);
                price = 10000 * nummber;
                missing = price - balence;
                DecimalFormat formatterm = new DecimalFormat("#,###");
                String formatierteMissing = formatterm.format(missing);
                if (price >= balence) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + formatierteMissing + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + formatierteMissing + "$.");
                    }
                    break;
                }
                inventory.addItem(spawner);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(price);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast ", NamedTextColor.GREEN))
                            .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                            .append(Component.text("Spawner gekauft, für ", NamedTextColor.YELLOW))
                            .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought ", NamedTextColor.GREEN))
                            .append(Component.text(nummber + " ", NamedTextColor.GOLD))
                            .append(Component.text("Spawner, for ", NamedTextColor.YELLOW))
                            .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                }
                CoinAPI.getApi().removeCoins(player, price);
                break;
            }

            default:
                sendUsage(player);
                break;
        }
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        if (args.length == 0 || args.length == 1) {
            List<String> timercommands = new ArrayList<>();
            timercommands.add("copper");
            timercommands.add("amethyst");
            timercommands.add("diamond");
            timercommands.add("netherite");
            timercommands.add("spawner");

            return timercommands;
        }
        return Collections.emptyList();
    }

    private void sendUsage(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/buy copper, amethyst, diamond, netherite, spawner <Anzahl> (Anzahl max. 100000)");
        } else {
            sender.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/buy copper, amethyst, diamond, netherite, spawner <number> (number max. 100000)");
        }

    }

}


























