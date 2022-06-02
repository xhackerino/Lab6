package com.itmo.app;

import java.io.Serializable;

/**
 * класс человека, используется для описания админа группы
 */

public class Person implements Serializable {
    private String name;
    private String passportID;
    private Country nationality;

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public String getPassportID() {
        return passportID;
    }



    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", passportID='" + passportID + '\'' +
                ", nationaliyu=" + nationality +
                '}';
    }
}
