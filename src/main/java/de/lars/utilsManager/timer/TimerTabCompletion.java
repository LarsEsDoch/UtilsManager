package de.lars.utilsManager.timer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TimerTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> timercommands = new ArrayList<>();
            timercommands.add("resume");
            timercommands.add("stop");
            timercommands.add("time");
            timercommands.add("reset");
            timercommands.add("off");
            timercommands.add("on");
            timercommands.add("timer");
            timercommands.add("stopwatch");
            timercommands.add("public");

            return timercommands;
        }

        return  null;
    }
}
