package de.lars.utilsManager.backpack;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class BackpackCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Component.text("Only player can send Messages.", NamedTextColor.RED));
            return;
        }

        if (!(player.hasPermission("plugin.backpack"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (args.length == 0 || RankAPI.getApi().getRankID(player) < 9) {
            Backpack backpack = Main.getInstance().getBackpackManager().getBackpack(player.getUniqueId());
            player.openInventory(backpack.getInventory());
            return;
        }

        if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
            OfflinePlayer openPlayer = Bukkit.getOfflinePlayer(args[0]);
            Backpack backpack = Main.getInstance().getBackpackManager().getBackpack(openPlayer.getUniqueId());

            player.openInventory(backpack.getInventory());
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast den Rucksack von ", NamedTextColor.DARK_RED))
                        .append(Component.text(Objects.requireNonNull(openPlayer.getName()), NamedTextColor.WHITE))
                        .append(Component.text("geöffnet.", NamedTextColor.DARK_RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You've opened the backpack of ", NamedTextColor.DARK_RED))
                        .append(Component.text(Objects.requireNonNull(openPlayer.getName()), NamedTextColor.WHITE))
                        .append(Component.text(".", NamedTextColor.DARK_RED)));
            }
        } else {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Dieser Spieler besitzt kein Rucksack!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("This player doesn't own a backpack!", NamedTextColor.RED)));
            }
        }
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (RankAPI.getApi().getRankID(player) < 9) return Collections.emptyList();
        if (args.length == 1 || args.length == 0) {
            List<String> names = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                names.add(p.getName());
            }

            return names;
        }
        return Collections.emptyList();
    }
}
