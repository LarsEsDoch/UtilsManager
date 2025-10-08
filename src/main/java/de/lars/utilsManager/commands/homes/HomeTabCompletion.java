package de.lars.utilsManager.commands.homes;

import de.lars.apiManager.homeAPI.HomeAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HomeTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {
            List<String> homes = new ArrayList<>();
            for (String string : HomeAPI.getApi().getHomes((Player) sender)) {
                homes.add(string.toLowerCase());
            }
            return homes;
        }
        return null;
    }

}
