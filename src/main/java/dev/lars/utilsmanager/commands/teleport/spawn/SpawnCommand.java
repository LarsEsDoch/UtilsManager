package dev.lars.utilsmanager.commands.teleport.spawn;

import dev.lars.apimanager.apis.courtAPI.CourtAPI;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import dev.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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

        if (!(player.hasPermission("utilsmanager.feature.spawn"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if (CourtAPI.getApi().getStatus(player) == 5) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        Location loc = ServerSettingsAPI.getApi().getSpawnLocation();
        if (loc == null) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Es konnte keine Spawn-Location gefunden werden!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("No Spawn-Location could be found!", NamedTextColor.RED)));
            }
            return;
        }

        player.teleport(loc);
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest zum Spawn teleportiert.", NamedTextColor.GREEN)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You have been teleported to the spawn.", NamedTextColor.GREEN)));
        }
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
    }
}