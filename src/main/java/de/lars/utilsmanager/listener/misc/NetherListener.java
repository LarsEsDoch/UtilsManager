package de.lars.utilsmanager.listener.misc;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.util.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.Calendar;

public class NetherListener implements Listener {

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        
        if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
            Calendar date = Calendar.getInstance();
            Calendar finaldate = Calendar.getInstance();
            finaldate.set(2025, Calendar.NOVEMBER, 7, 21, 0);
            if (date.before(finaldate)) {
                long millis = finaldate.getTimeInMillis() - date.getTimeInMillis();
                long seconds = millis / 1000;
                Component time = Statements.formatDuration(seconds);
                if (LanguageAPI.getApi().getLanguage(player ) == 2) {
                    player.sendMessage(Component.text("Du kannst nicht in den Nether gehen.", NamedTextColor.RED));
                    player.sendMessage(Component.text("Der Nether wird freigeschaltet in ", NamedTextColor.YELLOW)
                            .append(time));
                } else {
                    player.sendMessage(Component.text("You can't go to the Nether.", NamedTextColor.RED));
                    player.sendMessage(Component.text("The Nether will be unlocked in ", NamedTextColor.YELLOW)
                    .append(time));
                }
                event.setCancelled(true);
            }
        }
        if (event.getTo().getWorld().getEnvironment() == World.Environment.THE_END) {
            Calendar date = Calendar.getInstance();
            Calendar finaldate = Calendar.getInstance();
            finaldate.set(2025, Calendar.NOVEMBER, 14, 21, 0);
            if (date.before(finaldate)) {
                long millis = finaldate.getTimeInMillis() - date.getTimeInMillis();
                long seconds = millis / 1000;
                Component time = Statements.formatDuration(seconds);
                if (LanguageAPI.getApi().getLanguage(player ) == 2) {
                    player.sendMessage(Component.text("Du kannst nicht in das End gehen.", NamedTextColor.RED));
                    player.sendMessage(Component.text("Das End wird freigeschaltet in ", NamedTextColor.YELLOW)
                            .append(time));
                } else {
                    player.sendMessage(Component.text("You can't go to the end.", NamedTextColor.RED));
                    player.sendMessage(Component.text("The End will be unlocked in ", NamedTextColor.YELLOW)
                    .append(time));
                }
                event.setCancelled(true);
            }
        }
    }
}
