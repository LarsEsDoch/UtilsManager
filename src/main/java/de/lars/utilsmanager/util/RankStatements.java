package de.lars.utilsmanager.util;

import de.lars.apimanager.apis.prefixAPI.PrefixAPI;
import de.lars.apimanager.apis.rankAPI.RankAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import java.util.Set;

public class RankStatements {

    public static Component getRank(Player player) {
        Set<TextDecoration> prefixDecoration = PrefixAPI.getApi().getDecoration(player);
        NamedTextColor prefixColor = PrefixAPI.getApi().getColor(player);
        Integer rank = RankAPI.getApi().getRankId(player);

        TextDecoration[] decorations = prefixDecoration.toArray(new TextDecoration[0]);

        Component nameComponent = Component.text(player.getName(), prefixColor, decorations);

        Component prefix = switch (rank) {
            case 2 -> Component.text("Premium ", NamedTextColor.GREEN);
            case 3 -> Component.text("Supreme ", NamedTextColor.AQUA);
            case 4 -> Component.text("Titan ", NamedTextColor.BLUE);
            case 5 -> Component.text("Matrix ", NamedTextColor.GOLD);
            case 6 -> Component.text("Builder ", NamedTextColor.YELLOW);
            case 7 -> Component.text("Developer ", NamedTextColor.DARK_PURPLE);
            case 8 -> Component.text("Team ", NamedTextColor.DARK_BLUE);
            case 9 -> Component.text("Admin ", NamedTextColor.RED);
            case 10 -> Component.text("Owner ", NamedTextColor.DARK_RED);
            default -> Component.text("Player ", NamedTextColor.GRAY);
        };

        return prefix
                .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                .append(nameComponent);
    }

    public static Component getCleanRank(Player player) {
        return switch (RankAPI.getApi().getRankId(player)) {
            case 2 -> Component.text("Premium ", NamedTextColor.GREEN);
            case 3 -> Component.text("Supreme ", NamedTextColor.AQUA);
            case 4 -> Component.text("Titan ", NamedTextColor.BLUE);
            case 5 -> Component.text("Matrix ", NamedTextColor.GOLD);
            case 6 -> Component.text("Builder ", NamedTextColor.YELLOW);
            case 7 -> Component.text("Developer ", NamedTextColor.DARK_PURPLE);
            case 8 -> Component.text("Team ", NamedTextColor.DARK_BLUE);
            case 9 -> Component.text("Admin ", NamedTextColor.RED);
            case 10 -> Component.text("Owner ", NamedTextColor.DARK_RED);
            default -> Component.text("Player ", NamedTextColor.GRAY);
        };
    }

    public static Component getCleanRankLong(Player player) {
        return switch (RankAPI.getApi().getRankId(player)) {
            case 2 -> Component.text("Premium ", NamedTextColor.GREEN).append(Component.text(" | ", NamedTextColor.DARK_GRAY));
            case 3 -> Component.text("Supreme ", NamedTextColor.AQUA).append(Component.text(" | ", NamedTextColor.DARK_GRAY));
            case 4 -> Component.text("Titan ", NamedTextColor.BLUE).append(Component.text(" | ", NamedTextColor.DARK_GRAY));
            case 5 -> Component.text("Matrix ", NamedTextColor.GOLD).append(Component.text(" | ", NamedTextColor.DARK_GRAY));
            case 6 -> Component.text("Builder ", NamedTextColor.YELLOW).append(Component.text(" | ", NamedTextColor.DARK_GRAY));
            case 7 -> Component.text("Developer ", NamedTextColor.DARK_PURPLE).append(Component.text(" | ", NamedTextColor.DARK_GRAY));
            case 8 -> Component.text("Team ", NamedTextColor.DARK_BLUE).append(Component.text(" | ", NamedTextColor.DARK_GRAY));
            case 9 -> Component.text("Admin ", NamedTextColor.RED).append(Component.text(" | ", NamedTextColor.DARK_GRAY));
            case 10 -> Component.text("Owner ", NamedTextColor.DARK_RED).append(Component.text(" | ", NamedTextColor.DARK_GRAY));
            default -> Component.text("Player ", NamedTextColor.GRAY).append(Component.text(" | ", NamedTextColor.DARK_GRAY));
        };
    }

    public static String getUnformattedRank(Player player) {
        Integer rank = RankAPI.getApi().getRankId(player);
        if (rank == 1) {
            return "Player | ";
        } else if (rank == 2) {
            return "Premium | ";
        } else if (rank == 3) {
            return "Supreme | ";
        } else if (rank == 4) {
            return "Titan | ";
        } else if (rank == 5) {
            return "Matrix | ";
        } else if (rank == 6) {
            return "Builder | ";
        } else if (rank == 7) {
            return "Developer | ";
        } else if (rank == 8) {
            return "Team | ";
        } else if (rank == 9) {
            return"Admin | ";
        } else if (rank == 10) {
            return "Owner | ";
        } else {
            return "Player | ";
        }
    }
}
