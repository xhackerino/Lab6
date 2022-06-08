package manager;

import commands.base.CommandResult;
import studyGroup.StudyGroup;

import java.util.ArrayList;
import java.util.Stack;

public class CollectionManagerImplClient implements CollectionManager {
    private Stack<StudyGroup> studyGroup;
    private final ConsoleManager consoleManager;

    public CollectionManagerImplClient(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }


    @Override
    public Stack<StudyGroup> getStudyGroup() {
        return studyGroup;
    }

    /**
     * входящие ограничения на ввод данных
     */

    @Override
    public CommandResult getInfo() {
        return new CommandResult("");
    }

    @Override
    public CommandResult show() {
        return new CommandResult("");
    }

    @Override
    public CommandResult add(String... additionalInput) {
        StudyGroup studyGroup = consoleManager.askGroup(this.nextId());
        ArrayList<String> additional = new ArrayList<>();
        additional.add(studyGroup.getId().toString());
        additional.add(studyGroup.getName());
        additional.add(String.valueOf(studyGroup.getStudentsCount()));
        additional.add(String.valueOf(studyGroup.getExpelledStudents()));
        additional.add(studyGroup.getFormOfEducation().toString());
        additional.add(studyGroup.getSemesterEnum().toString());
        additional.add(studyGroup.getGroupAdmin().getName());
        additional.add(studyGroup.getGroupAdmin().getPassportID());
        additional.add(studyGroup.getGroupAdmin().getEyeColor().toString());
        additional.add(studyGroup.getGroupAdmin().getNationality().toString());
        additional.add(studyGroup.getCoordinates().getX().toString());
        additional.add(String.valueOf(studyGroup.getCoordinates().getY()));
        String[] to = new String[additional.size()];
        additional.toArray(to);
        return new CommandResult("", to);
    }

    @Override
    public long nextId() {
        return 1;
    }

    @Override
    public boolean checkId(long Id) {
        return true;
    }

    @Override
    public CommandResult updateElement(String... additionalInput) {
        StudyGroup studyGroup = consoleManager.askGroup(this.nextId());
        ArrayList<String> additional = new ArrayList<>();
        additional.add(studyGroup.getId().toString());
        additional.add(studyGroup.getName());
        additional.add(String.valueOf(studyGroup.getStudentsCount()));
        additional.add(String.valueOf(studyGroup.getExpelledStudents()));
        additional.add(studyGroup.getFormOfEducation().toString());
        additional.add(studyGroup.getSemesterEnum().toString());
        additional.add(studyGroup.getGroupAdmin().getName());
        additional.add(studyGroup.getGroupAdmin().getPassportID());
        additional.add(studyGroup.getGroupAdmin().getEyeColor().toString());
        additional.add(studyGroup.getGroupAdmin().getNationality().toString());
        additional.add(studyGroup.getCoordinates().getX().toString());
        additional.add(String.valueOf(studyGroup.getCoordinates().getY()));
        String[] to = new String[additional.size()];
        additional.toArray(to);
        return new CommandResult("", to);
    }

    @Override
    public CommandResult removeElement(Long id) {
        return new CommandResult("");
    }

    @Override
    public CommandResult save() {
        return new CommandResult("");
    }

    @Override
    public CommandResult clear() {
        return new CommandResult("");
    }

    @Override
    public CommandResult exit() {
        System.out.println("Ending of program. Bye!");
        System.exit(0);
        return new CommandResult("");
    }

    @Override
    public CommandResult removeByIndex(int index) {
        return new CommandResult("");
    }

    @Override
    public CommandResult removeLast() {
        return new CommandResult("");
    }

    @Override
    public CommandResult removeAllGreater(int index) {
        return new CommandResult("");
    }

    @Override
    public CommandResult sumOfExpelledStudents() {
        return new CommandResult("");
    }

    @Override
    public CommandResult filterStartsWithName(String name) {
        return new CommandResult("");
    }

    @Override
    public CommandResult printFieldAscendingStudentsCount() {
        return new CommandResult("");
    }
}
