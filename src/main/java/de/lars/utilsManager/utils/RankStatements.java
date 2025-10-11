package de.lars.utilsManager.utils;

import de.lars.apiManager.rankAPI.RankAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

public class RankStatements {

    public static Component getRank(Player player) {
        Integer colorType = RankAPI.getApi().getPrefixType(player);
        Integer prefix = RankAPI.getApi().getPrefix(player);
        NamedTextColor namedTextColor = getNamedTextColor(prefix);
        TextDecoration type = null;

        Integer rank = RankAPI.getApi().getRankID(player);
        if (colorType == 0) {
            return switch (rank) {
                case 2 -> Component.text("Premium ", NamedTextColor.GREEN)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor));
                case 3 -> Component.text("Supreme ", NamedTextColor.AQUA)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor));
                case 4 -> Component.text("Titan ", NamedTextColor.BLUE)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor));
                case 5 -> Component.text("Matrix ", NamedTextColor.GOLD)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor));
                case 6 -> Component.text("Builder ", NamedTextColor.YELLOW)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor));
                case 7 -> Component.text("Developer ", NamedTextColor.DARK_PURPLE)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor));
                case 8 -> Component.text("Team ", NamedTextColor.DARK_BLUE)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor));
                case 9 -> Component.text("Admin " , NamedTextColor.RED)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(),namedTextColor));
                case 10 -> Component.text("Owner ", NamedTextColor.DARK_RED)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor));
                default -> Component.text("Player ", NamedTextColor.GRAY)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor));
            };
        } else {
            if (colorType == 1) {
                type = TextDecoration.BOLD;
            }
            if (colorType == 2) {
                type = TextDecoration.ITALIC;
            }
            if (colorType == 3) {
                type = TextDecoration.OBFUSCATED;
            }
            if (colorType == 4) {
                type = TextDecoration.STRIKETHROUGH;
            }
            if (colorType == 5) {
                type = TextDecoration.UNDERLINED;
            }
            return switch (rank) {
                case 2 -> Component.text("Premium ", NamedTextColor.GREEN)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor, type));
                case 3 -> Component.text("Supreme ", NamedTextColor.AQUA)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor, type));
                case 4 -> Component.text("Titan ", NamedTextColor.BLUE)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor, type));
                case 5 -> Component.text("Matrix ", NamedTextColor.GOLD)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor, type));
                case 6 -> Component.text("Builder ", NamedTextColor.YELLOW)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor, type));
                case 7 -> Component.text("Developer ", NamedTextColor.DARK_PURPLE)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor, type));
                case 8 -> Component.text("Team ", NamedTextColor.DARK_BLUE)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor, type));
                case 9 -> Component.text("Admin ", NamedTextColor.RED)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor, type));
                case 10 -> Component.text("Owner ", NamedTextColor.DARK_RED)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor, type));
                default -> Component.text("Player ", NamedTextColor.GRAY)
                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(player.getName(), namedTextColor, type));
            };
        }

    }

    public static Component getCleanRank(Player player) {
        return switch (RankAPI.getApi().getRankID(player)) {
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
        return switch (RankAPI.getApi().getRankID(player)) {
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
        Integer rank = RankAPI.getApi().getRankID(player);
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

    private static NamedTextColor getNamedTextColor(Integer prefixID) {
        return switch (prefixID) {
            case 0 -> NamedTextColor.BLACK;
            case 1 -> NamedTextColor.DARK_BLUE;
            case 2 -> NamedTextColor.DARK_GREEN;
            case 3 -> NamedTextColor.DARK_AQUA;
            case 4 -> NamedTextColor.DARK_RED;
            case 5 -> NamedTextColor.DARK_PURPLE;
            case 6 -> NamedTextColor.GOLD;
            case 7 -> NamedTextColor.GRAY;
            case 8 -> NamedTextColor.DARK_GRAY;
            case 9 -> NamedTextColor.BLUE;
            case 10 -> NamedTextColor.GREEN;
            case 11 -> NamedTextColor.AQUA;
            case 12 -> NamedTextColor.RED;
            case 13 -> NamedTextColor.LIGHT_PURPLE;
            case 14 -> NamedTextColor.YELLOW;
            default -> NamedTextColor.WHITE;
        };
    }
}
