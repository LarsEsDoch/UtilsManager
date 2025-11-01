package de.lars.utilsmanager.util;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class Statements {

    public static Component getPrefix() {
        String symbol = "☯";
        return Component.text("[", NamedTextColor.DARK_GRAY)
                .append(Component.text("UtilsManager", NamedTextColor.GOLD))
                .append(Component.text("]", NamedTextColor.DARK_GRAY))
                .append(Component.text(" ", NamedTextColor.GRAY));
    }

    public static Component getNotAllowed(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            return Component.text("Du bist nicht berechtigt um das auszuführen!", NamedTextColor.RED);
        } else {
            return Component.text("You aren´t allowed to do that!", NamedTextColor.RED);
        }
    }

    public static Component getPlayerNotFound(Player player, String name) {
        if (LanguageAPI.getApi().getLanguage(player) == 2)
            return Component.text("Spieler " + name + " nicht gefunden.", NamedTextColor.RED);
        else {
            return Component.text("Player " + name + " not found.", NamedTextColor.RED);
        }
    }

    public static Component getOnlyPlayers() {
        return Component.text("Only player can send Messages.", NamedTextColor.RED);
    }

    public static Component formatDuration(long totalSeconds) {
        if (totalSeconds < 0) totalSeconds = 0;

        int days = Math.toIntExact(totalSeconds / 86400);
        int hours = Math.toIntExact((totalSeconds / 3600) % 24);
        int minutes = Math.toIntExact((totalSeconds / 60) % 60);
        int seconds = Math.toIntExact(totalSeconds % 60);

        String formatted;
        if (days > 0) {
            formatted = String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, seconds);
        } else if (hours > 0) {
            formatted = String.format("%02dh %02dm %02ds", hours, minutes, seconds);
        } else {
            formatted = String.format("%02dm %02ds", minutes, seconds);
        }

        return Component.text(formatted, NamedTextColor.GOLD);
    }
}
