package com.itmo.app;


import java.io.Serializable;
import java.util.Arrays;

/**
 * форма обучения группы
 */
public enum FormOfEducation implements Serializable {
    DISTANCE_EDUCATION("Distance"),
    FULL_TIME_EDUCATION("Full time"),
    EVENING_CLASSES("Evening classes");

    private String humanity;

    FormOfEducation(String humanity) {
        this.humanity = humanity;
    }

    /**
     * аналог valueOf только еще и сообщением об ошибке
     *
     * @param value         - строка, которую ищем
     * @param messageIfNull - сообщение, если не нашли
     */
    public static FormOfEducation getValue(String value, String messageIfNull) {
        try {
            return Arrays.stream(FormOfEducation.values()).filter(f -> f.ordinal()+1 == Integer.parseInt(value))
                    .findAny().orElse(null);
        } catch (NumberFormatException ignored){}
        System.out.println(messageIfNull);
        return null;
    }
}