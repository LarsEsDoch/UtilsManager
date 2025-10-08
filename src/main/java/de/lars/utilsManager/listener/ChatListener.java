package de.lars.utilsManager.listener;

import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.utils.RankStatements;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        Component originalMessage = event.message();

        String legacyMessage = LegacyComponentSerializer.legacySection().serialize(originalMessage);

        String coloredMessage = ChatColor.translateAlternateColorCodes('&', legacyMessage);

        Component formattedMessage = RankStatements.getRank(player)
                .append(Component.text(">: ", NamedTextColor.DARK_GRAY))
                .append(Component.text(coloredMessage, NamedTextColor.WHITE));

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(formattedMessage);
        }
        event.setCancelled(true);
    }

}
