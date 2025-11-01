package de.lars.utilsmanager.features.freecam;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.util.Statements;
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
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (!player.hasPermission("plugin.freecam")) {
            player.sendMessage(Statements.getNotAllowed(player));
            return;
        }
        FreecamListener freecamListener = UtilsManager.getInstance().getFreecamListener();

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
