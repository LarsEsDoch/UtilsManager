package de.lars.utilsmanager.quest;

import dev.lars.apimanager.apis.economyAPI.EconomyAPI;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import dev.lars.apimanager.apis.questAPI.QuestAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.time.Instant;
import java.util.Calendar;

public class QuestManager implements Listener {

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

    public QuestManager() {
        long hours;
        long minutes;
        long seconds;
        if (Calendar.HOUR_OF_DAY < 6) {
            Calendar now = Calendar.getInstance();
            Calendar six = Calendar.getInstance();
            six.set(Calendar.HOUR_OF_DAY, 6);
            six.set(Calendar.MINUTE, 0);
            six.set(Calendar.SECOND, 0);

            long diffInMillis = six.getTimeInMillis() - now.getTimeInMillis();

            long diffInSeconds = diffInMillis / 1000;
            hours = diffInSeconds / 3600;
            minutes = (diffInSeconds % 3600) / 60;
            seconds = diffInSeconds % 60;
        } else {
            Calendar now = Calendar.getInstance();
            Calendar tomorrow = Calendar.getInstance();
            tomorrow.add(Calendar.DAY_OF_YEAR, 1);
            tomorrow.set(Calendar.HOUR_OF_DAY, 6);
            tomorrow.set(Calendar.MINUTE, 0);
            tomorrow.set(Calendar.SECOND, 0);

            long diffInMillis = tomorrow.getTimeInMillis() - now.getTimeInMillis();

            long diffInSeconds = diffInMillis / 1000;
            hours = diffInSeconds / 3600;
            minutes = (diffInSeconds % 3600) / 60;
            seconds = diffInSeconds % 60;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            String quest = generateQuest(player);
            if (!QuestAPI.getApi().isQuestComplete(player)) {
                if (LanguageAPI.getApi().getLanguage(player) == 2) {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Deine tägliche Aufgabe ist: ", NamedTextColor.WHITE))
                            .append(Component.text(quest, NamedTextColor.GOLD)));
                    player.sendMessage(Statements.getPrefix().append(Component.text("Du hast noch ", NamedTextColor.BLUE))
                            .append(Component.text(hours + " Stunden, " + minutes + " Minuten und " + seconds + " Sekunden ", NamedTextColor.BLUE))
                            .append(Component.text(" um sie abzuschließen.", NamedTextColor.BLUE)));
                } else {
                    player.sendMessage(Statements.getPrefix().append(Component.text("Your daily quest is: ", NamedTextColor.WHITE))
                            .append(Component.text(quest, NamedTextColor.GOLD)));
                    player.sendMessage(Statements.getPrefix().append(Component.text("You have ", NamedTextColor.BLUE))
                            .append(Component.text(hours + " hours, " + minutes + " minutes and " + seconds + " seconds", NamedTextColor.BLUE))
                            .append(Component.text(" to complete it.", NamedTextColor.BLUE)));
                }
            }
        }

