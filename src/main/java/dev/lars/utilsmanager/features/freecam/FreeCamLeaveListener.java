package dev.lars.utilsmanager.features.freecam;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class FreeCamLeaveListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SPECTATOR) return;
        if (player.getTargetEntity(6) != null) return;
        FreeCamManager freeCamManager = UtilsManager.getInstance().getFreeCamManager();
        if (freeCamManager.exitFreeCam(player)) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du bist nun zurück im Überlebens Modus.", NamedTextColor.GREEN)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You're now back in Survival mode.", NamedTextColor.GREEN)));
            }
            event.setCancelled(true);
        }
    }
}