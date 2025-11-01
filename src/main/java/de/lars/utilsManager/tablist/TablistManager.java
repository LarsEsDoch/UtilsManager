package de.lars.utilsmanager.tablist;

import de.lars.apimanager.apis.prefixAPI.PrefixAPI;
import de.lars.apimanager.apis.rankAPI.RankAPI;
import de.lars.apimanager.apis.statusAPI.StatusAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.util.RankStatements;
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
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            if (RankAPI.getApi().getRankId(player) > 8) {

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
            Integer rank = RankAPI.getApi().getRankId(target);
            NamedTextColor namedTextColor = PrefixAPI.getApi().getColor(target);

            Team scoreboardTeam = scoreboard.getTeam(rank+target.getName());
            if (scoreboardTeam == null) {
                scoreboardTeam = scoreboard.registerNewTeam(rank+target.getName());
            }
            scoreboardTeam.displayName(RankStatements.getRank(target));
            scoreboardTeam.prefix(RankStatements.getCleanRankLong(target));
            scoreboardTeam.color(namedTextColor);
            if (StatusAPI.getApi().getStatus(target).isBlank()) {
                target.playerListName(RankStatements.getRank(target));
            } else {
                target.playerListName(RankStatements.getRank(target).append(Component.text(" [", NamedTextColor.GRAY))
                        .append(Component.text(StatusAPI.getApi().getStatus(target), StatusAPI.getApi().getColor(target)))
                        .append(Component.text("]", NamedTextColor.GRAY)));
            }
            scoreboardTeam.addEntry(target.getName());

        }
    }
}
