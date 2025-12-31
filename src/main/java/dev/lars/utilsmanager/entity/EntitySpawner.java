package dev.lars.utilsmanager.entity;

import dev.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EntitySpawner {

    private final World world;
    private final List<Entity> spawned = new ArrayList<>();
    private final ItemStack netheriteSword = new ItemStack(Material.NETHERITE_SWORD);

    public EntitySpawner() {
        world = Bukkit.getWorld("world");
    }

    public void spawnAll() {
        try {
            world.loadChunk(12, 7);
            world.loadChunk(12, 8);
            world.loadChunk(4, 2);
            world.loadChunk(4, 4);
            world.loadChunk(25, 1);
            world.loadChunk(26, 31);

            Location ls1sheep = loc(5, 134, 22);
            Location ls1pig = loc(8, 134, 25);
            Location ls2strider = loc(-1, 135, 19);
            Location ls2wither = loc(-3.5, 134, 15.5);
            Location ls3creeper = loc(-9.5, 134, 22.5);
            Location ls3spider = loc(-12.5, 134, 26.5);
            Location ls3minecart = loc(-9, 135, 26);
            Location ls4enderman = loc(-4, 134, 35);
            Location ls4endcrystal = loc(-1.5, 136, 32.5);
            Location ls4endmilb = loc(-4, 135, 30);
            Location ls4endmilb2 = loc(0, 135, 30);

            Location leenderman1 = loc(207.5, 73, 139.5);
            Location leenderman2 = loc(207.5, 73, 132.5);
            Location leenderman3 = loc(207.5, 73, 120.5);
            Location leenderman4 = loc(207.5, 73, 113.5);

            Location lwchicken = loc(78.5, 64, 42.5);
            Location lwchicken2 = loc(69.5, 65, 76.5);
            Location lwchicken3 = loc(-86.5, 65, -7.5);
            Location lwboat1 = loc(110, 63, 52);
            Location lwboat2 = loc(76.5, 63, 42.5);
            Location lwboat3 = loc(79, 63, 42.5);
            Location lwboat4 = loc(-108, 63, 21.5);
            Location lwboat5 = loc(-106, 63, 21.5);

            Location lnfisher = loc(-132.5, 74, -22);

            Sheep s1sheep = spawn(ls1sheep, EntityType.SHEEP, e -> {
                ((Sheep) e).customName(Component.text("Larsi"));
                ((Sheep) e).setCustomNameVisible(false);
                ((Sheep) e).setColor(DyeColor.WHITE);
                ((Sheep) e).setSilent(true);
            });

            Pig s1spig = spawn(ls1pig, EntityType.PIG, e -> {
                ((Pig) e).customName(Component.text("Larsi"));
                ((Pig) e).setCustomNameVisible(false);
                ((Pig) e).setSilent(true);
            });

            Strider s2strider = spawn(ls2strider, EntityType.STRIDER, e -> {
                ((Strider) e).customName(Component.text("Larsi"));
                ((Strider) e).setCustomNameVisible(false);
                ((Strider) e).setSilent(true);
            });

            WitherSkeleton s2wither = spawn(ls2wither, EntityType.WITHER_SKELETON, e -> {
                ((WitherSkeleton) e).customName(Component.text("Larsi"));
                ((WitherSkeleton) e).setCustomNameVisible(false);
                ((WitherSkeleton) e).getEquipment().setItemInMainHand(netheriteSword);
                ((WitherSkeleton) e).getEquipment().setItemInOffHand(netheriteSword.clone());
                ((WitherSkeleton) e).setSilent(true);
            });

            Creeper s3creeper = spawn(ls3creeper, EntityType.CREEPER, e -> {
                ((Creeper) e).customName(Component.text("Larsi"));
                ((Creeper) e).setCustomNameVisible(false);
            });

            CaveSpider s3spider = spawn(ls3spider, EntityType.CAVE_SPIDER, e -> {
                ((CaveSpider) e).customName(Component.text("Larsi"));
                ((CaveSpider) e).setCustomNameVisible(false);
                ((CaveSpider) e).setSilent(true);
            });

            Minecart s3minecart = spawn(ls3minecart, EntityType.MINECART, e -> {
                ((Minecart) e).customName(Component.text("Minecart"));
                ((Minecart) e).setCustomNameVisible(false);
            });

            Enderman s4enderman = spawn(ls4enderman, EntityType.ENDERMAN, e -> {
                ((Enderman) e).customName(Component.text("Aelxibexi"));
                ((Enderman) e).setCustomNameVisible(false);
                ((Enderman) e).setAI(false);
                ((Enderman) e).setSilent(true);
                ((Enderman) e).setRotation(-135f, 0f);
            });

            EnderCrystal s4endcrystal = spawn(ls4endcrystal, EntityType.END_CRYSTAL, e -> {
                ((EnderCrystal) e).setShowingBottom(false);
            });

            Endermite s4endmite = spawn(ls4endmilb, EntityType.ENDERMITE, e -> {
                ((Endermite) e).customName(Component.text("Alexi"));
                ((Endermite) e).setCustomNameVisible(false);
                ((Endermite) e).setSilent(true);
            });

            Endermite s4endmite2 = spawn(ls4endmilb2, EntityType.ENDERMITE, e -> {
                ((Endermite) e).customName(Component.text("Alexi"));
                ((Endermite) e).setCustomNameVisible(false);
                ((Endermite) e).setSilent(true);
            });

            Enderman eenderman1 = spawn(leenderman1, EntityType.ENDERMAN, e -> {
                ((Enderman) e).customName(Component.text("Alexibexi"));
                ((Enderman) e).setCustomNameVisible(false);
                ((Enderman) e).setAI(false);
                ((Enderman) e).setRotation(90f, 0f);
            });
            Enderman eenderman2 = spawn(leenderman2, EntityType.ENDERMAN, e -> {
                ((Enderman) e).customName(Component.text("Alexibexi"));
                ((Enderman) e).setCustomNameVisible(false);
                ((Enderman) e).setAI(false);
                ((Enderman) e).setRotation(90f, 0f);
            });
            Enderman eenderman3 = spawn(leenderman3, EntityType.ENDERMAN, e -> {
                ((Enderman) e).customName(Component.text("Alexibexi"));
                ((Enderman) e).setCustomNameVisible(false);
                ((Enderman) e).setAI(false);
                ((Enderman) e).setRotation(90f, 0f);
            });
            Enderman eenderman4 = spawn(leenderman4, EntityType.ENDERMAN, e -> {
                ((Enderman) e).customName(Component.text("Alexibexi"));
                ((Enderman) e).setCustomNameVisible(false);
                ((Enderman) e).setAI(false);
                ((Enderman) e).setRotation(90f, 0f);
            });

            Chicken wchicken1 = spawn(lwchicken, EntityType.CHICKEN, e -> {
                ((Chicken) e).customName(Component.text("Kjelli"));
                ((Chicken) e).setCustomNameVisible(false);
            });
            Chicken wchicken2 = spawn(lwchicken2, EntityType.CHICKEN, e -> {
                ((Chicken) e).customName(Component.text("Kjelli"));
                ((Chicken) e).setCustomNameVisible(false);
            });
            Chicken wchicken3 = spawn(lwchicken3, EntityType.CHICKEN, e -> {
                ((Chicken) e).customName(Component.text("Kjelli"));
                ((Chicken) e).setCustomNameVisible(false);
            });

            Boat wboat1 = spawn(lwboat1, EntityType.OAK_BOAT, e -> {
                ((Boat) e).setRotation(0f, 0f);
            });
            Boat wboat2 = spawn(lwboat2, EntityType.OAK_BOAT, e -> {
                ((Boat) e).setRotation(90f, 0f);
            });
            Boat wboat3 = spawn(lwboat3, EntityType.OAK_BOAT, e -> {
                ((Boat) e).setRotation(90f, 0f);
                ((Boat) e).addPassenger(wchicken1);
            });
            Boat wboat4 = spawn(lwboat4, EntityType.OAK_BOAT, e -> {
                ((Boat) e).setRotation(90f, 0f);
            });
            Boat wboat5 = spawn(lwboat5, EntityType.OAK_BOAT, e -> {
                ((Boat) e).setRotation(90f, 0f);
            });

            Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("Summoned all entities", NamedTextColor.BLUE)));

        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(Component.text("Failed to spawn entities: " + ex.getMessage(), NamedTextColor.RED));
            ex.printStackTrace();
        }
    }

    public void despawnAll() {
        for (Entity e : new ArrayList<>(spawned)) {
            if (e != null && !e.isDead()) {
                e.remove();
            }
        }
        spawned.clear();
    }

    private Location loc(double x, double y, double z) {
        return new Location(world, x, y, z);
    }

    @SuppressWarnings("unchecked")
    private <T extends Entity> T spawn(Location location, EntityType type, Consumer<T> configurator) {
        Entity e = world.spawnEntity(location, type);
        try {
            configurator.accept((T) e);
        } catch (ClassCastException cce) {
            Bukkit.getConsoleSender().sendMessage(Component.text("Entity type mismatch while configuring: " + type, NamedTextColor.RED));
            cce.printStackTrace();
        }
        spawned.add(e);
        return (T) e;
    }
}