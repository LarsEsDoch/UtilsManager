package dev.lars.utilsmanager.commands.admin;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NickCommand implements BasicCommand {

    static TextDecoration type;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

        if (!player.hasPermission("plugin.nick")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        String nickname = args[0];
        Component nickComponent = Component.text(nickname, NamedTextColor.GOLD);

        player.displayName(nickComponent);
        player.customName(nickComponent);
        player.playerListName(nickComponent);
        player.setCustomNameVisible(true);

        UtilsManager.getInstance().getTablistManager().setAllPlayerTeams();

        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Dein Nickname ist nun: ", NamedTextColor.WHITE))
                    .append(nickComponent)
                    .append(Component.text(".", NamedTextColor.WHITE)));
        } else {
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Your nickname is now: ", NamedTextColor.WHITE))
                    .append(nickComponent)
                    .append(Component.text(".", NamedTextColor.WHITE)));
        }
    }

    private void sendUsage(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Component.text("Verwendung: ", NamedTextColor.GRAY)
                    .append(Component.text("/nick <Name>", NamedTextColor.BLUE)));
        } else {
            player.sendMessage(Component.text("Use: ", NamedTextColor.GRAY)
                    .append(Component.text("/nick <name>", NamedTextColor.BLUE)));
        }
    }
}
