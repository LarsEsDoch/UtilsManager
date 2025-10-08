package de.lars.utilsManager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class StatusTabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 2) {
            List<String> rangcommands = new ArrayList<>();
            rangcommands.add("aqua");
            rangcommands.add("black");
            rangcommands.add("blue");
            rangcommands.add("bold");
            rangcommands.add("dark_aqua");
            rangcommands.add("dark_blue");
            rangcommands.add("dark_gray");
            rangcommands.add("dark_green");
            rangcommands.add("dark_purple");
            rangcommands.add("dark_red");
            rangcommands.add("gold");
            rangcommands.add("gray");
            rangcommands.add("green");
            rangcommands.add("italic");
            rangcommands.add("light_purple");
            rangcommands.add("magic");
            rangcommands.add("red");
            rangcommands.add("reset");
            rangcommands.add("strikethrough");
            rangcommands.add("underline");
            rangcommands.add("white");
            rangcommands.add("yellow");

            return rangcommands;
        }

        return  null;
    }
}
