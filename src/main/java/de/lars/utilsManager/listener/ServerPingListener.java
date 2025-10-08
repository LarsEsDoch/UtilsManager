package de.lars.utilsManager.listener;

/*
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;

 */
import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import de.lars.apiManager.dataAPI.DataAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.awt.Color;
import java.util.UUID;

public class ServerPingListener /*extends PacketAdapter */implements Listener {

    private static final UUID ZERO_UUID = new UUID(0, 0);

    /*public ServerPingListener(final Main plugin, final Main base) {
        super(base, ListenerPriority.HIGHEST, PacketType.Status.Server.SERVER_INFO);
        this.plugin = plugin;
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }*/

    @EventHandler(priority = EventPriority.HIGH)
    public void onServerPing(PaperServerListPingEvent event) {

        Component motd = Component.text("          ")
                .append(Component.text("M", NamedTextColor.GOLD, TextDecoration.OBFUSCATED))
                .append(Component.text()
                        .append(gradient(" N", "#50FB08", "#006EFF", 0, 17))
                        .append(gradient("i", "#50FB08", "#006EFF", 1, 17))
                        .append(gradient("g", "#50FB08", "#006EFF", 2, 17))
                        .append(gradient("g", "#50FB08", "#006EFF", 3, 17))
                        .append(gradient("a", "#50FB08", "#006EFF", 4, 17))
                        .append(gradient("s", "#50FB08", "#006EFF", 5, 17))
                        .append(gradient(" ", "#50FB08", "#006EFF", 6, 17))
                        .append(gradient("i", "#50FB08", "#006EFF", 7, 17))
                        .append(gradient("n", "#50FB08", "#006EFF", 8, 17))
                        .append(gradient(" ", "#50FB08", "#006EFF", 9, 17))
                        .append(gradient("M", "#50FB08", "#006EFF", 10, 17))
                        .append(gradient("i", "#50FB08", "#006EFF", 11, 17))
                        .append(gradient("n", "#50FB08", "#006EFF", 12, 17))
                        .append(gradient("e", "#50FB08", "#006EFF", 13, 17))
                        .append(gradient("c", "#50FB08", "#006EFF", 14, 17))
                        .append(gradient("r", "#50FB08", "#006EFF", 15, 17))
                        .append(gradient("a", "#50FB08", "#006EFF", 16, 17))
                        .append(gradient("f", "#50FB08", "#006EFF", 17, 17))
                        .append(gradient("t", "#50FB08", "#006EFF", 18, 17))
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
        String spaces = DataAPI.getApi().getMaintenanceReason().length() > 80 ? " " : " ".repeat((60 - DataAPI.getApi().getMaintenanceReason().length()) / 2);
        if (DataAPI.getApi().isMaintenanceActive()) {
            event.motd(motd.append(Component.text("             Info: ", NamedTextColor.GREEN)).append(Component.text("Server is in maintenance!", NamedTextColor.RED))
                    .append(Component.text("\n " + spaces + DataAPI.getApi().getMaintenanceReason(), NamedTextColor.RED)));
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


    /*
    @Override
    public void onPacketSending(final PacketEvent event) {
        final WrappedServerPing ping = event.getPacket().getServerPings().read(0);


        if (2 == 3+5) {
            ping.setVersionProtocol(1);
            ping.setVersionName(" W ");
        }

        final List<WrappedGameProfile> players = new ArrayList<>();
        players.add(new WrappedGameProfile(ZERO_UUID, "Whats up?"));
        players.add(new WrappedGameProfile(ZERO_UUID,  "Join us now!"));
        ping.setPlayers(players);
    }*/
}
