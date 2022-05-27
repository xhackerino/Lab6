package Command;

import util.CollectionManager;

import java.io.IOException;
import Exception.EmptyIOException;

/**
 * Команда 'save'. Сохраняет коллекцию в файл.
 */
public class SaveCommand implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param cm коллекция
     */
    public SaveCommand(CollectionManager cm) {
        this.collectionManager = cm;
    }
    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return " : saves the collection to a file";
    }

    @Override
    public boolean execute(String arg) throws IOException {
        collectionManager.save();
        return true;
    }
}
