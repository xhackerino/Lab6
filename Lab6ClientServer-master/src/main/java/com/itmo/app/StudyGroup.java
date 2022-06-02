package com.itmo.app;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/**
 * класс учебной группы, содержит в себе проверки пользовательского ввода и описание группы
 */

public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long studentsCount; //Значение поля должно быть больше 0, Поле может быть null
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Semester semesterEnum; //Поле может быть null
    private Country country;
    private Person groupAdmin; //Поле не может быть null
    private Scanner in = new Scanner(System.in);

    /**
     * для команды show
     */
    @Override
    public String toString() {
        return "StudyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(creationDate) +
                ", studentsCount=" + studentsCount +
                ", formOfEducation=" + formOfEducation +
                ", semesterEnum=" + semesterEnum +
                ", country=" + country +
                ", groupAdmin=" + groupAdmin.toString() +
                '}';
    }

    /**
     * устанавливает все поля
     */
    public void setAllFields() {
        do {
            System.out.println("Enter name of group");
            name = in.nextLine();
        } while (!FieldsValidator.checkNumber((long) name.length(), 2, 19, "Incorrect name of group. Try again", false));
        setFields();
    }

    /**
     * устанавливает все поля кроме имени
     */
    public void setFields() {
        checkCoordinates();
        checkStudentsCount();
        checkFormOfEducation();
        checkSemesterEnum();
        checkCountry();
        checkGroupAdmin();
        setCreationDate();
    }

    public void setCreationDate() {
        creationDate = ZonedDateTime.now();
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }


    /**
     * устанавливает сканер для считывания пользовательского ввода не только с консоли
     */
    public void setScanner(Scanner scanner) {
        in = scanner;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }
    public void setStudentsCount(Long studentsCount) {
        this.studentsCount = studentsCount;
    }
    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public Long getStudentsCount() {
        return studentsCount;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }
    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }
    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * проверка корректности введённых пользователем координат
     */
    public void checkCoordinates() {
        String nextLine;
        do {
            System.out.println("Enter X coordinate of group: ");
            nextLine = in.nextLine();
        } while (!FieldsValidator.checkStringParseToLong(nextLine, "Error in coordinates. X must be integer. Try again."));
        Long x = Long.parseLong(nextLine);
        do {
            System.out.println("Enter Y coordinate of group: ");
            nextLine = in.nextLine();
        } while (!FieldsValidator.checkStringParseToLong(nextLine, "Error in coordinates. Y must be integer. Try again."));
        long y = Long.parseLong(nextLine);
        this.coordinates = new Coordinates(x, y);
    }

    /**
     * проверка корректности введённого пользователем кол-ва студентов
     */
    public void checkStudentsCount() {
        String nextLine;
        do {
            System.out.println("Enter number of students in group: ");
            nextLine = in.nextLine();
        } while ((!FieldsValidator.checkStringParseToLong(nextLine, "Error in students count. Count must be integer. Try again.")
                || !FieldsValidator.checkNumber(Long.parseLong(nextLine), 0, 5000, "Incorrect number of students. Try again.", true))
                && !nextLine.equals(""));
        if (!nextLine.equals("")) studentsCount = Long.parseLong(nextLine);
        else {
            System.out.println("Number of students is not set. Set default value: 0");
            studentsCount = null;
        }
    }

    /**
     * проверка корректности введённой пользователем формы обучения
     */
    public void checkFormOfEducation() {
        String nextLine;
        do {
            System.out.println("Enter form of education (Distance, Full time, Evening classes): ");
            nextLine = in.nextLine();
        } while (FormOfEducation.getValue(nextLine, "There are no such forms of educations. Try again. ") == null);
        formOfEducation = FormOfEducation.getValue(nextLine, "");
    }

    /**
     * проверка корректности введённого пользователем номера семестра
     */
    public void checkSemesterEnum() {
        String nextLine;
        do {
            System.out.println("Enter semester(1,2,3,4,5): ");
            nextLine = in.nextLine();
        } while (Semester.getValue(nextLine, "Incorrect semester entered.") == null && !nextLine.equals(""));
        if (nextLine.equals("")) {
            semesterEnum = null;
            System.out.println("Incorrect semester entered. Set default value: null");
        } else semesterEnum = Semester.getValue(nextLine, "");
    }

    public void checkCountry() {
        String nextLine;
        do {
            System.out.println("Enter country: ");
            nextLine = in.nextLine();
        } while (Country.getValue(nextLine, "Incorrect country entered.") == null && !nextLine.equals(""));
        if (nextLine.equals("")) {
            country = null;
            System.out.println("Country is not set. Set default value: null");
        } else country = Country.getValue(nextLine, "");
    }

    /**
     * проверка корректности введённого пользователем админа группы
     */
    public void checkGroupAdmin() {
        groupAdmin = new Person();
        System.out.println("Now you can enter group admin.");
        String nextLine;
        do {
            System.out.println("Enter name of group admin: ");
            nextLine = in.nextLine();
        } while (!FieldsValidator.checkNumber((long) nextLine.length(), 2, 19, "Incorrect name of admin. Try again.", false));
        groupAdmin.setName(nextLine);
        do {
            System.out.println("Enter passport ID: ");
            nextLine = in.nextLine();
        } while (!FieldsValidator.checkNumber((long) nextLine.length(), 1, 500, "Incorrect entered, try again. ", false));
        groupAdmin.setPassportID(nextLine);
    }
    /**
     * генерация случайного и уникального идентификатора группы
     *
     * @param idList - лист идентификаторов, относительно которого id должен быть уникален
     */
    public static long generateId(HashSet<Long> idList) {
        Random random = new Random();
        boolean goodId = false;
        long id = Long.MAX_VALUE;
        while (!goodId) {
            id = random.nextLong();
            if (id <= 0) continue;
            goodId = idList.add(id);
        }
        return id;
    }

    /**
     * сравнение по имени группы
     *
     * @param studyGroup - элемент с которым идет сравнение
     */
    @Override
    public int compareTo(StudyGroup studyGroup) {
        if (studyGroup == null) return 1;
        if (studyGroup.getName() == null) return 1;
        if (getName() == null) return -1;
        return getName().compareTo(studyGroup.getName());
    }
}