package de.lars.utilsmanager.commands.economy;

import de.lars.apiManager.coinAPI.CoinAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsmanager.util.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AddCoinsCommand implements BasicCommand {

    private int addcoins;
    private Player player;
    private OfflinePlayer offlinePlayer;

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        if (args.length == 1) {
            sendUsage(player);
            return;
        }

        if (!(player.hasPermission("plugin.addcoins"))) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }

        if (!(args[0] instanceof String)) {
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

        addcoins = Integer.parseInt(args[1]);
        if (addcoins <= 0) {
            sendUsage(player);
            return;
        }
        offlinePlayer = Bukkit.getPlayer(args[0]);
        if (!CoinAPI.getApi().doesUserExist((Player) offlinePlayer)) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(NamedTextColor.RED + "Der Spieler existiert nicht!");
            } else {
                player.sendMessage(NamedTextColor.RED + "The Player dosent exist!");
            }
            return;
        }
        CoinAPI.getApi().addCoins((Player) offlinePlayer, addcoins);
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatierteZahl = formatter.format(addcoins);
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.BLUE))
                    .append(Component.text(offlinePlayer.getName(), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text(" "))
                    .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                    .append(Component.text("$ gegeben.", NamedTextColor.BLUE)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You have add ", NamedTextColor.BLUE))
                    .append(Component.text(offlinePlayer.getName(), NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text(" "))
                    .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                    .append(Component.text("$.", NamedTextColor.BLUE)));
        }
        if (offlinePlayer.isOnline()) {
            Player player1 = (Player) offlinePlayer;
            if (LanguageAPI.getApi().getLanguage((Player) player) == 2) {
                player1.sendMessage(Statements.getPrefix().append(Component.text("Dir wurde ", NamedTextColor.BLUE))
                        .append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                        .append(Component.text("$ auf dein Konto hinzugefügt.", NamedTextColor.BLUE)));
            } else {
                player1.sendMessage(Statements.getPrefix().append(Component.text(formatierteZahl, NamedTextColor.GOLD))
                        .append(Component.text("$ has been added to your account.", NamedTextColor.BLUE)));
            }
        }
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (!player.hasPermission("plugin.sudo")) return Collections.emptyList();
        if (args.length == 1 || args.length == 0) {
            List<String> names = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                names.add(p.getName());
            }

            return names;
        }
        return Collections.emptyList();
    }

    private void sendUsage(Player player) {
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/addcoins <Spieler> <Coins>", NamedTextColor.BLUE)));
        } else {
            player.sendMessage(Component.text("Use", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/addcoins <Player> <Coins>", NamedTextColor.BLUE)));
        }
    }
}
