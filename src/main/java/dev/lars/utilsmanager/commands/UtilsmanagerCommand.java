package dev.lars.utilsmanager.commands;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.Statements;
import dev.lars.utilsmanager.utils.SuggestHelper;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public record UtilsmanagerCommand(UtilsManager plugin) implements BasicCommand {

    @Override
    public void execute(CommandSourceStack stack, String @NonNull [] args) {
        var sender = stack.getSender();

        boolean allowed = sender.isOp() || sender.hasPermission("utilsmanager.admin") || sender instanceof org.bukkit.command.ConsoleCommandSender;
        if (!allowed) {
            sender.sendMessage(Statements.getPrefix().append(Component.text("You aren't allowed to execute this command!", NamedTextColor.RED)));
            return;
        }

        if (args.length == 0) {
            sendUsage(sender);
            return;
        }

        String sub = args[0];
        if (sub.equalsIgnoreCase("reload") || sub.equalsIgnoreCase("rl")) {
            handleReload(sender);
            return;
        } else if (sub.equalsIgnoreCase("version") || sub.equalsIgnoreCase("v")) {
            handleVersion(sender);
            return;
        } else if (sub.equalsIgnoreCase("status") || sub.equalsIgnoreCase("s")) {
            handleStatus(sender);
            return;
        }
        sender.sendMessage(Statements.getPrefix().append(Component.text("Unknown command!", NamedTextColor.RED)));
        sendUsage(sender);
    }

    private void handleReload(CommandSender sender) {
        Bukkit.getScheduler().cancelTasks(plugin);
        plugin.getDiscordBot().disable();

        plugin.reloadConfig();

        plugin.initializeManagers();
        plugin.initializeDiscordBot();
        plugin.registerGameFeatures();
        plugin.startBackgroundTasks();
        sender.sendMessage(Statements.getPrefix().append(Component.text("UtilsManager successfully reloaded", NamedTextColor.GREEN)));
        for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
            Component kickMessage;
            if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                kickMessage = Component.text("Server Features wurden neu geladen!", NamedTextColor.GOLD);
            } else {
                kickMessage = Component.text("Server features got reloaded!", NamedTextColor.GOLD);
            }
            onlinePlayer.kick(kickMessage);
        }
        Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("Server features got reloaded!", NamedTextColor.GOLD)));
    }

    private void handleVersion(CommandSender sender) {
        sender.sendMessage(Statements.getPrefix().append(Component.text("=== " + plugin.getName() + " Version ===", NamedTextColor.AQUA)));
            sender.sendMessage(Statements.getPrefix().append(Component.text("Version ", NamedTextColor.GRAY))
                .append(Component.text("v" + plugin.getVersion(), NamedTextColor.GOLD)));
            sender.sendMessage(Statements.getPrefix().append(Component.text("Api Version ", NamedTextColor.GRAY))
                .append(Component.text(plugin.getApiVersion(), NamedTextColor.GOLD)));
            sender.sendMessage(Statements.getPrefix().append(Component.text("Developer ", NamedTextColor.GRAY))
                .append(Component.text(String.join(", ", plugin.getDevelopers()), NamedTextColor.GOLD)));
            sender.sendMessage(Statements.getPrefix().append(Component.text("Website ", NamedTextColor.GRAY))
                .append(Component.text(plugin.getWebsite(), NamedTextColor.GOLD)));
            sender.sendMessage(Statements.getPrefix().append(Component.text("Command ", NamedTextColor.GRAY))
                .append(Component.text("/" + plugin.getName() + " /um", NamedTextColor.GOLD)));
    }

    private void handleStatus(CommandSender sender) {
        sender.sendMessage(Statements.getPrefix()
            .append(Component.text("=== " + plugin.getName() + " Status ===", NamedTextColor.AQUA)));

        // discord websocket api
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(Statements.getPrefix().append(Component.text("=== " + plugin.getName() + " Commands ===", NamedTextColor.AQUA)));
        sender.sendMessage(Statements.getPrefix().append(Component.text("/utilsmanager reload", NamedTextColor.GOLD))
            .append(Component.text(" - Reload configuration", NamedTextColor.GRAY)));
        sender.sendMessage(Statements.getPrefix().append(Component.text("/utilsmanager status", NamedTextColor.GOLD))
            .append(Component.text(" - Shows api status", NamedTextColor.GRAY)));
        sender.sendMessage(Statements.getPrefix().append(Component.text("/utilsmanager version", NamedTextColor.GOLD))
            .append(Component.text(" - Shows plugin version and development info", NamedTextColor.GRAY)));
    }

    @Override
    public @NotNull Collection<String> suggest(final @NotNull CommandSourceStack commandSourceStack, final String[] args) {
        if (args.length <= 1) {
            return SuggestHelper.filter(args[0], "reload", "rl", "version", "v", "status", "s");
        }
        return Collections.emptyList();
    }
}