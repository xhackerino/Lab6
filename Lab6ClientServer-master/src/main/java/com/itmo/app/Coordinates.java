package com.itmo.app;

import java.io.Serializable;

/**
 * класс координат учебной группы
 */
public class Coordinates implements Serializable {
    private final Long x;
    private final long y;

    Coordinates(Long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

