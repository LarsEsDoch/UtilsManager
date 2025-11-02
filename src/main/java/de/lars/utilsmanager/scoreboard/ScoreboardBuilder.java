package de.lars.utilsmanager.scoreboard;

import de.lars.apimanager.apis.serverSettingsAPI.ServerSettingsAPI;
import de.lars.utilsmanager.UtilsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;

import java.awt.*;
import java.util.Objects;

public abstract class ScoreboardBuilder {

    public Scoreboard scoreboard;
    protected final Objective objective;

    protected final Player player;

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
        String text = ServerSettingsAPI.getApi().getServerName();
        Bukkit.getScheduler().runTaskTimerAsynchronously(UtilsManager.getInstance(), bukkitTask -> {
            setDisplayName(animatedGradient(text, "#50FB08", "#006EFF", rgb[0]));

            rgb[0]++;
            if (rgb[0] >= text.length()) {
                rgb[0] = 0;
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

    private Component gradient(String text, String startColor, String endColor, int index, int total) {
        Color color = blend(Color.decode(startColor), Color.decode(endColor), (float) index / total);
        return Component.text(text, TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
    }

    private Color blend(Color color1, Color color2, float ratio) {
        ratio = Math.max(0, Math.min(1, ratio));

        int red = (int) (color1.getRed() * (1 - ratio) + color2.getRed() * ratio);
        int green = (int) (color1.getGreen() * (1 - ratio) + color2.getGreen() * ratio);
        int blue = (int) (color1.getBlue() * (1 - ratio) + color2.getBlue() * ratio);

        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));

        return new Color(red, green, blue);
    }

    private Component animatedGradient(String text, String startColor, String endColor, int highlightIndex) {
        var builder = Component.text();

        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if (i == highlightIndex) {
                builder.append(Component.text(c, NamedTextColor.WHITE, TextDecoration.BOLD));
            } else {
                builder.append(gradient(String.valueOf(c), startColor, endColor, i, length));
            }
        }

        return builder.build().decorate(TextDecoration.BOLD);
    }
}