package de.lars.utilsManager.utils;

import de.lars.apiManager.languageAPI.LanguageAPI;
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
    
}
