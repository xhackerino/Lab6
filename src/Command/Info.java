package Command;

import util.CollectionManager;
import static util.ConsoleManager.Print;

import java.io.IOException;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param cm менеджер коллекции.
     */
    public Info(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return " : prints information about the collection (type, initialization date, number of elements, etc.)";
    }

    @Override
    public boolean execute(String arg) throws IOException {
        Print(collectionManager.getInfo());
        return true;
    }
}
