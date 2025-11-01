package de.lars.utilsmanager.commands.admin;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.util.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class EntityCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if(!player.hasPermission("plugin.entity")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if(args.length < 2) {
            sendUsage(player);
            return;
        }
        for (String arg : args) {
            try {
                Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sendUsage(player);
                return;
            }
        }
        Integer amount = Integer.parseInt(args[1]);
        try {
            EntityType entityType = EntityType.valueOf(args[0].toUpperCase());
            for (int i = 0; i < amount; i++) {
                player.getWorld().spawnEntity(player.getLocation(), entityType);
            }
            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Erfolgreich ", NamedTextColor.WHITE))
                        .append(Component.text(amount, NamedTextColor.GREEN))
                        .append(Component.text(" " + entityType, NamedTextColor.BLUE))
                        .append(Component.text(" gespawnt.", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("Successful spawned ", NamedTextColor.WHITE))
                        .append(Component.text(amount, NamedTextColor.GREEN))
                        .append(Component.text(" " + entityType, NamedTextColor.BLUE))
                        .append(Component.text(".", NamedTextColor.WHITE)));
            }
        } catch (IllegalArgumentException e) {
            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Ungültiger Mob-Typ: " + args[0], NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("Invalid Entity-Typ: " + args[0], NamedTextColor.WHITE)));
            }
        }
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        if (args.length == 0 || args.length == 1) {
            Collection<String> entityList = new ArrayList<>();
            for (EntityType entity : EntityType.values()) {
                entityList.add(entity.name().toLowerCase());
            }
            return entityList;
        }
        return Collections.emptyList();
    }

    private void sendUsage(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/entity <Entität> <Anzahl>");
        } else {
            player.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/entity <entity> <amount>");
        }
    }
}
