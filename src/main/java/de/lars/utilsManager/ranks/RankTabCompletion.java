package de.lars.utilsManager.ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class RankTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2) {
            List<String> rankCommands = new ArrayList<>();
            rankCommands.add("player");
            rankCommands.add("premium");
            rankCommands.add("supreme");
            rankCommands.add("titan");
            rankCommands.add("matrix");
            rankCommands.add("builder");
            rankCommands.add("developer");
            rankCommands.add("team");
            rankCommands.add("admin");
            rankCommands.add("owner");

            return rankCommands;
        }

        return  null;
    }
}
