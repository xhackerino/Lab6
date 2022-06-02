package com.itmo.app;


import java.io.Serializable;
import java.util.Arrays;

/**
 * Класс описывает страну
 */
public enum Country implements Serializable {
    UNITED_KINGDOM("Великобритания"),
    FRANCE("Франция"),
    SPAIN("Испания"),
    INDIA("Индия"),
    SOUTH_KOREA("Южная Корея");

    private String russian;

    Country(String russian) {
        this.russian = russian;
    }
    public static Country getValue(String value, String messageIfNull) {
        try {
            return Arrays.stream(Country.values()).filter(f -> f.ordinal()+1 == Integer.parseInt(value))
                    .findAny().orElse(null);
        } catch (NumberFormatException ignored){}
        System.out.println(messageIfNull);
        return null;
    }
}
