package dev.lars.utilsmanager.commands.admin;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.utils.Statements;
import dev.lars.utilsmanager.utils.SuggestHelper;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class EntityCommand implements BasicCommand {

    private static final List<String> ENTITY_NAMES =
        Arrays.stream(EntityType.values())
              .map(Enum::name)
              .toList();

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if(!player.hasPermission("utilsmanager.entity")) {
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
    public @NonNull Collection<String> suggest(final @NonNull CommandSourceStack commandSourceStack, final String[] args) {
        Player player = (Player) commandSourceStack.getSender();
        if(!player.hasPermission("utilsmanager.entity")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return Collections.emptyList();
        }
        if (args.length == 0) {
            return ENTITY_NAMES;
        }
        if (args.length == 1) {
            return SuggestHelper.filter(args[0], ENTITY_NAMES.toArray(new String[0]));
        }
        return Collections.emptyList();
    }

    private void sendUsage(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getUsage(player)
                    .append(Component.text("/entity <Entität> <Anzahl>", NamedTextColor.BLUE))
            );
        } else {
            player.sendMessage(Statements.getUsage(player)
                    .append(Component.text("/entity <entity> <amount>", NamedTextColor.BLUE))
            );
        }
    }
}