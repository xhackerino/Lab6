package commands;

import commands.base.Command;
import commands.base.CommandResult;
import exception.CommandException;
import manager.CollectionManager;
import manager.ConsoleManager;


/**
 * Команда 'show'. Выводит на экран все элементы коллекции.
 */
public class Show implements Command {
    private final CollectionManager collectionManager;

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
        return "prints all elements of the collection in string representation";
    }

    @Override
    public CommandResult execute(String[] args, String ... additionalInput) throws CommandException {
        return collectionManager.show();
    }
}