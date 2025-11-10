package de.lars.utilsmanager.listener.server;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import de.lars.utilsmanager.utils.Gradient;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ServerPingListener implements Listener {

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
        for (int i = 0; i < length; i++) {
            serverNameComponent = serverNameComponent.append(Gradient.gradient(
                    String.valueOf(serverName.charAt(i)),
                    "#50FB08",
                    "#006EFF",
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
                .append(Component.text("\n "));

        int extraShift = 5;

        if (Bukkit.hasWhitelist()) {
            String info = "Info: Whitelist enabled!";
            Component centered = centerLines(wrapText(info, totalWidth), totalWidth, NamedTextColor.GOLD, extraShift);
            event.motd(header.append(centered));
            return;
        }

        if (ServerSettingsAPI.getApi().isMaintenanceEnabled()) {
            List<String> lines = new ArrayList<>();
            lines.add("Info: Server is in maintenance!");
            String maintenanceReason = ServerSettingsAPI.getApi().getMaintenanceReason() == null ? "" : ServerSettingsAPI.getApi().getMaintenanceReason().trim();
            if (!maintenanceReason.isEmpty()) {
                lines.addAll(wrapText(maintenanceReason, totalWidth));
            }
            Component firstLine = centerLines(List.of(lines.getFirst()), totalWidth, NamedTextColor.GREEN, extraShift);
            List<String> detailLines = lines.size() > 1 ? lines.subList(1, lines.size()) : List.of();
            Component details = centerLines(detailLines, totalWidth, NamedTextColor.RED, extraShift);
            event.motd(header.append(firstLine).append(details));
            return;
        }

        if (Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()) {
            String info = "Info: Server is full!";
            Component centered = centerLines(wrapText(info, totalWidth), totalWidth, NamedTextColor.RED, extraShift);
            event.motd(header.append(centered));
            return;
        }

        String info = "Info: Server is online!";
        Component centered = centerLines(wrapText(info, totalWidth), totalWidth, NamedTextColor.GREEN, extraShift);
        event.motd(header.append(centered));
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

    private static Component centerLines(List<String> lines, int width, net.kyori.adventure.text.format.NamedTextColor color, int extraShift) {
        if (lines == null || lines.isEmpty()) {
            return Component.empty();
        }
        Component result = Component.empty();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int padding = Math.max(0, (width - line.length()) / 2 + extraShift);
            String pad = " ".repeat(padding);
            result = result.append(Component.text(pad)).append(Component.text(line, color));
            if (i < lines.size() - 1) {
                result = result.append(Component.text("\n")); // removed the extra single space here
            }
        }
        return result;
    }
}