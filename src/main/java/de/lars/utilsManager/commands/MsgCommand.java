package de.lars.utilsManager.commands;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.utilsManager.utils.RankStatements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MsgCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String @NotNull [] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Component.text("Only player can send Messages.", NamedTextColor.RED));
            return;
        }

        if (args.length < 2) {
            player.sendMessage(Component.text("", NamedTextColor.YELLOW));
            return;
        }

        String[] targetNames = args[0].split(",");
        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        Set<Player> targets = new HashSet<>();

        for (String name : targetNames) {
            Player target = Bukkit.getPlayerExact(name);
            if (target != null && target.isOnline()) {
                targets.add(target);
            } else {
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Component.text("Spieler " + name + " wurde nicht gefunden.", NamedTextColor.RED));
                } else {
                    player.sendMessage(Component.text("Player " + name + " was not found.", NamedTextColor.RED));
                }
            }
        }

        if (targets.isEmpty()) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Component.text("Keiner der angegebenen Spieler ist online!", NamedTextColor.RED));
                } else {
                    player.sendMessage(Component.text("None of the specified players are online!", NamedTextColor.RED));
                }
            return;
        }

        Component targetList = targets.stream()
        .map(RankStatements::getRank)
        .reduce((a, b) -> a.append(Component.text(", ", NamedTextColor.GRAY)).append(b))
        .orElse(Component.empty()).color(NamedTextColor.WHITE);


        for (Player target : targets) {
            target.sendMessage(RankStatements.getRank(player)
                .append(Component.text("> ", NamedTextColor.DARK_GRAY))
                .append(targetList)
                .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                .append(Component.text(message, NamedTextColor.WHITE)));
        }

        player.sendMessage(targetList
                .append(Component.text("> ", NamedTextColor.DARK_GRAY))
                .append(RankStatements.getRank(player))
                .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                .append(Component.text(message, NamedTextColor.WHITE)));
    }

    private void sendUsage(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(Component.text("Verwendung", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/msg <Player(,Player2,Player3,...) <message>", NamedTextColor.BLUE)));
        } else {
            sender.sendMessage(Component.text("Use", NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("/language german/english", NamedTextColor.BLUE)));
        }
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        List<String> suggest = new ArrayList<>();
        if (args.length == 0 || args.length == 1) {
            String input = args[0];
            String[] parts = input.split(",");
            String lastPart = parts[parts.length - 1].toLowerCase(Locale.ROOT);

            Set<String> chosen = Arrays.stream(parts)
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> !chosen.contains(name.toLowerCase()))
                    .filter(name -> name.toLowerCase().startsWith(lastPart))
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .forEach(suggest::add);
        }

        return suggest;
    }
}
