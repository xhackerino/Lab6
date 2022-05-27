package Command;

import util.CollectionManager;
import util.ConsoleManager;

import java.io.IOException;

import Exception.EmptyIOException;

import static util.ConsoleManager.PrintError;

/**
 * Команда 'remove_greater'. Удаляет из коллекции все элементы, превышающие заданный.
 */
public class RemoveGreater implements Command {
    CollectionManager collectionManager;
    int index;

    /**
     * Конструктор команды.
     * @param cm менеджер коллекции.
     */
    public RemoveGreater(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return " {element} : removes all elements greater than {element} from the collection";
    }

    @Override
    public boolean execute(String arg) throws IOException {
        int index;
        try {
            if (arg.trim().equals("")) throw new EmptyIOException();
            index = Integer.parseInt(arg.trim());
        } catch (NumberFormatException e) {
            PrintError("index must be an integer");
            return true;
        } catch (EmptyIOException e) {
            PrintError("index must be non-empty");
            return true;
        }
        if (index < 0) {
            PrintError("index must be non-negative");
            return true;
        }
        if (collectionManager.getStudyGroup().size() < index) {
            PrintError("index must be less than the size of the collection");
            return true;
        }
        collectionManager.removeAllGreater(index);
        return true;
    }
}
