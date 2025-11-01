package de.lars.utilsmanager.config;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MessageManager {

    private final JavaPlugin plugin;
    private final MiniMessage mini = MiniMessage.miniMessage();

    private final Map<Integer, FileConfiguration> languages = new HashMap<>();
    private final int defaultLangId = 1;

    public MessageManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadLanguages();
    }

    private void loadLanguages() {
        loadLang(1, "messages_en.yml");
        loadLang(2, "messages_de.yml");
    }

    private void loadLang(int id, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        languages.put(id, cfg);
    }

    private int getPlayerLang(Player player) {
        try {
            return LanguageAPI.getApi().getLanguage(player); // 1 = EN, 2 = DE
        } catch (Exception e) {
            return defaultLangId;
        }
    }

    public Component get(Player player, String key, Map<String, String> placeholders) {
        int langId = getPlayerLang(player);
        String raw = getRaw(langId, key);
        if (raw == null && langId != defaultLangId)
            raw = getRaw(defaultLangId, key);
        if (raw == null)
            raw = "<red>Missing translation: " + key + "</red>";

        if (raw.contains("{prefix}")) {
            String prefix = getRaw(langId, "prefix");
            if (prefix == null) prefix = getRaw(defaultLangId, "prefix");
            if (prefix != null)
                raw = raw.replace("{prefix}", prefix);
        }

        if (placeholders != null) {
            for (Map.Entry<String, String> e : placeholders.entrySet()) {
                raw = raw.replace("{" + e.getKey() + "}", e.getValue());
            }
        }

        return mini.deserialize(raw);
    }

    public Component get(Player player, String key) {
        return get(player, key, null);
    }

    private String getRaw(int langId, String key) {
        FileConfiguration cfg = languages.get(langId);
        if (cfg == null) return null;
        return cfg.getString(key);
    }
}
