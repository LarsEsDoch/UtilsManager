package dev.lars.utilsmanager.commands.economy;

import dev.lars.apimanager.apis.economyAPI.EconomyAPI;
import dev.lars.apimanager.apis.languageAPI.Language;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.utils.Statements;
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

    private long price;
    private long balance;
    private long number;
    private long missing;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

        if (!(player.hasPermission("utilsmanager.buy"))) {
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
                ItemStack copper = new ItemStack(Material.COPPER_INGOT, (int) number);
                balance = EconomyAPI.getApi().getBalance(player);
                price = 10 * number;
                missing = price - balance;
                DecimalFormat formatterm = new DecimalFormat("#,###");
                String formatierteMissing = formatterm.format(missing);
                if (price > balance) {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + formatierteMissing + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + formatierteMissing + "$.");
                    }
                    break;
                }
                inventory.addItem(copper);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(price);
                if (number >= 1) {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Kupfer Barren gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Copper Ingots, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                } else {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Kupfer Barren gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Copper Ingot, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                }

                EconomyAPI.getApi().decreaseBalance(player, price);
                break;
            }
            case "amethyst": {
                PlayerInventory inventory = player.getInventory();
                ItemStack amethyst = new ItemStack(Material.AMETHYST_SHARD, (int) number);
                balance = EconomyAPI.getApi().getBalance(player);
                price = 30 * number;
                missing = price - balance;
                DecimalFormat formatterm = new DecimalFormat("#,###");
                String formatierteMissing = formatterm.format(missing);
                if (price > balance) {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + formatierteMissing + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + formatierteMissing + "$.");
                    }
                    break;
                }
                inventory.addItem(amethyst);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(price);
                if (number >= 1) {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Amethyst Scherben gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Amethyst Shards, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                } else {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Amethyst Scherbe gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Amethyst Shard, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                }
                EconomyAPI.getApi().decreaseBalance(player, price);
                break;
            }
            case "diamond": {
                PlayerInventory inventory = player.getInventory();
                ItemStack diamond = new ItemStack(Material.DIAMOND, (int) number);
                balance = EconomyAPI.getApi().getBalance(player);
                price = 150 * number;
                missing = price - balance;
                DecimalFormat formatterm = new DecimalFormat("#,###");
                String formatierteMissing = formatterm.format(missing);
                if (price > balance) {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + formatierteMissing + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + formatierteMissing + "$.");
                    }
                    break;
                }
                inventory.addItem(diamond);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(price);
                if (number >= 1) {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Diamanten gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Diamonds, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                } else {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Diamant gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Diamond, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                }

                EconomyAPI.getApi().decreaseBalance(player, price);
                break;
            }
            case "netherite": {
                PlayerInventory inventory = player.getInventory();
                ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT, (int) number);
                balance = EconomyAPI.getApi().getBalance(player);
                price = 1250 * number;
                missing = price - balance;
                DecimalFormat formatterm = new DecimalFormat("#,###");
                String formatierteMissing = formatterm.format(missing);
                if (price > balance) {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + formatierteMissing + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + formatierteMissing + "$.");
                    }
                    break;
                }
                inventory.addItem(netherite);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(price);
                if (number >= 1) {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Netherite Barren gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Netherite Ingots, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                } else {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du hast ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Netherite Barren gekauft, für ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You bought ", NamedTextColor.GREEN))
                                .append(Component.text(number + " ", NamedTextColor.GOLD))
                                .append(Component.text("Netherite Ingot, for ", NamedTextColor.YELLOW))
                                .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                    }
                }

                EconomyAPI.getApi().decreaseBalance(player, price);
                break;
            }
            case "spawner": {
                PlayerInventory inventory = player.getInventory();
                ItemStack spawner = new ItemStack(Material.SPAWNER, (int) number);
                balance = EconomyAPI.getApi().getBalance(player);
                price = 10000 * number;
                missing = price - balance;
                DecimalFormat formatterm = new DecimalFormat("#,###");
                String formatierteMissing = formatterm.format(missing);
                if (price >= balance) {
                    if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                        player.sendMessage(NamedTextColor.RED + "Du hast nicht genug Geld! Dir fehlen " + formatierteMissing + "$.");
                    } else {
                        player.sendMessage(NamedTextColor.RED + "You haven´t got enough money! You miss " + formatierteMissing + "$.");
                    }
                    break;
                }
                inventory.addItem(spawner);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(price);
                if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("Du hast ", NamedTextColor.GREEN))
                            .append(Component.text(number + " ", NamedTextColor.GOLD))
                            .append(Component.text("Spawner gekauft, für ", NamedTextColor.YELLOW))
                            .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                } else {
                    player.sendMessage(Statements.getPrefix()
                            .append(Component.text("You bought ", NamedTextColor.GREEN))
                            .append(Component.text(number + " ", NamedTextColor.GOLD))
                            .append(Component.text("Spawner, for ", NamedTextColor.YELLOW))
                            .append(Component.text(formatierteZahl + "$", NamedTextColor.LIGHT_PURPLE)));
                }
                EconomyAPI.getApi().decreaseBalance(player, price);
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
            List<String> materials = new ArrayList<>();
            materials.add("copper");
            materials.add("amethyst");
            materials.add("diamond");
            materials.add("netherite");
            materials.add("spawner");

            return materials;
        }
        return Collections.emptyList();
    }

    private void sendUsage(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == Language.GERMAN) {
            sender.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/buy copper, amethyst, diamond, netherite, spawner <Anzahl> (Anzahl max. 100000)");
        } else {
            sender.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/buy copper, amethyst, diamond, netherite, spawner <number> (number max. 100000)");
        }

    }

}


























