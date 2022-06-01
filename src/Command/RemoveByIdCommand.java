package Command;

import Exception.EmptyIOException;
import util.CollectionManager;
import util.ConsoleManager;

import java.io.IOException;

import static util.ConsoleManager.PrintError;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по его id.
 */
public class RemoveByIdCommand implements Command {
    CollectionManager collectionManager;
    ConsoleManager consoleManager;

    public RemoveByIdCommand(CollectionManager cm, ConsoleManager csm) {
        this.collectionManager = cm;
        this.consoleManager = csm;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return " id : removes element by id";
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
        collectionManager.removeElement(id);
        return true;
    }
}
