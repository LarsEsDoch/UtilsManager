package de.lars.utilsManager.listener;

import de.lars.apiManager.backpackAPI.BackpackAPI;
import de.lars.apiManager.banAPI.BanAPI;
import de.lars.apiManager.chunkAPI.ChunkAPI;
import de.lars.apiManager.coinAPI.CoinAPI;
import de.lars.apiManager.dataAPI.DataAPI;
import de.lars.apiManager.languageAPI.LanguageAPI;
import de.lars.apiManager.playersAPI.PlayerAPI;
import de.lars.apiManager.questAPI.QuestAPI;
import de.lars.apiManager.rankAPI.RankAPI;
import de.lars.apiManager.timerAPI.TimerAPI;
import de.lars.apiManager.toggleAPI.ToggleAPI;
import de.lars.utilsManager.Main;
import de.lars.utilsManager.scoreboard.TestScoreboard;
import de.lars.utilsManager.utils.RankStatements;
import de.lars.utilsManager.utils.Statements;
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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;

public class JoinListener implements Listener {


    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        //Main.getInstance().getQuestManager().sendDailyQuests(player);
        if (ToggleAPI.getApi().getScoreboardToggle(player)) {
            new TestScoreboard(player);
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 50, 0));

        if (!PlayerAPI.getApi().doesUserExist(player) ||
                !CoinAPI.getApi().doesUserExist(player) ||
                !BackpackAPI.getApi().doesUserExist(player) ||
                !BanAPI.getApi().doesUserExist(player) ||
                !BanAPI.getApi().doesUserExist(player) ||
                !LanguageAPI.getApi().doesUserExist(player) ||
                !BanAPI.getApi().doesUserExist(player) ||
                !QuestAPI.getApi().doesUserExist(player) ||
                !RankAPI.getApi().doesUserExist(player) ||
                !TimerAPI.getApi().doesUserExist(player) ||
                !ToggleAPI.getApi().doesUserExist(player) ||
                !player.hasPlayedBefore()) {
            player.teleport(new Location(Bukkit.getWorld("world"), -205.5, 78.0, -102.5, -90, 0));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), bukkitTask -> {
                firstJoin(player);
            }, 1);
        } else {
            if (ToggleAPI.getApi().getBedToggle(player)) {
                Location loc = new Location(Bukkit.getWorld("world"), -205.5, 78.0, -102.5, -90, 0);
                player.teleport(loc);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
            } else {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 2);
            }
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> showTitleGe(player), 35);
                Component message = Component.text("Willkommen zurück!")
                        .color(NamedTextColor.GOLD);
                player.sendMessage(Statements.getPrefix().append(message));
            } else {
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> showTitle(player), 35);
                Component message = Component.text("Welcome back!")
                        .color(NamedTextColor.GOLD);
                player.sendMessage(Statements.getPrefix().append(message));
            }

            if (!CoinAPI.getApi().getGifts(player).isEmpty()) {
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
                    //player.sendMessage(text2);
                } else {
                    //player.sendMessage(text2e);
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
            Main.getInstance().getRangManager().setRange(player);
            Main.getInstance().getRangManager().setPerm();
            Main.getInstance().getTablistManager().setTabList(player);
            Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), bukkitTask -> {
                //Main.getInstance().getDiscordBot().sendPlayerOnMessage(player);
                Main.getInstance().getTablistManager().setAllPlayerTeams();
            }, 20);
        }


        event.joinMessage(Component.text(""));
    }

    public void firstJoin(Player player) {
        LanguageAPI.getApi().setLanguage(player, 2);

        RankAPI.getApi().setRankID(player, 5, 30, Calendar.getInstance());
        RankAPI.getApi().setPrefix(player, 6);
        //RankAPI.getApi().setPrefix(player, 7);
        BackpackAPI.getApi().setSlots(player, 27);
        ToggleAPI.getApi().setBedToggle(player, false);



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
        Main.getInstance().getRangManager().setRange(player);
        Main.getInstance().getRangManager().setPerm();
        Main.getInstance().getTablistManager().setTabList(player);
        Component LanguageText = getLanguageText();
        player.sendMessage(LanguageText);
        player.sendMessage(Statements.getPrefix().append(Component.text("Falls du Hilfe brauchst nutze die Serverfunktion: ", NamedTextColor.GOLD))
                .append(Component.text("/help ", NamedTextColor.GRAY))
                .append(Component.text("oder [Hilfe hier]", NamedTextColor.AQUA).clickEvent(ClickEvent.runCommand("/help"))));
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), bukkitTask -> {
            //sendOnMessage(player);
            Main.getInstance().getTablistManager().setAllPlayerTeams();
            showTitleFi(player);
        }, 20);
    }

    private static @NotNull Component getLanguageText() {
        Component lMG = Component.text("[German]", NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/language deutsch"));
        Component lME = Component.text("[English]", NamedTextColor.BLUE).clickEvent(ClickEvent.runCommand("/language english"));
        Component LanguageText = Statements.getPrefix().append(Component.text("Choose your language: ", NamedTextColor.GRAY))
                .append(lMG).append(Component.text(" / ", NamedTextColor.GRAY)).append(lME);
        return LanguageText;
    }

    private static @NotNull Component getComponent() {
        Component CGJ = Component.text("[Yes]", NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/gifts"))
                .hoverEvent(HoverEvent.showText(Component.text("Take it!", NamedTextColor.GRAY)));
        Component CGN = Component.text("[No]", NamedTextColor.BLUE).clickEvent(ClickEvent.runCommand(""));
        Component GiftText = Statements.getPrefix().append(Component.text("Take your starter gift: ", NamedTextColor.GRAY))
                .append(CGJ).append(Component.text(" / ", NamedTextColor.GRAY)).append(CGN);
        return GiftText;
    }

    @EventHandler
    public void onJoinCriminal(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Integer criminal = BanAPI.getApi().isCriminal(player);
        if (criminal == 5) {
            Integer cell = BanAPI.getApi().getCell(player);
            BanAPI.getApi().setLockTime(player, BanAPI.getApi().getCriminalTime(player) - 1);
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

            if(!ToggleAPI.getApi().getScoreboardToggle(player)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Title.Times times = Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(1000), Duration.ofMillis(1000));
                        Component time = Component.text("Playtime: ", NamedTextColor.AQUA)
                                .append(Component.text(PlayerAPI.getApi().getPlaytime(player) / 3600 + " Hours", NamedTextColor.LIGHT_PURPLE));
                        player.showTitle(Title.title(time, Component.text(""), times));
                    }
                }.runTaskLaterAsynchronously(Main.getInstance(), 100);
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

            if(!ToggleAPI.getApi().getScoreboardToggle(player)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Title.Times times = Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(1000), Duration.ofMillis(1000));
                        Component time = Component.text("Spielzeit: ", NamedTextColor.AQUA)
                                .append(Component.text(PlayerAPI.getApi().getPlaytime(player) / 3600 + " Stunden", NamedTextColor.LIGHT_PURPLE));
                        player.showTitle(Title.title(time, Component.text(""), times));
                    }
                }.runTaskLaterAsynchronously(Main.getInstance(), 100);
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