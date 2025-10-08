package de.lars.utilsManager.listener;

import de.lars.apiManager.languageAPI.LanguageAPI;
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
            finaldate.set(2025, Calendar.APRIL, 13, 0, 0);
            if (date.before(finaldate)) {
                Long dateDiffernce = finaldate.getTimeInMillis() - date.getTimeInMillis();
                long days = dateDiffernce / (1000 * 60 * 60 * 24);
                long hours = (dateDiffernce / (1000 * 60 * 60)) % 24;
                long minutes = (dateDiffernce / (1000 * 60)) % 60;
                String time = String.format("%d days, %02d:%02d hours", days, hours, minutes);
                if (LanguageAPI.getApi().getLanguage(player ) == 2) {
                    player.sendMessage(Component.text("Du kannst nicht in den Nether gehen.", NamedTextColor.RED));
                    player.sendMessage(Component.text("Der Nether wird freigeschaltet in ", NamedTextColor.YELLOW)
                            .append(Component.text(time, NamedTextColor.AQUA)));
                } else {
                    player.sendMessage(Component.text("You can't go to the Nether.", NamedTextColor.RED));
                    player.sendMessage(Component.text("The Nether will be unlocked in ", NamedTextColor.YELLOW)
                    .append(Component.text(time, NamedTextColor.AQUA)));
                }
                event.setCancelled(true);
            }
            
        }
    }
}
