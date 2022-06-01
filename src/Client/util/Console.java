package Client.util;

import Client.Client;
import Command.ExecuteScriptCommand;
import Command.*;
import Exception.NoDataException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.NoSuchElementException;


public class Console {
    private final Output output;
    private final Input input;
    private final Client client;
    private final AskGroup askGroup;
    private final Collection<String> commandes;


    public Console(Output output, Input input, Client client,
                   Collection<String> commandes) {
        this.output = output;
        this.input = input;
        this.client = client;
        this.commandes = commandes;
        this.askGroup = new AskGroup(input, output);
    }

    public void start() throws ClassNotFoundException, IOException {
        String inpuut;
        do {
            inpuut = readNextCommand();
            final String[] parsedInp = parseToNameAndArg(inpuut);
            final String commandName = parsedInp[0];
            Serializable commandArg = parsedInp[1];
            String commandArg2 = ""; // only for update command in this case
            if (commandes.contains(commandName)) {
                if ("add".equals(commandName) || "add_if_min".equals(commandName) || "remove_greater".equals(commandName)) {
                    commandArg = askGroup.makeStudyGroup();
                }
                if ("update".equals(commandName)) {
                    commandArg2 = (String) commandArg;
                    commandArg = askGroup.makeStudyGroup();
                }
                if ("execute_script".equals(commandName)) {
                    new ExecuteScriptCommand((String) commandArg).execute(input);
                } else {
                    try {
                        output.println(
                                client.sendCommand(new CommandFromClient(getCommandObjectByName(commandName, commandArg, commandArg2)))
                                        .getResult().toString()
                        );
                    } catch (NoDataException e) {
                        output.println("Could not send a command");
                    }
                }
            } else {
                output.println("The command was not found. Please use \"help\" to know about commands.");
            }
        } while (!"exit".equals(input));
    }

    private String[] parseToNameAndArg(String input) {
        String[] arrayInput = input.split(" ");
        String inputCommand = arrayInput[0];
        String inputArg = "";

        if (arrayInput.length >= 2) {
            inputArg = arrayInput[1];
        }

        return new String[]{inputCommand, inputArg};
    }

    private String readNextCommand() throws IOException {
        output.print(">>>");
        try {
            return input.nextLine();
        } catch (NoSuchElementException e) {
            return "exit";
        }
    }

    private Command getCommandObjectByName(String commandName, Serializable arg, String arg2) throws IOException {
        Command command;
        switch (commandName) {
            case "add":
                System.out.println("add");
//                command = new AddCommand(collectionManager, consoleManager);
                break;
            case "clear":
                System.out.println("clear");
//                command = new Clear(collectionManager);
                break;
//            case "filter_less_than_semester_enum": command = new FilterLessThanSemesterEnumCommand((String) arg);
//                break;
//            case "history": command = new HistoryCommand();
//                break;
//            case "info": command = new InfoCommand();
//                break;
//            case "min_by_id": command = new MinByIDCommand((String) arg);
//                break;
//            case "print_ascending": command = new PrintAscendingCommand();
//                break;
//            case "remove_by_id": command = new RemoveByIdCommand((String) arg);
//                break;
//            case "remove_greater": command = new RemoveGreaterCommand((StudyGroup) arg);
//                break;
//            case "show": command = new ShowCommand();
//                break;
//            case "update": command = new UpdateCommand((StudyGroup) arg, arg2);
//                break;
//            default: command = new HelpCommand();
//                break;
        }
        return null;
    }
}
