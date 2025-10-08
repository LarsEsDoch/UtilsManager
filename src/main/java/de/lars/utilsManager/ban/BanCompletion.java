package de.lars.utilsManager.ban;

import de.lars.apiManager.banAPI.BanAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class BanCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender.hasPermission("plugin.ban"))) {
            return null;
        }
        List<String> players = BanAPI.getApi().getBannedPlayers();
        if (args.length == 1) {

            return players;
        }
        return  null;
    }
}
