package dev.lars.utilsmanager.entity;

import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class EntitySummons {

    World world = Bukkit.getWorld("world");
    Location ls1sheep = new Location(world, 5, 134, 22);
    Location ls1pig = new Location(world, 8, 134, 25);
    Location ls2strider = new Location(world, -1, 135, 19);
    Location ls2wither = new Location(world, -3.5, 134, 15.5);
    Location ls3crepper = new Location(world, -9.5, 134, 22.5);
    Location ls3spider = new Location(world, -12.5, 134, 26.5);
    Location ls3minecart = new Location(world, -9, 135, 26);
    Location ls4enderman = new Location(world, -4, 134, 35);
    Location ls4endcrystal = new Location(world, -1.5, 136, 32.5);
    Location ls4endmilb = new Location(world, -4, 135, 30);
    Location ls4endmilb2 = new Location(world, 0, 135, 30);

    Location leenderman1 = new Location(world, 207.5,73,139.5);
    Location leenderman2 = new Location(world, 207.5,73,132.5);
    Location leenderman3 = new Location(world, 207.5,73,120.5);
    Location leenderman4 = new Location(world, 207.5,73,113.5);

    Location lwchicken = new Location(world, 78.5, 64,42.5);
    Location lwchicken2 = new Location(world, 69.5, 65,76.5);
    Location lwchicken3 = new Location(world, -86.5, 65,-7.5);
    Location lwboat1 = new Location(world, 110, 63,52);
    Location lwboat2 = new Location(world, 76.5, 63,42.5);
    Location lwboat3 = new Location(world, 79, 63,42.5);
    Location lwboat4 = new Location(world, -108, 63,21.5);
    Location lwboat5 = new Location(world, -106, 63,21.5);

    Location lnfisher = new Location(world, -132.5, 74, -22);

    ItemStack netheriteSword = new ItemStack(Material.NETHERITE_SWORD);
    public void EntitysSummons() {
        Sheep s1sheep = (Sheep) world.spawnEntity(ls1sheep, EntityType.SHEEP);
        Pig s1spig = (Pig) world.spawnEntity(ls1pig, EntityType.PIG);

        Strider s2strider = (Strider) world.spawnEntity(ls2strider, EntityType.STRIDER);
        WitherSkeleton s2wither = (WitherSkeleton) world.spawnEntity(ls2wither, EntityType.WITHER_SKELETON);

        Creeper s3crepper = (Creeper) world.spawnEntity(ls3crepper, EntityType.CREEPER);
        Spider s3spider = (Spider) world.spawnEntity(ls3spider, EntityType.CAVE_SPIDER);
        Minecart s3minecart = (Minecart) world.spawnEntity(ls3minecart, EntityType.MINECART);

        Enderman s4enderman = (Enderman) world.spawnEntity(ls4enderman, EntityType.ENDERMAN);
        EnderCrystal s4endcrystal = (EnderCrystal) world.spawnEntity(ls4endcrystal, EntityType.END_CRYSTAL);
        Endermite s4endmite = (Endermite) world.spawnEntity(ls4endmilb, EntityType.ENDERMITE);
        Endermite s4endmite2 = (Endermite) world.spawnEntity(ls4endmilb2, EntityType.ENDERMITE);

        Enderman eenderman1 = (Enderman) world.spawnEntity(leenderman1, EntityType.ENDERMAN);
        Enderman eenderman2 = (Enderman) world.spawnEntity(leenderman2, EntityType.ENDERMAN);
        Enderman eenderman3 = (Enderman) world.spawnEntity(leenderman3, EntityType.ENDERMAN);
        Enderman eenderman4 = (Enderman) world.spawnEntity(leenderman4, EntityType.ENDERMAN);

        Chicken wchicken1 = (Chicken) world.spawnEntity(lwchicken, EntityType.CHICKEN);
        Chicken wchicken2 = (Chicken) world.spawnEntity(lwchicken2, EntityType.CHICKEN);
        Chicken wchicken3 = (Chicken) world.spawnEntity(lwchicken3, EntityType.CHICKEN);
        Boat wboat1 = (Boat) world.spawnEntity(lwboat1, EntityType.OAK_BOAT);
        Boat wboat2 = (Boat) world.spawnEntity(lwboat2, EntityType.OAK_BOAT);
        Boat wboat3 = (Boat) world.spawnEntity(lwboat3, EntityType.OAK_BOAT);
        Boat wboat4 = (Boat) world.spawnEntity(lwboat4, EntityType.OAK_BOAT);
        Boat wboat5 = (Boat) world.spawnEntity(lwboat5, EntityType.OAK_BOAT);
        Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("Summoned all entity's", NamedTextColor.BLUE)));

        s1sheep.setCustomNameVisible(false);
        s1sheep.customName(Component.text("Larsi"));
        s1sheep.setColor(DyeColor.WHITE);
        s1sheep.setSilent(true);
        s1spig.setCustomNameVisible(false);
        s1spig.customName(Component.text("Larsi"));
        s1spig.setSilent(true);

        s2strider.setCustomNameVisible(false);
        s2strider.customName(Component.text("Larsi"));
        s2strider.setSilent(true);
        s2wither.setCustomNameVisible(false);
        s2wither.customName(Component.text("Larsi"));
        s2wither.getEquipment().setItemInMainHand(netheriteSword);
        s2wither.getEquipment().setItemInOffHand(netheriteSword);
        s2wither.setSilent(true);

        s3crepper.setCustomNameVisible(false);
        s3crepper.customName(Component.text("Larsi"));
        s3spider.setCustomNameVisible(false);
        s3spider.customName(Component.text("Larsi"));
        s3spider.setSilent(true);


        s4enderman.setCustomNameVisible(false);
        s4enderman.customName(Component.text("Aelxibexi"));
        s4enderman.setAI(false);
        s4enderman.setSilent(true);
        s4enderman.setRotation(-135, 0);
        s4endcrystal.setShowingBottom(false);
        s4endmite.setCustomNameVisible(false);
        s4endmite.customName(Component.text("Alexi"));
        s4endmite.setSilent(true);
        s4endmite2.setCustomNameVisible(false);
        s4endmite2.customName(Component.text("Alexi"));
        s4endmite2.setSilent(true);


        world.loadChunk(12, 7);
        world.loadChunk(12, 8);
        eenderman1.setCustomNameVisible(false);
        eenderman1.customName(Component.text("Alexibexi"));
        eenderman1.setAI(false);
        eenderman1.setRotation(90, 0);
        eenderman2.setCustomNameVisible(false);
        eenderman2.customName(Component.text("Alexibexi"));
        eenderman2.setAI(false);
        eenderman2.setRotation(90, 0);
        eenderman3.setCustomNameVisible(false);
        eenderman3.customName(Component.text("Alexibexi"));
        eenderman3.setAI(false);
        eenderman3.setRotation(90, 0);
        eenderman4.setCustomNameVisible(false);
        eenderman4.customName(Component.text("Alexibexi"));
        eenderman4.setAI(false);
        eenderman4.setRotation(90, 0);


        world.loadChunk(4,2);
        world.loadChunk(4,4);
        world.loadChunk(25,1);
        world.loadChunk(26,31);
        wboat2.setRotation(90, 0);
        wboat4.setRotation(90, 0);
        wboat5.setRotation(90, 0);
        wboat3.setRotation(90, 0);
        wboat3.addPassenger(wchicken1);
        wchicken1.setCustomNameVisible(false);
        wchicken1.customName(Component.text("Kjelli"));
        wchicken2.setCustomNameVisible(false);
        wchicken2.customName(Component.text("Kjelli"));
        wchicken3.setCustomNameVisible(false);
        wchicken3.customName(Component.text("Kjelli"));
    }


    public void EntitysSummonsEnd() {
        /*s1sheep.remove();
        s1spig.remove();
        s2strider.remove();
        s2wither.remove();
        s3crepper.remove();
        s3spider.remove();
        s3minecart.remove();
        s4enderman.remove();
        s4endcrystal.remove();
        s4endmite.remove();
        s4endmite2.remove();

        eenderman1.remove();
        eenderman2.remove();
        eenderman3.remove();
        eenderman4.remove();

        wboat1.remove();
        wboat2.remove();
        wboat3.remove();
        wboat4.remove();
        wboat5.remove();
        wchicken1.remove();
        wchicken2.remove();
        wchicken3.remove();

         */


    }
}