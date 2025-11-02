package de.lars.utilsmanager.commands.admin;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.util.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class InvseeCommand implements BasicCommand {

    Player player;
    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player sendplayer = (Player) stack.getSender();
        if (!sendplayer.hasPermission("plugin.inv")) {
            sendplayer.sendMessage(Statements.getNotAllowed(sendplayer));
            return;
        }

        if (args.length == 0) {
            sendUsage(sendplayer);
            return;
        }

        player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                sendplayer.sendMessage(Component.text("Der Spieler existiert nicht!", NamedTextColor.RED));
            } else {
                sendplayer.sendMessage(Component.text("The Player doesn't exist!", NamedTextColor.RED));
            }
            return;
        }

        Inventory inv = player.getInventory();

        sendplayer.openInventory(inv);
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (!player.hasPermission("plugin.inv")) return Collections.emptyList();
        if (args.length == 1 || args.length == 0) {
            List<String> names = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                names.add(p.getName());
            }

            return names;
        }
        return Collections.emptyList();
    }

    private void sendUsage(Player sendplayer) {
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sendplayer.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/invsee <Spieler>");
        } else {
            sendplayer.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/invsee <player>");
        }
    }
}
