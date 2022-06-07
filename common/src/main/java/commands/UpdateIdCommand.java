package commands;

import commands.base.Command;
import commands.base.CommandResult;
import exception.CommandException;
import manager.CollectionManager;


/**
 * Команда 'update id'. Обновляет значение элемента коллекции, id которого равен заданному.
 */
public class UpdateIdCommand implements Command {
    CollectionManager collectionManager;

    public UpdateIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "{id of element} : update the value of the collection element whose id is equal to the given one";
    }

    @Override
    public CommandResult execute(String[] args, String ... additionalInput) throws CommandException {
        long id;
        try {
            if (args[0].trim().equals("")) throw new Exception();
            id = Long.parseLong(args[0].trim());
        } catch (NumberFormatException e) {
            throw new CommandException("id must be an integer");
        } catch (Exception e) {
            throw new CommandException("id mustn't be empty");
        }
        if (!collectionManager.checkId(id)) {
            throw new CommandException("There is no element with such id");
        }
        return collectionManager.updateElement(additionalInput);
    }
}