package de.lars.utilsmanager.listener.server;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import de.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.awt.*;
import java.util.UUID;

public class ServerPingListener implements Listener {

    private static final UUID ZERO_UUID = new UUID(0, 0);

    @EventHandler(priority = EventPriority.HIGH)
    public void onServerPing(PaperServerListPingEvent event) {

        Component motd = Component.text("          ")
                .append(Component.text("M", NamedTextColor.GOLD, TextDecoration.OBFUSCATED))
                .append(Component.text()
                    .append(gradient("A", "#50FB08", "#006EFF", 0, 7))
                    .append(gradient(" ", "#50FB08", "#006EFF", 1, 7))
                    .append(gradient("S", "#50FB08", "#006EFF", 2, 7))
                    .append(gradient("e", "#50FB08", "#006EFF", 3, 7))
                    .append(gradient("r", "#50FB08", "#006EFF", 4, 7))
                    .append(gradient("v", "#50FB08", "#006EFF", 5, 7))
                    .append(gradient("e", "#50FB08", "#006EFF", 6, 7))
                    .append(gradient("r", "#50FB08", "#006EFF", 7, 7))
                    .build()
                    .decorate(TextDecoration.BOLD))
                .append(Component.text(" [", NamedTextColor.WHITE))
                .append(Component.text("1.8-1.21", NamedTextColor.GOLD))
                .append(Component.text("] ", NamedTextColor.WHITE))
                .append(Component.text(" M", NamedTextColor.GOLD, TextDecoration.OBFUSCATED))
                .append(Component.text("\n "));
        if (Bukkit.hasWhitelist()) {
            event.motd(motd.append(Component.text("                  Info: ", NamedTextColor.GREEN)).append(Component.text("Whitelist enabled!", NamedTextColor.GOLD)));
            return;
        }
        String spaces = ServerSettingsAPI.getApi().getMaintenanceReason().length() > 80 ? " " : " ".repeat((60 - ServerSettingsAPI.getApi().getMaintenanceReason().length()) / 2);
        if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) {
            event.motd(motd.append(Component.text("             Info: ", NamedTextColor.GREEN)).append(Component.text("Server is in maintenance!", NamedTextColor.RED))
                    .append(Component.text("\n " + spaces + ServerSettingsAPI.getApi().getMaintenanceReason(), NamedTextColor.RED)));
            return;
        }
        if (Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()) {
            event.motd(motd.append(Component.text("                  Info: ", NamedTextColor.GREEN)).append(Component.text( "Server is full!", NamedTextColor.RED)));
            return;
        }
        event.motd(motd.append(Component.text("                  Info: ", NamedTextColor.GREEN)).append(Component.text("Server is online!", NamedTextColor.GREEN)));
    }

    private Component gradient(String text, String startColor, String endColor, int index, int total) {
        Color color = blend(Color.decode(startColor), Color.decode(endColor), (float) index / total);
        return Component.text(text, TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
    }

    private Color blend(Color color1, Color color2, float ratio) {
        ratio = Math.max(0, Math.min(1, ratio));

        int red = (int) (color1.getRed() * (1 - ratio) + color2.getRed() * ratio);
        int green = (int) (color1.getGreen() * (1 - ratio) + color2.getGreen() * ratio);
        int blue = (int) (color1.getBlue() * (1 - ratio) + color2.getBlue() * ratio);

        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));

        return new Color(red, green, blue);
    }
}
