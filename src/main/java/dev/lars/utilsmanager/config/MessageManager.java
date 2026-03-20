package dev.lars.utilsmanager.config;

import dev.lars.apimanager.apis.languageAPI.Language;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
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
    private final Language defaultLanguage = Language.ENGLISH;

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

    private Language getPlayerLang(Player player) {
        try {
            return LanguageAPI.getApi().getLanguage(player);
        } catch (Exception e) {
            return defaultLanguage;
        }
    }

    public Component get(Player player, String key, Map<String, String> placeholders) {
        Language language = getPlayerLang(player);
        String raw = getRaw(language, key);
        if (raw == null && language != defaultLanguage)
            raw = getRaw(defaultLanguage, key);
        if (raw == null)
            raw = "<red>Missing translation: " + key + "</red>";

        if (raw.contains("{prefix}")) {
            String prefix = getRaw(language, "prefix");
            if (prefix == null) prefix = getRaw(defaultLanguage, "prefix");
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

    private String getRaw(Language language, String key) {
        FileConfiguration cfg = languages.get(language.getId());
        if (cfg == null) return null;
        return cfg.getString(key);
    }
}
