package de.lars.utilsmanager.scoreboard;

import dev.lars.apimanager.apis.economyAPI.EconomyAPI;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.playerAPI.PlayerAPI;
import dev.lars.apimanager.apis.questAPI.QuestAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.utils.RankStatements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Objects;

public class Scoreboard extends ScoreboardBuilder {

    private int coins;

    String[] questsDe = {"Werfe 10 Eier", "Bekomme 2 mal Hühner aus einem Ei-Wurf", "Triff dich 5 mal mit einem Pfeil", "Lasse 3 Bäume wachsen", "Esse 10 rohe Kartoffeln nacheinander",
            "Bekomme 10 mal Flint aus gravel", "Ziehe eine Schwarze Leder Rüstung an", "Esse 4 mal ein Keks und trinke danach Milch", "Töte 20 Zombies", "Fische 2 mal Rüstung", "Laufe 1000 Blöcke",
            "Füttere 2 Pferde mit Gold-Äpfeln", "Nimm eine Blazze an die Leine mit einer Angel", "Koche 5 verschiedene Arten von Nahrung", "Ernte eine Ackerfläche von 100 Blöcken",
            "Zähme 10 Wölfe", "Fange 5 Pufferfische", "3 Red-cats füttern und zähmen", "Töte 5 Ghasts mit ihren eigenen Feuerbällen", "Töte ein Eisengolem mit einem Kuchen", "Sammle 64 Äpfel",
            "Erreiche Level 50"};
    String[] questsEn = {"Throw 10 Eggs", "Get chickens from one egg litter twice", "Hit yourself 5 times with an arrow", "Grow 3 trees", "Eat 10 raw potatoes in a row",
            "Get Flint from gravel 10 times", "Put on black leather armor", "Eat a cookie 4 times and then drink milk", "Kill 20 zombies", "Fish 2 times armor", "Run 1000 blocks",
            "Feed 2 horses with gold apples", "Put a Blazze on a line with a fishing rod", "Cook 5 different types of food", "Harvest a field of 100 blocks",
            "Tame 10 wolves", "Catch 5 puffer fish", "Feed and tame 3 red-cats", "Kill 5 Ghasts with their own fireballs", "Kill an Iron Golem with a Cake", "Collect 64 apples",
            "Reach level 50"};

