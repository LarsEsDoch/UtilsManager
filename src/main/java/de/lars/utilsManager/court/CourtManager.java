package de.lars.utilsManager.court;

import de.lars.apiManager.banAPI.BanAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.timerAPI.TimerAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.utils.RankStatements;
import de.lars.utilsManager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CourtManager {

    public List<Player> members = new ArrayList<>();

    public List<Player> witnesser = new ArrayList<>();

    public List<Player> judgedVotes = new ArrayList<>();

    public List<Player> freeVotes = new ArrayList<>();

    public CourtManager() {
        checkCriminal();
    }

    public void checkCriminal() {
        /*Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), bukkitTask -> {
            for (Player player: Bukkit.getOnlinePlayers()) {
                Integer criminal = BanAPI.getApi().isCriminal(player);
                if (criminal == 1) {
                    if (isPlayerOffline(player.getUniqueId().toString())) {
                        BanAPI.getApi().setOnLock(player);
                        return;
                    }
                    if (isPlayerOffline(BanAPI.getApi().getProsecutor(player))) {
                        BanAPI.getApi().setUnlocked(player);
                        return;
                    }
                    UUID prosecutorUUID = UUID.fromString(BanAPI.getApi().getProsecutor(player));
                    Player prosecutor = Bukkit.getPlayer(prosecutorUUID);
                    for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                        if (onlineplayer == player) {
                            onlineplayer.sendMessage(" ");
                        }
                        if(LanguageAPI.getApi().getLanguage(onlineplayer) == 2) {
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("|----------------------------------|", NamedTextColor.DARK_GRAY)));
                            onlineplayer.sendMessage(" ");
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(RankStatements.getRank(player))
                                    .append(Component.text(" wurde von ", NamedTextColor.WHITE))
                                    .append(RankStatements.getRank(prosecutor))
                                    .append(Component.text(" angeklagt!", NamedTextColor.WHITE)));
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Sein genannter Grund ist: ", NamedTextColor.WHITE))
                                    .append(Component.text(BanAPI.getApi().getCriminalReason(player), NamedTextColor.BLUE)));
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Die Gerichtsversammlung beginnt in 60 Sekunden. Kommst du?", NamedTextColor.WHITE)));

                            ComponentBuilder comeChecker = Component.text("").append(Statements.getPrefix()).toBuilder();
                            Component comeMessageYes = Component.text("[Ja]")
                                    .color(NamedTextColor.GREEN)
                                    .clickEvent(ClickEvent.runCommand("/court join"))
                                    .hoverEvent(HoverEvent.showText(Component.text("Du wirst kommen").color(NamedTextColor.GREEN)));
                            Component comeMessageNo = Component.text("[Nein]")
                                    .color(NamedTextColor.RED)
                                    .clickEvent(ClickEvent.runCommand("court pas"))
                                    .hoverEvent(HoverEvent.showText(Component.text("Du wirst nicht kommen").color(NamedTextColor.RED)));

                            comeChecker.append(comeMessageYes);
                            comeChecker.append(Component.text(" / ", NamedTextColor.GRAY));
                            comeChecker.append(comeMessageNo);
                            onlineplayer.sendMessage(comeChecker.build());
                            onlineplayer.sendMessage(" ");
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("|----------------------------------|", NamedTextColor.DARK_GRAY)));
                        } else {
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("|----------------------------------|", NamedTextColor.DARK_GRAY)));
                            onlineplayer.sendMessage(" ");
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(RankStatements.getRank(player))
                                    .append(Component.text(" is accused by ", NamedTextColor.WHITE))
                                    .append(RankStatements.getRank(prosecutor))
                                    .append(Component.text("!", NamedTextColor.WHITE)));
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("His said reason is: ", NamedTextColor.WHITE))
                                    .append(Component.text(BanAPI.getApi().getCriminalReason(player), NamedTextColor.BLUE)));
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("The court meeting begins in 60 seconds. Do you come?", NamedTextColor.WHITE)));

                            ComponentBuilder comeChecker = Component.text("").append(Statements.getPrefix()).toBuilder();
                            Component comeMessageYes = Component.text("[Yes]")
                                    .color(NamedTextColor.GREEN)
                                    .clickEvent(ClickEvent.runCommand("/court join"))
                                    .hoverEvent(HoverEvent.showText(Component.text("You will come").color(NamedTextColor.GREEN)));
                            Component comeMessageNo = Component.text("[No]")
                                    .color(NamedTextColor.RED)
                                    .clickEvent(ClickEvent.runCommand("court pas"))
                                    .hoverEvent(HoverEvent.showText(Component.text("You don't will come").color(NamedTextColor.RED)));

                            comeChecker.append(comeMessageYes);
                            comeChecker.append(Component.text(" / ", NamedTextColor.GRAY));
                            comeChecker.append(comeMessageNo);
                            onlineplayer.sendMessage(comeChecker.build());
                            onlineplayer.sendMessage(" ");
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("|----------------------------------|", NamedTextColor.DARK_GRAY)));
                        }
                        BanAPI.getApi().setOnWait(player);
                    }
                }
                if (criminal == 2) {
                    timetocourt--;
                    if (isPlayerOffline(BanAPI.getApi().getProsecutor(player))) {
                        BanAPI.getApi().setUnlocked(player);
                        return;
                    }
                    if (isPlayerOffline(player.getUniqueId().toString())) {
                        BanAPI.getApi().setOnLock(player);
                        return;
                    }
                }
                if (timetocourt == 0) {
                    if (criminal == 3) {
                        return;
                    }
                    timetocourt = 60;
                    if (isPlayerOffline(BanAPI.getApi().getProsecutor(player))) {
                        BanAPI.getApi().setUnlocked(player);
                        return;
                    }
                    if (isPlayerOffline(player.getUniqueId().toString())) {
                        BanAPI.getApi().setOnLock(player);
                        return;
                    }
                    World world = Bukkit.getWorld("world");
                    Location placecriminal = new Location(world, -74, 132, 173);
                    placecriminal.setYaw(-180);
                    placecriminal.setPitch(0);
                    player.teleport(placecriminal);

                    ArmorStand armorStandCriminal = (ArmorStand) placecriminal.getWorld().spawnEntity(placecriminal.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                    armorStandCriminal.setVisible(false);
                    armorStandCriminal.setGravity(false);
                    armorStandCriminal.addPassenger(player);

                    UUID prosecutorUUID = UUID.fromString(BanAPI.getApi().getProsecutor(player));
                    Player prosecutor = Bukkit.getPlayer(prosecutorUUID);
                    Location placeProsecutor = new Location(world, -75, 132, 173);
                    placeProsecutor.setYaw(-180);
                    placeProsecutor.setPitch(0);
                    prosecutor.teleport(placeProsecutor);

                    ArmorStand armorStandProsecutor = (ArmorStand) placeProsecutor.getWorld().spawnEntity(placeProsecutor.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                    armorStandProsecutor.setVisible(false);
                    armorStandProsecutor.setGravity(false);
                    armorStandProsecutor.addPassenger(player);

                    for (int i = 0; i < members.size(); i++) {
                        Player member = members.get(i);
                        int place = i + 1;
                        switch (place) {
                            case 1: {
                                Location place1 = new Location(world, -73, 131, 168);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 2: {
                                Location place1 = new Location(world, -76, 131, 168);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 3: {
                                Location place1 = new Location(world, -72, 131, 168);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 4: {
                                Location place1 = new Location(world, -77, 131, 168);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 5: {
                                Location place1 = new Location(world, -73, 131, 166);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 6: {
                                Location place1 = new Location(world, -76, 131, 166);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 7: {
                                Location place1 = new Location(world, -70, 131, 168);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 8: {
                                Location place1 = new Location(world, -79, 131, 168);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 9: {
                                Location place1 = new Location(world, -72, 131, 166);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 10: {
                                Location place1 = new Location(world, -77, 131, 166);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 11: {
                                Location place1 = new Location(world, -69, 131, 168);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 12: {
                                Location place1 = new Location(world, -80, 131, 168);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 13: {
                                Location place1 = new Location(world, -70, 131, 166);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 14: {
                                Location place1 = new Location(world, -79, 131, 166);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 15: {
                                Location place1 = new Location(world, -73, 131, 164);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 16: {
                                Location place1 = new Location(world, -76, 131, 164);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 17: {
                                Location place1 = new Location(world, -72, 131, 164);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 18: {
                                Location place1 = new Location(world, -77, 131, 164);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 19: {
                                Location place1 = new Location(world, -69, 131, 166);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 20: {
                                Location place1 = new Location(world, -80, 131, 166);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 21: {
                                Location place1 = new Location(world, -70, 131, 164);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 22: {
                                Location place1 = new Location(world, -79, 131, 164);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 23: {
                                Location place1 = new Location(world, -69, 131, 164);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            case 24: {
                                Location place1 = new Location(world, -80, 131, 164);
                                place1.setYaw(0);
                                place1.setPitch(0);
                                member.teleport(place1);
                                place1.setYaw(-180);

                                ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.40, 0.5), EntityType.ARMOR_STAND);
                                armorStand.setVisible(false);
                                armorStand.setGravity(false);

                                armorStand.addPassenger(member);
                                break;
                            }

                            default:

                                break;

                        }
                    }
                    BanAPI.getApi().setOnCourt(player);

                    for (Player member: members) {
                        if (LanguageAPI.getApi().getLanguage(member) == 2) {
                            member.sendMessage(Statements.getPrefix().append(Component.text("Die Verhandlung hat begonnen!", NamedTextColor.LIGHT_PURPLE)));

                            ComponentBuilder wittnesChecker = Component.text("").append(Statements.getPrefix())
                                    .append(Component.text("Hast du etwas zu bezeugen? : ", NamedTextColor.GRAY)).toBuilder();
                            Component witnessYes = Component.text("[Ja]")
                                    .color(NamedTextColor.GREEN)
                                    .clickEvent(ClickEvent.runCommand("/court witness"))
                                    .hoverEvent(HoverEvent.showText(Component.text("Du wirst zu einem Zeugen").color(NamedTextColor.GREEN)));
                            wittnesChecker.append(witnessYes);
                            member.sendMessage(wittnesChecker.build());
                        } else {
                            member.sendMessage(Statements.getPrefix().append(Component.text("The negotiation has started!", NamedTextColor.LIGHT_PURPLE)));
                            ComponentBuilder wittnesChecker = Component.text("").append(Statements.getPrefix())
                                    .append(Component.text("Do you have something to testify? : ", NamedTextColor.GRAY)).toBuilder();
                            Component witnessYes = Component.text("[Yes]")
                                    .color(NamedTextColor.GREEN)
                                    .clickEvent(ClickEvent.runCommand("/court witness"))
                                    .hoverEvent(HoverEvent.showText(Component.text("You will get a witnesser").color(NamedTextColor.GREEN)));
                            wittnesChecker.append(witnessYes);
                            member.sendMessage(wittnesChecker.build());
                        }
                        TimerAPI.getApi().setOff(member, false);
                        TimerAPI.getApi().setTimer(member, true);
                        TimerAPI.getApi().setTime(member, 120);
                        TimerAPI.getApi().setRunning(member,true);
                    }
                    TimerAPI.getApi().setOff(prosecutor, false);
                    TimerAPI.getApi().setTimer(prosecutor, true);
                    TimerAPI.getApi().setTime(prosecutor, 120);
                    TimerAPI.getApi().setRunning(prosecutor,true);
                    TimerAPI.getApi().setOff(player, false);
                    TimerAPI.getApi().setTimer(player, true);
                    TimerAPI.getApi().setTime(player, 120);
                    TimerAPI.getApi().setRunning(player,true);
                    player.sendMessage(Statements.getPrefix().append(Component.text("Die Verhandlung hat begonnen!", NamedTextColor.LIGHT_PURPLE)));
                    prosecutor.sendMessage(Statements.getPrefix().append(Component.text("Die Verhandlung hat begonnen!", NamedTextColor.LIGHT_PURPLE)));
                }
                if (criminal == 3) {
                    Player prosecutor = Bukkit.getPlayer(BanAPI.getApi().getProsecutor(player));
                    if (isPlayerOffline(BanAPI.getApi().getProsecutor(player))) {
                        BanAPI.getApi().setUnlocked(player);

                        for (Player member: members) {
                            TimerAPI.getApi().setRunning(member, false);
                            TimerAPI.getApi().setTime(member, 0);
                            TimerAPI.getApi().setTimer(member, false);
                            TimerAPI.getApi().setOff(member, true);
                        }
                        TimerAPI.getApi().setOff(player, true);
                        TimerAPI.getApi().setTimer(player, false);
                        TimerAPI.getApi().setTime(player, 0);
                        TimerAPI.getApi().setRunning(player,false);
                        return;
                    }
                    if (isPlayerOffline(player.getUniqueId().toString())) {
                        BanAPI.getApi().setOnLock(player);
                        for (Player member: members) {
                            TimerAPI.getApi().setRunning(member, false);
                            TimerAPI.getApi().setTime(member, 0);
                            TimerAPI.getApi().setTimer(member, false);
                            TimerAPI.getApi().setOff(member, true);
                        }
                        TimerAPI.getApi().setOff(player, true);
                        TimerAPI.getApi().setTimer(player, false);
                        TimerAPI.getApi().setTime(player, 0);
                        TimerAPI.getApi().setRunning(player,false);

                        TimerAPI.getApi().setOff(prosecutor, true);
                        TimerAPI.getApi().setTimer(prosecutor, false);
                        TimerAPI.getApi().setTime(prosecutor, 0);
                        TimerAPI.getApi().setRunning(prosecutor,false);
                        return;
                    }

                    if (TimerAPI.getApi().getTime(player) <= 0) {
                        BanAPI.getApi().setOnLock(player);
                    }
                }
                if (criminal == 4) {
                    for (Player member: members) {
                        TimerAPI.getApi().setRunning(member, false);
                        TimerAPI.getApi().setTime(member, 0);
                        TimerAPI.getApi().setTimer(member, false);
                        TimerAPI.getApi().setOff(member, true);
                    }
                    UUID prosecutorUUID = UUID.fromString(BanAPI.getApi().getProsecutor(player));
                    Player prosecutor = Bukkit.getPlayer(prosecutorUUID);
                    TimerAPI.getApi().setOff(player, true);
                    TimerAPI.getApi().setTimer(player, false);
                    TimerAPI.getApi().setTime(player, 0);
                    TimerAPI.getApi().setRunning(player,false);
                    TimerAPI.getApi().setOff(prosecutor, true);
                    TimerAPI.getApi().setTimer(prosecutor, false);
                    TimerAPI.getApi().setTime(prosecutor, 0);
                    TimerAPI.getApi().setRunning(prosecutor,false);

                    if (freeVotes.size() >= judgedVotes.size()) {
                        for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                            if (onlineplayer == player) {
                                onlineplayer.sendMessage(" ");
                            }
                            if(LanguageAPI.getApi().getLanguage(onlineplayer) == 2) {
                                onlineplayer.sendMessage(Statements.getPrefix()
                                        .append(Component.text("|----------------------------------|", NamedTextColor.DARK_GRAY)));
                                onlineplayer.sendMessage(" ");
                                onlineplayer.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                                        .append(Component.text(" wurde von ", NamedTextColor.WHITE))
                                        .append(RankStatements.getRank(prosecutor))
                                        .append(Component.text(" vor 3 Minuten angeklagt!", NamedTextColor.WHITE)));
                                onlineplayer.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Sein genannter Grund ist: ", NamedTextColor.WHITE))
                                        .append(Component.text(BanAPI.getApi().getCriminalReason(player), NamedTextColor.BLUE)));
                                onlineplayer.sendMessage(Statements.getPrefix()
                                        .append(Component.text("Die Mehrheit war für die Freilassung von ", NamedTextColor.WHITE))
                                        .append(RankStatements.getRank(player)));
                                onlineplayer.sendMessage(Statements.getPrefix()
                                        .append(Component.text(freeVotes.size(), NamedTextColor.DARK_GREEN))
                                        .append(Component.text("/", NamedTextColor.WHITE))
                                        .append(Component.text(judgedVotes.size(), NamedTextColor.DARK_RED)));
                                onlineplayer.sendMessage(" ");
                                onlineplayer.sendMessage(Statements.getPrefix()
                                        .append(Component.text("|----------------------------------|", NamedTextColor.DARK_GRAY)));
                            } else {
                                onlineplayer.sendMessage(Statements.getPrefix()
                                        .append(Component.text("|----------------------------------|", NamedTextColor.DARK_GRAY)));
                                onlineplayer.sendMessage(" ");

                                onlineplayer.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                                        .append(Component.text(" was accused by ", NamedTextColor.WHITE))
                                        .append(RankStatements.getRank(prosecutor))
                                        .append(Component.text(" since 3 minutes!", NamedTextColor.WHITE)));
                                onlineplayer.sendMessage(Statements.getPrefix()
                                        .append(Component.text("His said reason is: ", NamedTextColor.WHITE))
                                        .append(Component.text(BanAPI.getApi().getCriminalReason(player), NamedTextColor.BLUE)));
                                onlineplayer.sendMessage(Statements.getPrefix()
                                        .append(Component.text("The majority supported the release of ", NamedTextColor.WHITE))
                                        .append(RankStatements.getRank(player)));
                                onlineplayer.sendMessage(Statements.getPrefix()
                                        .append(Component.text(freeVotes.size(), NamedTextColor.DARK_GREEN))
                                        .append(Component.text("/", NamedTextColor.WHITE))
                                        .append(Component.text(judgedVotes.size(), NamedTextColor.DARK_RED)));
                                onlineplayer.sendMessage(" ");
                                onlineplayer.sendMessage(Statements.getPrefix()
                                        .append(Component.text("|----------------------------------|", NamedTextColor.DARK_GRAY)));
                            }
                            BanAPI.getApi().setUnlocked(player);
                        }
                        break;
                    }
                    for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                        if (onlineplayer == player) {
                            onlineplayer.sendMessage(" ");
                        }

                        if(LanguageAPI.getApi().getLanguage(onlineplayer) == 2) {
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("|----------------------------------|", NamedTextColor.DARK_GRAY)));
                            onlineplayer.sendMessage(" ");
                            onlineplayer.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                                    .append(Component.text(" wurde von ", NamedTextColor.WHITE))
                                    .append(RankStatements.getRank(prosecutor))
                                    .append(Component.text(" vor 3 Minuten angeklagt!", NamedTextColor.WHITE)));
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Sein genannter Grund ist: ", NamedTextColor.WHITE))
                                    .append(Component.text(BanAPI.getApi().getCriminalReason(player), NamedTextColor.BLUE)));
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("Die Mehrheit war für die Bestrafung von ", NamedTextColor.WHITE))
                                    .append(RankStatements.getRank(player)));
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text(freeVotes.size(), NamedTextColor.DARK_GREEN))
                                    .append(Component.text("/", NamedTextColor.WHITE))
                                    .append(Component.text(judgedVotes.size(), NamedTextColor.DARK_RED)));
                            onlineplayer.sendMessage(" ");
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("|----------------------------------|", NamedTextColor.DARK_GRAY)));
                        } else {
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("|----------------------------------|", NamedTextColor.DARK_GRAY)));
                            onlineplayer.sendMessage(" ");

                            onlineplayer.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                                    .append(Component.text(" was accused by ", NamedTextColor.WHITE))
                                    .append(RankStatements.getRank(prosecutor))
                                    .append(Component.text(" since 3 minutes!", NamedTextColor.WHITE)));
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("His said reason is: ", NamedTextColor.WHITE))
                                    .append(Component.text(BanAPI.getApi().getCriminalReason(player), NamedTextColor.BLUE)));
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("The majority supported the punishment of ", NamedTextColor.WHITE))
                                    .append(RankStatements.getRank(player)));
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text(freeVotes.size(), NamedTextColor.DARK_GREEN))
                                    .append(Component.text("/", NamedTextColor.WHITE))
                                    .append(Component.text(judgedVotes.size(), NamedTextColor.DARK_RED)));
                            onlineplayer.sendMessage(" ");
                            onlineplayer.sendMessage(Statements.getPrefix()
                                    .append(Component.text("|----------------------------------|", NamedTextColor.DARK_GRAY)));
                        }
                    }
                    Integer cell = new Random().nextInt(5) + 1;
                    TimerAPI.getApi().setOff(player, false);
                    TimerAPI.getApi().setTime(player, 600);
                    TimerAPI.getApi().setTimer(player, true);
                    TimerAPI.getApi().setRunning(player,true);
                    player.addAttachment(Main.getInstance()).setPermission("worldedit.navigation.jumpto.command", false);
                    PermissionAttachment attachment = player.addAttachment(Main.getInstance());
                    attachment.setPermission("plugin.addcoins", true);
                    switch (cell) {
                        case 1: {
                            Location cell1 = new Location(Bukkit.getWorld("world"), -6.5, 103.5, 29.5);
                            player.teleport(cell1);
                            BanAPI.getApi().setLocked(player, 600, 1);
                        }
                        case 2: {
                            Location cell2 = new Location(Bukkit.getWorld("world"), -6.5, 112.5, 24.5);
                            player.teleport(cell2);
                            BanAPI.getApi().setLocked(player, 600, 2);
                        }
                        case 3: {
                            Location cell3 = new Location(Bukkit.getWorld("world"), -2.5, 107.5, 20.5);
                            player.teleport(cell3);
                            BanAPI.getApi().setLocked(player, 600, 3);
                        }
                        case 4: {
                            Location cell4 = new Location(Bukkit.getWorld("world"), 3.5, 111.5, 18.5);
                            player.teleport(cell4);
                            BanAPI.getApi().setLocked(player, 600, 4);
                        }
                        case 5: {
                            Location cell5 = new Location(Bukkit.getWorld("world"), -0.5, 115.5, 25.5);
                            player.teleport(cell5);
                            BanAPI.getApi().setLocked(player, 600, 5);
                        }

                        default:

                    }
                }
                if (criminal == 5) {
                    BanAPI.getApi().setLockTime(player, BanAPI.getApi().getCriminalTime(player) - 1);
                    if (BanAPI.getApi().getCriminalTime(player) == 0) {
                        BanAPI.getApi().setUnlocked(player);
                        player.addAttachment(Main.getInstance()).setPermission("worldedit.navigation.jumpto.command", true);
                        player.performCommand("/spawn");
                    }
                }
            }
        }, 20, 20);

         */
    }

    public boolean isPlayerOffline(String playerUUIDString) {
        UUID playerUUID = UUID.fromString(playerUUIDString);
        Player player = Bukkit.getPlayer(playerUUID);
        return player == null;
    }

    public Integer join(Player player) {
        if (BanAPI.getApi().isCriminal(player) != 0) {
            return 3;
        }
        if (members.size() >= 24) {
            return 404;
        }

        if (members.contains(player)) {
            return 507;
        }
        members.add(player);
        return 1;
    }

    public Integer pas(Player player) {
        if (!members.contains(player)) {
            return 507;
        }
        members.remove(player);
        return 1;
    }

    public Integer witness(Player player) {
        if (witnesser.size() >= 4) {
            return -1;
        }
        if (witnesser.contains(player)) {
            witnesser.remove(player);
            sitOn();
            return 1;
        } else {
            witnesser.add(player);
            for (int i = 0; i < witnesser.size(); i++) {
                Player witness = witnesser.get(i);
                World world = Bukkit.getWorld("world");
                int place = i + 1;
                switch (place) {
                    case 1: {
                        Location place1 = new Location(world, -65, 132, 170);
                        place1.setYaw(0);
                        place1.setPitch(90);
                        witness.teleport(place1);

                        ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                        armorStand.setVisible(false);
                        armorStand.setGravity(false);

                        armorStand.addPassenger(witness);
                        break;
                    }
                    case 2: {
                        Location place1 = new Location(world, -65, 132, 169);
                        place1.setYaw(0);
                        place1.setPitch(90);
                        witness.teleport(place1);

                        ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                        armorStand.setVisible(false);
                        armorStand.setGravity(false);

                        armorStand.addPassenger(witness);
                        break;
                    }
                    case 3: {
                        Location place1 = new Location(world, -65, 132, 168);
                        place1.setYaw(0);
                        place1.setPitch(90);
                        witness.teleport(place1);

                        ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                        armorStand.setVisible(false);
                        armorStand.setGravity(false);

                        armorStand.addPassenger(witness);
                        break;
                    }
                    case 4: {
                        Location place1 = new Location(world, -65, 132, 167);
                        place1.setYaw(0);
                        place1.setPitch(90);
                        witness.teleport(place1);

                        ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                        armorStand.setVisible(false);
                        armorStand.setGravity(false);

                        armorStand.addPassenger(witness);
                        break;
                    }
                    default:
                        break;
                }
            }
            return 0;
        }
    }

    public Integer judged(Player player) {
        if (judgedVotes.contains(player)) {
            return 0;
        }
        if (freeVotes.contains(player)) {
            freeVotes.remove(player);
            judgedVotes.add(player);
            return 1;
        }
        judgedVotes.add(player);
        return 1;
    }

    public Integer free(Player player) {
        if (freeVotes.contains(player)) {
            return 0;
        }
        if (judgedVotes.contains(player)) {
            judgedVotes.remove(player);
            freeVotes.add(player);
            return 1;
        }
        freeVotes.add(player);
        return 1;
    }

    public void sitOn() {
        for (int i = 0; i < members.size(); i++) {
            Player player = members.get(i);
            Player member = player;
            World world = Bukkit.getWorld("world");
            int place = i + 1;
            switch (place) {
                case 1: {
                    Location place1 = new Location(world, -73, 131, 168);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 2: {
                    Location place1 = new Location(world, -76, 131, 168);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 3: {
                    Location place1 = new Location(world, -72, 131, 168);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 4: {
                    Location place1 = new Location(world, -77, 131, 168);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 5: {
                    Location place1 = new Location(world, -73, 131, 166);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 6: {
                    Location place1 = new Location(world, -76, 131, 166);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 7: {
                    Location place1 = new Location(world, -70, 131, 168);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 8: {
                    Location place1 = new Location(world, -79, 131, 168);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 9: {
                    Location place1 = new Location(world, -72, 131, 166);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 10: {
                    Location place1 = new Location(world, -77, 131, 166);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 11: {
                    Location place1 = new Location(world, -69, 131, 168);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 12: {
                    Location place1 = new Location(world, -80, 131, 168);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 13: {
                    Location place1 = new Location(world, -70, 131, 166);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 14: {
                    Location place1 = new Location(world, -79, 131, 166);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 15: {
                    Location place1 = new Location(world, -73, 131, 164);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 16: {
                    Location place1 = new Location(world, -76, 131, 164);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 17: {
                    Location place1 = new Location(world, -72, 131, 164);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 18: {
                    Location place1 = new Location(world, -77, 131, 164);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 19: {
                    Location place1 = new Location(world, -69, 131, 166);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 20: {
                    Location place1 = new Location(world, -80, 131, 166);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 21: {
                    Location place1 = new Location(world, -70, 131, 164);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 22: {
                    Location place1 = new Location(world, -79, 131, 164);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 23: {
                    Location place1 = new Location(world, -69, 131, 164);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                case 24: {
                    Location place1 = new Location(world, -80, 131, 164);
                    place1.setYaw(0);
                    place1.setPitch(0);
                    player.teleport(place1);

                    ArmorStand armorStand = (ArmorStand) place1.getWorld().spawnEntity(place1.add(0.5, -1.10, 0.5), EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);

                    armorStand.addPassenger(member);
                    break;
                }

                default:

                    break;

            }
        }
    }
}
