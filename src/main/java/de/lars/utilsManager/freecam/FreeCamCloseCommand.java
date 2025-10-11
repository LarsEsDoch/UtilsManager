package de.lars.utilsManager.freecam;

import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FreeCamCloseCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Component.text("Only player can send Messages.", NamedTextColor.RED));
            return;
        }
        if (!player.hasPermission("plugin.freecam")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        FreecamListener freecamListener = Main.getInstance().getFreecamListener();

        if (!freecamListener.getFreeCamUser().containsKey(player.getName())) return;
        player.teleport(freecamListener.freeCamUser.get(player.getName()));
        player.setGameMode(GameMode.SURVIVAL);
        ArmorStand armorStand = freecamListener.getFreeCamArmorStand().get(player.getUniqueId());
        freecamListener.getFreeCamArmorStand().remove(player.getUniqueId());
        freecamListener.getFreeCamUser().remove(player.getName());

        if (armorStand != null && !armorStand.isDead()) {
            armorStand.remove();
        }
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du bist nun zurück im Überlebens Modus", NamedTextColor.GREEN)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You're now back in Survival mode.", NamedTextColor.GREEN)));
        }

    }
}
