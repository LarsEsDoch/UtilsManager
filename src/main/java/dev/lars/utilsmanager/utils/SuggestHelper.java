package dev.lars.utilsmanager.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SuggestHelper {

    public static Collection<String> filter(String input, String... values) {
        String lower = input.toLowerCase();
        return Arrays.stream(values)
                .filter(v -> v.toLowerCase().startsWith(lower))
                .toList();
    }

    public static List<String> getTimeSuggestions(String input) {
        List<String> suggestions = new ArrayList<>();

        if (input == null || input.isBlank()) {
            return List.of("30s", "5m", "15m", "1h", "12h", "1d", "7d");
        }

        String trimmed = input.trim().toLowerCase();

        if (trimmed.matches("\\d+")) {
            long number = Long.parseLong(trimmed);

            suggestions.add(number + "s");
            suggestions.add(number + "m");
            suggestions.add(number + "h");
            suggestions.add(number + "d");

            long nextNumber = number * 10;
            if (nextNumber <= 999999) {
                suggestions.add(nextNumber + "s");
                suggestions.add(nextNumber + "m");
                suggestions.add(nextNumber + "h");
                suggestions.add(nextNumber + "d");
            }

            return suggestions;
        }


        if (trimmed.matches("\\d+[smhd]")) {
            suggestions.add(trimmed);
            return suggestions;
        }

        String[] allSuggestions = {"30s", "5m", "15m", "1h", "12h", "1d", "7d"};
        for (String suggestion : allSuggestions) {
            if (suggestion.startsWith(trimmed)) {
                suggestions.add(suggestion);
            }
        }

        return suggestions.isEmpty() ? List.of() : suggestions;
    }

    public static List<String> getFilteredTimeSuggestions(String input) {
        List<String> allSuggestions = getTimeSuggestions(input);

        if (input == null || input.isBlank()) {
            return allSuggestions;
        }

        String lower = input.toLowerCase();
        return allSuggestions.stream()
                .filter(s -> s.startsWith(lower))
                .toList();
    }
}