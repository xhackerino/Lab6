package commands;


import commands.base.Command;
import commands.base.CommandResult;
import exception.CommandException;
import manager.CollectionManager;


/**
 * Команда 'remove_at'. Удаляет элемент из коллекции по индексу.
 */
public class RemoveAt implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager объект класса CollectionManager.
     */
    public RemoveAt(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "remove_at";
    }

    @Override
    public String getDescription() {
        return "{index} : removes the element at the given position in the collection (index)";
    }

    @Override
    public CommandResult execute(String[] args, String... additionalInput) throws CommandException {
        if(args.length != 1)
            throw new CommandException("Enter correct id (non-negative integer)");
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
            return collectionManager.removeByIndex(index);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
