package Command;

import java.io.IOException;
import Exception.EmptyIOException;
import util.CollectionManager;

/**
 * Команда 'exit'. Завершает выполнение программы.
 */
public class Exit implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param cm менеджер коллекции.
     */
    public Exit(CollectionManager cm) {
        this.collectionManager = cm;
    }
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return " : end of program (without saving)";
    }

    @Override
    public boolean execute(String arg) throws IOException {
        collectionManager.exit();
        return true;
    }
}
