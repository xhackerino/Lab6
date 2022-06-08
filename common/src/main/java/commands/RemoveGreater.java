package commands;

import commands.base.Command;
import commands.base.CommandResult;
import exception.CommandException;
import manager.CollectionManager;
import manager.ConsoleManager;


/**
 * Команда 'remove_greater'. Удаляет из коллекции все элементы, превышающие заданный.
 */
public class RemoveGreater implements Command {
    CollectionManager collectionManager;
    int index;

    /**
     * Конструктор команды.
     * @param cm менеджер коллекции.
     */
    public RemoveGreater(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "{element} : removes all elements greater than {element} from the collection";
    }

    @Override
    public CommandResult execute(String[] args, String ... additionalInput) throws CommandException {
        int index;
        try {
            if (args[0].trim().equals("")) throw new Exception();
            index = Integer.parseInt(args[0].trim());
        } catch (NumberFormatException e) {
            throw new CommandException("index must be an integer");
        } catch (Exception e) {
            throw new CommandException("index must be non-empty");
        }
        if (index < 0) {
            throw new CommandException("index must be non-negative");
        }
        try {
        return collectionManager.removeAllGreater(index);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
