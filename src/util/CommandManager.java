package util;

import Command.Command;
import Exception.EmptyIOException;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

import static util.ConsoleManager.Print;
import static util.ConsoleManager.PrintError;

/**
 * Класс нечто, которое будет парсить все поступаемые команды и вызывать их выполнение.
 */
public class CommandManager {
    /**
     * Поля, содержащие объекты команд.
     */
    private final Scanner scanner;
    private final Command[] commands;
    private final ConsoleManager consoleManager;
    private final Stack<String> openedScripts;
    /**
     * Конструктор менеджера. Автоматически инициализирует объекты всех команд при создании и менеджера коллекций.
     *
     * @param console    Менеджер консоли.
     * @param scanner    Сканер команд.
     * @param commands   Список команд.
     * @see CollectionManager Менеджер коллекций.
     */
    public CommandManager(ConsoleManager console, Scanner scanner, Command[] commands) {
        consoleManager = console;
        this.scanner = scanner;
        this.commands = commands;
        openedScripts = new Stack<>();
    }

    /**
     * Метод для скрипта. Парсит все команды из файла и выполняет их.
     * @param filename имя файла.
     * @throws IOException в случае ошибки ввода-вывода.
     * @throws EmptyIOException в случае пустого файла.
     */
    public void ScriptMode(String filename) throws IOException, EmptyIOException {
        ScriptManager scriptManager = new ScriptManager(filename.trim());
        openedScripts.add(filename.trim());
        boolean isRunning = true;
        while (isRunning) {
            String nextLine = scriptManager.getNextLine();
            if (nextLine == null)
                break;
            String[] cmd = (nextLine.trim() + " ").split(" ", 2);
            if (cmd[0].trim().equals("help")) {
                for (Command comnd : commands) {
                    Print(comnd.getName() + comnd.getDescription());
                }
            } else if (cmd[0].trim().equals("execute_script")) {
                if (!cmd[1].trim().equals("")) {
                    if (openedScripts.contains(cmd[1].trim())) {
                        PrintError("Script is already opened.");
                    } else {
                        ScriptMode(cmd[1].trim());
                    }
                } else {
                    PrintError("There is no script name.");
                }
            } else if (!cmd[0].trim().equals("")) {
                boolean commandFound = false;
                for (Command comnd : commands) {
                    if (cmd[0].trim().equals(comnd.getName())) {
                        if (comnd.getName().trim().equals("add")) {
                            consoleManager.ChangeScanner(scriptManager.getScanner());
                            try {
                                isRunning = comnd.execute(cmd[1].trim());
                                commandFound = true;
                            } catch (NoSuchElementException e) {
                                PrintError("Not enough parameters.");
                            }
                            consoleManager.ChangeScanner(new Scanner(System.in));
                        } else {
                            isRunning = comnd.execute(cmd[1].trim());
                            commandFound = true;
                        }
                        Print("Script " + filename + " finished.");
                        break;
                    }
                }
                if (!commandFound) {
                    PrintError(cmd[0] + " " + "'" + cmd[1] + "'" + " command not found.");
                }
            }
        }
    }

    /**
     * Метод для консоли. Парсит все команды из консоли и выполняет их.
     * @throws IOException в случае ошибки ввода-вывода.
     * @throws EmptyIOException в случае пустого файла.
     */
    public void consoleMode() throws IOException, EmptyIOException {
        boolean isRunning = true;
        while (isRunning) {
            String[] cmd = (scanner.nextLine().trim() + " ").split(" ", 2);
            if (cmd[0].trim().equals("help")) {
                Print("help : prints available commands.");
                Print("execute_script file_name : reads and executes script from the specified file, " +
                        "the script contains commands in the same form in which they are entered " +
                        "by the user in the console.");
                for (Command comnd : commands) {
                    Print(comnd.getName() + comnd.getDescription());
                }
            } else if (cmd[0].trim().equals("execute_script")) {
                if (!cmd[1].trim().equals("")) {
                    ScriptMode(cmd[1].trim());
                    openedScripts.clear();
                } else {
                    PrintError("Enter script name.");
                }
            } else if (!cmd[0].trim().equals("")) {
                boolean commandFound = false;
                for (Command comnd : commands) {
                    if (cmd[0].trim().equals(comnd.getName())) {
                        isRunning = comnd.execute(cmd[1]);
                        commandFound = true;
                        break;
                    }
                }
                if (!commandFound) {
                    PrintError("Command '" + cmd[0] + "' not found.");
                }
            }
        }
    }
}
