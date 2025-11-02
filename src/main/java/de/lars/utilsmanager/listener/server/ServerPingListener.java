package de.lars.utilsmanager.listener.server;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import de.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import de.lars.utilsmanager.util.Gradient;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ServerPingListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onServerPing(PaperServerListPingEvent event) {
        String serverName = ServerSettingsAPI.getApi().getServerName();
        int totalWidth = 25;
        int nameLength = serverName.length();

        int spacesCount = Math.max(0, totalWidth - nameLength);
        String spaces2 = " ".repeat(spacesCount);

        int length = serverName.length();

        Component serverNameComponent = Component.empty();
        for (int i = 0; i < length; i++) {
            serverNameComponent = serverNameComponent.append(Gradient.gradient(
                String.valueOf(serverName.charAt(i)),
                "#50FB08",
                "#006EFF",
                i,
                length - 1
            ));
        }

        Component pingMessage = Component.text(spaces2)
                .append(Component.text("M ", NamedTextColor.GOLD, TextDecoration.OBFUSCATED))
                .append(serverNameComponent.decorate(TextDecoration.BOLD))
                .append(Component.text(" [", NamedTextColor.WHITE))
                .append(Component.text(ServerSettingsAPI.getApi().getServerVersion(), NamedTextColor.GOLD))
                .append(Component.text("] ", NamedTextColor.WHITE))
                .append(Component.text(" M", NamedTextColor.GOLD, TextDecoration.OBFUSCATED))
                .append(Component.text("\n "));
        if (Bukkit.hasWhitelist()) {
            event.motd(pingMessage.append(Component.text("                  Info: ", NamedTextColor.GREEN)).append(Component.text("Whitelist enabled!", NamedTextColor.GOLD)));
            return;
        }
        String spaces = ServerSettingsAPI.getApi().getMaintenanceReason().length() > 80 ? " " : " ".repeat((60 - ServerSettingsAPI.getApi().getMaintenanceReason().length()) / 2);
        if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) {
            event.motd(pingMessage.append(Component.text("             Info: ", NamedTextColor.GREEN)).append(Component.text("Server is in maintenance!", NamedTextColor.RED))
                    .append(Component.text("\n " + spaces + ServerSettingsAPI.getApi().getMaintenanceReason(), NamedTextColor.RED)));
            return;
        }
        if (Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()) {
            event.motd(pingMessage.append(Component.text("                  Info: ", NamedTextColor.GREEN)).append(Component.text( "Server is full!", NamedTextColor.RED)));
            return;
        }
        event.motd(pingMessage.append(Component.text("                 Info: ", NamedTextColor.GREEN)).append(Component.text("Server is online!", NamedTextColor.GREEN)));
    }
}