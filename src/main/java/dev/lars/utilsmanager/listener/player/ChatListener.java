package dev.lars.utilsmanager.listener.player;

import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.RankStatements;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        Component originalMessage = event.message();

        String plain = PlainTextComponentSerializer.plainText().serialize(originalMessage);

        Component withColors = LegacyComponentSerializer.legacyAmpersand().deserialize(plain);

        Component formattedMessage = RankStatements.getRank(player)
                .append(Component.text(">: ", NamedTextColor.DARK_GRAY))
                .append(withColors.colorIfAbsent(NamedTextColor.WHITE));

        Bukkit.getScheduler().runTask(UtilsManager.getInstance(), () -> {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(formattedMessage);
            }
            Bukkit.getConsoleSender().sendMessage(formattedMessage);
        });

        event.setCancelled(true);
    }
}