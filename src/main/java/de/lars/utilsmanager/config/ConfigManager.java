package de.lars.utilsmanager.config;

import de.lars.utilsmanager.util.Names;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

public class ConfigManager {

    private final JavaPlugin plugin;
    private FileConfiguration config;
    private FileConfiguration messages;
    private File messagesFile;

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{([^}]+)}");

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        // load default config.yml
        plugin.saveDefaultConfig();
        config = plugin.getConfig();

        // load messages_en.yml (externalizable so server owners can edit)
        messagesFile = new File(plugin.getDataFolder(), Names.ConfigKeys.MESSAGES_FILE);
        if (!messagesFile.exists()) {
            plugin.saveResource(Names.ConfigKeys.MESSAGES_FILE, false);
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void reload() {
        load();
    }

    // Generic getters for config.yml
    public String getString(String path) {
        return config.getString(path);
    }

    public int getInt(String path, int def) {
        return config.getInt(path, def);
    }

    public boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }

    // Messages getters (with replacements)
    public String getMessage(String key) {
        return getMessage(key, (Map<String, String>) null);
    }

    public String getMessage(String key, Map<String, String> replacements) {
        String raw = messages.getString(key);
        if (raw == null) {
            // fallback to prefix or key so missing keys are visible
            raw = messages.getString(Names.MessageKeys.PREFIX, "&8[&cMissing&8] &r") + " &cMissing message: " + key;
        }

        // replace placeholders if any
        if (replacements != null && !replacements.isEmpty()) {
            for (Map.Entry<String, String> e : replacements.entrySet()) {
                raw = raw.replace("{" + e.getKey() + "}", e.getValue() == null ? "" : e.getValue());
            }
        }

        // also replace any {prefix} from messages_en.yml
        if (raw.contains("{prefix}")) {
            String prefix = messages.getString(Names.MessageKeys.PREFIX, "");
            raw = raw.replace("{prefix}", prefix);
        }

        return colorize(raw);
    }

    /** Convenience varargs replacement: key, value, key, value, ... */
    public String getMessage(String key, String... replacements) {
        if (replacements == null || replacements.length == 0) {
            return getMessage(key, (Map<String, String>) null);
        }
        // build map
        java.util.Map<String, String> map = new java.util.HashMap<>();
        for (int i = 0; i + 1 < replacements.length; i += 2) {
            String k = replacements[i];
            String v = replacements[i + 1];
            map.put(k, v);
        }
        return getMessage(key, map);
    }

    private String colorize(String input) {
        if (input == null) return null;
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public void saveMessages() {
        try {
            if (messages instanceof YamlConfiguration) {
                ((YamlConfiguration) messages).save(messagesFile);
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save messages_en.yml: " + e.getMessage());
        }
    }
}
