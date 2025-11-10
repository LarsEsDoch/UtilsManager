package de.lars.utilsmanager.commands.admin;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.utils.RankStatements;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClearChatCommand implements BasicCommand {


    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (!(player.hasPermission("plugin.clearchat"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        for (int i = 0; i < 1000; i++) {
            for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(" ");
            }
        }
        LocalDateTime jetzt = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(Statements.getPrefix().append(Component.text("                    ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                    .append(Component.text("[ ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("ChatManager", NamedTextColor.GREEN))
                    .append(Component.text(" ]", NamedTextColor.DARK_GRAY))
                    .append(Component.text("                    ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH)));

            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                onlinePlayer.sendMessage(Statements.getPrefix().append(Component.text("Der Chat wurde von ", NamedTextColor.WHITE))
                        .append(RankStatements.getRank(player))
                        .append(Component.text(" gelöscht!", NamedTextColor.WHITE)));
            } else {
                onlinePlayer.sendMessage(Statements.getPrefix().append(Component.text("The Chat was cleared by ", NamedTextColor.WHITE))
                        .append(RankStatements.getRank(player))
                        .append(Component.text(" !", NamedTextColor.WHITE)));
            }
            onlinePlayer.sendMessage(Statements.getPrefix().append(Component.text("                         ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH))
                    .append(Component.text("[ ", NamedTextColor.DARK_GRAY))
                    .append(Component.text(jetzt.format(format), NamedTextColor.GREEN))
                    .append(Component.text(" ]", NamedTextColor.DARK_GRAY))
                    .append(Component.text("                         ", NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH)));
        }
    }
}
