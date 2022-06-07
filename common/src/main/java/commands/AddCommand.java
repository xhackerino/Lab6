package commands;


import commands.base.Command;
import commands.base.CommandResult;
import exception.CommandException;
import manager.CollectionManager;
import manager.ConsoleManager;

/// я тут почудил

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class AddCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param collectionManager менеджер коллекции.
     */
    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "{element} : add new element to collection";
    }

    @Override
    public CommandResult execute(String[] args, String ... additionalInput) throws CommandException {
        return collectionManager.add(additionalInput);
    }
}
