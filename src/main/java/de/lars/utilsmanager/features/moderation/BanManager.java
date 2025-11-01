package de.lars.utilsmanager.features.moderation;

import de.lars.apimanager.apis.banAPI.BanAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.util.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;

public class BanManager {

    public void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            for (Player target : Bukkit.getOnlinePlayers()) {
                if (BanAPI.getApi().isBanned(target)) {
                    Instant now = Instant.now();
                Instant end = BanAPI.getApi().getEnd(target);

                if (end == null) {
                    target.kick(
                    Component.text("You're permanently banned! Reason:\n", NamedTextColor.WHITE)
                        .append(Component.text(BanAPI.getApi().getReason(target), NamedTextColor.RED))
                );
                    return;
                }

                long remainingSeconds = Duration.between(now, end).getSeconds();

                if (remainingSeconds <= 0) {
                    BanAPI.getApi().setUnBanned(target);
                    return;
                }

                Component remainingTime = Statements.formatDuration(remainingSeconds);

                Bukkit.getScheduler().runTask(UtilsManager.getInstance(), bukkitTask1 -> {
                    target.kick(
                    Component.text("You're banned! Reason:\n", NamedTextColor.WHITE)
                        .append(Component.text(BanAPI.getApi().getReason(target), NamedTextColor.RED))
                        .append(Component.text("\nTime to wait: ", NamedTextColor.WHITE))
                        .append(remainingTime)
                );
                });
                }
            }
        }, 20, 20);
    }

    public void checkBanned(Player target) {
        if (BanAPI.getApi().isBanned(target)) {
            Instant now = Instant.now();
            Instant end = BanAPI.getApi().getEnd(target);
            if (end != null && end.isBefore(now)) {
                BanAPI.getApi().setUnBanned(target);
            }
        }
    }
}
