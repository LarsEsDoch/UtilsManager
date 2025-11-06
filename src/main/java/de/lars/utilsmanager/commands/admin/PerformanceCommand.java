package de.lars.utilsmanager.commands.admin;

import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.types.DoubleStatistic;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.DecimalFormat;

public class PerformanceCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        DecimalFormat df = new DecimalFormat("#.##");

        Spark spark = SparkProvider.get();
        DoubleStatistic<StatisticWindow.TicksPerSecond> tpsAPI = spark.tps();
        double tps5s = tpsAPI.poll(StatisticWindow.TicksPerSecond.SECONDS_5);
        double tps10s = tpsAPI.poll(StatisticWindow.TicksPerSecond.SECONDS_10);
        double tps1m = tpsAPI.poll(StatisticWindow.TicksPerSecond.MINUTES_1);
        double tps5m = tpsAPI.poll(StatisticWindow.TicksPerSecond.MINUTES_5);
        double tps15m = tpsAPI.poll(StatisticWindow.TicksPerSecond.MINUTES_15);
        int decimalPlaces = 1;
        double t5S = Math.round(tps5s * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
        double t10S = Math.round(tps10s * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
        double t1M = Math.round(tps1m * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
        double t5M = Math.round(tps5m * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
        double t15M = Math.round(tps15m * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
        String tps5S = "";
        String tps10S = "";
        String tps1M = "";
        String tps5M = "";
        String tps15M = "";

        if (t5S >= 15.0) {
            tps5S = NamedTextColor.RED.toString() + t5S;
        }
        if (t5S >= 17.0) {
            tps5S = NamedTextColor.YELLOW.toString() + t5S;
        }
        if (t5S >= 19.0) {
            tps5S = NamedTextColor.GREEN.toString() + t5S;
        }
        if (t5S >= 20.0) {
            tps5S = NamedTextColor.GREEN.toString() + "*20.0";
        }

        if (t10S >= 15.0) {
            tps10S = NamedTextColor.RED.toString() + t10S;
        }
        if (t10S >= 17.0) {
            tps10S = NamedTextColor.YELLOW.toString() + t10S;
        }
        if (t10S >= 19.0) {
            tps10S = NamedTextColor.GREEN.toString() + t10S;
        }
        if (t10S >= 20.0) {
            tps10S = NamedTextColor.GREEN.toString() + "*20.0";
        }

        if (t1M >= 15.0) {
            tps1M = NamedTextColor.RED.toString() + t1M;
        }
        if (t1M >= 17.0) {
            tps1M = NamedTextColor.YELLOW.toString() + t1M;
        }
        if (t1M >= 19.0) {
            tps1M = NamedTextColor.GREEN.toString() + t1M;
        }
        if (t1M >= 20.0) {
            tps1M = NamedTextColor.GREEN.toString() + "*20.0";
        }

        if (t5M >= 15.0) {
            tps5M = NamedTextColor.RED.toString() + t5M;
        }
        if (t5M >= 17.0) {
            tps5M = NamedTextColor.YELLOW.toString() + t5M;
        }
        if (t5M >= 19.0) {
            tps5M = NamedTextColor.GREEN.toString() + t5M;
        }
        if (t5M >= 20.0) {
            tps5M = NamedTextColor.GREEN.toString() + "*20.0";
        }

        if (t15M >= 15.0) {
            tps15M = NamedTextColor.RED.toString() + t15M;
        }
        if (t15M >= 17.0) {
            tps15M = NamedTextColor.YELLOW.toString() + t15M;
        }
        if (t15M >= 19.0) {
            tps15M = NamedTextColor.GREEN.toString() + t15M;
        }
        if (t15M >= 20.0) {
            tps15M = NamedTextColor.GREEN.toString() + "*20.0";
        }

        DoubleStatistic<StatisticWindow.CpuUsage> cpuAPI = spark.cpuSystem();
        double cpu10s = cpuAPI.poll(StatisticWindow.CpuUsage.SECONDS_10);
        double cpu1m = cpuAPI.poll(StatisticWindow.CpuUsage.MINUTES_1);
        double cpu15m = cpuAPI.poll(StatisticWindow.CpuUsage.MINUTES_15);
        cpu10s = Math.round(cpu10s * Math.pow(10, 2)) / Math.pow(10, 2);
        cpu1m = Math.round(cpu1m * Math.pow(10, 2)) / Math.pow(10, 2);
        cpu15m = Math.round(cpu15m * Math.pow(10, 2)) / Math.pow(10, 2);
        int C10s = (int) (cpu10s * 100);
        int C1m = (int) (cpu1m * 100);
        int C15m = (int) (cpu15m * 100);
        String cpu10S = "";
        String cpu1M = "";
        String cpu15M = "";

        if (cpu10s >= 0) {
            cpu10S = NamedTextColor.GREEN.toString() + C10s + "%";
        }
        if (cpu10s >= 0.65) {
            cpu10S = NamedTextColor.YELLOW.toString() + C10s + "%";
        }
        if (cpu10s >= 0.90) {
            cpu10S = NamedTextColor.RED.toString() + C10s + "%";
        }

        if (cpu1m >= 0) {
            cpu1M = NamedTextColor.GREEN.toString() + C1m + "%";
        }
        if (cpu1m >= 0.65) {
            cpu1M = NamedTextColor.YELLOW.toString() + C1m + "%";
        }
        if (cpu1m >= 0.90) {
            cpu1M = NamedTextColor.RED.toString() + C1m + "%";
        }

        if (cpu15m >= 0) {
            cpu15M = NamedTextColor.GREEN.toString() + C15m + "%";
        }
        if (cpu15m >= 0.65) {
            cpu15M = NamedTextColor.YELLOW.toString() + C15m + "%";
        }
        if (cpu15m >= 0.90) {
            cpu15M = NamedTextColor.RED.toString() + C15m + "%";
        }

        DoubleStatistic<StatisticWindow.CpuUsage> cpuAPI2 = spark.cpuProcess();
        double cpu10s2 = cpuAPI2.poll(StatisticWindow.CpuUsage.SECONDS_10);
        double cpu1m2 = cpuAPI2.poll(StatisticWindow.CpuUsage.MINUTES_1);
        double cpu15m2 = cpuAPI2.poll(StatisticWindow.CpuUsage.MINUTES_15);
        cpu10s2 = Math.round(cpu10s2 * Math.pow(10, 2)) / Math.pow(10, 2);
        cpu1m2 = Math.round(cpu1m2 * Math.pow(10, 2)) / Math.pow(10, 2);
        cpu15m2 = Math.round(cpu15m2 * Math.pow(10, 2)) / Math.pow(10, 2);
        int C10s2 = (int) (cpu10s2 * 100);
        int C1m2 = (int) (cpu1m2 * 100);
        int C15m2 = (int) (cpu15m2 * 100);
        String cpu10S2 = "";
        String cpu1M2 = "";
        String cpu15M2 = "";

        if (cpu10s2 >= 0) {
            cpu10S2 = NamedTextColor.GREEN.toString() + C10s2 + "%";
        }
        if (cpu10s2 >= 0.65) {
            cpu10S2 = NamedTextColor.YELLOW.toString() + C10s2 + "%";
        }
        if (cpu10s2 >= 0.90) {
            cpu10S2 = NamedTextColor.RED.toString() + C10s2 + "%";
        }

        if (cpu1m2 >= 0) {
            cpu1M2 = NamedTextColor.GREEN.toString() + C1m2 + "%";
        }
        if (cpu1m2 >= 0.65) {
            cpu1M2 = NamedTextColor.YELLOW.toString() + C1m2 + "%";
        }
        if (cpu1m2 >= 0.90) {
            cpu1M2 = NamedTextColor.RED.toString() + C1m2 + "%";
        }

        if (cpu15m2 >= 0) {
            cpu15M2 = NamedTextColor.GREEN.toString() + C15m2 + "%";
        }
        if (cpu15m2 >= 0.65) {
            cpu15M2 = NamedTextColor.YELLOW.toString() + C15m2 + "%";
        }
        if (cpu15m2 >= 0.90) {
            cpu15M2 = NamedTextColor.RED.toString() + C15m2 + "%";
        }

        long maxMemory = Runtime.getRuntime().maxMemory();
        long usedMemory = Runtime.getRuntime().totalMemory();
        double memoryUsage = (double) usedMemory / maxMemory;
        memoryUsage = 0.7;
        player.sendMessage("Usage: " + memoryUsage);
        player.sendMessage("Max: " + maxMemory);
        player.sendMessage("Used/Total: " + usedMemory);
        int barSize = 100;
        StringBuilder memoryBar = new StringBuilder(NamedTextColor.DARK_GRAY + "[");
        int memoryBars = (int) (memoryUsage * barSize);
        for (int i = 0; i < barSize; i++) {
            if (i < memoryBars) {
                if(memoryUsage < 60) {
                    memoryBar.append(NamedTextColor.GREEN + "|");
                } if (memoryUsage >= 60) {
                    memoryBar.append(NamedTextColor.YELLOW + "|");
                } if (memoryUsage >= 90) {
                    memoryBar.append(NamedTextColor.RED + "|");
                }
            } else {
                memoryBar.append(NamedTextColor.GRAY + "|");
            }
        }
        memoryBar.append(NamedTextColor.DARK_GRAY + "] ");
        String memoryUsageColor = "";
        if(memoryUsage < 60) {
            memoryUsageColor = NamedTextColor.GREEN + df.format(memoryUsage * 100) + "%";
        } if (memoryUsage >= 60) {
            memoryUsageColor = NamedTextColor.YELLOW + df.format(memoryUsage * 100) + "%";
        } if (memoryUsage >= 90) {
            memoryUsageColor = NamedTextColor.RED + df.format(memoryUsage * 100) + "%";
        }

        File currentDir = new File(System.getProperty("user.dir"));
        String serverPath = currentDir.getAbsolutePath();
        File disk = new File(serverPath);
        long totalSpace = disk.getTotalSpace();
        long freeSpace = disk.getFreeSpace();
        long usedSpace = totalSpace - freeSpace;
        double spaceUsage = (double) usedSpace / totalSpace;
        StringBuilder spaceBar = new StringBuilder(NamedTextColor.DARK_GRAY + "[");
        int spaceBars = (int) (spaceUsage * barSize);
        for (int i = 0; i < barSize; i++) {
            if (i < spaceBars) {
                if(spaceUsage < 80) {
                    spaceBar.append(NamedTextColor.GREEN + "|");
                } if (spaceUsage >= 80) {
                    spaceBar.append(NamedTextColor.YELLOW + "|");
                } if (spaceUsage >= 90) {
                    spaceBar.append(NamedTextColor.RED + "|");
                }
            } else {
                spaceBar.append(NamedTextColor.GRAY + "|");
            }
        }
        spaceBar.append(NamedTextColor.DARK_GRAY + "] ");
        String spaceUsageColor = "";
        if(spaceUsage < 80) {
            spaceUsageColor = NamedTextColor.GREEN + df.format(spaceUsage * 100) + "%";
        } if (spaceUsage >= 80) {
            spaceUsageColor = NamedTextColor.YELLOW + df.format(spaceUsage * 100) + "%";
        } if (spaceUsage >= 90) {
            spaceUsageColor = NamedTextColor.RED + df.format(spaceUsage * 100) + "%";
        }

        String prefix = NamedTextColor.DARK_GRAY + "[" + NamedTextColor.YELLOW + "\u26A1" + NamedTextColor.DARK_GRAY + "] " + NamedTextColor.GRAY;
        player.sendMessage(prefix + NamedTextColor.GRAY + "All performance information:");
        player.sendMessage(prefix);
        player.sendMessage(prefix + NamedTextColor.GOLD + " TPS from last 5s, 10s, 1m, 5m, 15m:");
        player.sendMessage(prefix + " >> " + tps5S + NamedTextColor.GRAY + ", " + tps10S + NamedTextColor.GRAY + ", " + tps1M + NamedTextColor.GRAY + ", " + tps5M + NamedTextColor.GRAY + ", " + tps15M);
        player.sendMessage(prefix);
        player.sendMessage(prefix + NamedTextColor.GOLD + "CPU usage from last 10s, 1m, 15m:");
        player.sendMessage(prefix + " >> " + cpu10S + NamedTextColor.GRAY + ", " + cpu1M + NamedTextColor.GRAY + ", " + cpu15M + NamedTextColor.DARK_GRAY + "  (system)");
        player.sendMessage(prefix + " >> " + cpu10S2 + NamedTextColor.GRAY + ", " + cpu1M2 + NamedTextColor.GRAY + ", " + cpu15M2 + NamedTextColor.DARK_GRAY + "  (process)");
        player.sendMessage(prefix);
        player.sendMessage(prefix + " Meomory usage:");
        player.sendMessage(prefix + " >> " + NamedTextColor.WHITE + formatMemory(usedMemory) + NamedTextColor.GRAY + " / " + NamedTextColor.WHITE + formatMemory(maxMemory) + NamedTextColor.GRAY +
                "   (" + memoryUsageColor + NamedTextColor.GRAY + ")");
        player.sendMessage(prefix + " >> " + memoryBar);
        player.sendMessage(prefix);
        player.sendMessage(prefix + " Disk usage:");
        player.sendMessage(prefix + " >> " + NamedTextColor.WHITE + formatMemory(usedSpace) + NamedTextColor.GRAY + " / " + NamedTextColor.WHITE + formatMemory(totalSpace) +
                "   (" + spaceUsageColor + NamedTextColor.GRAY + ")");
        player.sendMessage(prefix + " >> " + spaceBar);
        player.sendMessage(prefix);
        player.sendMessage(prefix + " Entity Living:");
        player.sendMessage(prefix + " >> " + NamedTextColor.GREEN + (long) Bukkit.getWorld("world").getEntities().size() + " Entities");
        player.sendMessage(prefix);
    }

    private String formatMemory(long bytes) {
        double tb = bytes / (1024.0 * 1024 * 1024 * 1024);
        if (tb >= 1) {
            return new DecimalFormat("#.##").format(tb) + " TB";
        }
        double gb = bytes / (1024.0 * 1024 * 1024);
        if (gb >= 1) {
            return new DecimalFormat("#.##").format(gb) + " GB";
        }
        double mb = bytes / (1024.0 * 1024);
        if (mb >= 1) {
            return new DecimalFormat("#.##").format(mb) + " MB";
        }
        double kb = bytes / 1024.0;
        return new DecimalFormat("#.##").format(kb) + " KB";
    }
}
