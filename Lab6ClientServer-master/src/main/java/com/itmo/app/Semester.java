package com.itmo.app;


import java.io.Serializable;
import java.util.Arrays;

/**
 * текущий семестр для учебной группы
 */
public enum Semester implements Serializable {
    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4"),
    FIFTH("5");

    private String russian;

    Semester(String russian) {
        this.russian = russian;
    }

    /**
     * аналог valueOf только еще и сообщением об ошибке
     *
     * @param value         - строка, которую ищем
     * @param messageIfNull - сообщение, если не нашли
     */
    public static Semester getValue(String value, String messageIfNull) {
        Semester semester = Arrays.stream(Semester.values()).filter(s -> s.russian.equals(value)).findAny().orElse(null);
        if(semester == null) System.out.println(messageIfNull);
        return semester;
    }
}