package commands;


import commands.base.Command;
import commands.base.CommandResult;
import exception.CommandException;
import manager.CollectionManager;
import manager.ConsoleManager;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear implements Command {
    private final CollectionManager collectionManager;
    /**
     * Конструктор команды.
     * @param cm менеджер коллекции.
     */
    public Clear(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clears the collection";
    }

    @Override
    public CommandResult execute(String[] args, String ... additionalInput) throws CommandException {
        return collectionManager.clear();
    }
}
