package StudyGroup;

/**
 * Класс Person
 */
public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportID; //Поле не может быть null
    private Color eyeColor; //Поле не может быть null
    private Country nationality; //Поле может быть null

    /**
     * Конструктор класса Person.
     *
     * @param name        имя. Не может быть null, строка не пустая
     * @param passportID  Номер паспорта. Не может быть null
     * @param eyeColor    Цвет глаз. Не может быть null
     * @param nationality Национальность. Может быть null
     */
    public Person(String name, String passportID, Color eyeColor, Country nationality) {
        this.name = name;
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.nationality = nationality;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }


    public String getPassportID() {
        return passportID;
    }

    public Color getEyeColor() {
        return eyeColor;
    }


    public Country getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Person {" +
                "name ='" + name + '\'' +
                ", passport ID ='" + passportID + '\'' +
                ", eye color = " + eyeColor +
                ", nationality = " + nationality +
                '}';
    }
}
