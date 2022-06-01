package Command;

import java.io.*;

import Exception.EmptyIOException;
import StudyGroup.StudyGroup;
import util.CollectionManager;
import util.ConsoleManager;

import static util.ConsoleManager.PrintError;

/**
 * Команда 'update id'. Обновляет значение элемента коллекции, id которого равен заданному.
 */
public class UpdateIdCommand implements Command {
    CollectionManager collectionManager;
    ConsoleManager consoleManager;

    public UpdateIdCommand(CollectionManager cm, ConsoleManager csm) {
        this.collectionManager = cm;
        this.consoleManager = csm;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return " id {element} : update the value of the collection element whose id is equal to the given one";
    }

    @Override
    public boolean execute(String arg) throws IOException {
        long id;
        try {
            if (arg.trim().equals("")) throw new EmptyIOException();
            id = Long.parseLong(arg.trim());
        } catch (NumberFormatException e) {
            PrintError("id must be an integer");
            return true;
        } catch (EmptyIOException e) {
            PrintError("id mustn't be empty");
            return true;
        }
        if (!collectionManager.checkId(id)) {
            PrintError("There is no element with such id");
            return true;
        }
        StudyGroup group = consoleManager.askGroup(collectionManager.nextId());
        collectionManager.updateElement(group);
        return true;
    }
}