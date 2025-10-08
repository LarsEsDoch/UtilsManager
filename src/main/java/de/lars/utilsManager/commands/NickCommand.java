package de.lars.utilsManager.commands;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.RankStatements;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

public class NickCommand implements BasicCommand {

    static TextDecoration type;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player player = (Player) stack.getSender();
        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        if (!(player.hasPermission("plugin.nick"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (args[0] == null) {
            sendUsage(player);
            return;
        }

        String nickname = args[0];

        Scoreboard scoreboard = player.getScoreboard();


        player.setDisplayName(nickname);
        player.setCustomName(nickname);
        player.setCustomNameVisible(true);
        player.setPlayerListName(nickname);
        setPlayerTeam(player);
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), bukkitTask -> {
            Main.getInstance().getTablistManager().setAllPlayerTeams();
        }, 20);
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Dein Nickname ist nun: ", NamedTextColor.WHITE))
                    .append(Component.text(nickname, NamedTextColor.GOLD))
                    .append(Component.text(".", NamedTextColor.WHITE)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("Your nickname is now: ", NamedTextColor.WHITE))
                    .append(Component.text(nickname, NamedTextColor.GOLD))
                    .append(Component.text(".", NamedTextColor.WHITE)));
        }
    }

    public void setPlayerTeam(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        for (Player target : Bukkit.getOnlinePlayers()) {
            Integer rank = 5;
            Integer colorType = RankAPI.getApi().getPrefixType(target);
            NamedTextColor namedTextColor = getNamedTextColor(RankAPI.getApi().getPrefix(target));

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


            Team scoreboardTeam = scoreboard.getTeam(rank+target.getCustomName());
            if (scoreboardTeam == null) {
                scoreboardTeam = scoreboard.registerNewTeam(rank+target.getCustomName());
            }
            scoreboardTeam.prefix(RankStatements.getRank(target));
            scoreboardTeam.color(namedTextColor);
            if (RankAPI.getApi().getStatus(target).equals("00-00")) {
                target.setPlayerListName(RankStatements.getRank(target) + target.getCustomName());
            } else {
                String input = RankAPI.getApi().getStatus(target);
                String[] parts = input.split(",");
                String status = parts[0];
                Integer color = Integer.valueOf(parts[1]);
                target.setPlayerListName(RankStatements.getRank(target) + target.getName() + NamedTextColor.GRAY + " [" + getNamedTextColor(color) + status + NamedTextColor.GRAY + "]");
            }
            scoreboardTeam.addEntry(target.getCustomName());

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
            case 15 -> NamedTextColor.WHITE;
            default -> NamedTextColor.WHITE;
        };
    }

    private void sendUsage(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/nick <Name>");
        } else {
            player.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/nick <name>");
        }
    }
}
