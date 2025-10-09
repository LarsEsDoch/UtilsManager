package de.lars.utilsManager.DiscordBot;

import de.lars.apiManager.dataAPI.DataAPI;
import de.lars.utilsManager.utils.RankStatements;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.time.Duration;
import java.time.LocalDateTime;

public class DiscordBot {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final String botToken;
    private final String serverStatusChannelID;
    private final String playerStatusChannelID;
    private final String punishmentsChannelID;

    public DiscordBot(String token, String applicationID, String serverChannelID, String playerChannelID, String punishmentsChannelId) {
        startTime = LocalDateTime.now();
        botToken = token;
        serverStatusChannelID = serverChannelID;
        playerStatusChannelID = playerChannelID;
        punishmentsChannelID = punishmentsChannelId;

        try {
            URL url = new URL("https://discord.com/api/v10/applications/" + applicationID + "/commands");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Authorization", "Bot " + botToken);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = "{\n" +
                    "    \"name\": \"blep\",\n" +
                    "    \"type\": 1,\n" +
                    "    \"description\": \"Send a random adorable animal photo\",\n" +
                    "    \"options\": [\n" +
                    "        {\n" +
                    "            \"name\": \"animal\",\n" +
                    "            \"description\": \"The type of animal\",\n" +
                    "            \"type\": 3,\n" +
                    "            \"required\": True,\n" +
                    "            \"choices\": [\n" +
                    "                {\n" +
                    "                    \"name\": \"Dog\",\n" +
                    "                    \"value\": \"animal_dog\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"name\": \"Cat\",\n" +
                    "                    \"value\": \"animal_cat\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"name\": \"Penguin\",\n" +
                    "                    \"value\": \"animal_penguin\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"name\": \"only_smol\",\n" +
                    "            \"description\": \"Whether to show only baby animals\",\n" +
                    "            \"type\": 5,\n" +
                    "            \"required\": False\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String newStatus = "Online on A Server";
        updateBotStatus(newStatus);
    }

    public void sendOnMessage() {
        String messageContent = "Der Server ist online!";

        try {
            URL url = new URL("https://discord.com/api/v9/channels/" + serverStatusChannelID + "/messages");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Authorization", "Bot " + botToken);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = "{\n" +
                    "    \"mobile_network_type\": \"unknown\",\n" +
                    "    \"content\": \"" + messageContent + "\",\n" +
                    "    \"tts\": false,\n" +
                    "    \"flags\": 0\n" +
                    "}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendOffMessage() {
        endTime = LocalDateTime.now();

        String messageContent = "Der Server ist nun offline!\\nEr war für " + Duration.between(startTime, endTime).toMinutes() + " Minuten aktiv!";

        try {
            URL url = new URL("https://discord.com/api/v9/channels/" + serverStatusChannelID + "/messages");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Authorization", "Bot " + botToken);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = "{\n" +
                    "    \"mobile_network_type\": \"unknown\",\n" +
                    "    \"content\": \"" + messageContent +"\",\n" +
                    "    \"tts\": false,\n" +
                    "    \"flags\": 0\n" +
                    "}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPlayerOnMessage(Player player) {
        if (DataAPI.getApi().isMaintenanceActive()) return;
        StringBuilder messageContent;

        if (Bukkit.getOnlinePlayers().size() > 1) {
            messageContent = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " ist dem Server beigetreten.\\n\\nEs sind aktuell " + Bukkit.getOnlinePlayers().size() + " Spieler online.\\n");
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                messageContent.append(RankStatements.getUnformattedRank(onlinePlayer)).append(onlinePlayer.getName()).append("\\n");
            }
        } else {
            messageContent = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " ist dem Server beigetreten.\\n\\nEs ist aktuell nur er online.");
        }
        try {
            URL url = new URL("https://discord.com/api/v9/channels/" + playerStatusChannelID + "/messages");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Authorization", "Bot " + botToken);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = "{\n" +
                    "    \"mobile_network_type\": \"unknown\",\n" +
                    "    \"content\": \"" + messageContent + "\",\n" +
                    "    \"tts\": false,\n" +
                    "    \"flags\": 0\n" +
                    "}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL("https://discord.com/api/v9/channels/" + playerStatusChannelID + "/messages/bulk-delete");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Authorization", "Bot " + botToken);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = "{\n" +
                    "    \"messages\": \"12\",\n" +
                    "}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPlayerOffMessage(Player player) {
        if(DataAPI.getApi().isMaintenanceActive()) return;
        StringBuilder messageContent = new StringBuilder();
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            messageContent = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " hat den Server verlassen.\\n\\nEs ist jetzt kein Spieler mehr online.");
        } if(Bukkit.getOnlinePlayers().size() == 1) {
            messageContent = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " hat den Server verlassen.\\n\\nEs ist jetzt nur noch 1 Spieler online.\\n");
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                messageContent.append(RankStatements.getUnformattedRank(onlinePlayer)).append(onlinePlayer.getName()).append("\\n");
            }
        } if (Bukkit.getOnlinePlayers().size() > 1) {
            messageContent = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " hat den Server verlassen.\\n\\nEs sind jetzt nur noch " + (Bukkit.getOnlinePlayers().size()) + " Spieler online.\\n");
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                messageContent.append(RankStatements.getUnformattedRank(onlinePlayer)).append(onlinePlayer.getName()).append("\\n");
            }
        }
        try {
            URL url = new URL("https://discord.com/api/v9/channels/" + playerStatusChannelID + "/messages");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Authorization", "Bot " + botToken);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = "{\n" +
                    "    \"mobile_network_type\": \"unknown\",\n" +
                    "    \"content\": \"" + messageContent + "\",\n" +
                    "    \"tts\": false,\n" +
                    "    \"flags\": 0\n" +
                    "}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendBanMessage(Player tragetPlayer, Integer time, String reason) {
        try {
            URL url = new URL("https://discord.com/api/v9/channels/" + punishmentsChannelID + "/messages");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Authorization", "Bot " + botToken);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = "{\n" +
                    "    \"mobile_network_type\": \"unknown\",\n" +
                    "    \"content\": \"" + "Der Spieler " + RankStatements.getUnformattedRank(tragetPlayer) + tragetPlayer.getName() + " wurde bestraft für " + NamedTextColor.BLUE + time + NamedTextColor.YELLOW + " Tage und dem Grund " + NamedTextColor.GREEN + reason + NamedTextColor.YELLOW + "!" + "\",\n" +
                    "    \"tts\": false,\n" +
                    "    \"flags\": 0\n" +
                    "}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendKickMessage(Player tragetPlayer, Integer time, String reason) {
        String timeString;
        if (time == 0) {
            timeString = "den ";
        } else {
            int seconds = time % 60;
            int minutes = (time / 60) % 60;
            int hours = (time / 3600);
            String formatedTime = String.format("%02d Stunden %02d Minuten %02d Sekunden", hours, minutes, seconds);
            timeString = formatedTime + " und dem ";
        }
        try {
            URL url = new URL("https://discord.com/api/v9/channels/" + punishmentsChannelID + "/messages");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Authorization", "Bot " + botToken);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = "{\n" +
                    "    \"mobile_network_type\": \"unknown\",\n" +
                    "    \"content\": \"" + "Der Spieler " + RankStatements.getUnformattedRank(tragetPlayer) + tragetPlayer.getName() + " wurde gekickt für " + timeString + "Grund " + reason + "!" + "\",\n" +
                    "    \"tts\": false,\n" +
                    "    \"flags\": 0\n" +
                    "}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <StatusUpdate> void updateBotStatus(String status) {
        HttpClient client = HttpClient.newHttpClient();
        String[] string = new String[1];
        try {
            WebSocketConnection.main(botToken, string);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        /*
        String payload3 = "{\n" +
                "  \"op\": 6,\n" +
                "  \"d\": {\n" +
                "    \"token\": \"" + botToken + "\",\n" +
                "    \"session_id\": \"session_id_i_stored\",\n" +
                "    \"seq\": 1337\n" +
                "  }\n" +
                "}";
        webSocket.sendText(payload3, true);
        webSocket.request(1);

         */
    }
}
