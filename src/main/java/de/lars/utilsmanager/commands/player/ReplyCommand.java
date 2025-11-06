package de.lars.utilsmanager.commands.player;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.utils.RankStatements;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReplyCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack source, @NotNull String[] args) {
        if (!(source.getExecutor() instanceof Player player)) {
            source.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

        if (args.length < 1) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Component.text("Verwendung: /r <Nachricht>", NamedTextColor.YELLOW));
            } else {
                player.sendMessage(Component.text("Usage: /r <message>", NamedTextColor.YELLOW));
            }
            return;
        }

        Set<UUID> last = MsgCommand.lastRecipients.get(player.getUniqueId());
        if (last == null || last.isEmpty()) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Component.text("⚠ Du hast niemandem, auf den du antworten kannst!", NamedTextColor.RED));
            } else {
                player.sendMessage(Component.text("⚠ You have no one to reply to!", NamedTextColor.RED));
            }
            return;
        }

        String message = String.join(" ", args);
        Set<Player> targets = last.stream()
                .map(Bukkit::getPlayer)
                .filter(p -> p != null && p.isOnline())
                .collect(Collectors.toSet());

        if (targets.isEmpty()) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Component.text("Deine letzten Empfänger sind nicht mehr online!", NamedTextColor.RED));
            } else {
                player.sendMessage(Component.text("Your last recipients are no longer online!", NamedTextColor.RED));
            }
            return;
        }

        Component targetList = targets.stream()
                .map(RankStatements::getRank)
                .reduce((a, b) -> a.append(Component.text(", ", NamedTextColor.GRAY)).append(b))
                .orElse(Component.empty());

        for (Player target : targets) {
            target.sendMessage(RankStatements.getRank(player)
                    .append(Component.text("> ", NamedTextColor.DARK_GRAY))
                    .append(targetList)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text(message, NamedTextColor.WHITE)));

            MsgCommand.lastRecipients.put(target.getUniqueId(), Set.of(player.getUniqueId()));
        }

        MsgCommand.lastRecipients.put(player.getUniqueId(), targets.stream().map(Player::getUniqueId).collect(Collectors.toSet()));

        player.sendMessage(targetList
                .append(Component.text("> ", NamedTextColor.DARK_GRAY))
                .append(RankStatements.getRank(player))
                .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                .append(Component.text(message, NamedTextColor.WHITE)));
    }
}