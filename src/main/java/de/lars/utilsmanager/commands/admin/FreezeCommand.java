package de.lars.utilsmanager.commands.admin;

import de.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FreezeCommand implements BasicCommand {

    Integer time;
    Integer twenty;
    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Player sendplayer = (Player) stack.getSender();
        if (!(sendplayer.hasPermission("plugin.freeze"))) {
            sendplayer.sendMessage(Statements.getNotAllowed(sendplayer));
            return;
        }
        if (args.length == 0 || args.length == 1) {
            sendUsage(sendplayer);
            return;
        }
        try {
            Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sendUsage(sendplayer);
            return;
        }
        time = Integer.parseInt(args[1]);
        twenty = 1;
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
                sendplayer.sendMessage(Component.text("Der Spieler existiert nicht!", NamedTextColor.RED));
            } else {
                sendplayer.sendMessage(Component.text("This player dosen't exits!", NamedTextColor.RED));
            }
            return;
        }

        if (time < 1) {
            sendUsage(sendplayer);
            return;
        }
        Location loc = player.getLocation();
        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(loc);
                twenty++;
                Location targetLocation = player.getLocation();
                EvokerFangs evokerFangs = (EvokerFangs) player.getWorld().spawnEntity(targetLocation, EntityType.EVOKER_FANGS);
                evokerFangs.setOwner(player);
                player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 3600, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3600, 0));
                evokerFangs.setTicksLived(200);
                if (twenty >= 20) {
                    if (time <= 0) {
                        evokerFangs.remove();
                        player.removePotionEffect(PotionEffectType.DARKNESS);
                        player.removePotionEffect(PotionEffectType.BLINDNESS);
                        cancel();
                    }
                    time--;
                    twenty = 0;
                }
            }
        }.runTaskTimer(UtilsManager.getInstance(), 1,1);
    }

    @Override
    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        Player player = (Player) commandSourceStack.getSender();
        if (!player.hasPermission("plugin.freeze")) return Collections.emptyList();
        if (args.length == 1 || args.length == 0) {
            List<String> names = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                names.add(p.getName());
            }

            return names;
        }
        return Collections.emptyList();
    }

    private void sendUsage(Player sendplayer) {
        if (LanguageAPI.getApi().getLanguage(sendplayer) == 2) {
            sendplayer.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/freeze <Spieler> <Zeit>");
        } else {
            sendplayer.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/freeze <player> <time>");
        }
    }
}
