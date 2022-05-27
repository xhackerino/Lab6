package Command;

import java.io.IOException;

import Exception.EmptyIOException;
import util.CollectionManager;
import static util.ConsoleManager.Print;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param cm менеджер коллекции.
     */
    public Clear(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return " : clears the collection";
    }

    @Override
    public boolean execute(String arg) throws IOException {
        collectionManager.clear();
        Print("Collection cleared");
        return true;
    }
}
