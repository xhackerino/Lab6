package commands;

import commands.base.Command;
import commands.base.CommandResult;
import exception.CommandException;
import manager.CollectionManager;

/**
 * Команда 'exit'. Завершает выполнение программы.
 */
public class Exit implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param cm менеджер коллекции.
     */
    public Exit(CollectionManager cm) {
        this.collectionManager = cm;
    }
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "end of program";
    }

    @Override
    public CommandResult execute(String[] args, String ... additionalInput) throws CommandException {
        return collectionManager.exit();
    }
}
