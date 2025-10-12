/*package your.package.name;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReplyCommand extends BasicCommand {

    public ReplyCommand() {
        super("r");
        setDescription("Antwortet auf die letzte private Nachricht");
    }

    @Override
    public void execute(@NotNull CommandSourceStack source, @NotNull String[] args) {
        if (!(source.getExecutor() instanceof Player player)) {
            source.getSender().sendMessage(Component.text("❌ Nur Spieler können diesen Command verwenden.", NamedTextColor.RED));
            return;
        }

        if (args.length < 1) {
            player.sendMessage(Component.text("Verwendung: /r <Nachricht>", NamedTextColor.YELLOW));
            return;
        }

        Set<UUID> last = MsgCommand.lastRecipients.get(player.getUniqueId());
        if (last == null || last.isEmpty()) {
            player.sendMessage(Component.text("⚠ Du hast niemandem, auf den du antworten kannst!", NamedTextColor.RED));
            return;
        }

        String nachricht = String.join(" ", args);
        Set<Player> targets = last.stream()
                .map(Bukkit::getPlayer)
                .filter(p -> p != null && p.isOnline())
                .collect(Collectors.toSet());

        if (targets.isEmpty()) {
            player.sendMessage(Component.text("❌ Deine letzten Empfänger sind nicht mehr online!", NamedTextColor.RED));
            return;
        }

        for (Player target : targets) {
            target.sendMessage(Component.text("[PM] ", NamedTextColor.LIGHT_PURPLE)
                    .append(Component.text(player.getName(), NamedTextColor.DARK_PURPLE)
                            .hoverEvent(HoverEvent.showText(Component.text("Antwort von " + player.getName(), NamedTextColor.GRAY))))
                    .append(Component.text(" → dir: ", NamedTextColor.GRAY))
                    .append(Component.text(nachricht, NamedTextColor.WHITE)));

            // Gegenseitig speichern, damit der Empfänger auch direkt wieder /r nutzen kann
            MsgCommand.lastRecipients.put(target.getUniqueId(), Set.of(player.getUniqueId()));
        }

        player.sendMessage(Component.text("[PM] ", NamedTextColor.LIGHT_PURPLE)
                .append(Component.text("An ", NamedTextColor.GRAY))
                .append(Component.text(
                        targets.stream().map(Player::getName).collect(Collectors.joining(", ")),
                        NamedTextColor.DARK_PURPLE))
                .append(Component.text(": ", NamedTextColor.GRAY))
                .append(Component.text(nachricht, NamedTextColor.WHITE)));
    }
}

 */
