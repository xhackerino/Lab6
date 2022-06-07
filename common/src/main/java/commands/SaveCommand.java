package commands;

import commands.base.Command;
import commands.base.CommandResult;
import exception.CommandException;
import manager.CollectionManager;


/**
 * Команда 'save'. Сохраняет коллекцию в файл.
 */
public class SaveCommand implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param cm коллекция
     */
    public SaveCommand(CollectionManager cm) {
        this.collectionManager = cm;
    }
    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return " : saves the collection to a file";
    }

    @Override
    public CommandResult execute(String[] args, String ... additionalInput) throws CommandException {
        return collectionManager.save();
    }
}
