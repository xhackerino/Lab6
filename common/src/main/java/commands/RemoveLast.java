package commands;

import commands.base.Command;
import commands.base.CommandResult;
import exception.CommandException;
import manager.CollectionManager;

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
        return "removes the last element of the collection";
    }

    @Override
    public CommandResult execute(String[] args, String ... additionalInput) throws CommandException {
        return collectionManager.removeLast();
    }
}