        //startQuests();
        Completer();
    }

    public void sendQuests(Player player) {
        String quest = generateQuest(player);
        if (!QuestAPI.getApi().isQuestComplete(player)) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            long hours;
            long minutes;
            long seconds;
            if (hour < 6) {
                Calendar now = Calendar.getInstance();
                Calendar six = Calendar.getInstance();
                six.set(Calendar.HOUR_OF_DAY, 6);
                six.set(Calendar.MINUTE, 0);
                six.set(Calendar.SECOND, 0);

                long diffInMillis = six.getTimeInMillis() - now.getTimeInMillis();

                long diffInSeconds = diffInMillis / 1000;
                hours = diffInSeconds / 3600;
                minutes = (diffInSeconds % 3600) / 60;
                seconds = diffInSeconds % 60;
            } else {
                Calendar now = Calendar.getInstance();
                Calendar tomorrow = Calendar.getInstance();
                tomorrow.add(Calendar.DAY_OF_YEAR, 1);
                tomorrow.set(Calendar.HOUR_OF_DAY, 6);
                tomorrow.set(Calendar.MINUTE, 0);
                tomorrow.set(Calendar.SECOND, 0);

                long diffInMillis = tomorrow.getTimeInMillis() - now.getTimeInMillis();

                long diffInSeconds = diffInMillis / 1000;
                hours = diffInSeconds / 3600;
                minutes = (diffInSeconds % 3600) / 60;
                seconds = diffInSeconds % 60;
            }
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Deine tägliche Aufgabe ist: ", NamedTextColor.WHITE))
                        .append(Component.text(quest, NamedTextColor.GOLD)));
                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast noch ", NamedTextColor.BLUE))
                        .append(Component.text(hours + " Stunden, " + minutes + " Minuten und " + seconds + " Sekunden ", NamedTextColor.BLUE))
                        .append(Component.text(" um sie abzuschließen.", NamedTextColor.BLUE)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("Your daily quest is: ", NamedTextColor.WHITE))
                        .append(Component.text(quest, NamedTextColor.GOLD)));
                player.sendMessage(Statements.getPrefix().append(Component.text("You have ", NamedTextColor.BLUE))
                        .append(Component.text(hours + " hours, " + minutes + " minutes and " + seconds + " seconds", NamedTextColor.BLUE))
                        .append(Component.text(" to complete it.", NamedTextColor.BLUE)));
            }
        }

    }

    public void setComplete(Player player) {
        long hours;
        long minutes;
        long seconds;
        Calendar calendar = Calendar.getInstance();
        if (Calendar.HOUR_OF_DAY < 6) {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            calendar.set(Calendar.HOUR_OF_DAY, 6);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Calendar now = Calendar.getInstance();
            long diffInMillis = now.getTimeInMillis() - calendar.getTimeInMillis();

            int diffInSeconds = (int) (diffInMillis / 1000);
            hours = diffInSeconds / 3600;
            minutes = (diffInSeconds % 3600) / 60;
            seconds = diffInSeconds % 60;
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 6);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Calendar now = Calendar.getInstance();
            long diffInMillis = now.getTimeInMillis() - calendar.getTimeInMillis();

            int diffInSeconds = (int) (diffInMillis / 1000);
            hours = diffInSeconds / 3600;
            minutes = (diffInSeconds % 3600) / 60;
            seconds = diffInSeconds % 60;
        }
        String quest = generateQuest(player);
        /*if (LanguageAPI.getApi().getLanguage(player) == 2) {
            player.sendMessage(Statements.getPrefix().append(Component.text("Du hast deine tägliche Aufgabe abgeschlossen!", NamedTextColor.GREEN)));
            player.sendMessage(Statements.getPrefix().append(Component.text("Sie war: ", NamedTextColor.WHITE))
                    .append(Component.text(quest, NamedTextColor.GOLD)));
            player.sendMessage(Statements.getPrefix().append(Component.text("Du hast ", NamedTextColor.BLUE))
                    .append(Component.text(hours + " Stunden, " + minutes + " Minuten und " + seconds + " Sekunden ", NamedTextColor.BLUE))
                    .append(Component.text(" gebraucht.", NamedTextColor.BLUE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("Dir wurde ", NamedTextColor.GREEN))
                    .append(Component.text((100 + 25 * (QuestAPI.getApi().getStreak(player)+1)) + "$", NamedTextColor.DARK_GREEN))
                    .append(Component.text(" überwiesen!", NamedTextColor.GREEN)));
        } else {
            player.sendMessage(Statements.getPrefix().append(Component.text("You have completed your daily quest!", NamedTextColor.GREEN)));
            player.sendMessage(Statements.getPrefix().append(Component.text("It was: ", NamedTextColor.WHITE))
                    .append(Component.text(quest, NamedTextColor.GOLD)));
            player.sendMessage(Statements.getPrefix().append(Component.text("It took you ", NamedTextColor.BLUE))
                    .append(Component.text(hours + " hours, " + minutes + " minutes and " + seconds + " seconds ", NamedTextColor.BLUE))
                    .append(Component.text(".", NamedTextColor.BLUE)));
            player.sendMessage(Statements.getPrefix()
                    .append(Component.text("You were transferred ", NamedTextColor.GREEN))
                    .append(Component.text((100 + 25 * (QuestAPI.getApi().getStreak(player)+1)) + "$", NamedTextColor.DARK_GREEN))
                    .append(Component.text(" !", NamedTextColor.GREEN)));
        }
        EconomyAPI.getApi().addGift(player, 100 + (25 * (QuestAPI.getApi().getStreak(player)+1)));
        QuestAPI.getApi().setQuestComplete(player, true);
        QuestAPI.getApi().setStreak(player, QuestAPI.getApi().getStreak(player)+1);

         */
    }

    public void startQuests() {

        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            if (Calendar.HOUR_OF_DAY == 6 && Calendar.MINUTE == 0) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String quest = generateQuest(player);
                    if (LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Deine tägliche Aufgabe ist: ", NamedTextColor.WHITE))
                                .append(Component.text(quest, NamedTextColor.GOLD)));
                    } else {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Your daily quest is: ", NamedTextColor.WHITE))
                                .append(Component.text(quest, NamedTextColor.GOLD)));
                    }
                }
            }
        }, 20, 1200);
    }

    public String generateQuest(Player player) {
        Integer quest;
        Integer streak;
        Boolean complete;
        Instant now = Instant.now();
        if (Calendar.HOUR_OF_DAY < 6) {
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                return questsDe[QuestAPI.getApi().getQuest(player)];
            } else {
                return questsEn[QuestAPI.getApi().getQuest(player)];
            }
        } else {
            complete = QuestAPI.getApi().isQuestComplete(player);
            streak = QuestAPI.getApi().getStreak(player);
            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                if (QuestAPI.getApi().getQuestDate(player).compareTo(now) < 0) {
                    if (!complete) {
                        quest = 0;
                        QuestAPI.getApi().setStreak(player, 0);
                        setQuest(player, quest);
                    } else {
                        quest = streak;
                        setQuest(player, quest);
                    }
                } else {
                    if (QuestAPI.getApi().getQuest(player) == -1) {
                        quest = 0;
                        QuestAPI.getApi().setStreak(player, 0);
                        setQuest(player, quest);
                    } else {
                        quest = QuestAPI.getApi().getQuest(player);
                    }
                }
                return questsDe[quest];
            } else {Instant questDate = QuestAPI.getApi().getQuestDate(player);
                if (questDate == null || questDate.compareTo(now) < 0) {
                    if (!complete) {
                        quest = 0;
                        QuestAPI.getApi().setStreak(player, 0);
                        setQuest(player, quest);
                    } else {
                        quest = streak;
                        setQuest(player, quest);
                    }
                } else {
                    if (QuestAPI.getApi().getQuest(player) == -1) {
                        quest = 0;
                        QuestAPI.getApi().setStreak(player, 0);
                        setQuest(player, quest);
                    } else {
                        quest = QuestAPI.getApi().getQuest(player);
                    }
                }
                return questsEn[quest];
            }
        }
    }

    private void setQuest(Player player, Integer quest) {
        String questName = "";
        if (LanguageAPI.getApi().getLanguage(player) == 1) {
            questName = questsEn[quest];
        } else {
            questName = questsDe[quest];
        }

        if (quest == 0) {
            QuestAPI.getApi().setQuest(player, quest, 10, questName);
        }
        if (quest == 1) {
            QuestAPI.getApi().setQuest(player, quest, 2, questName);
        }
        if (quest == 2) {
            QuestAPI.getApi().setQuest(player, quest, 5, questName);
        }
        if (quest == 3) {
            QuestAPI.getApi().setQuest(player, quest, 3, questName);
        }
        if (quest == 4) {
            QuestAPI.getApi().setQuest(player, quest, 10, questName);
        }
        if (quest == 5) {
            QuestAPI.getApi().setQuest(player, quest, 10, questName);
        }
        if (quest == 6) {
            QuestAPI.getApi().setQuest(player, quest, 1, questName);
        }
        if (quest == 7) {
            QuestAPI.getApi().setQuest(player, quest, 4, questName);
        }
        if (quest == 8) {
            QuestAPI.getApi().setQuest(player, quest, 20, questName);
        }
        if (quest == 9) {
            QuestAPI.getApi().setQuest(player, quest, 2, questName);
        }
        if (quest == 10) {
            QuestAPI.getApi().setQuest(player, quest, 1000, questName);
        }
        if (quest == 11) {
            QuestAPI.getApi().setQuest(player, quest, 2, questName);
        }
        if (quest == 12) {
            QuestAPI.getApi().setQuest(player, quest, 1, questName);
        }
        if (quest == 13) {
            QuestAPI.getApi().setQuest(player, quest, 5, questName);
        }
        if (quest == 14) {
            QuestAPI.getApi().setQuest(player, quest, 100, questName);
        }
        if (quest == 15) {
            QuestAPI.getApi().setQuest(player, quest, 10, questName);
        }
        if (quest == 16) {
            QuestAPI.getApi().setQuest(player, quest, 5, questName);
        }
        if (quest == 17) {
            QuestAPI.getApi().setQuest(player, quest, 3, questName);
        }
        if (quest == 18) {
            QuestAPI.getApi().setQuest(player, quest, 5, questName);
        }
        if (quest == 19) {
            QuestAPI.getApi().setQuest(player, quest, 1, questName);
        }
        if (quest == 20) {
            QuestAPI.getApi().setQuest(player, quest, 64, questName);
        }
        if (quest == 21) {
            QuestAPI.getApi().setQuest(player, quest, 50, questName);
        }
    }

    public void Completer() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (QuestAPI.getApi().isQuestComplete(player)) {
                    return;
                }
                int number = QuestAPI.getApi().getProgress(player);
                int hasnumber = QuestAPI.getApi().getProgress(player);
                if (hasnumber > number || hasnumber == number) {
                    setComplete(player);
                }

                if (QuestAPI.getApi().getQuest(player) == 21) {
                    QuestAPI.getApi().setProgress(player, player.getLevel());
                }

                if (QuestAPI.getApi().getQuest(player) == 2) {
                    PlayerInventory inventory = player.getInventory();
                    ItemStack apple = new ItemStack(Material.APPLE);
                    int existing = inventory.all(Material.APPLE).values().stream()
                            .mapToInt(ItemStack::getAmount).sum();
                    QuestAPI.getApi().setProgress(player, existing);
                }

                if (QuestAPI.getApi().getQuest(player) == 6) {
                    PlayerInventory inventory = player.getInventory();
                    ItemStack cactus = new ItemStack(Material.CACTUS);
                    int existing = inventory.all(Material.CACTUS).values().stream()
                            .mapToInt(ItemStack::getAmount).sum();
                    QuestAPI.getApi().setProgress(player, existing);
                }
            }
        }, 40, 40);

    }

    @EventHandler
    public void onQuest3(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player player) {
            if (event.getHitEntity() == null) return;
            if (event.getHitEntity() instanceof Player) {
                if (event.getHitEntity() == event.getEntity().getShooter()); {
                    if (QuestAPI.getApi().getQuest(player) == 2) {
                        QuestAPI.getApi().increaseProgress(player, 1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onQuest4(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock != null && event.hasItem()) {
            Material itemMaterial = event.getItem().getType();
            Material material = clickedBlock.getType();

            if (itemMaterial == Material.BONE_MEAL && (material == Material.OAK_SAPLING || material == Material.BIRCH_SAPLING || material == Material.SPRUCE_SAPLING
                    || material == Material.JUNGLE_SAPLING || material == Material.ACACIA_SAPLING || material == Material.DARK_OAK_SAPLING
                    || material == Material.OAK_LOG || material == Material.BIRCH_LOG || material == Material.SPRUCE_LOG
                    || material == Material.JUNGLE_LOG || material == Material.ACACIA_LOG || material == Material.DARK_OAK_LOG)) {
                if (QuestAPI.getApi().getQuest(player) == 3) {
                    QuestAPI.getApi().increaseProgress(player, 1);
                }
            }
        }
    }


    @EventHandler
    public void onQuest8(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Zombie) {
            Player player = ((Zombie) entity).getKiller();
            if (player != null) {
                if (QuestAPI.getApi().getQuest(player) == 8) {
                    QuestAPI.getApi().increaseProgress(player, 1);
                }
            }
        }
    }

    @EventHandler
    public void onQuest1o2(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Egg) {
            Player player = (Player) event.getEntity().getShooter();
            if (QuestAPI.getApi().getQuest(player) == 0) {
                if (event.getEntity().getShooter() instanceof Player) {
                    QuestAPI.getApi().increaseProgress(player, 1);
                }
            }
            if (QuestAPI.getApi().getQuest(player) == 1) {
                Egg egg = (Egg) event.getEntity();
                if (egg.getShooter() instanceof Player) {
                    if (event.getEntity() instanceof Chicken) {
                        QuestAPI.getApi().increaseProgress(player, 1);
                    }
                }
            }
        }
    }
}