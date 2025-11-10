package de.lars.utilsmanager.features.freecam;

import com.destroystokyo.paper.profile.PlayerProfile;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

public class FreeCamCommand implements BasicCommand {

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
        if (freecamListener.getFreeCamUser().containsKey(player.getName())) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Du bist bereits im Kamera Modus!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("You're already in the Freecam mode!", NamedTextColor.RED)));
            }
            return;
        }
        freecamListener.getFreeCamUser().put(player.getName(), player.getLocation());
        player.setGameMode(GameMode.SPECTATOR);

        ArmorStand armorStand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setMarker(true);
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        if (skullMeta != null) {
            PlayerProfile profile = Bukkit.createProfile(player.getUniqueId(), player.getName());
            skullMeta.setPlayerProfile(profile);
            skull.setItemMeta(skullMeta);
        }
        freecamListener.getFreeCamArmorStand().put(player.getUniqueId(), armorStand);

        armorStand.getEquipment().setHelmet(skull);
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du befindest dich nun im Kamera Modus. Benutzte ", NamedTextColor.GREEN))
                    .append(Component.text("/freecamleave", NamedTextColor.AQUA).clickEvent(ClickEvent.runCommand("\"freecamleave\"")))
                    .append(Component.text(" zum verlassen.", NamedTextColor.GREEN)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You're now in the cam mode. Use ", NamedTextColor.GREEN))
                    .append(Component.text("/freecamleave", NamedTextColor.AQUA).clickEvent(ClickEvent.runCommand("\"freecamleave\"")))
                    .append(Component.text(" to leave.", NamedTextColor.GREEN)));
        }
    }

    private BlockFace getBlockFace(Player player) {
        float yaw = player.getLocation().getYaw();
        yaw += 180;

        if (yaw >= 45 && yaw <= 135) {
            return BlockFace.WEST;
        } else if (yaw > 135 && yaw <= 225) {
            return BlockFace.NORTH;
        } else if (yaw > 225 && yaw <= 315) {
            return BlockFace.EAST;
        }
        return BlockFace.SOUTH;
    }
}
