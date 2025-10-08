package de.lars.utilsManager.chunks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChunkTabCompletion implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> chunkArgs = new ArrayList<>();
            chunkArgs.add("claim");
            chunkArgs.add("sell");
            chunkArgs.add("friend");
            chunkArgs.add("list");
            chunkArgs.add("info");
            chunkArgs.add("free");
            chunkArgs.add("flag");
            return chunkArgs;
        }
        if (args.length == 2 && args[0].equals("friend")) {
            List<String> chunkArgs = new ArrayList<>();
            chunkArgs.add("add");
            chunkArgs.add("remove");
            chunkArgs.add("list");
            chunkArgs.add("all");
            return chunkArgs;
        }
        if (args.length == 3 && args[0].equals("friend") && args[1].equals("all")) {
            List<String> chunkArgs = new ArrayList<>();
            chunkArgs.add("add");
            chunkArgs.add("remove");
            return chunkArgs;
        }
        return null;
    }

}
