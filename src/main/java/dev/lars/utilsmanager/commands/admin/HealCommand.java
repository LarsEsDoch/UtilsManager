package dev.lars.utilsmanager.commands.admin;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.utils.CheckPlayers;
import dev.lars.utilsmanager.utils.RankStatements;
import dev.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HealCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String @NotNull [] args) {
        Player sender = (Player) stack.getSender();
        if (!sender.hasPermission("utilsmanager.heal")) {
            sender.sendMessage(Statements.getNotAllowed(sender));
            return;
        }

        if (args.length == 0) {
            sendUsage(sender);
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (CheckPlayers.checkPlayer(sender, target)) return;
        if (LanguageAPI.getApi().getLanguage(sender) == 2) {
            sender.sendMessage(Statements.getPrefix().append(Component.text("Du hast den Spieler ", NamedTextColor.GREEN).append(RankStatements.getRank(target)).append(Component.text(target.getName(), NamedTextColor.GREEN)))
                    .append(Component.text(" geheilt!", NamedTextColor.GREEN)));
        } else {
            sender.sendMessage(Statements.getPrefix().append(Component.text("You healed the player ", NamedTextColor.GREEN).append(RankStatements.getRank(target)).append(Component.text(target.getName(), NamedTextColor.GREEN)))
                    .append(Component.text("!", NamedTextColor.GREEN)));
        }
        if (LanguageAPI.getApi().getLanguage(target) == 2) {
            target.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest geheilt!", NamedTextColor.GREEN)));
        } else {
            target.sendMessage(Statements.getPrefix().append(Component.text("You were healed!", NamedTextColor.GREEN)));
        }
        target.setHealth(20.0);
    }

    @Override
    public @NotNull Collection<String> suggest(final CommandSourceStack commandSourceStack, final String @NotNull [] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (!player.hasPermission("utilsmanager.heal")) return Collections.emptyList();
        if (args.length == 1 || args.length == 0) {
            List<String> names = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                names.add(p.getName());
            }

            return names;
        }
        return Collections.emptyList();
    }

    private void sendUsage(Player sender) {
        if (LanguageAPI.getApi().getLanguage(sender) == 2) {
            sender.sendMessage(Statements.getUsage(sender)
                    .append(Component.text("/heal <Spieler>", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Statements.getUsage(sender)
                    .append(Component.text("/heal <player>")));
        }
    }
}