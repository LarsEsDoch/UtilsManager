package dev.lars.utilsmanager.features.bank;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class BankTextListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        World world = Bukkit.getWorld("world");
        Location copperloc = new Location(world, -80.5, 73, -44.5);
        Location sellcopperloc = new Location(world, -62.5, 73, -44.5);
        Location amethystloc = new Location(world, -80.5, 73, -46.5);
        Location sellamethystloc = new Location(world, -62.5, 73, -46.5);
        Location diamondloc = new Location(world, -80.5, 73, -48.5);
        Location selldiamondloc = new Location(world, -62.5, 73, -48.5);
        Location netheriteloc = new Location(world, -80.5, 73, -50.5);
        Location sellnetheriteloc = new Location(world, -62.5, 73, -50.5);
        Location spawnerloc = new Location(world, -80.5, 73, -52.5);
        Location sellspawnerloc = new Location(world, -62.5, 73, -52.5);
        if (event.getRightClicked().getType() == EntityType.ARMOR_STAND) {

            if (entity.getLocation().getX() == copperloc.getX()) {
                if (entity.getLocation().getZ() == copperloc.getZ()) {
                    player.performCommand("buy copper 1");

                }
            }

            if (entity.getLocation().getX() == amethystloc.getX()) {
                if (entity.getLocation().getZ() == amethystloc.getZ()) {
                    player.performCommand("buy amethyst 1");

                }
            }

            if (entity.getLocation().getX() == diamondloc.getX()) {
                if (entity.getLocation().getZ() == diamondloc.getZ()) {
                    player.performCommand("buy diamond 1");

                }
            }

            if (entity.getLocation().getX() == netheriteloc.getX()) {
                if (entity.getLocation().getZ() == netheriteloc.getZ()) {
                    player.performCommand("buy netherite 1");

                }
            }

            if (entity.getLocation().getX() == spawnerloc.getX()) {
                if (entity.getLocation().getZ() == spawnerloc.getZ()) {
                    player.performCommand("buy spawner 1");

                }
            }


            if (entity.getLocation().getX() == sellcopperloc.getX()) {
                if (entity.getLocation().getZ() == sellcopperloc.getZ()) {
                    player.performCommand("sell copper 1");

                }
            }

            if (entity.getLocation().getX() == sellamethystloc.getX()) {
                if (entity.getLocation().getZ() == sellamethystloc.getZ()) {
                    player.performCommand("sell amethyst 1");

                }
            }

            if (entity.getLocation().getX() == selldiamondloc.getX()) {
                if (entity.getLocation().getZ() == selldiamondloc.getZ()) {
                    player.performCommand("sell diamond 1");

                }
            }

            if (entity.getLocation().getX() == sellnetheriteloc.getX()) {
                if (entity.getLocation().getZ() == sellnetheriteloc.getZ()) {
                    player.performCommand("sell netherite 1");

                }
            }

            if (entity.getLocation().getX() == sellspawnerloc.getX()) {
                if (entity.getLocation().getZ() == sellspawnerloc.getZ()) {
                    player.performCommand("sell spawner 1");

                }
            }
        }

    }

    @EventHandler
    public void onPlayerInteractEntity(EntityDamageByEntityEvent event) {
        World world = Bukkit.getWorld("world");
        Location copperloc = new Location(world, -80.5, 73, -44.5);
        Location sellcopperloc = new Location(world, -62.5, 73, -44.5);
        Location amethystloc = new Location(world, -80.5, 73, -46.5);
        Location sellamethystloc = new Location(world, -62.5, 73, -46.5);
        Location diamondloc = new Location(world, -80.5, 73, -48.5);
        Location selldiamondloc = new Location(world, -62.5, 73, -48.5);
        Location netheriteloc = new Location(world, -80.5, 73, -50.5);
        Location sellnetheriteloc = new Location(world, -62.5, 73, -50.5);
        Location spawnerloc = new Location(world, -80.5, 73, -52.5);
        Location sellspawnerloc = new Location(world, -62.5, 73, -52.5);
        Entity entity = event.getEntity();

        if (entity instanceof ArmorStand || event.getDamager() instanceof Player) {
            if (!(entity.getType() == EntityType.ARMOR_STAND)) return ;
            assert entity instanceof ArmorStand;
            ArmorStand armorStand;
            try {
                armorStand = (ArmorStand) entity;
            } catch (NullPointerException e) {
                return;
            }
            Player player = (Player) event.getDamager();

            if (armorStand.getLocation().getX() == copperloc.getX()) {
                if (armorStand.getLocation().getZ() == copperloc.getZ()) {
                    player.performCommand("buy copper 1");

                }
            }

            if (armorStand.getLocation().getX() == amethystloc.getX()) {
                if (armorStand.getLocation().getZ() == amethystloc.getZ()) {
                    player.performCommand("buy amethyst 1");

                }
            }

            if (armorStand.getLocation().getX() == diamondloc.getX()) {
                if (armorStand.getLocation().getZ() == diamondloc.getZ()) {
                    player.performCommand("buy diamond 1");

                }
            }

            if (armorStand.getLocation().getX() == netheriteloc.getX()) {
                if (armorStand.getLocation().getZ() == netheriteloc.getZ()) {
                    player.performCommand("buy netherite 1");

                }
            }

            if (armorStand.getLocation().getX() == spawnerloc.getX()) {
                if (armorStand.getLocation().getZ() == spawnerloc.getZ()) {
                    player.performCommand("buy spawner 1");

                }
            }


            if (armorStand.getLocation().getX() == sellcopperloc.getX()) {
                if (armorStand.getLocation().getZ() == sellcopperloc.getZ()) {
                    player.performCommand("sell copper 1");

                }
            }

            if (armorStand.getLocation().getX() == sellamethystloc.getX()) {
                if (armorStand.getLocation().getZ() == sellamethystloc.getZ()) {
                    player.performCommand("sell amethyst 1");

                }
            }

            if (armorStand.getLocation().getX() == selldiamondloc.getX()) {
                if (armorStand.getLocation().getZ() == selldiamondloc.getZ()) {
                    player.performCommand("sell diamond 1");

                }
            }

            if (armorStand.getLocation().getX() == sellnetheriteloc.getX()) {
                if (armorStand.getLocation().getZ() == sellnetheriteloc.getZ()) {
                    player.performCommand("sell netherite 1");

                }
            }

            if (armorStand.getLocation().getX() == sellspawnerloc.getX()) {
                if (armorStand.getLocation().getZ() == sellspawnerloc.getZ()) {
                    player.performCommand("sell spawner 1");

                }
            }
        }

    }
}
