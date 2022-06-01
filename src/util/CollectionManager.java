package util;

import StudyGroup.StudyGroup;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;
import java.util.Stack;

import static util.ConsoleManager.Print;
import static util.ConsoleManager.PrintError;

/**
 * Класс, объект которого хранит в себе коллекцию и управляет ей.
 */
public class CollectionManager {
    private final Stack<StudyGroup> studyGroup;
    private final FileManager fileManager;
    private final java.time.ZonedDateTime currTime;

    /**
     * Конструктор класса CollectionManager
     *
     * @param fm объект класса FileManager
     * @throws IOException исключение возникающее при ошибке чтения или записи в файл
     */

    public CollectionManager(FileManager fm) throws IOException {
        this.fileManager = fm;
        studyGroup = fileManager.ReadCollection();
        currTime = java.time.ZonedDateTime.now();
        System.out.println("Enter 'help' to see all the commands");
    }

    public Stack<StudyGroup> getStudyGroup() {
        return studyGroup;
    }

    /**
     * Метод для получения информации о коллекции.
     *
     * @return возвращает информацию о коллекции
     */
    public String getInfo() {
        return "Collection with type Stack of StudyGroup elements"
                + ", date of initialization: " + DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(currTime)
                + ", number of elements in the collection: " + (studyGroup.size());
    }

    /**
     * Метод для вывода коллекции в консоль.
     *
     * @return возвращает коллекцию в консоль в виде строк
     */
    public String show() {
        StringBuilder show = new StringBuilder();
        for (StudyGroup sgp : studyGroup) {
            show.append(sgp.toString()).append("\n");
        }
        return show.toString();
    }

    /**
     * Метод для добавления элемента в коллекцию.
     *
     * @param sg объект класса StudyGroup
     */
    public void add(StudyGroup sg) {
        studyGroup.add(sg);
        Print("Element added");
//        Print(studyGroup.toString()+"\n");
    }

    /**
     * Метод для генерации следующего id для нового элемента.
     *
     * @return возвращает следующий идентификатор
     */
    public long nextId() {
        if (studyGroup.isEmpty())
            return 1;
        else
            return studyGroup.lastElement().getId() + 1;
    }

    /**
     * Метод для проверки соответствия ID.
     *
     * @param Id идентификатор
     * @return возвращает true/false
     */
    public boolean checkId(long Id) {
        for (StudyGroup group : studyGroup) {
            {
                if (group.getId() == Id) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод для обновления значений элемента коллекции.
     *
     * @param sg объект класса StudyGroup.
     */
    public void updateElement(StudyGroup sg) {
        for (StudyGroup group : studyGroup) {
            if (Objects.equals(group.getId(), sg.getId())) {
                group.setId(sg.getId());
                group.setName(sg.getName());
                group.setCoordinates(sg.getCoordinates());
                group.setCreationDate(sg.getCreationDate());
                group.setStudentsCount(sg.getStudentsCount());
                group.setExpelledStudents(sg.getExpelledStudents());
                group.setFormOfEducation(sg.getFormOfEducation());
                group.setSemesterEnum(sg.getSemesterEnum());
                group.setGroupAdmin(sg.getGroupAdmin());
                Print("Element has been updated");
//                Print(studyGroup.toString());
            }
        }
    }

    /**
     * Метод для удаления элемента по ID.
     *
     * @param id идентификатор
     */
    public void removeElement(Long id) {
        for (StudyGroup group : studyGroup) {
            if (Objects.equals(group.getId(), id)) {
                studyGroup.remove(group);
                Print("Element removed");
//                Print(studyGroup.toString());
            }
        }
    }

    /**
     * Метод для сохранения коллекции в файл.
     */
    public void save() throws IOException {
        fileManager.WriteCollection(studyGroup);
        Print("Collection saved");
    }

    /**
     * Метод очищающий коллекцию.
     */
    public void clear() {
        if (studyGroup.size() > 0) {
            studyGroup.clear();
        } else {
            Print("Collection cleared");
        }
//        Print(studyGroup.toString());
    }

    /**
     * Метод выхода из программы.
     */
    public void exit() {
        Print("Ending of program. Bye, bye...");
        System.exit(0);
    }

    /**
     * Метод удаления элемента по индексу.
     *
     * @param index индекс
     */
    public void removeByIndex(int index) {
        int indeks = 0;
        for (StudyGroup group : studyGroup) {
            if (indeks == index) {
                studyGroup.remove(group);
                Print("Element removed");
                break;
            } else {
                indeks++;
            }
        }
    }

    /**
     * Метод удаления последнего элемента коллекции.
     */
    public void removeLast() {
        studyGroup.remove(studyGroup.size() - 1);
        Print("Element removed");
    }

    /**
     * Метод удалить все элементы больше заданного
     * @param index индекс
     */
    public void removeAllGreater(int index) {
        int i = 0;
        int k = 0;
        while (true) {
            try {
                if (i > index) {
                    studyGroup.remove(i);
                    k++;
                } else {
                    i++;
                }
            } catch (Exception e) {
                break;
            }
        }
        if (k == 1) {
            Print("Removed " + k + " element");
        } else if (k == 0){
            Print("Nothing to remove");
        } else if (k > 1) {
            Print("Removed " + k + " elements");
        }
    }
    /**
     * Метод подсчета суммы отчисленных студентов.
     */
    public void sumOfExpelledStudents() {
        int sum = 0;
        for (StudyGroup group : studyGroup) {
            sum += group.getExpelledStudents();
        }
        Print("Sum of selected elements: " + sum);
    }

    /**
     * Метод вывода элементов, значение поля name которых начинаются с заданной подстроки
     *
     * @param name имя в поле name
     */
    public void filterStartsWithName(String name) {
        String fswn = "";
        for (StudyGroup group : studyGroup) {
            if (name.equals("")) {
                PrintError("Name can't be empty");
                break;
            } else if (group.getName().startsWith(name.trim())) {
                fswn += group + "\n";
                Print("Elements, which 'name' field starts with " + "'" + name + "'");
                Print(fswn);
            }
        }
        if (fswn.equals("")) {
            Print("There are no elements matching the conditions in the collection, try other options");
        }
    }

    /**
     * Метод вывода значений поля StudentsCoubt всех элементов в порядке возрастания
     */
    public void printFieldAscendingStudentsCount() {
        studyGroup.sort(Comparator.comparing(StudyGroup::getStudentsCount));
        Print("The collection is sorted by the field 'StudentsCount' in ascending order");
//        Print(studyGroup.toString());
    }
}