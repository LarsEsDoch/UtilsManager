package de.lars.utilsManager.commands;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.utils.RankStatements;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SearchCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player player = (Player) stack.getSender();
        if (!player.hasPermission("plugin.search")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Alle Spieler die online sind:")));
            for (Player SearchedPlayer: Bukkit.getOnlinePlayers()) {
                Location loc = SearchedPlayer.getLocation();
                player.sendMessage(Statements.getPrefix()
                        .append(RankStatements.getRank(SearchedPlayer))
                        .append(Component.text(": X: " + loc.getBlockX() + " Z: " + loc.getBlockZ() + " Y: " + loc.getBlockZ(), NamedTextColor.GREEN)));
            }
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("All players wich are online:")));
            for (Player SearchedPlayer: Bukkit.getOnlinePlayers()) {
                Location loc = SearchedPlayer.getLocation();
                player.sendMessage(Statements.getPrefix()
                        .append(RankStatements.getRank(SearchedPlayer))
                        .append(Component.text(": X: " + loc.getBlockX() + " Z: " + loc.getBlockZ() + " Y: " + loc.getBlockZ(), NamedTextColor.GREEN)));
            }
        }
    }
}
