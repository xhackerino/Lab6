import Command.*;
import util.CollectionManager;
import util.CommandManager;
import util.ConsoleManager;
import util.FileManager;

import java.io.PrintStream;
import java.util.Scanner;

import static util.ConsoleManager.Print;

/**
 * Главный класс программы. Запускает программу.
 * @author Ilya Rakin ISU 336934
 */
public class Main {
    public static void main(String[] args) {
        boolean success = false;
        while (!success) {
            try {
                System.out.println("Hello, World!");
                String file_name = "FILE_NAME";
                System.setOut(new PrintStream(System.out, true, "UTF-8"));
                if (System.getenv(file_name) == null) {
                    Print("No file name specified.\n");
                }
                String fileName = System.getenv(file_name);
                FileManager fm = new FileManager(fileName);
                Scanner scanner = new Scanner(System.in);
                ConsoleManager consoleManager = new ConsoleManager(scanner);
                CollectionManager collectionManager = new CollectionManager(fm);
                System.out.println("Enter your command: ");
                CommandManager commandManager = new CommandManager(consoleManager, scanner, new Command[]{
                        new AddCommand(collectionManager, consoleManager), new Clear(collectionManager), new Exit(collectionManager),
                        new FilterStartsWithName(collectionManager), new Info(collectionManager), new PrintFieldAscendingStudentsCount(collectionManager),
                        new RemoveAt(collectionManager), new RemoveByIdCommand(collectionManager, consoleManager), new RemoveGreater(collectionManager),
                        new RemoveLast(collectionManager), new SaveCommand(collectionManager), new Show(collectionManager), new SumOfExpelledStudents(collectionManager),
                        new UpdateIdCommand(collectionManager, consoleManager)});
                commandManager.consoleMode();
                success = true;
            } catch (Exception e) {
                break;
            }
        }
    }
}