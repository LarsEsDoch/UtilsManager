package de.lars.utilsmanager.commands.teleport;

import de.lars.apiManager.banAPI.BanAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsmanager.util.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

        if (!(player.hasPermission("plugin.spawn"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if (BanAPI.getApi().isCriminal(player) == 5) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        Location loc = new Location(Bukkit.getWorld("world"), -205.5, 78.0, -102.5, -90, 0);
        player.teleport(loc);
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest zum Spawn teleportiert.", NamedTextColor.GREEN)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You have been teleported to the spawn.", NamedTextColor.GREEN)));
        }
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
    }
}



















