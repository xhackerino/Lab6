package Command;

import util.CollectionManager;

import java.io.IOException;
import Exception.EmptyIOException;

/**
 * Команда 'remove_last'. Удаляет последний элемент из коллекции.
 */
public class RemoveLast implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param cm менеджер коллекции.
     */
    public RemoveLast(CollectionManager cm){
        this.collectionManager = cm;
    }
    @Override
    public String getName() {
        return "remove_last";
    }

    @Override
    public String getDescription() {
        return " : removes the last element of the collection";
    }

    @Override
    public boolean execute(String arg) throws IOException {
        collectionManager.removeLast();
        return true;
    }
}
