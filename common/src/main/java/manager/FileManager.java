package manager;

import studyGroup.*;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Stack;

import static manager.ConsoleManager.print;
import static manager.ConsoleManager.printError;

/**
 * Класс для работы с файлами
 */
public class FileManager {
    private final String fileName;
    private static ArrayList<String> fields;
    private static ArrayList<String> keys;

    /**
     * Метод для получения имени файла
     * @param fileName имя файла
     */
    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    private static void MakeFieldsArray(String line) {
        String word = "";
        fields = new ArrayList<>();
        keys = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ';') {
                word += line.charAt(i);
            } else {
                fields.add(word);
                word = "";
            }
        }
        fields.add(word);
    }

    /**
     * Метод для записи коллекции в файл
     * @param stack коллекция
     */
    public void WriteCollection(Stack<StudyGroup> stack) {
        if (fileName == null) {
            printError("Can't be saved");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            String stek = "ID;NAME;CREATIONDATE;STUDENTSCOUNT;EXPELLEDSTUDENTS;FORMOFEDUCATION;" +
                    "SEMESTERENUM;PERSONNAME;PASSPORTID;EYECOLOR;NATIONALITY;X;Y\n";
            for (StudyGroup StudyGroup : stack) {
                stek += StudyGroup.getId().toString() + ";" + StudyGroup.getName() + ";" + StudyGroup.getCreationDate().toString() + ";" +
                        StudyGroup.getStudentsCount() + ";" + StudyGroup.getExpelledStudents().toString() + ";" + StudyGroup.getFormOfEducation().toString() + ";" +
                        StudyGroup.getSemesterEnum() + ";" + StudyGroup.getGroupAdmin().getName() + ";"
                        + StudyGroup.getGroupAdmin().getPassportID() + ";" + StudyGroup.getGroupAdmin().getEyeColor().toString() + ";"
                        + StudyGroup.getGroupAdmin().getNationality().toString() + ";" + StudyGroup.getCoordinates().getX().toString() + ";" +
                        StudyGroup.getCoordinates().getY() + "\n";

            }
            fos.write(stek.getBytes());
        } catch (IOException e) {
            printError("File not found");
        }
    }

    /**
     * Метод для считывания коллекции из файла
     * @return коллекция
     */
    public Stack<StudyGroup> ReadCollection() {
        if (fileName == null) {
            return new Stack<>();
        }
        try {
            Stack<StudyGroup> stack = new Stack<>();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            MakeFieldsArray(reader.readLine());
            while (reader.ready()) {
                stack.add(studyGroupFromCSV(reader.readLine()));
            }
            reader.close();
            print("Collection was loaded");
            return stack;
        } catch (FileNotFoundException e) {
            printError("Collection not found");
        } catch (NoSuchElementException e) {
            printError("File is empty");
        } catch (IOException e) {
            printError("Error with access to file");
        }
        return new Stack<>();
    }

    private StudyGroup studyGroupFromCSV(String line) {
        String word = "";
        Long id = null;
        String name = null;
        java.time.ZonedDateTime creationDate = null;
        Long coordinatesX = null;
        Double coordinatesY = null;
        Long studentsCount = null;
        Long expelledStudents = null;
        FormOfEducation formOfEducation = null;
        Semester semesterEnum = null;
        Person groupAdmin;
        StudyGroup studyGroup;
        Coordinates coordinates;
        String personName = null;
        String personPassportId = null;
        Color eyeColor = null;
        Country nationality = null;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ';') {
                word += line.charAt(i);
            } else {
                keys.add(word);
                word = "";
            }

        }
        keys.add(word);
//        System.out.println(keys);
        for (int i = 0; i < keys.size(); i++) {
            switch (Chooser(i)) {
                case 1:
                    id = Long.parseLong(keys.get(i));
                    break;
                case 2:
                    name = keys.get(i);
                    break;
                case 3:
                    creationDate = ZonedDateTime.parse(keys.get(i));
                    break;
                case 4:
                    studentsCount = Long.parseLong(keys.get(i));
                    break;
                case 5:
                    if (keys.get(i).equals("null")) {
                        expelledStudents = null;
                    } else {
                        expelledStudents = Long.parseLong(keys.get(i));
                    }
                    break;
                case 6:
                    if (keys.get(i).equals("null")) {
                        formOfEducation = null;
                    } else {
                        formOfEducation = FormOfEducation.valueOf(keys.get(i));
                    }
                    break;
                case 7:
                    semesterEnum = Semester.valueOf(keys.get(i));
                    break;
                case 8:
                    personName = keys.get(i);
                    break;
                case 9:
                    personPassportId = keys.get(i);
                    break;
                case 10:
                    eyeColor = Color.valueOf(keys.get(i));
                    break;
                case 11:
                    nationality = Country.valueOf(keys.get(i));
                    break;
                case 12:
                    coordinatesX = Long.parseLong(keys.get(i));
                    break;
                case 13:
                    coordinatesY = Double.parseDouble(keys.get(i));
                    break;
            }
        }
        groupAdmin = new Person(personName, personPassportId, eyeColor, nationality);
        coordinates = new Coordinates(coordinatesX, coordinatesY);
        studyGroup = new StudyGroup(id, name, creationDate, studentsCount, expelledStudents,
                formOfEducation, semesterEnum, groupAdmin, coordinates);
        keys.clear();
        return studyGroup;
    }

    /**
     * Метод для выбора поля из коллекции
     * @param i номер поля
     * @return номер поля
     */
    private static int Chooser(int i) {
        if (fields.get(i).equals("ID")) {
            return 1;
        } else if (fields.get(i).equals("NAME")) {
            return 2;
        } else if (fields.get(i).equals("CREATIONDATE")) {
            return 3;
        } else if (fields.get(i).equals("STUDENTSCOUNT")) {
            return 4;
        } else if (fields.get(i).equals("EXPELLEDSTUDENTS")) {
            return 5;
        } else if (fields.get(i).equals("FORMOFEDUCATION")) {
            return 6;
        } else if (fields.get(i).equals("SEMESTERENUM")) {
            return 7;
        } else if (fields.get(i).equals("PERSONNAME")) {
            return 8;
        } else if (fields.get(i).equals("PASSPORTID")) {
            return 9;
        } else if (fields.get(i).equals("EYECOLOR")) {
            return 10;
        } else if (fields.get(i).equals("NATIONALITY")) {
            return 11;
        } else if (fields.get(i).equals("X")) {
            return 12;
        } else if (fields.get(i).equals("Y")) {
            return 13;
        } else {
            return 0;
        }
    }
}