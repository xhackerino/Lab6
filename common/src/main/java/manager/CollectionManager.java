package manager;

import commands.base.CommandResult;
import studyGroup.StudyGroup;

import java.util.Stack;

public interface CollectionManager {
    Stack<StudyGroup> getStudyGroup();

    CommandResult getInfo();

    CommandResult show();

    CommandResult add(String ... additionalInput);

    long nextId();

    boolean checkId(long Id);

    CommandResult updateElement(String ... additionalInput);

    CommandResult removeElement(Long id);

    CommandResult save(); //work

    CommandResult clear();

    CommandResult exit();

    CommandResult removeByIndex(int index) throws IllegalArgumentException;

    CommandResult removeLast();

    CommandResult removeAllGreater(int index) throws IllegalArgumentException;

    CommandResult sumOfExpelledStudents();

    CommandResult filterStartsWithName(String name);

    CommandResult printFieldAscendingStudentsCount();
}
