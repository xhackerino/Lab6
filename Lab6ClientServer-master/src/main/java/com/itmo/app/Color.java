package com.itmo.app;

import java.util.Arrays;

public enum Color {
    BLUE,
    YELLOW,
    WHITE;

    public static Color getValue(String value, String messageIfNull) {
        try {
            return Arrays.stream(Color.values()).filter(f -> f.ordinal() + 1 == Integer.parseInt(value))
                    .findAny().orElse(null);
        } catch (NumberFormatException ignored) {
        }
        System.out.println(messageIfNull);
        return null;
    }
}
