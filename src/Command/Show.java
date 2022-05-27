package Command;

import util.CollectionManager;

import java.io.IOException;

import static util.ConsoleManager.Print;

/**
 * Команда 'show'. Выводит на экран все элементы коллекции.
 */
public class Show implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды 'show'.
     * @param cm объект класса CollectionManager.
     */
    public Show(CollectionManager cm) {
        this.collectionManager = cm;
    }
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return " : prints all elements of the collection in string representation";
    }

    @Override
    public boolean execute(String arg) throws IOException {
        Print("All elements of the collection: ");
        Print(collectionManager.show());
        return true;
    }
}