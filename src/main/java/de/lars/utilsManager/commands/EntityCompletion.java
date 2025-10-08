package de.lars.utilsManager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class EntityCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1 ||args.length == 2) {
            List<String> entityList = new ArrayList<>();
            for (EntityType entity : EntityType.values()) {
                entityList.add(entity.name().toLowerCase());
            }
            return entityList;
        }
        return null;
    }
}
