package commands;

import commands.base.Command;
import commands.base.CommandResult;
import exception.CommandException;
import manager.CollectionManager;


/**
 * Команда 'sum_of_expelled_students'. Выводит сумму значений поля expelledStudents для всех элементов коллекции.
 */
public class SumOfExpelledStudents implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param collectionManager менеджер коллекции.
     */
    public SumOfExpelledStudents(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "sum_of_expelled_students";
    }

    @Override
    public String getDescription() {
        return "prints the sum of the expelledStudents field of all elements in the collection.";
    }

    @Override
    public CommandResult execute(String[] args, String ... additionalInput) throws CommandException {
        return collectionManager.sumOfExpelledStudents();
    }
}
