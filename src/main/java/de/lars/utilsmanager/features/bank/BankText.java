package de.lars.utilsmanager.features.bank;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class BankText {

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

    Location pcopperloc = new Location(world, -80.5, 72, -44.5);
    Location psellcopperloc = new Location(world, -62.5, 72, -44.5);
    Location pamethystloc = new Location(world, -80.5, 72, -46.5);
    Location psellamethystloc = new Location(world, -62.5, 72, -46.5);
    Location pdiamondloc = new Location(world, -80.5, 72, -48.5);
    Location pselldiamondloc = new Location(world, -62.5, 72, -48.5);
    Location pnetheriteloc = new Location(world, -80.5, 72, -50.5);
    Location psellnetheriteloc = new Location(world, -62.5, 72, -50.5);
    Location pspawnerloc = new Location(world, -80.5, 72, -52.5);
    Location psellspawnerloc = new Location(world, -62.5, 72, -52.5);

    ArmorStand copper = (ArmorStand) world.spawnEntity(copperloc, EntityType.ARMOR_STAND);
    ArmorStand sellcopper = (ArmorStand) world.spawnEntity(sellcopperloc, EntityType.ARMOR_STAND);
    ArmorStand amethyst = (ArmorStand) world.spawnEntity(amethystloc, EntityType.ARMOR_STAND);
    ArmorStand sellamethyst = (ArmorStand) world.spawnEntity(sellamethystloc, EntityType.ARMOR_STAND);
    ArmorStand diamond = (ArmorStand) world.spawnEntity(diamondloc, EntityType.ARMOR_STAND);
    ArmorStand selldiamond = (ArmorStand) world.spawnEntity(selldiamondloc, EntityType.ARMOR_STAND);
    ArmorStand netherite = (ArmorStand) world.spawnEntity(netheriteloc, EntityType.ARMOR_STAND);
    ArmorStand sellnetherite = (ArmorStand) world.spawnEntity(sellnetheriteloc, EntityType.ARMOR_STAND);
    ArmorStand spawner = (ArmorStand) world.spawnEntity(spawnerloc, EntityType.ARMOR_STAND);
    ArmorStand sellspawner = (ArmorStand) world.spawnEntity(sellspawnerloc, EntityType.ARMOR_STAND);

    ArmorStand pcopper = (ArmorStand) world.spawnEntity(pcopperloc, EntityType.ARMOR_STAND);
    ArmorStand psellcopper = (ArmorStand) world.spawnEntity(psellcopperloc, EntityType.ARMOR_STAND);
    ArmorStand pamethyst = (ArmorStand) world.spawnEntity(pamethystloc, EntityType.ARMOR_STAND);
    ArmorStand psellamethyst = (ArmorStand) world.spawnEntity(psellamethystloc, EntityType.ARMOR_STAND);
    ArmorStand pdiamond = (ArmorStand) world.spawnEntity(pdiamondloc, EntityType.ARMOR_STAND);
    ArmorStand pselldiamond = (ArmorStand) world.spawnEntity(pselldiamondloc, EntityType.ARMOR_STAND);
    ArmorStand pnetherite = (ArmorStand) world.spawnEntity(pnetheriteloc, EntityType.ARMOR_STAND);
    ArmorStand psellnetherite = (ArmorStand) world.spawnEntity(psellnetheriteloc, EntityType.ARMOR_STAND);
    ArmorStand pspawner = (ArmorStand) world.spawnEntity(pspawnerloc, EntityType.ARMOR_STAND);
    ArmorStand psellspawner = (ArmorStand) world.spawnEntity(psellspawnerloc, EntityType.ARMOR_STAND);
    public BankText() {

        Textcreate();
    }

    public void Textcreate() {
        copper.setCustomNameVisible(true);
        copper.customName(Component.text("Copper ", NamedTextColor.YELLOW));
        copper.setGravity(false);
        copper.setVisible(false);

        sellcopper.setCustomNameVisible(true);
        sellcopper.customName(Component.text("Copper ", NamedTextColor.YELLOW));
        sellcopper.setGravity(false);
        sellcopper.setVisible(false);

        amethyst.setCustomNameVisible(true);
        amethyst.customName(Component.text("Amethyst", NamedTextColor.LIGHT_PURPLE));
        amethyst.setGravity(false);
        amethyst.setVisible(false);

        sellamethyst.setCustomNameVisible(true);
        sellamethyst.customName(Component.text("Amethyst", NamedTextColor.LIGHT_PURPLE));
        sellamethyst.setGravity(false);
        sellamethyst.setVisible(false);

        diamond.setCustomNameVisible(true);
        diamond.customName(Component.text("Diamond", NamedTextColor.AQUA));
        diamond.setGravity(false);
        diamond.setVisible(false);

        selldiamond.setCustomNameVisible(true);
        selldiamond.customName(Component.text("Diamond", NamedTextColor.AQUA));
        selldiamond.setGravity(false);
        selldiamond.setVisible(false);

        netherite.setCustomNameVisible(true);
        netherite.customName(Component.text("Netherite", NamedTextColor.DARK_RED));
        netherite.setGravity(false);
        netherite.setVisible(false);

        sellnetherite.setCustomNameVisible(true);
        netherite.customName(Component.text("Netherite", NamedTextColor.DARK_RED));
        sellnetherite.setGravity(false);
        sellnetherite.setVisible(false);

        spawner.setCustomNameVisible(true);
        spawner.customName(Component.text("Spawner", NamedTextColor.BLACK));
        spawner.setGravity(false);
        spawner.setVisible(false);

        sellspawner.setCustomNameVisible(true);
        spawner.customName(Component.text("Spawner", NamedTextColor.BLACK));
        sellspawner.setGravity(false);
        sellspawner.setVisible(false);


        pcopper.setCustomNameVisible(true);
        pcopper.customName(Component.text("Price: 10$", NamedTextColor.BLACK));
        pcopper.setGravity(false);
        pcopper.setVisible(false);

        psellcopper.setCustomNameVisible(true);
        psellcopper.customName(Component.text("Sellprice: 5$", NamedTextColor.BLACK));
        psellcopper.setGravity(false);
        psellcopper.setVisible(false);

        pamethyst.setCustomNameVisible(true);
        pamethyst.customName(Component.text("Price: 30$", NamedTextColor.BLACK));
        pamethyst.setGravity(false);
        pamethyst.setVisible(false);

        psellamethyst.setCustomNameVisible(true);
        psellamethyst.customName(Component.text("Sellprice: 20$", NamedTextColor.BLACK));
        psellamethyst.setGravity(false);
        psellamethyst.setVisible(false);

        pdiamond.setCustomNameVisible(true);
        pdiamond.customName(Component.text("Price: 150$", NamedTextColor.BLACK));
        pdiamond.setGravity(false);
        pdiamond.setVisible(false);

        pselldiamond.setCustomNameVisible(true);
        pselldiamond.customName(Component.text("Sellprice: 100$", NamedTextColor.BLACK));
        pselldiamond.setGravity(false);
        pselldiamond.setVisible(false);

        pnetherite.setCustomNameVisible(true);
        pnetherite.customName(Component.text("Price: 1.250$", NamedTextColor.BLACK));
        pnetherite.setGravity(false);
        pnetherite.setVisible(false);

        psellnetherite.setCustomNameVisible(true);
        psellnetherite.customName(Component.text("Sellprice: 1.000$", NamedTextColor.BLACK));
        psellnetherite.setGravity(false);
        psellnetherite.setVisible(false);

        pspawner.setCustomNameVisible(true);
        pspawner.customName(Component.text("Price: 10.000$", NamedTextColor.BLACK));
        pspawner.setGravity(false);
        pspawner.setVisible(false);

        psellspawner.setCustomNameVisible(true);
        psellspawner.customName(Component.text("Sellprice: 7.500$", NamedTextColor.BLACK));
        psellspawner.setGravity(false);
        psellspawner.setVisible(false);
    }

    public void BankTextend() {
        copper.remove();
        sellcopper.remove();
        amethyst.remove();
        sellamethyst.remove();
        diamond.remove();
        selldiamond.remove();
        netherite.remove();
        sellnetherite.remove();
        spawner.remove();
        sellspawner.remove();

        pcopper.remove();
        psellcopper.remove();
        pamethyst.remove();
        psellamethyst.remove();
        pdiamond.remove();
        pselldiamond.remove();
        pnetherite.remove();
        psellnetherite.remove();
        pspawner.remove();
        psellspawner.remove();
    }

}
