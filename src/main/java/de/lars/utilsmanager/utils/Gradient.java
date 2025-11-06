package de.lars.utilsmanager.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.awt.*;

public class Gradient {

    public static Component gradient(String text, String startColor, String endColor, int index, int total) {
        Color color = blend(Color.decode(startColor), Color.decode(endColor), (float) index / total);
        return Component.text(text, TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
    }

    public static Color blend(Color color1, Color color2, float ratio) {
        ratio = Math.max(0, Math.min(1, ratio));

        int red = (int) (color1.getRed() * (1 - ratio) + color2.getRed() * ratio);
        int green = (int) (color1.getGreen() * (1 - ratio) + color2.getGreen() * ratio);
        int blue = (int) (color1.getBlue() * (1 - ratio) + color2.getBlue() * ratio);

        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));

        return new Color(red, green, blue);
    }

    public static Component animatedGradient(String text, String startColor, String endColor, int highlightIndex) {
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