package Command;

import Exception.EmptyIOException;
import util.CollectionManager;

import java.io.IOException;

import static util.ConsoleManager.PrintError;

/**
 * Команда 'remove_at'. Удаляет элемент из коллекции по индексу.
 */
public class RemoveAt implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param cm объект класса CollectionManager.
     */
    public RemoveAt(CollectionManager cm) {
        this.collectionManager = cm;
    }
    @Override
    public String getName() {
        return "remove_at";
    }

    @Override
    public String getDescription() {
        return " index : removes the element at the given position in the collection (index)";
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
        }
        collectionManager.removeByIndex(index);
        return true;
    }
}
