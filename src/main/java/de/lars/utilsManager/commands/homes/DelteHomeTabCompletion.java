package de.lars.utilsManager.commands.homes;

import de.lars.apiManager.homeAPI.HomeAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DelteHomeTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            List<String> homes = new ArrayList<>();
            for (String string : HomeAPI.getApi().getOwnHomes(player)) {
                homes.add(string);
            }
            return homes;
        }
        return null;
    }
}
