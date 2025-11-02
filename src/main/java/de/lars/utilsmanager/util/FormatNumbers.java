package de.lars.utilsmanager.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.format.NamedTextColor;

import java.text.DecimalFormat;

public class FormatNumbers {

    public static Component formatDuration(long totalSeconds) {
        if (totalSeconds < 0) totalSeconds = 0;

        int days = Math.toIntExact(totalSeconds / 86400);
        int hours = Math.toIntExact((totalSeconds / 3600) % 24);
        int minutes = Math.toIntExact((totalSeconds / 60) % 60);
        int seconds = Math.toIntExact(totalSeconds % 60);

        String formatted;
        if (days > 0) {
            formatted = String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, seconds);
        } else if (hours > 0) {
            formatted = String.format("%02dh %02dm %02ds", hours, minutes, seconds);
        } else {
            formatted = String.format("%02dm %02ds", minutes, seconds);
        }

        return Component.text(formatted, NamedTextColor.GOLD);
    }

    public static Component formatTimeComponent(int totalSeconds) {
        int days = totalSeconds / 86400;
        int hours = (totalSeconds % 86400) / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        Component component = Component.text("");

        String timeString = String.format("%02d%02d%02d%02d", days, hours, minutes, seconds);
        int index = 0;

        if (days > 0) {
            for (char c : String.format("%02d", days).toCharArray()) {
                component = component.append(Gradient.gradient(String.valueOf(c), "#00FFFF", "#8000FF", index, timeString.length()));
                index++;
            }
            component = component.append(Component.text("d ", NamedTextColor.GRAY));
        }

        if (hours > 0 || days > 0) {
            for (char c : String.format("%02d", hours).toCharArray()) {
                component = component.append(Gradient.gradient(String.valueOf(c), "#00FFFF", "#8000FF", index, timeString.length()));
                index++;
            }
            component = component.append(Component.text("h ", NamedTextColor.GRAY));
        }

        if (minutes > 0 || hours > 0 || days > 0) {
            for (char c : String.format("%02d", minutes).toCharArray()) {
                component = component.append(Gradient.gradient(String.valueOf(c), "#00FFFF", "#8000FF", index, timeString.length()));
                index++;
            }
            component = component.append(Component.text("m ", NamedTextColor.GRAY));
        }

        for (char c : String.format("%02d", seconds).toCharArray()) {
            component = component.append(Gradient.gradient(String.valueOf(c), "#00FFFF", "#8000FF", index, timeString.length()));
            index++;
        }
        component = component.append(Component.text("s", NamedTextColor.GRAY));

        return component;
    }

    public static Component formatMoney(int coins) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatierteZahl = formatter.format(coins);
        return Component.text(formatierteZahl + "$", NamedTextColor.GOLD);
    }
}
