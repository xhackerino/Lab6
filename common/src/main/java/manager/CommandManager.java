package manager;

import commands.base.Command;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

import static manager.ConsoleManager.print;
import static manager.ConsoleManager.printError;


/**
 * Класс нечто, которое будет парсить все поступаемые команды и вызывать их выполнение.
 */
public class CommandManager {
    /**
     * Поля, содержащие объекты команд.
     */
    private final Scanner scanner;
    private final Command[] commands;
    private final CollectionManager collectionManager;
    private final ConsoleManager consoleManager;
    private final Stack<String> openedScripts;
    /**
     * Конструктор менеджера. Автоматически инициализирует объекты всех команд при создании и менеджера коллекций.
     *
     * @param console    Менеджер консоли.
     * @param collection Менеджер коллекций.
     * @param scanner    Сканер команд.
     * @param commands   Список команд.
     * @see CollectionManager Менеджер коллекций.
     */
    public CommandManager(ConsoleManager console, CollectionManager collection, Scanner scanner, Command[] commands) {
        consoleManager = console;
        collectionManager = collection;
        this.scanner = scanner;
        this.commands = commands;
        openedScripts = new Stack<>();
    }

    /**
     * Метод для скрипта. Парсит все команды из файла и выполняет их.
     * @param filename имя файла.
     * @throws IOException в случае ошибки ввода-вывода.
     * @throws Exception в случае пустого файла.
     */
    public void ScriptMode(String filename) throws IOException, Exception {
        ScriptManager scriptManager = new ScriptManager(filename.trim());
        openedScripts.add(filename.trim());
        boolean isRunning = true;
        String line;
        String[] input;
        while (isRunning) {
            String nextLine = scriptManager.getNextLine();
            line = scanner.nextLine();
            input = line.split(" ");
            if (nextLine == null)
                break;
            String[] cmd = (nextLine.trim() + " ").split(" ", 2);
            System.out.println("suka"+cmd[0]);
            if (cmd[0].trim().equals("help")) {
                for (Command comnd : commands) {
                    print(comnd.getName() + comnd.getDescription());
                }
            } else if (cmd[0].trim().equals("execute_script")) {
                if (!cmd[1].trim().equals("")) {
                    if (openedScripts.contains(cmd[1].trim())) {
                        printError("Script is already opened.");
                    } else {
                        ScriptMode(cmd[1].trim());
                    }
                } else {
                    printError("There is no script name.");
                }
            } else if (!cmd[0].trim().equals("")) {
                boolean commandFound = false;
                for (Command comnd : commands) {
                    if (cmd[0].trim().equals(comnd.getName())) {
                        if (comnd.getName().trim().equals("add")) {
                            consoleManager.ChangeScanner(scriptManager.getScanner());
                            try {
                                comnd.execute(Arrays.copyOfRange(input, 1, input.length));
                                commandFound = true;
                            } catch (NoSuchElementException e) {
                                printError("Not enough parameters.");
                            }
                            consoleManager.ChangeScanner(new Scanner(System.in));
                        } else {
                            comnd.execute(Arrays.copyOfRange(input, 1, input.length));
                            commandFound = true;
                        }
                        print("Script " + filename + " finished.");
                        break;
                    }
                }
                if (!commandFound) {
                    printError(cmd[0] + " " + "'" + cmd[1] + "'" + " command not found.");
                }
            }
        }
    }

    /**
     * Метод для консоли. Парсит все команды из консоли и выполняет их.
     * @throws IOException в случае ошибки ввода-вывода.
     * @throws Exception в случае пустого файла.
     */
    public void consoleMode() throws IOException, Exception {
        boolean isRunning = true;
        String line;
        String[] input;
        while (isRunning) {
            line = scanner.nextLine();
            input = line.split(" ");
            String[] cmd = (scanner.nextLine().trim() + " ").split(" ", 2);
            if (cmd[0].trim().equals("help")) {
                print("help : prints available commands.");
                print("execute_script file_name : reads and executes script from the specified file, " +
                        "the script contains commands in the same form in which they are entered " +
                        "by the user in the console.");
                for (Command comnd : commands) {
                    print(comnd.getName() + comnd.getDescription());
                }
            } else if (cmd[0].trim().equals("execute_script")) {
                if (!cmd[1].trim().equals("")) {
                    ScriptMode(cmd[1].trim());
                    openedScripts.clear();
                } else {
                    printError("Enter script name.");
                }
            } else if (!cmd[0].trim().equals("")) {
                boolean commandFound = false;
                for (Command comnd : commands) {
                    if (cmd[0].trim().equals(comnd.getName())) {
                        comnd.execute(Arrays.copyOfRange(input, 1, input.length));
                        commandFound = true;
                        break;
                    }
                }
                if (!commandFound) {
                    printError("Command '" + cmd[0] + "' not found.");
                }
            }
        }
    }
}
