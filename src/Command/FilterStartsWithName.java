package Command;

import util.CollectionManager;

import java.io.IOException;
import Exception.EmptyIOException;

/**
 * Команда 'filter_starts_with_name'. Выводит элементы коллекции, значение поля name которых начинается с заданной подстроки.
 */
public class FilterStartsWithName implements Command{
    CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager объект класса CollectionManager
     */
    public FilterStartsWithName(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public String getName() {
        return "filter_starts_with_name";
    }

    @Override
    public String getDescription() {
        return " name : print elements, which 'name' field value starts with the given substring";
    }

    @Override
    public boolean execute(String arg) throws IOException {
        collectionManager.filterStartsWithName(arg);
        return true;
    }
}
