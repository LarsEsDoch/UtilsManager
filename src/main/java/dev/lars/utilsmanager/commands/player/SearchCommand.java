package dev.lars.utilsmanager.commands.player;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.playerIdentityAPI.PlayerIdentityAPI;
import dev.lars.utilsmanager.utils.RankStatements;
import dev.lars.utilsmanager.utils.Statements;
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
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getPrefix().append(Component.text("All players wich are online:")));
            for (Player searchedPlayer: Bukkit.getOnlinePlayers()) {
                if (PlayerIdentityAPI.getApi().isVanished(searchedPlayer)) continue;
                Location loc = searchedPlayer.getLocation();
                stack.getSender().sendMessage(Statements.getPrefix()
                        .append(RankStatements.getRank(searchedPlayer))
                        .append(Component.text(": X: " + loc.getBlockX() + " Y: " + loc.getBlockY() + " Z: " + loc.getBlockZ(), NamedTextColor.GREEN)));
            }
            return;
        }
        if (!player.hasPermission("utilsmanager.search")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Alle Spieler die online sind:")));
            for (Player searchedPlayer: Bukkit.getOnlinePlayers()) {
                if (PlayerIdentityAPI.getApi().isVanished(searchedPlayer)) continue;
                Location loc = searchedPlayer.getLocation();
                player.sendMessage(Statements.getPrefix()
                        .append(RankStatements.getRank(searchedPlayer))
                        .append(Component.text(": X: " + loc.getBlockX() + " Y: " + loc.getBlockY() + " Z: " + loc.getBlockZ(), NamedTextColor.GREEN)));
            }
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("All players wich are online:")));
            for (Player searchedPlayer: Bukkit.getOnlinePlayers()) {
                if (PlayerIdentityAPI.getApi().isVanished(searchedPlayer)) continue;
                Location loc = searchedPlayer.getLocation();
                player.sendMessage(Statements.getPrefix()
                        .append(RankStatements.getRank(searchedPlayer))
                        .append(Component.text(": X: " + loc.getBlockX() + " Y: " + loc.getBlockY() + " Z: " + loc.getBlockZ(), NamedTextColor.GREEN)));
            }
        }
    }
}