package de.lars.utilsmanager.scoreboard;

import de.lars.utilsmanager.UtilsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public abstract class ScoreboardBuilder {

    public Scoreboard scoreboard;
    protected final Objective objective;

    protected final Player player;

    public boolean mode = false;

    public ScoreboardBuilder(Player player, Component displayName) {
        this.player = player;

        if(player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }

        this.scoreboard = player.getScoreboard();

        if(this.scoreboard.getObjective("display") != null) {
            Objects.requireNonNull(this.scoreboard.getObjective("display")).unregister();
        }

        this.objective = this.scoreboard.registerNewObjective("display", Criteria.DUMMY, displayName);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        final int[] rgb = {1};
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            if (mode) {
                switch (rgb[0]) {
                    case 1:
                        setDisplayName(Component.text()
                                .append(Component.text("  A", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text(" Server  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 2:
                        setDisplayName(Component.text()
                                .append(Component.text("  A", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text(" S", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("erver  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 3:
                        setDisplayName(Component.text()
                                .append(Component.text("  A S", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("e", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("rver  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 4:
                        setDisplayName(Component.text()
                                .append(Component.text("  A Se", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("r", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("ver  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 5:
                        setDisplayName(Component.text()
                                .append(Component.text("  A Ser", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("v", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("er  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 6:
                        setDisplayName(Component.text()
                                .append(Component.text("  A Serv", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("e", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("r  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 7:
                        setDisplayName(Component.text()
                                .append(Component.text("  A Serve", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("r", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    default:
                        setDisplayName(Component.text("  A Server  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD).toBuilder().build());
                        break;
                }

                rgb[0]++;
                if (rgb[0] == 8) {
                    rgb[0] = 1;
                }
            } else {
                switch (rgb[0]) {
                    case 1:
                        setDisplayName(Component.text()
                                .append(Component.text("  N", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("iggas in Minecraft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 2:
                        setDisplayName(Component.text()
                                .append(Component.text("  Ni", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("g", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("gas in Minecraft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 3:
                        setDisplayName(Component.text()
                                .append(Component.text("  Nig", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("g", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("as in Minecraft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 4:
                        setDisplayName(Component.text()
                                .append(Component.text("  Nigg", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("a", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("s in Minecraft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 5:
                        setDisplayName(Component.text()
                                .append(Component.text("  Nigga", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("s", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text(" in Minecraft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 6:
                        setDisplayName(Component.text()
                                .append(Component.text("  Niggas", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text(" i", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("n Minecraft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 7:
                        setDisplayName(Component.text()
                                .append(Component.text("  Niggas i", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("n", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text(" Minecraft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 8:
                        setDisplayName(Component.text()
                                .append(Component.text("  Niggas in", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text(" M", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("inecraft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 9:
                        setDisplayName(Component.text()
                                .append(Component.text("  Niggas in M", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("i", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("necraft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 10:
                        setDisplayName(Component.text()
                                .append(Component.text("  Niggas in Mi", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("n", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("ecraft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 11:
                        setDisplayName(Component.text()
                                .append(Component.text("  Niggas in Min", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("e", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("craft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 12:
                        setDisplayName(Component.text()
                                .append(Component.text("  Niggas in Mine", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("c", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("raft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 13:
                        setDisplayName(Component.text()
                                .append(Component.text("  Niggas in Minec", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("r", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("aft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 14:
                        setDisplayName(Component.text()
                                .append(Component.text("  Niggas in Minecr", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("a", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("ft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 15:
                        setDisplayName(Component.text()
                                .append(Component.text("  Niggas in Minecra", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("f", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("t  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    case 16:
                        setDisplayName(Component.text()
                                .append(Component.text("  Niggas in Minecraf", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("t", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                                .append(Component.text("  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)).build());
                        break;
                    default:
                        setDisplayName(Component.text("  Friend in Minecraft  ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD).toBuilder().build());
                        break;
                }

                rgb[0]++;
                if (rgb[0] == 17) {
                    rgb[0] = 1;
                }
            }

        }, 10, 10);

        createScoreboard();
    }

    public abstract void createScoreboard();

    public abstract void update();

    public void setDisplayName(Component displayName) {
        this.objective.displayName(displayName);
    }

    public void setScore(Component content, int score) {
        Team team = getTeamByScore(score);

        if(team == null) {
            return;
        }

        team.prefix(content);
        showScore(score);
    }

    public void removeScore(int score) {
        hideScore(score);
    }

    private EntryName getEntryNameByScore(int score) {
        for(EntryName name : EntryName.values()) {
            if(score == name.getEntry()) {
                return name;
            }
        }

        return null;
    }

    private Team getTeamByScore(int score) {
        EntryName name = getEntryNameByScore(score);

        if(name == null) {
            return null;
        }

        Team team = scoreboard.getEntryTeam(name.getEntryName());

        if(team != null) {
            return team;
        }

        team = scoreboard.registerNewTeam(name.name());
        team.addEntry(name.getEntryName());
        return team;
    }

    private void showScore(int score) {
        EntryName name = getEntryNameByScore(score);

        if(name == null) {
            return;
        }

        if(objective.getScore(name.getEntryName()).isScoreSet()) {
            return;
        }

        objective.getScore(name.getEntryName()).setScore(score);
    }

    private void hideScore(int score) {
        EntryName name = getEntryNameByScore(score);

        if(name == null) {
            return;
        }

        if(!objective.getScore(name.getEntryName()).isScoreSet()) {
            return;
        }

        scoreboard.resetScores(name.getEntryName());
    }
}














