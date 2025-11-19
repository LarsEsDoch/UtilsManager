package dev.lars.utilsmanager.commands.admin;

import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.playerIdentityAPI.PlayerIdentityAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.RankStatements;
import dev.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VanishCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (!player.hasPermission("utilsmanager.vanish")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        if (PlayerIdentityAPI.getApi().isVanished(player)) {
            player.setInvisible(false);
            PlayerIdentityAPI.getApi().setVanished(player, false);

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (LanguageAPI.getApi().getLanguage(p) == 2) {
                    p.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                            .append(Component.text(" hat den Server betreten.", NamedTextColor.WHITE)));
                } else {
                    p.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                            .append(Component.text(" joined the server.", NamedTextColor.WHITE)));
                }
            }

            Bukkit.getScheduler().runTaskAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
                StringBuilder message;

                if (Bukkit.getOnlinePlayers().size() > 1) {
                    message = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " ist dem Server beigetreten.\n\nEs sind aktuell " + Bukkit.getOnlinePlayers().size() + " Spieler online.\n");
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        message.append(RankStatements.getUnformattedRank(onlinePlayer)).append(onlinePlayer.getName()).append("\n");
                    }
                } else {
                    message = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " ist dem Server beigetreten.\n\nEs ist aktuell nur er online.");
                }

                UtilsManager.getInstance().getDiscordBot().sendPlayerMessage(String.valueOf(message));
            });

            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du bist nun für alle wieder ", NamedTextColor.WHITE))
                        .append(Component.text("sichtbar ", NamedTextColor.GRAY)).append(Component.text("!", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You're now ", NamedTextColor.WHITE))
                                .append(Component.text("visible ", NamedTextColor.GRAY)).append(Component.text("again for everyone!", NamedTextColor.WHITE)));
            }
        } else {
            player.setInvisible(true);
            PlayerIdentityAPI.getApi().setVanished(player, true);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (LanguageAPI.getApi().getLanguage(onlinePlayer) == 2) {
                    onlinePlayer.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                            .append(Component.text(" hat den Server verlassen.", NamedTextColor.WHITE)));
                } else {
                    onlinePlayer.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                            .append(Component.text(" left the network.", NamedTextColor.WHITE)));
                }
            }

            Bukkit.getScheduler().runTaskAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
                StringBuilder message = new StringBuilder();

                if (Bukkit.getOnlinePlayers().size() == 1) {
                    message = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " hat den Server verlassen.\n\nEs ist jetzt kein Spieler mehr online.");
                } if(Bukkit.getOnlinePlayers().size() == 2) {
                    message = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " hat den Server verlassen.\n\nEs ist jetzt nur noch 1 Spieler online.\n");
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (onlinePlayer == player) continue;
                        message.append(RankStatements.getUnformattedRank(onlinePlayer)).append(onlinePlayer.getName()).append("\n");
                    }
                } if (Bukkit.getOnlinePlayers().size() > 2) {
                    message = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " hat den Server verlassen.\n\nEs sind jetzt nur noch " + (Bukkit.getOnlinePlayers().size() - 1) + " Spieler online.\n");
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (onlinePlayer == player) continue;
                        message.append(RankStatements.getUnformattedRank(onlinePlayer)).append(onlinePlayer.getName()).append("\n");
                    }
                }

                UtilsManager.getInstance().getDiscordBot().sendPlayerMessage(String.valueOf(message));
            });

            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du bist nun für alle ", NamedTextColor.WHITE))
                                .append(Component.text("unsichtbar ", NamedTextColor.GRAY)).append(Component.text("!", NamedTextColor.WHITE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You're now ", NamedTextColor.WHITE))
                                .append(Component.text("invisible ", NamedTextColor.GRAY)).append(Component.text("for everyone!", NamedTextColor.WHITE)));
            }
        }
        UtilsManager.getInstance().getTablistManager().setAllPlayerTeams();
    }
}
