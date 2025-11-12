package dev.lars.utilsmanager.listener.player;

import dev.lars.apimanager.apis.courtAPI.CourtAPI;
import dev.lars.apimanager.apis.economyAPI.EconomyAPI;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.limitAPI.LimitAPI;
import dev.lars.apimanager.apis.playerAPI.PlayerAPI;
import dev.lars.apimanager.apis.playerSettingsAPI.PlayerSettingsAPI;
import dev.lars.apimanager.apis.prefixAPI.PrefixAPI;
import dev.lars.apimanager.apis.rankAPI.RankAPI;
import dev.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import dev.lars.utilsmanager.UtilsManager;
import dev.lars.utilsmanager.scoreboard.Scoreboard;
import dev.lars.utilsmanager.utils.RankStatements;
import dev.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UtilsManager.getInstance().getQuestManager().sendQuests(player);
        if (PlayerSettingsAPI.getApi().getScoreboardToggle(player)) {
            new Scoreboard(player);
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 50, 0));

        if (!player.hasPlayedBefore()) {
            player.teleport(new Location(Bukkit.getWorld("world"), -205.5, 78.0, -102.5, -90, 0));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
            LimitAPI.getApi().setMaxChunks(player, 128);
            Bukkit.getScheduler().runTaskLater(UtilsManager.getInstance(), bukkitTask -> {
                firstJoin(player);
            }, 1);
        } else {
            if (PlayerSettingsAPI.getApi().getBedToggle(player)) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 2);
            } else {
                Location loc = ServerSettingsAPI.getApi().getSpawnLocation();
                if (loc == null) {
                    player.teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
                } else {
                    player.teleport(loc);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
                }
            }
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                Bukkit.getScheduler().runTaskLater(UtilsManager.getInstance(), () -> showTitleGe(player), 35);
                Component message = Component.text("Willkommen zurück!")
                        .color(NamedTextColor.GOLD);
                player.sendMessage(Statements.getPrefix().append(message));
            } else {
                Bukkit.getScheduler().runTaskLater(UtilsManager.getInstance(), () -> showTitle(player), 35);
                Component message = Component.text("Welcome back!")
                        .color(NamedTextColor.GOLD);
                player.sendMessage(Statements.getPrefix().append(message));
            }

            if (!EconomyAPI.getApi().getGifts(player).isEmpty()) {
                Component cmj = Component.text("[Yes]")
                        .color(NamedTextColor.GREEN)
                        .clickEvent(ClickEvent.runCommand("/gifts"))
                        .hoverEvent(HoverEvent.showText(Component.text("Take it").color(NamedTextColor.GRAY)));

                Component cmn = Component.text("[No]")
                        .color(NamedTextColor.RED)
                        .clickEvent(ClickEvent.runCommand(""));

                Component text2 = Statements.getPrefix().append(Component.text("")
                        .append(Component.text("Du hast noch Geschenke! Hier siehst du sie: ", NamedTextColor.GRAY))
                        .append(cmj)
                        .append(Component.text(" / ", NamedTextColor.GRAY))
                        .append(cmn));

                Component text2e = Statements.getPrefix().append(Component.text("")
                        .append(Component.text("You still have gifts! Here you can see it: ", NamedTextColor.GRAY))
                        .append(cmj)
                        .append(Component.text(" / ", NamedTextColor.GRAY))
                        .append(cmn));

                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(text2);
                } else {
                    player.sendMessage(text2e);
                }
            }

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (LanguageAPI.getApi().getLanguage(p) == 2) {
                    p.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                            .append(Component.text(" hat den Server betreten.", NamedTextColor.WHITE)));
                } else {
                    p.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                            .append(Component.text(" joined the server.", NamedTextColor.WHITE)));
                }
            }
            UtilsManager.getInstance().getRankManager().setPermisssions(player);
            UtilsManager.getInstance().getTablistManager().setTabList(player);
            Bukkit.getScheduler().runTaskAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
                StringBuilder message;

                if (Bukkit.getOnlinePlayers().size() > 1) {
                    message = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " ist dem Server beigetreten.\n\nEs sind aktuell " + Bukkit.getOnlinePlayers().size() + " Spieler online.\n");
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        message.append(RankStatements.getUnformattedRank(onlinePlayer)).append(onlinePlayer.getName()).append("\n");
                    }
                } else {
                    message = new StringBuilder(RankStatements.getUnformattedRank(player) + player.getName() + " ist dem Server beigetreten.\n\nEs ist aktuell nur er online.");
                }

                UtilsManager.getInstance().getDiscordBot().sendPlayerMessage(String.valueOf(message));
                UtilsManager.getInstance().getTablistManager().setAllPlayerTeams();
            });
        }


        event.joinMessage(Component.text(""));
    }

    public void firstJoin(Player player) {
        LanguageAPI.getApi().setLanguage(player, 2);

        RankAPI.getApi().setRank(player, 5, 30);
        PrefixAPI.getApi().setColor(player, NamedTextColor.GOLD);
        LimitAPI.getApi().setBackpackSlots(player, 27);



        Component GiftText = getComponent();
        //player.sendMessage(GiftText);


        for (Player p : Bukkit.getOnlinePlayers()) {
            if (LanguageAPI.getApi().getLanguage(p) == 2) {
                p.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                        .append(Component.text(" hat den Server zum ersten mal betreten.", NamedTextColor.WHITE)));
            } else {
                p.sendMessage(Statements.getPrefix().append(RankStatements.getRank(player))
                        .append(Component.text(" joined the server for the first time.", NamedTextColor.WHITE)));
            }
        }
        player.sendMessage(Statements.getPrefix().append(Component.text("Willkommen auf diesem Server! Habe Spaß und genieße es.", NamedTextColor.GOLD, TextDecoration.BOLD)));
        UtilsManager.getInstance().getRankManager().setPermisssions(player);
        UtilsManager.getInstance().getTablistManager().setTabList(player);
        Component LanguageText = getLanguageText();
        player.sendMessage(LanguageText);
        player.sendMessage(Statements.getPrefix().append(Component.text("Falls du Hilfe brauchst nutze die Serverfunktion: ", NamedTextColor.GOLD))
                .append(Component.text("/help ", NamedTextColor.GRAY))
                .append(Component.text("oder [Hilfe hier]", NamedTextColor.AQUA).clickEvent(ClickEvent.runCommand("/help"))));
        Bukkit.getScheduler().runTaskLaterAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            UtilsManager.getInstance().getTablistManager().setAllPlayerTeams();
            showTitleFi(player);
        }, 20);
    }

    private static @NotNull Component getLanguageText() {
        Component lMG = Component.text("[German]", NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/language deutsch"));
        Component lME = Component.text("[English]", NamedTextColor.BLUE).clickEvent(ClickEvent.runCommand("/language english"));
        return Statements.getPrefix().append(Component.text("Choose your language: ", NamedTextColor.GRAY))
                .append(lMG).append(Component.text(" / ", NamedTextColor.GRAY)).append(lME);
    }

    private static @NotNull Component getComponent() {
        Component CGJ = Component.text("[Yes]", NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/gifts"))
                .hoverEvent(HoverEvent.showText(Component.text("Take it!", NamedTextColor.GRAY)));
        Component CGN = Component.text("[No]", NamedTextColor.BLUE).clickEvent(ClickEvent.runCommand(""));
        return Statements.getPrefix().append(Component.text("Take your starter gift: ", NamedTextColor.GRAY))
                .append(CGJ).append(Component.text(" / ", NamedTextColor.GRAY)).append(CGN);
    }

    @EventHandler
    public void onJoinCriminal(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Integer criminal = CourtAPI.getApi().getStatus(player);
        if (criminal == 5) {
            Integer cell = CourtAPI.getApi().getCell(player);
            CourtAPI.getApi().setTime(player, CourtAPI.getApi().getTime(player) - 1);
            switch (cell) {
                case 1: {
                    Location cell1 = new Location(Bukkit.getWorld("world"), -6.5, 103.5, 29.5);
                    player.teleport(cell1);
                    break;
                }
                case 2: {
                    Location cell2 = new Location(Bukkit.getWorld("world"), -6.5, 112.5, 24.5);
                    player.teleport(cell2);
                    break;
                }
                case 3: {
                    Location cell3 = new Location(Bukkit.getWorld("world"), -2.5, 107.5, 20.5);
                    player.teleport(cell3);
                    break;
                }
                case 4: {
                    Location cell4 = new Location(Bukkit.getWorld("world"), 3.5, 111.5, 18.5);
                    player.teleport(cell4);
                    break;
                }
                case 5: {
                    Location cell5 = new Location(Bukkit.getWorld("world"), -0.5, 115.5, 25.5);
                    player.teleport(cell5);
                    break;
                }

                default:
                    break;
            }
        }
    }

    public void showTitleFi(Player player) {
        new Thread(() -> {
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);

            String text = player.getName();
            int length = text.length();
            int center = Math.floorDiv(length, 2);
            Component visibleText;
            int percent;
            NamedTextColor color;
            Component subTitle = Component.text("");

            for (int i = 0; i <= center; i++) {
                visibleText = Component.text(text.substring(center - i, center + i));

                percent = (int) (((double) i / center) * 100);

                if (percent >= 50) {
                    color = NamedTextColor.YELLOW;

                    if (percent >= 60) {
                        if (month == 12 || month == 1 || month == 2) {
                            subTitle = Component.text("Happy ", NamedTextColor.WHITE)
                                    .append(Component.text("Winter!", NamedTextColor.AQUA));
                        } else if (month >= 3 && month <= 5) {
                            subTitle = Component.text("Happy ", NamedTextColor.WHITE)
                                    .append(Component.text("Spring!", NamedTextColor.GREEN));
                        } else if (month >= 6 && month <= 8) {
                            subTitle = Component.text("Happy ", NamedTextColor.WHITE)
                                    .append(Component.text("Summer!", NamedTextColor.DARK_GREEN));
                        } else {
                            subTitle = Component.text("Happy ", NamedTextColor.WHITE)
                                    .append(Component.text("Autumn!", NamedTextColor.GOLD));
                        }
                        if (month == 12 & day == 24) {
                            subTitle = Component.text("Merry ", NamedTextColor.GREEN)
                                    .append(Component.text("Christmas!", NamedTextColor.RED));
                        }
                        if (month == 12 & day == 31) {
                            subTitle = Component.text("Happy ", NamedTextColor.GREEN)
                                    .append(Component.text("new", NamedTextColor.GOLD))
                                    .append(Component.text(" Year!", NamedTextColor.AQUA));
                        }
                        if (month == 8 & day == 31) {
                            subTitle = Component.text("Scary ", NamedTextColor.BLACK)
                                    .append(Component.text("Halloween!", NamedTextColor.GOLD));
                        }
                        LocalDate ostern = berechneOstern(Calendar.YEAR);
                        if (month == ostern.getMonthValue() & day == ostern.getDayOfMonth()) {
                            subTitle = Component.text("Happy ", NamedTextColor.GREEN)
                                    .append(Component.text("Easter!", NamedTextColor.LIGHT_PURPLE));
                        }
                    }
                    if (percent >= 75) {
                        color = NamedTextColor.GREEN;
                    }
                } else {
                    if (percent >= 25) {
                        color = NamedTextColor.GOLD;
                    } else {
                        color = NamedTextColor.RED;
                    }
                    subTitle = Component.text("Welcome!", NamedTextColor.GOLD);

                }

                Title.Times times = Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(5000), Duration.ofMillis(0));
                player.showTitle(Title.title(visibleText.color(color), subTitle, times));
                player.spawnParticle(Particle.FALLING_HONEY, player.getLocation(), 50, 0.5, 0.5, 0.5, 1);
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            visibleText = Component.text("≪ ", NamedTextColor.GOLD)
                    .append(Component.text(player.getName(), NamedTextColor.GREEN))
                    .append(Component.text(" ≫", NamedTextColor.GOLD));
            Title.Times times = Title.Times.times(Duration.ofMillis(0), Duration.ofMillis(3000), Duration.ofMillis(500));
            player.showTitle(Title.title(visibleText, subTitle, times));
            player.spawnParticle(Particle.FALLING_HONEY, player.getLocation(), 50, 0.5, 0.5, 0.5, 1);
        }).start();

    }

    public void showTitle(Player player) {
        new Thread(() -> {
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);

            String playerName = player.getName();
            int length = playerName.length();
            int center = Math.floorDiv(length, 2);
            Component visibleText;
            int percent;
            NamedTextColor color;
            Component subTitle = Component.text("");

            for (int i = 0; i <= center; i++) {
                visibleText = Component.text(playerName.substring(center - i, center + i));

                percent = (int) (((double) i / center) * 100);

                if (percent >= 50) {
                    color = NamedTextColor.YELLOW;
                    if (percent >= 60) {
                        if (month == 12 || month == 1 || month == 2) {
                            subTitle = Component.text("Happy ", NamedTextColor.WHITE)
                                    .append(Component.text("Winter!", NamedTextColor.AQUA));
                        } else if (month >= 3 && month <= 5) {
                            subTitle = Component.text("Happy ", NamedTextColor.WHITE)
                                    .append(Component.text("Spring!", NamedTextColor.GREEN));
                        } else if (month >= 6 && month <= 8) {
                            subTitle = Component.text("Happy ", NamedTextColor.WHITE)
                                    .append(Component.text("Summer!", NamedTextColor.DARK_GREEN));
                        } else {
                            subTitle = Component.text("Happy ", NamedTextColor.WHITE)
                                    .append(Component.text("Autumn!", NamedTextColor.GOLD));
                        }
                        if (month == 12 & day == 24) {
                            subTitle = Component.text("Merry ", NamedTextColor.GREEN)
                                    .append(Component.text("Christmas!", NamedTextColor.RED));
                        }
                        if (month == 12 & day == 31) {
                            subTitle = Component.text("Happy ", NamedTextColor.GREEN)
                                    .append(Component.text("new", NamedTextColor.GOLD))
                                    .append(Component.text(" Year!", NamedTextColor.AQUA));
                        }
                        if (month == 8 & day == 31) {
                            subTitle = Component.text("Scary ", NamedTextColor.BLACK)
                                    .append(Component.text("Halloween!", NamedTextColor.GOLD));
                        }
                        LocalDate ostern = berechneOstern(Calendar.YEAR);
                        if (month == ostern.getMonthValue() & day == ostern.getDayOfMonth()) {
                            subTitle = Component.text("Happy ", NamedTextColor.GREEN)
                                    .append(Component.text("Easter!", NamedTextColor.LIGHT_PURPLE));
                        }
                    }

                    if (percent >= 75) {
                        color = NamedTextColor.GREEN;
                    }
                } else {
                    if (percent >= 25) {
                        color = NamedTextColor.GOLD;
                    } else {
                        color = NamedTextColor.RED;
                    }
                    subTitle = Component.text("Welcome back!", NamedTextColor.GOLD);

                }
                Title.Times times = Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(5000), Duration.ofMillis(0));
                player.showTitle(Title.title(visibleText.color(color), subTitle, times));
                player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation(), 50, 0.5, 0.5, 0.5, 1);

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            visibleText = Component.text("≪ ", NamedTextColor.GOLD)
                    .append(Component.text(player.getName(), NamedTextColor.GREEN))
                    .append(Component.text(" ≫", NamedTextColor.GOLD));
            Title.Times times = Title.Times.times(Duration.ofMillis(0), Duration.ofMillis(3000), Duration.ofMillis(500));
            player.showTitle(Title.title(visibleText, subTitle, times));
            player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation(), 50, 0.5, 0.5, 0.5, 1);

            if(!PlayerSettingsAPI.getApi().getScoreboardToggle(player)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Title.Times times = Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(1000), Duration.ofMillis(1000));
                        Component time = Component.text("Playtime: ", NamedTextColor.AQUA)
                                .append(Component.text(PlayerAPI.getApi().getPlaytime(player) / 3600 + " Hours", NamedTextColor.LIGHT_PURPLE));
                        player.showTitle(Title.title(time, Component.text(""), times));
                    }
                }.runTaskLaterAsynchronously(UtilsManager.getInstance(), 100);
            }

        }).start();
    }

    public void showTitleGe(Player player) {
        new Thread(() -> {
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);

            String playerName = player.getName();
            int length = playerName.length();
            int center = Math.floorDiv(length, 2);
            Component visibleText;
            int percent;
            NamedTextColor color;
            Component subTitle = Component.text("");

            for (int i = 0; i <= center; i++) {
                visibleText = Component.text(playerName.substring(center - i, center + i));

                percent = (int) (((double) i / center) * 100);

                if (percent >= 50) {
                    color = NamedTextColor.YELLOW;
                    if (percent >= 60) {
                        if (month == 12 || month == 1 || month == 2) {
                            subTitle = Component.text("Fröhlichen ", NamedTextColor.WHITE)
                                    .append(Component.text("Winter!", NamedTextColor.AQUA));
                        } else if (month >= 3 && month <= 5) {
                            subTitle = Component.text("Fröhlichen ", NamedTextColor.WHITE)
                                    .append(Component.text("Frühling!", NamedTextColor.GREEN));
                        } else if (month >= 6 && month <= 8) {
                            subTitle = Component.text("Fröhlichen ", NamedTextColor.WHITE)
                                    .append(Component.text("Sommer!", NamedTextColor.DARK_GREEN));
                        } else {
                            subTitle = Component.text("Fröhlichen ", NamedTextColor.WHITE)
                                    .append(Component.text("Herbst!", NamedTextColor.GOLD));
                        }
                        if (month == 12 & day == 24) {
                            subTitle = Component.text("Fröhliche", NamedTextColor.GREEN)
                                    .append(Component.text(" Weihnachten!", NamedTextColor.RED));
                        }
                        if (month == 12 & day == 31) {
                            subTitle = Component.text("Fröhliches", NamedTextColor.GREEN)
                                    .append(Component.text(" neues", NamedTextColor.GOLD))
                                    .append(Component.text(" Jahr!", NamedTextColor.AQUA));
                        }
                        if (month == 8 & day == 31) {
                            subTitle = Component.text("Gruseliges ", NamedTextColor.BLACK)
                                    .append(Component.text("Halloween!", NamedTextColor.GOLD));
                        }
                        LocalDate ostern = berechneOstern(Calendar.YEAR);
                        if (month == ostern.getMonthValue() & day == ostern.getDayOfMonth()) {
                            subTitle = Component.text("Fröhliche ", NamedTextColor.GREEN)
                                    .append(Component.text("Ostern!", NamedTextColor.LIGHT_PURPLE));
                        }
                    }

                    if (percent >= 75) {
                        color = NamedTextColor.GREEN;
                    }
                } else {
                    if (percent >= 25) {
                        color = NamedTextColor.GOLD;
                    } else {
                        color = NamedTextColor.RED;
                    }
                    subTitle = Component.text("Willkommen zurück!");

                }

                Title.Times times = Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(5000), Duration.ofMillis(0));
                player.showTitle(Title.title(visibleText.color(color), subTitle, times));
                player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation(), 50, 0.5, 0.5, 0.5, 1);
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            visibleText = Component.text("≪ ", NamedTextColor.GOLD)
                    .append(Component.text(player.getName(), NamedTextColor.GREEN))
                    .append(Component.text(" ≫", NamedTextColor.GOLD));
            Title.Times times = Title.Times.times(Duration.ofMillis(0), Duration.ofMillis(3000), Duration.ofMillis(500));
            player.showTitle(Title.title(visibleText, subTitle, times));
            player.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation(), 50, 0.5, 0.5, 0.5, 1);

            if(!PlayerSettingsAPI.getApi().getScoreboardToggle(player)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Title.Times times = Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(1000), Duration.ofMillis(1000));
                        Component time = Component.text("Spielzeit: ", NamedTextColor.AQUA)
                                .append(Component.text(PlayerAPI.getApi().getPlaytime(player) / 3600 + " Stunden", NamedTextColor.LIGHT_PURPLE));
                        player.showTitle(Title.title(time, Component.text(""), times));
                    }
                }.runTaskLaterAsynchronously(UtilsManager.getInstance(), 100);
            }
        }).start();
    }

    public static LocalDate berechneOstern(int year) {
        int k = year / 100;
        int m = 15 + (3 * k + 3) / 4 - (8 * k + 13) / 25;
        int s = 2 - (3 * k + 3) / 4;
        int a = year % 19;
        int d = (19 * a + m) % 30;
        int r = (d + a / 11) / 29 + (d + a / 11 == 28 && a > 10 ? 1 : 0);
        int og = 21 + d - r;
        int sz = 7 - (year + year / 4 + s) % 7;
        int oe = 7 - (og - sz) % 7;
        int os = og + oe;

        if (os <= 31) {
            return LocalDate.of(year, 3, os);
        } else {
            return LocalDate.of(year, 4, os - 31);
        }
    }
}