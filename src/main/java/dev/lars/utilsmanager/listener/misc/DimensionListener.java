package dev.lars.utilsmanager.listener.misc;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.progressionAPI.ProgressionAPI;
import dev.lars.utilsmanager.utils.FormatNumbers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;

public class DimensionListener implements Listener {

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        
        if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
            Instant now = Instant.now();
            Instant netherUnlockAt = ProgressionAPI.getApi().getNetherUnlockAt();
            if (now.isBefore(netherUnlockAt)) {
                long seconds = Duration.between(now, netherUnlockAt).getSeconds();
                Component time = FormatNumbers.formatDuration(seconds);
                if (LanguageAPI.getApi().getLanguage(player ) == 2) {
                    player.sendMessage(Component.text("Du kannst nicht in den Nether gehen!", NamedTextColor.RED));
                    player.sendMessage(Component.text("Der Nether wird freigeschaltet in ", NamedTextColor.YELLOW)
                            .append(time));
                } else {
                    player.sendMessage(Component.text("You can't go to the Nether!", NamedTextColor.RED));
                    player.sendMessage(Component.text("The Nether will be unlocked in ", NamedTextColor.YELLOW)
                    .append(time));
                }
                event.setCancelled(true);
            }
        }
        if (event.getTo().getWorld().getEnvironment() == World.Environment.THE_END) {
            Instant now = Instant.now();
            Instant endUnlockAt = ProgressionAPI.getApi().getEndUnlockAt();
            if (now.isBefore(endUnlockAt)) {
                long seconds = Duration.between(now, endUnlockAt).getSeconds();
                Component time = FormatNumbers.formatDuration(seconds);
                if (LanguageAPI.getApi().getLanguage(player ) == 2) {
                    player.sendMessage(Component.text("Du kannst nicht in das End gehen!", NamedTextColor.RED));
                    player.sendMessage(Component.text("Das End wird freigeschaltet in ", NamedTextColor.YELLOW)
                            .append(time));
                } else {
                    player.sendMessage(Component.text("You can't go to the end!", NamedTextColor.RED));
                    player.sendMessage(Component.text("The End will be unlocked in ", NamedTextColor.YELLOW)
                    .append(time));
                }
                event.setCancelled(true);
            }
        }
    }
}