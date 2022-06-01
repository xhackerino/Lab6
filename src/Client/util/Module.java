package Client.util;

import Command.*;
import util.CollectionManager;

import java.io.IOException;
import java.util.Scanner;

public class Module {
    private static CollectionManager collectionManager;
    private static String outputMessage = "";
    private static final String[] commands = {"help", "info", "exit", "clear", "add", "remove_at", "update", "show", "save",
            "sum_of_expelled_students", "remove_greater", "remove_last", "remove_by_id", "print_field_ascending_students_count",
            "filter_starts_with_name"};

    private static int chooseCommand(String commandName) {
        for (int i = 0; i < commandName.length(); i++) {
            if (commandName.equals(commands[i])) {
                return i;
            }
        }
        return -1;
    }

    public static void setCollectionManager(CollectionManager collectionManager) {
        Module.collectionManager = collectionManager;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public static boolean run(Command command) throws IOException{
        String commandName = command.getName();
        Scanner scanner = new Scanner(commandName);
        scanner.useDelimiter("\\s");
        commandName = scanner.next();
        String[] cmd = (scanner.nextLine().trim() + " ").split(" ", 2);
        switch (chooseCommand(commandName)) {
            case (0): {

            }
            case (1): {
                Info info = (Info) command;
                return info.execute("info");
            }
            case (2): {
                Show showCommand = (Show) command;
                return showCommand.execute("show");
            }
            case (3): {
                AddCommand addCommand = (AddCommand) command;
                return addCommand.execute("add");
            }
            case (4): {
                UpdateIdCommand updateIdCommand = (UpdateIdCommand) command;
                return updateIdCommand.execute("update");
            }
            case (5): {
                RemoveByIdCommand removebyIDCommand = (RemoveByIdCommand) command;
                return removebyIDCommand.execute("remove_at");
            }
            case (6): {
                Clear clear = (Clear) command;
                return clear.execute("clear");
            }
            case (7): {
                SaveCommand save = (SaveCommand) command;
                return save.execute("save");
            }
//            case (8): {
//                ExecuteCommand execute = (ExecuteCommand) command;
//            }
            case (9): {
                Exit exit = (Exit) command;
                return exit.execute("exit");
            }
            case (10): {
                RemoveAt removeAt = (RemoveAt) command;
                return removeAt.execute("remove_at");
            }
            case (11): {
                RemoveLast removeLast = (RemoveLast) command;
                return removeLast.execute("remove_last");
            }
            case (12): {
                RemoveGreater removeGreater = (RemoveGreater) command;
                return removeGreater.execute("remove_greater");
            }
            case (13): {
                SumOfExpelledStudents sumOfExpelledStudents = (SumOfExpelledStudents) command;
                return sumOfExpelledStudents.execute("sum_of_expelled_students");
            }
            case (14): {
                FilterStartsWithName filterStartsWithName = (FilterStartsWithName) command;
                return filterStartsWithName.execute("filter_starts_with_name");
            }
            case (15): {
                PrintFieldAscendingStudentsCount printFieldAscendingStudentsCount = (PrintFieldAscendingStudentsCount) command;
                return printFieldAscendingStudentsCount.execute("print_field_ascending_students_count");
            }
            case (-1): {
                System.out.println("Неизвестная команда");
                return false;
            }
        }
        return false;
    }
    public static String messageFlush(){
        String message = outputMessage;
        Module.outputMessage = "";
        return message;
    }
    public static void messageAdd(String message){
        Module.outputMessage += message+"\n";
    }
}
