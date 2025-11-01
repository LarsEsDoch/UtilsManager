package de.lars.utilsmanager.commands.admin;

import de.lars.apimanager.apis.rankAPI.RankAPI;
import de.lars.utilsmanager.UtilsManager;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class MagnetCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (RankAPI.getApi().getRankId((Player) stack.getSender()) < 9) {
            return;
        }
        Player player = Bukkit.getPlayer(args[1]);
        Integer RADIUS = 15;
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            player.getNearbyEntities(RADIUS, RADIUS, RADIUS)
                .stream()
                .filter(livingEntity -> !livingEntity.equals(player))
                .forEach(livingEntity -> {
                    double x = player.getLocation().getX() - livingEntity.getLocation().getX();
                    double y = player.getLocation().getY() - livingEntity.getLocation().getY();
                    double z = player.getLocation().getZ() - livingEntity.getLocation().getZ();

                    Vector vector = new Vector(x, y, z);
                    vector.multiply(0.1);
                    livingEntity.setVelocity(vector);
            });
            if (!Bukkit.getOnlinePlayers().contains(player)) {
                bukkitTask.cancel();
            }
        }, 5, 5);
    }
}














