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

public class SellCommand implements BasicCommand {

    private int sellprice;
    private int number;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Component.text("Only player can send Messages.", NamedTextColor.RED));
            return;
        }

        if (!(player.hasPermission("plugin.sell"))) {
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

        number = Integer.parseInt(args[1]);
        if (number <= 0) {
            sendUsage(player);
            return;
        }
        if (number >= 100001) {
            sendUsage(player);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "copper": {
                PlayerInventory inventory = player.getInventory();
                ItemStack copper = new ItemStack(Material.COPPER_INGOT, number);
                int existing = inventory.all(Material.COPPER_INGOT).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                sellprice = 5 * number;
                if (number > existing) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Kupfer Barren! Dir fehlen " + (number - existing) + ".");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough Copper Ingots! You miss " + (number - existing) + ".");
                    }
                    break;
                }
                inventory.removeItem(copper);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(sellprice);
                if (number >= 1) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Kupfer Barren verkauft, für ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl + "$.", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You sold ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Copper Ingots, for ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl + "$.", NamedTextColor.LIGHT_PURPLE)));
                    }
                } else {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Kupfer Barren verkauft, für ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl + "$.", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You sold ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Copper Ingot, for ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl + "$.", NamedTextColor.LIGHT_PURPLE)));
                    }
                }

                CoinAPI.getApi().addCoins(player, sellprice);
                break;
            }
            case "amethyst": {
                PlayerInventory inventory = player.getInventory();
                ItemStack amethyst = new ItemStack(Material.AMETHYST_SHARD, number);
                int existing = inventory.all(Material.AMETHYST_SHARD).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                sellprice = 20 * number;
                if (number > existing) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Amethyst Scherben! Dir fehlen " + (number - existing) + ".");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough Amethyst Shards! You miss " + (number - existing) + ".");
                    }
                    break;
                }
                inventory.removeItem(amethyst);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(sellprice);
                if (number >= 1) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Amethyst Scherben verkauft, für ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl, NamedTextColor.LIGHT_PURPLE))
                                .append(Component.text("$.")));
                    } else {
                        player.sendMessage(Statements.getPrefix().append(Component.text("You sold ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Amethyst Shards, for ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl, NamedTextColor.LIGHT_PURPLE))
                                .append(Component.text("$.")));
                    }
                } else {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Amethyst Scherbe verkauft, für ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl, NamedTextColor.LIGHT_PURPLE))
                                .append(Component.text("$.")));
                    } else {
                        player.sendMessage(Statements.getPrefix().append(Component.text("You sold ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Amethyst Shard, for ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl, NamedTextColor.LIGHT_PURPLE))
                                .append(Component.text("$.")));
                    }
                }

                CoinAPI.getApi().addCoins(player, sellprice);
                break;
            }
            case "diamond": {
                PlayerInventory inventory = player.getInventory();
                ItemStack diamond = new ItemStack(Material.DIAMOND, number);
                int existing = inventory.all(Material.DIAMOND).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                sellprice = 100 * number;
                if (number > existing) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Diamanten! Dir fehlen " + (number - existing) + ".");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough Diamonds! You miss " + (number - existing) + ".");
                    }
                    break;
                }
                inventory.removeItem(diamond);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(sellprice);
                if (number >= 1) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Diamanten verkauft, für ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl, NamedTextColor.AQUA))
                                .append(Component.text("$.")));
                    } else {
                        player.sendMessage(Statements.getPrefix().append(Component.text("You sold ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Diamonds, for ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl, NamedTextColor.AQUA))
                                .append(Component.text("$.")));
                    }
                } else {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Diamant verkauft, für ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl, NamedTextColor.AQUA))
                                .append(Component.text("$.")));
                    } else {
                        player.sendMessage(Statements.getPrefix().append(Component.text("You sold ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Diamond, for ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl, NamedTextColor.AQUA))
                                .append(Component.text("$.")));
                    }
                }
                CoinAPI.getApi().addCoins(player, sellprice);
                break;
            }
            case "netherite": {
                PlayerInventory inventory = player.getInventory();
                ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT, number);
                int existing = inventory.all(Material.NETHERITE_INGOT).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                sellprice = 1000 * number;
                if (number > existing) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Netherite Barren! Dir fehlen " + (number - existing) + ".");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough Netherite Ingots! You miss " + (number - existing) + ".");
                    }
                    break;
                }
                inventory.removeItem(netherite);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(sellprice);
                if (number >= 1) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Netherite Barren verkauft, für ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl, NamedTextColor.DARK_GRAY))
                                .append(Component.text("$.")));
                    } else {
                        player.sendMessage(Statements.getPrefix().append(Component.text("You sold ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Netherite Ingots, for ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl, NamedTextColor.DARK_GRAY))
                                .append(Component.text("$.")));
                    }
                } else {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Netherite Barren verkauft, für ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl, NamedTextColor.DARK_GRAY))
                                .append(Component.text("$.")));
                    } else {
                        player.sendMessage(Statements.getPrefix().append(Component.text("You sold ", NamedTextColor.WHITE))
                                .append(Component.text(number, NamedTextColor.GOLD))
                                .append(Component.text(" Netherite Ingot, for ", NamedTextColor.WHITE))
                                .append(Component.text(formatierteZahl, NamedTextColor.DARK_GRAY))
                                .append(Component.text("$.")));
                    }
                }

                CoinAPI.getApi().addCoins(player, sellprice);
                break;
            }
            case "spawner": {
                PlayerInventory inventory = player.getInventory();
                ItemStack spawner = new ItemStack(Material.SPAWNER, number);
                int existing = inventory.all(Material.SPAWNER).values().stream()
                        .mapToInt(ItemStack::getAmount).sum();
                sellprice = 7500 * number;
                if (number > existing) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Spawner! Dir fehlen " + (number - existing) + ".");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough Spawner! You miss " + (number - existing) + ".");
                    }
                    break;
                }
                inventory.removeItem(spawner);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(sellprice);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.WHITE))
                            .append(Component.text(number, NamedTextColor.GOLD))
                            .append(Component.text(" Spawner verkauft, für ", NamedTextColor.WHITE))
                            .append(Component.text(formatierteZahl, NamedTextColor.BLACK))
                            .append(Component.text("$.")));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("You sold ", NamedTextColor.WHITE))
                            .append(Component.text(number, NamedTextColor.GOLD))
                            .append(Component.text(" spawner, for ", NamedTextColor.WHITE))
                            .append(Component.text(formatierteZahl, NamedTextColor.BLACK))
                            .append(Component.text("$.")));
                }
                CoinAPI.getApi().addCoins(player, sellprice);
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


























