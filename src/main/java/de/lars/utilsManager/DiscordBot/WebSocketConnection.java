package de.lars.utilsManager.DiscordBot;

import de.lars.utilsManager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WebSocketConnection extends WebSocketClient {

    public static WebSocketConnection c;

    public static String botToken;

    public static Long heartbeatInterval;

    public WebSocketConnection(URI serverURI) {
        super(serverURI);
    }

    public static void main(String token, String[] args) throws URISyntaxException {
         c = new WebSocketConnection(new URI(
                "wss://gateway.discord.gg/?v=10&encoding=json"));
         c.connect();

        if (heartbeatInterval == null) {
            heartbeatInterval = 41250L;
        }

        botToken = token;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (c.isOpen()) {
                    c.send("{\n" +
                            "\t\"op\": 1,\n" +
                            "\t\"d\": 251\n" +
                            "}");
                } else {
                    c.reconnect();
                }
            }
        }, heartbeatInterval, heartbeatInterval);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        send("{\n" +
                "  \"op\": 2,\n" +
                "  \"d\": {\n" +
                "    \"token\": \"" + botToken + "\",\n" +
                "    \"intents\": 513,\n" +
                "    \"properties\": {\n" +
                "      \"os\": \"linux\",\n" +
                "      \"browser\": \"de.lars.utilsManager\",\n" +
                "      \"device\": \"de.lars.utilsManager\"\n" +
                "    }\n" +
                "  }\n" +
                "}");

        send("{\n" +
                "  \"op\": 3,\n" +
                "  \"d\": {\n" +
                "    \"since\": 91879201,\n" +
                "    \"activities\": [{\n" +
                "      \"name\": \"Minecraft Survival\",\n" +
                "      \"type\": 0\n" +
                "    }],\n" +
                "    \"status\": \"online\",\n" +
                "    \"afk\": false\n" +
                "  }\n" +
                "}");
        Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text().append(Component.text("Discord Gateway connected", NamedTextColor.GREEN))));
    }

    @Override
    public void onMessage(String message) {
        if (message.contains("\"op\":11") || message.contains("\"op\":0")) return;
        Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("Received Discord Message: ", NamedTextColor.YELLOW))
                .append(Component.text(message, NamedTextColor.BLUE)));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Bukkit.getConsoleSender().sendMessage(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                        + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public WebSocketConnection getWebSocket() {
        return c;
    }
}