    public Scoreboard(Player player) {
        super(player, Component.text("  A Server  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD));;
        coins = 0;

        run();
    }

    @Override
    public void createScoreboard() {
        if (mode) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                setScore(Component.text("Dein Rang:", NamedTextColor.DARK_AQUA), 14);
            } else {
                setScore(Component.text("Your rank:", NamedTextColor.DARK_AQUA), 14);
            }

            setScore(Component.text(">> ", NamedTextColor.GRAY).append(RankStatements.getCleanRank(player)), 13);
            setScore(Component.text(""), 12);

            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                setScore(Component.text("Deine Kontostand:", NamedTextColor.DARK_AQUA), 11);
            } else {
                setScore(Component.text("Your balance:", NamedTextColor.DARK_AQUA), 11);
            }
            setScore(Component.text(""), 10);
            setScore(Component.text(""), 9);
        } else {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                setScore(Component.text("Deine Tode:", NamedTextColor.DARK_AQUA), 11);
            } else {
                setScore(Component.text("Your deaths:", NamedTextColor.DARK_AQUA), 11);
            }
            setScore(Component.text(""), 10);
            setScore(Component.text(""), 9);
        }

        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            setScore(Component.text("Serverzeit:", NamedTextColor.DARK_AQUA), 8);
        } else {
            setScore(Component.text("Servertime:", NamedTextColor.DARK_AQUA), 8);
        }
        setScore(Component.text(""), 7);
        setScore(Component.text(""), 6);

        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            setScore(Component.text("Spielzeit:", NamedTextColor.DARK_AQUA), 5);
        } else {
            setScore(Component.text("Playtime:", NamedTextColor.DARK_AQUA), 5);
        }
        setScore(Component.text(""), 4);
        if (mode) {
            setScore(Component.text(""), 3);

            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                setScore(Component.text("Tägliche Aufgabe:", NamedTextColor.DARK_AQUA), 2);
            } else {
                setScore(Component.text("Daily quest:", NamedTextColor.DARK_AQUA), 2);
            }

            setScore(Component.text(""), 1);
            setScore(Component.text(""), 0);
        } else {
            setScore(Component.text(""), 3);

            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                setScore(Component.text("Deine Kordinaten:", NamedTextColor.DARK_AQUA), 2);
            } else {
                setScore(Component.text("Your coordinates:", NamedTextColor.DARK_AQUA), 2);
            }
            setScore(Component.text(""), 1);
        }



    }

    @Override
    public void update() {

    }

    public boolean mode = false;

    private void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            if (mode) {
                coins = EconomyAPI.getApi().getBalance(player);

                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatierteZahl = formatter.format(coins);


                setScore(Component.text(">> ", NamedTextColor.GRAY)
                        .append(Component.text(formatierteZahl + "$", NamedTextColor.GREEN)), 10);
            } else {
                int deaths = player.getStatistic(org.bukkit.Statistic.DEATHS);
                setScore(Component.text(">> ", NamedTextColor.GRAY)
                .append(Component.text(deaths, NamedTextColor.RED)), 10);
            }

            long daytime = (Objects.requireNonNull(Bukkit.getWorld("world")).getTime() + 6000) % 24000;
            int hours = (int) (daytime / 1000);
            int minutes = (int) ((daytime % 1000) * 60 / 1000);
            String timeString = String.format("%02d:%02d", hours, minutes);

            setScore(Component.text(">> ", NamedTextColor.GRAY)
                    .append(Component.text(timeString, NamedTextColor.AQUA)), 7);

            long playtime = PlayerAPI.getApi().getPlaytime(player);
            int days = (int) (playtime / 86400);
            int hours2 = (int) ((playtime % 86400) / 3600);
            int minutes2 = (int) ((playtime % 3600) / 60);
            String playtimeString = String.format("%02dd %02dh %02dm", days, hours2, minutes2);

            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                setScore(Component.text(">> ", NamedTextColor.GRAY)
                        .append(Component.text(playtimeString, NamedTextColor.GREEN)), 4);
            } else {
                setScore(Component.text(">> ", NamedTextColor.GRAY)
                        .append(Component.text(playtimeString, NamedTextColor.GREEN)), 4);
            }

            if (mode) {
                int amount = QuestAPI.getApi().getProgress(player);
                int hasNumber = QuestAPI.getApi().getProgress(player);
                Component progressBar = getProgressBar(hasNumber, amount);
                if (QuestAPI.getApi().isQuestComplete(player)) {
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        setScore(Component.text(">> ", NamedTextColor.GRAY)
                                .append(Component.text("Abgeschlossen!", NamedTextColor.GREEN, TextDecoration.BOLD)), 1);
                    } else {
                        setScore(Component.text(">> ", NamedTextColor.GRAY)
                                .append(Component.text("Completed!", NamedTextColor.GREEN, TextDecoration.BOLD)), 1);
                    }
                } else {
                    setScore(progressBar, 1);
                }

                int quest = QuestAPI.getApi().getQuest(player);
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    setScore(Component.text(">> ", NamedTextColor.GRAY)
                            .append(Component.text(questsDe[quest], NamedTextColor.WHITE)), 0);
                } else {
                    setScore(Component.text(">> ", NamedTextColor.GRAY)
                            .append(Component.text(questsEn[quest], NamedTextColor.WHITE)), 0);
                }
            } else {
                setScore(Component.text(">> ", NamedTextColor.GRAY)
                .append(Component.text("X: " + player.getLocation().getBlockX() + " Z: " + player.getLocation().getBlockZ(), NamedTextColor.LIGHT_PURPLE)), 1);
            }

        }, 20, 20);
    }

    private static Component getProgressBar(int hasNumber, int amount) {
        int barSize = 30;
        char progressBarChar = '|';

        double percent = (double) hasNumber / amount;
        int progressBars = (int) (percent * barSize);
        TextComponent.Builder progressBar = Component.text("[", NamedTextColor.WHITE).toBuilder();
        for (int i = 0; i < barSize; i++) {
            if (i < progressBars) {
                progressBar.append(Component.text(progressBarChar, NamedTextColor.GREEN));
            } else {
                progressBar.append(Component.text(progressBarChar, NamedTextColor.RED));
            }
        }
        progressBar.append(Component.text("]", NamedTextColor.WHITE)
                .append(Component.text(hasNumber, NamedTextColor.GREEN))
                .append(Component.text("/", NamedTextColor.WHITE))
                .append(Component.text(amount, NamedTextColor.RED)));
        return progressBar.build();
    }

}