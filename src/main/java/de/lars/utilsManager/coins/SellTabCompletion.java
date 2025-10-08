package de.lars.utilsManager.coins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SellTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> timercommands = new ArrayList<>();
            timercommands.add("copper");
            timercommands.add("amethyst");
            timercommands.add("diamond");
            timercommands.add("netherite");
            timercommands.add("spawner");

            return timercommands;
        }

        return  null;
    }
}
