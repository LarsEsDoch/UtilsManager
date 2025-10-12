package de.lars.utilsmanager.features.backpack;

import de.lars.apiManager.backpackAPI.BackpackAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsmanager.util.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BackpackConfigurationCommand implements BasicCommand {

    private Player player;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (!(player.hasPermission("plugin.backpack.config"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        int slots = Integer.parseInt(args[0]);

        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Die Backpack Slots sind nun ", NamedTextColor.WHITE))
                    .append(Component.text(slots, NamedTextColor.GREEN)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("The backpackslots are now ", NamedTextColor.WHITE))
                    .append(Component.text(slots, NamedTextColor.GREEN)));
        }
        BackpackAPI.getApi().setSlots(player, slots);
    }

    private void sendUsage(CommandSender sender) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(Component.text("Verwendung").color(NamedTextColor.GRAY)
                            .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                            .append(Component.text("/setBpSlots ").color(NamedTextColor.BLUE))
                            .append(Component.text("<Slots des Backpacks> ").color(NamedTextColor.BLUE))
                            .append(Component.text("! Die Slots müssen ein vielfaches von 9 sein (9 - 54) !", NamedTextColor.DARK_RED, TextDecoration.BOLD)));
        } else {
            sender.sendMessage(Component.text("Use").color(NamedTextColor.GRAY)
                    .append(Component.text(": ").color(NamedTextColor.DARK_GRAY))
                    .append(Component.text("/setBpSlots ").color(NamedTextColor.BLUE))
                    .append(Component.text("<Slots of the Backpack> ").color(NamedTextColor.BLUE))
                    .append(Component.text("! The slots must be a multiple of 9 (9 - 54 !", NamedTextColor.DARK_RED, TextDecoration.BOLD)));
        }
    }

}
