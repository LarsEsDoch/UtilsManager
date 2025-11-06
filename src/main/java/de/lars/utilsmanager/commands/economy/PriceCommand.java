package de.lars.utilsmanager.commands.economy;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PriceCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (!(player.hasPermission("plugin.price"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Kauf und Verkauf kosten:", NamedTextColor.GOLD, TextDecoration.BOLD)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Kaufkosten eines Kupfer Barren = 10$", NamedTextColor.YELLOW))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("10", NamedTextColor.DARK_RED))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Kaufkosten einer Amethyst Scherbe = 30$", NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("30", NamedTextColor.DARK_RED))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Kaufkosten eines Diamanten = 150$", NamedTextColor.AQUA))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("150", NamedTextColor.DARK_RED))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Kaufkosten eines Netherite Barren = 1.250$", NamedTextColor.RED))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("1.250", NamedTextColor.DARK_RED))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Kaufkosten eines Spawner = 10.000$", NamedTextColor.DARK_GRAY))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("10.000", NamedTextColor.DARK_RED))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Component.text(" "));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Verkaufkosten eines Kupfer Barren = 5$", NamedTextColor.YELLOW))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("5", NamedTextColor.GREEN))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Verkaufkosten einer Amethyst Scherbe = 20$", NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("20", NamedTextColor.GREEN))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Verkaufkosten eines Diamanten = 100$", NamedTextColor.AQUA))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("100", NamedTextColor.GREEN))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Verkaufkosten eines Netherite Barren = 1.000$", NamedTextColor.RED))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("1.000", NamedTextColor.GREEN))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Verkaufkosten eines Spawner = 7.500$", NamedTextColor.DARK_GRAY))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("7.500", NamedTextColor.GREEN))
                    .append(Component.text("$", NamedTextColor.WHITE)));
        } else {
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Buy and Sell costs:", NamedTextColor.GOLD, TextDecoration.BOLD)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Buycosts of a Copper Ingot = 10$", NamedTextColor.YELLOW))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("10", NamedTextColor.DARK_RED))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Buycosts of a Amethyst Shard = 30$", NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("30", NamedTextColor.DARK_RED))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Buycosts of a Diamond = 150$", NamedTextColor.AQUA))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("150", NamedTextColor.DARK_RED))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Buycosts of a Netherite Ingot = 1.250$", NamedTextColor.RED))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("1.250", NamedTextColor.DARK_RED))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Buycosts of a Spawner = 10.000$", NamedTextColor.DARK_GRAY))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("10.000", NamedTextColor.DARK_RED))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Component.text(" "));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Sellcosts of a Copper Ingot = 5$", NamedTextColor.YELLOW))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("5", NamedTextColor.GREEN))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Sellcosts of a Amethyst Shard = 20$", NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("20", NamedTextColor.GREEN))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Sellcosts of a Diamond = 100$", NamedTextColor.AQUA))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("100", NamedTextColor.GREEN))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Sellcosts of a Netherite Ingot = 1.000$", NamedTextColor.RED))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("1.000", NamedTextColor.GREEN))
                    .append(Component.text("$", NamedTextColor.WHITE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Sellcosts of a Spawner = 7.500$", NamedTextColor.DARK_GRAY))
                    .append(Component.text("= ", NamedTextColor.WHITE))
                    .append(Component.text("7.500", NamedTextColor.GREEN))
                    .append(Component.text("$", NamedTextColor.WHITE)));
        }
    }
}
