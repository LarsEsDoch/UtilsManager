package dev.lars.utilsmanager.commands.admin;

import com.destroystokyo.paper.profile.PlayerProfile;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.playerIdentityAPI.PlayerIdentityAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NickCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }

        if (!player.hasPermission("utilsmanager.nick")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        String nickname = args[0];

        if (nickname.equalsIgnoreCase("reset")) {

            PlayerIdentityAPI.getApi().resetNickname(player);

            PlayerProfile original = Bukkit.createProfile(
                    player.getUniqueId(),
                    player.getName()
            );

            original.setProperties(player.getPlayerProfile().getProperties());

            player.setPlayerProfile(original);

            player.displayName(Component.text(player.getName()));
            player.playerListName(Component.text(player.getName()));
            player.customName(Component.text(player.getName()));
            player.setCustomNameVisible(false);

            UtilsManager.getInstance().getTablistManager().setAllPlayerTeams();

            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix()
                        .append(Component.text("Du hast deinen Nickname zurückgesetzt.", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix()
                        .append(Component.text("You reset your nickname.", NamedTextColor.WHITE)));
            }
            return;
        }

        PlayerIdentityAPI.getApi().setNickname(player, nickname);

        PlayerProfile newProfile = Bukkit.createProfile(
                player.getUniqueId(),
                nickname
        );

        newProfile.setProperties(player.getPlayerProfile().getProperties());

        player.setPlayerProfile(newProfile);

        player.displayName(Component.text(nickname));
        player.playerListName(Component.text(nickname));
        player.customName(Component.text(nickname));
        player.setCustomNameVisible(false);

        UtilsManager.getInstance().getTablistManager().setAllPlayerTeams();

        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Dein Nickname ist nun: ", NamedTextColor.WHITE))
                    .append(Component.text(nickname, NamedTextColor.GOLD))
                    .append(Component.text(".", NamedTextColor.WHITE)));
        } else {
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Your nickname is now: ", NamedTextColor.WHITE))
                    .append(Component.text(nickname, NamedTextColor.GOLD))
                    .append(Component.text(".", NamedTextColor.WHITE)));
        }
    }

    private void sendUsage(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getUsage(player)
                    .append(Component.text("/nick <Name>/reset", NamedTextColor.BLUE)));
        } else {
            player.sendMessage(Statements.getUsage(player)
                    .append(Component.text("/nick <name>/reset", NamedTextColor.BLUE)));
        }
    }
}