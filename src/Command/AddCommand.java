package Command;

import util.CollectionManager;
import util.ConsoleManager;

import java.io.IOException;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class AddCommand implements Command {
    CollectionManager collectionManager;
    ConsoleManager consoleManager;

    /**
     * Конструктор команды.
     * @param cm менеджер коллекции.
     * @param csm менеджер консоли.
     */
    public AddCommand(CollectionManager cm, ConsoleManager csm) {
        this.collectionManager = cm;
        this.consoleManager = csm;
    }
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return " {element} : add new element to collection";
    }

    @Override
    public boolean execute(String arg) throws IOException {
        collectionManager.add(consoleManager.askGroup(collectionManager.nextId()));
        return true;
    }
}
