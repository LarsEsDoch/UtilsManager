package de.lars.utilsManager.tablist;

import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.RankStatements;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.types.DoubleStatistic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistManager{

    int cases = 1;
    static TextDecoration type;

    public void setTabList(Player player) {
        player.sendPlayerListHeader(
                Component.text()
                        .append(Component.text("          ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                        .append(Component.text("[ ", NamedTextColor.DARK_GRAY))
                        .append(Component.text("A Server", NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text("]", NamedTextColor.DARK_GRAY))
                        .append(Component.text("          ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                        .append(Component.newline())
                        .append(Component.text(Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(), NamedTextColor.GREEN))
                        .build()
        );
        Spark spark = SparkProvider.get();
        DoubleStatistic<StatisticWindow.TicksPerSecond> tpsInstance = spark.tps();
        DoubleStatistic<StatisticWindow.CpuUsage> cpuUsage = spark.cpuSystem();
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), bukkitTask -> {
            if (RankAPI.getApi().getRankID(player) > 8) {

                cases = cases + 1;

                if(cases >= 3) {
                    cases = 0;
                }
                switch (cases) {
                    case 0:
                        double tps = tpsInstance.poll(StatisticWindow.TicksPerSecond.SECONDS_5);
                        double roundedValue = Math.round(tps * Math.pow(10, 1)) / Math.pow(10, 1);
                        if (roundedValue >= 20.0) {
                            player.sendPlayerListFooter(Component.text()
                                    .append(Component.text("Informations: \n", NamedTextColor.LIGHT_PURPLE))
                                    .append(Component.text("Current Tps: \n", NamedTextColor.WHITE))
                                    .append(Component.text("*20.0", NamedTextColor.GREEN)));
                            return;
                        }
                        if (roundedValue >= 19.0) {
                            player.sendPlayerListFooter(Component.text()
                                    .append(Component.text("Informations: \n", NamedTextColor.LIGHT_PURPLE))
                                    .append(Component.text("Current Tps: \n", NamedTextColor.WHITE))
                                    .append(Component.text(roundedValue, NamedTextColor.GREEN)));
                            return;
                        }
                        if (roundedValue >= 17.0) {
                            player.sendPlayerListFooter(Component.text()
                                    .append(Component.text("Informations: \n", NamedTextColor.LIGHT_PURPLE))
                                    .append(Component.text("Current Tps: \n", NamedTextColor.WHITE))
                                    .append(Component.text(roundedValue, NamedTextColor.YELLOW)));
                            return;
                        }
                        if (roundedValue >= 15.0) {
                            player.sendPlayerListFooter(Component.text()
                                    .append(Component.text("Informations: \n", NamedTextColor.LIGHT_PURPLE))
                                    .append(Component.text("Current Tps: \n", NamedTextColor.WHITE))
                                    .append(Component.text(roundedValue, NamedTextColor.RED)));
                            return;
                        }
                        break;
                    case 1:
                        int totalPing = 0;
                        int playerCount = Bukkit.getOnlinePlayers().size();
                        if (playerCount == 0) {
                            playerCount = 1;
                        }
                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                            totalPing += player1.getPing();
                        }
                        totalPing = totalPing/playerCount;

                        player.sendPlayerListFooter(Component.text()
                                .append(Component.text("Informations: \n", NamedTextColor.LIGHT_PURPLE))
                                .append(Component.text("Average Ping: \n", NamedTextColor.WHITE))
                                .append(Component.text(totalPing + "ms", NamedTextColor.AQUA)));
                        break;
                    case 2:
                        double usagelastMin = cpuUsage.poll(StatisticWindow.CpuUsage.SECONDS_10);
                        double roundedUsage = Math.round(usagelastMin * Math.pow(10, 2)) / Math.pow(10, 2);
                        int percent = (int) (roundedUsage * 100);

                        String percentString = percent + "%";
                        player.sendPlayerListFooter(Component.text()
                                .append(Component.text("Informations: \n", NamedTextColor.LIGHT_PURPLE))
                                .append(Component.text("Cpu usage: \n", NamedTextColor.WHITE))
                                .append(Component.text(percentString, NamedTextColor.BLUE)));
                        break;
                }
            } else {
                player.sendPlayerListFooter(Component.text("Have fun!", NamedTextColor.BLUE));
                bukkitTask.cancel();
            }
        }, 40, 40);
    }

    public void setAllPlayerTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }

    public void setPlayerTeams(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        for (Player target : Bukkit.getOnlinePlayers()) {
            Integer rank = RankAPI.getApi().getRankID(target);
            NamedTextColor namedTextColor = getNamedTextColor(RankAPI.getApi().getPrefix(target));

            Team scoreboardTeam = scoreboard.getTeam(rank+target.getName());
            if (scoreboardTeam == null) {
                scoreboardTeam = scoreboard.registerNewTeam(rank+target.getName());
            }
            scoreboardTeam.displayName(RankStatements.getRank(target));
            scoreboardTeam.prefix(RankStatements.getCleanRankLong(target));
            scoreboardTeam.color(namedTextColor);
            if (RankAPI.getApi().getStatus(target).isEmpty()) {
                target.playerListName(RankStatements.getRank(target));
            } else {
                String input = RankAPI.getApi().getStatus(target);
                String[] parts = input.split(",");
                String status = parts[0];
                Integer color = Integer.valueOf(parts[1]);
                target.playerListName(RankStatements.getRank(target).append(Component.text(" [", NamedTextColor.GRAY))
                        .append(Component.text(status, getNamedTextColor(color)))
                        .append(Component.text("]", NamedTextColor.GRAY)));
            }
            scoreboardTeam.addEntry(target.getName());

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
