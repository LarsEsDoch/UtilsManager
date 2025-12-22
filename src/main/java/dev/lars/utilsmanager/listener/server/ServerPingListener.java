package dev.lars.utilsmanager.listener.server;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import dev.lars.apimanager.apis.playerIdentityAPI.PlayerIdentityAPI;
import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import dev.lars.utilsmanager.utils.Gradient;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.map.MapFont;
import org.bukkit.map.MinecraftFont;

import java.util.ArrayList;
import java.util.List;

public class ServerPingListener implements Listener {

    private static final MapFont MINECRAFT_FONT = MinecraftFont.Font;
    private static final int MOTD_WIDTH_PIXELS = 154;
    private static final int SPACE_WIDTH = MINECRAFT_FONT.getWidth(" ");

    @EventHandler(priority = EventPriority.HIGH)
    public void onServerPing(PaperServerListPingEvent event) {
        String serverName = ServerSettingsAPI.getApi().getServerName();
        String serverVersion = ServerSettingsAPI.getApi().getServerVersion();

        int totalWidth = 45;
        int nameLength = serverName.length();

        int spacesCount = Math.max(0, (totalWidth - (nameLength + serverVersion.length())) / 2);
        String spaces2 = " ".repeat(spacesCount);

        int length = serverName.length();

        Component serverNameComponent = Component.empty();
        List<String> gradient_colors = ServerSettingsAPI.getApi().getServerNameGradient();
        for (int i = 0; i < length; i++) {
            serverNameComponent = serverNameComponent.append(Gradient.gradient(
                    String.valueOf(serverName.charAt(i)),
                    gradient_colors.getFirst(),
                    gradient_colors.get(1),
                    i,
                    length - 1
            ));
        }

        Component header = Component.text(spaces2)
                .append(Component.text("M ", NamedTextColor.GOLD, TextDecoration.OBFUSCATED))
                .append(serverNameComponent.decorate(TextDecoration.BOLD))
                .append(Component.text(" [", NamedTextColor.WHITE))
                .append(Component.text(serverVersion, NamedTextColor.GOLD))
                .append(Component.text("] ", NamedTextColor.WHITE))
                .append(Component.text(" M", NamedTextColor.GOLD, TextDecoration.OBFUSCATED))
                .append(Component.text("\n"));

        int extraShift = 10;

        if (Bukkit.hasWhitelist()) {
            String info = "Info: Whitelist enabled!";
            Component centered = centerLinesPixelAccurate(wrapText(info, totalWidth), NamedTextColor.GOLD, extraShift);
            event.motd(header.append(centered));
            event.getListedPlayers().removeIf(listedPlayerInfo -> {
                OfflinePlayer offline = Bukkit.getOfflinePlayer(listedPlayerInfo.id());
                return PlayerIdentityAPI.getApi().isVanished(offline);
            });
            long visibleCount = event.getListedPlayers().size();
            event.setNumPlayers((int) visibleCount);
            return;
        }

        if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) {
            String maintenance = "Info: Server is in maintenance!";
            Component centered = centerLinesPixelAccurate(wrapText(maintenance, totalWidth), NamedTextColor.GOLD, extraShift + 2);
            event.motd(header.append(centered));

            event.getListedPlayers().removeIf(listedPlayerInfo -> {
                OfflinePlayer offline = Bukkit.getOfflinePlayer(listedPlayerInfo.id());
                return PlayerIdentityAPI.getApi().isVanished(offline);
            });
            long visibleCount = event.getListedPlayers().size();
            event.setNumPlayers((int) visibleCount);
            return;
        }

        if (Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()) {
            String info = "Info: Server is full!";
            Component centered = centerLinesPixelAccurate(wrapText(info, totalWidth), NamedTextColor.RED, extraShift);
            event.motd(header.append(centered));
            event.getListedPlayers().removeIf(listedPlayerInfo -> {
                OfflinePlayer offline = Bukkit.getOfflinePlayer(listedPlayerInfo.id());
                return PlayerIdentityAPI.getApi().isVanished(offline);
            });
            long visibleCount = event.getListedPlayers().size();
            event.setNumPlayers((int) visibleCount);
            return;
        }

        String info = "Info: Server is online!";
        Component centered = centerLinesPixelAccurate(wrapText(info, totalWidth), NamedTextColor.GREEN, extraShift);
        event.motd(header.append(centered));
        event.getListedPlayers().removeIf(listedPlayerInfo -> {
            OfflinePlayer offline = Bukkit.getOfflinePlayer(listedPlayerInfo.id());
            return PlayerIdentityAPI.getApi().isVanished(offline);
        });
        long visibleCount = event.getListedPlayers().size();
        event.setNumPlayers((int) visibleCount);
    }

    private static List<String> wrapText(String text, int width) {
        List<String> lines = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return lines;
        }

        String[] words = text.split("\\s+");
        StringBuilder current = new StringBuilder();

        for (String w : words) {
            if (current.isEmpty()) {
                current.append(w);
            } else if (current.length() + 1 + w.length() <= width) {
                current.append(' ').append(w);
            } else {
                lines.add(current.toString());
                current = new StringBuilder(w);
            }
        }
        if (!current.isEmpty()) lines.add(current.toString());
        return lines;
    }

    private static int getPixelWidth(String text) {
        String stripped = text.replaceAll("[§&][0-9a-fk-or]", "");
        return MINECRAFT_FONT.getWidth(stripped);
    }

    private static int calculateCenterSpaces(String text) {
        int textWidth = getPixelWidth(text);
        int padding = (MOTD_WIDTH_PIXELS - textWidth) / 2;
        return Math.max(0, padding / SPACE_WIDTH);
    }

    private static Component centerLinesPixelAccurate(List<String> lines, NamedTextColor color, int extraShift) {
        if (lines == null || lines.isEmpty()) {
            return Component.empty();
        }
        Component result = Component.empty();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int spacesNeeded = calculateCenterSpaces(line) + extraShift;
            String pad = " ".repeat(Math.max(0, spacesNeeded));
            result = result.append(Component.text(pad)).append(Component.text(line, color));
            if (i < lines.size() - 1) {
                result = result.append(Component.text("\n"));
            }
        }
        return result;
    }
}