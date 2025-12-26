package dev.lars.utilsmanager.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {

    private static final Pattern TIME_PATTERN = Pattern.compile("^(\\d+)([smhd])$");

    public static Instant parseTimeToInstant(String timeString) {
        if (timeString == null || timeString.isBlank()) {
            return null;
        }

        Matcher matcher = TIME_PATTERN.matcher(timeString.toLowerCase().trim());
        if (!matcher.matches()) {
            return null;
        }

        long amount = Long.parseLong(matcher.group(1));
        String unit = matcher.group(2);

        Duration duration = switch (unit) {
            case "s" -> Duration.ofSeconds(amount);
            case "m" -> Duration.ofMinutes(amount);
            case "h" -> Duration.ofHours(amount);
            case "d" -> Duration.ofDays(amount);
            default -> null;
        };

        if (duration == null) {
            return null;
        }

        return Instant.now().plus(duration);
    }

    public static Long parseTimeToMillis(String timeString) {
        if (timeString == null || timeString.isBlank()) {
            return null;
        }

        Matcher matcher = TIME_PATTERN.matcher(timeString.toLowerCase().trim());
        if (!matcher.matches()) {
            return null;
        }

        long amount = Long.parseLong(matcher.group(1));
        String unit = matcher.group(2);

        return switch (unit) {
            case "s" -> amount * 1000;
            case "m" -> amount * 60 * 1000;
            case "h" -> amount * 60 * 60 * 1000;
            case "d" -> amount * 24 * 60 * 60 * 1000;
            default -> null;
        };
    }

    public static Long parseTimeToSeconds(String timeString) {
        Long millis = parseTimeToMillis(timeString);
        if (millis == null) return null;
        return millis / 1000;
    }

    public static Long parseTimeToTicks(String timeString) {
        Long millis = parseTimeToMillis(timeString);
        if (millis == null) return null;
        return millis / 50;
    }

    public static Duration parseTimeToDuration(String timeString) {
        if (timeString == null || timeString.isBlank()) {
            return null;
        }

        Matcher matcher = TIME_PATTERN.matcher(timeString.toLowerCase().trim());
        if (!matcher.matches()) {
            return null;
        }

        long amount = Long.parseLong(matcher.group(1));
        String unit = matcher.group(2);

        return switch (unit) {
            case "s" -> Duration.ofSeconds(amount);
            case "m" -> Duration.ofMinutes(amount);
            case "h" -> Duration.ofHours(amount);
            case "d" -> Duration.ofDays(amount);
            default -> null;
        };
    }
}