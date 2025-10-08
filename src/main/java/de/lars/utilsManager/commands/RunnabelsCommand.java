package de.lars.utilsManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RunnabelsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int threadCount = Thread.getAllStackTraces().keySet().size();
        Bukkit.getLogger().info("Es laufen " + threadCount + " Threads auf dem Server.");
        return false;
    }
}
