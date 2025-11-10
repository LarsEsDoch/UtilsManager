package dev.lars.utilsmanager.utils;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CheckPlayers {
    public static boolean checkPlayer(Player sender, Player player) {
        if (player == null || !player.isOnline()) {
            if (LanguageAPI.getApi().getLanguage(sender) == 2) {
                sender.sendMessage(Component.text("Der Spieler ist nicht online!", NamedTextColor.RED));
            } else {
                sender.sendMessage(Component.text("The Player doesn't exist!", NamedTextColor.RED));
            }
            return true;
        }
        return false;
    }

    public static boolean checkOfflinePlayer(Player sender, OfflinePlayer player) {
        if (player == null) {
            if (LanguageAPI.getApi().getLanguage(sender) == 2) {
                sender.sendMessage(Component.text("Dieser Spieler konnte nicht gefunden werden!", NamedTextColor.RED));
            } else {
                sender.sendMessage(Component.text("That player could not be found!", NamedTextColor.RED));
            }
            return true;
        } else {
            player.getUniqueId();
        }

        if (!player.hasPlayedBefore() && !player.isOnline()) {
            if (LanguageAPI.getApi().getLanguage(sender) == 2) {
                sender.sendMessage(Component.text("Dieser Spieler war noch nie auf diesem Server!", NamedTextColor.RED));
            } else {
                sender.sendMessage(Component.text("That player has never joined before!", NamedTextColor.RED));
            }
            return true;
        }

        return false;
    }
}