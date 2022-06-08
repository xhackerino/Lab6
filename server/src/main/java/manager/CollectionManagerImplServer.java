package manager;

import commands.base.CommandResult;
//import exception.CommandException;
import studyGroup.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;
import java.util.Stack;


/**
 * Класс, объект которого хранит в себе коллекцию и управляет ей.
 */
public class CollectionManagerImplServer implements CollectionManager {
    private final Stack<StudyGroup> studyGroup;
    private final FileManager fileManager;
    private final java.time.ZonedDateTime currTime;

    /**
     * Конструктор класса CollectionManager
     *
     * @param fm объект класса FileManager
     */

    public CollectionManagerImplServer(FileManager fm) {
        this.fileManager = fm;
        studyGroup = fileManager.ReadCollection();
        currTime = java.time.ZonedDateTime.now();
    }

    @Override
    public Stack<StudyGroup> getStudyGroup() {
        return studyGroup;
    }

    /**
     * Метод для получения информации о коллекции.
     *
     * @return возвращает информацию о коллекции
     */
    @Override
    public CommandResult getInfo() {
        return new CommandResult("Collection with type Stack of StudyGroup elements"
                + ", date of initialization: " + DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(currTime)
                + ", number of elements in the collection: " + (studyGroup.size()));
    }

    /**
     * Метод для вывода коллекции в консоль.
     *
     * @return возвращает коллекцию в консоль в виде строк
     */
    @Override
    public CommandResult show() {
        StringBuilder sb = new StringBuilder();
        studyGroup.forEach(group -> sb.append(group.toString()).append("\n"));
        return new CommandResult("This is your collection:" + "\n" + sb);
    }


    /**
     * Метод для добавления элемента в коллекцию.
     */
    @Override
    public CommandResult add(String... additionalInput) {
        StudyGroup sg = new StudyGroup(
                Long.parseLong(additionalInput[0]),
                additionalInput[1],
                ZonedDateTime.now(),
                Long.parseLong(additionalInput[2]),
                Long.parseLong(additionalInput[3]),
                FormOfEducation.valueOf(additionalInput[4]),
                Semester.valueOf(additionalInput[5]),
                new Person(additionalInput[6], additionalInput[7], Color.valueOf(additionalInput[8]), Country.valueOf(additionalInput[9])),
                new Coordinates(Long.parseLong(additionalInput[10]), Double.parseDouble(additionalInput[11]))
        );
        studyGroup.add(sg);
        return new CommandResult("Element added");
    }

    /**
     * Метод для генерации следующего id для нового элемента.
     *
     * @return возвращает следующий идентификатор
     */

    @Override
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
    @Override
    public boolean checkId(long Id) {
        for (StudyGroup group : studyGroup) {
            if (group.getId() == Id)
                return true;
        }
        return false;
    }

    /**
     * Метод для обновления значений элемента коллекции.
     */
    @Override
    public CommandResult updateElement(String... additionalInput) {
        for (StudyGroup group : studyGroup) {
            if (group.getId() == Long.parseLong(additionalInput[0])) {
                group.setName(additionalInput[1]);
                group.setCreationDate(ZonedDateTime.now());
                group.setStudentsCount(Long.parseLong(additionalInput[2]));
                group.setExpelledStudents(Long.parseLong(additionalInput[3]));
                group.setFormOfEducation(FormOfEducation.valueOf(additionalInput[4]));
                group.setSemesterEnum(Semester.valueOf(additionalInput[5]));
                group.setGroupAdmin(new Person(additionalInput[6], additionalInput[7], Color.valueOf(additionalInput[8]), Country.valueOf(additionalInput[9])));
                group.setCoordinates(new Coordinates(Long.parseLong(additionalInput[10]), Double.parseDouble(additionalInput[11])));
                return new CommandResult("Element updated");
            }
        }
        return new CommandResult("Element not found");
    }

    /**
     * Метод для удаления элемента по ID.
     *
     * @param id идентификатор
     */
    @Override
    public CommandResult removeElement(Long id) {
        studyGroup.removeIf(group -> Objects.equals(group.getId(), id));
        return new CommandResult("Element has been removed by its ID");
    }

    /**
     * Метод для сохранения коллекции в файл.
     */
    @Override
    public CommandResult save() {
        fileManager.WriteCollection(studyGroup);
        return new CommandResult("Collection saved");
    }

    /**
     * Метод очищающий коллекцию.
     */
    @Override
    public CommandResult clear() {
        if (studyGroup.size() > 0) {
            studyGroup.clear();
            return new CommandResult("Collection cleared");
        } else {
            return new CommandResult("Collection is already empty");
        }
    }

    /**
     * Метод выхода из программы.
     */
    @Override
    public CommandResult exit() {
        fileManager.WriteCollection(studyGroup);
        return new CommandResult("");
    }

    /**
     * Метод удаления элемента по индексу.
     *
     * @param index индекс
     */
    @Override
    public CommandResult removeByIndex(int index) throws IllegalArgumentException {
        if (index > this.getStudyGroup().size()) {
            throw new IllegalArgumentException("Index is out of range");
        }
        int indeks = 0;
        for (StudyGroup group : studyGroup) {
            if (indeks == index) {
                studyGroup.remove(group);
                return new CommandResult("Element has been removed by its index");
            } else {
                indeks++;
            }
        }
        return new CommandResult("");
    }

    /**
     * Метод удаления последнего элемента коллекции.
     */
    @Override
    public CommandResult removeLast() {
//        try {
            if (studyGroup.size() > 0) {
                studyGroup.remove(studyGroup.lastElement());
                return new CommandResult("Last element has been removed");
//            } else throw new CommandException("Collection is already empty");
//
//        } catch(CommandException e){
//            e.printStackTrace();
        }
        return new CommandResult("");
    }

    /**
     * Метод удалить все элементы больше заданного
     */
    @Override
    public CommandResult removeAllGreater(int index) throws IllegalArgumentException {
        if (this.getStudyGroup().size() < index) {
            throw new IllegalArgumentException("index must be less than the size of the collection");
        }
        boolean success = false;
        int i = 0;
        int k = 0;
        while (!success) {
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
            return new CommandResult("Removed " + k + " element");
        } else if (k == 0) {
            return new CommandResult("Nothing to remove");
        } else if (k > 1) {
            return new CommandResult("Removed " + k + " elements");
        }
        return new CommandResult("");
    }

    /**
     * Метод подсчета суммы отчисленных студентов.
     */
    @Override
    public CommandResult sumOfExpelledStudents() {
        int sum = 0;
        for (StudyGroup group : studyGroup) {
            sum += group.getExpelledStudents();
        }
        return new CommandResult("Sum of selected elements: " + sum);
    }

    /**
     * Метод вывода элементов, значение поля name которых начинаются с заданной подстроки
     *
     * @param name имя в поле name
     */
    @Override
    public CommandResult filterStartsWithName(String name) {
        StringBuilder fswn = new StringBuilder();
        for (StudyGroup group : studyGroup) {
            if (name.equals("") || name == null) {
                return new CommandResult("Name can't be empty");
            } else {
                fswn.append(group).append("\n");
            }
        }
        System.out.println("Elements, which 'name' field starts with " + "'" + name + "'");
        return new CommandResult(fswn.toString());
    }

    /**
     * Метод вывода значений поля StudentsCount всех элементов в порядке возрастания
     */
    @Override
    public CommandResult printFieldAscendingStudentsCount() {
        studyGroup.sort(Comparator.comparing(StudyGroup::getStudentsCount));
        return new CommandResult("The collection is sorted by the field 'StudentsCount' in ascending order");
    }
}