package Client;

import Client.util.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

public final class Main {
    private static final int MAX_PORT = 65535;
    private static final Collection<String> COMMANDLIST = new HashSet<>();
    private static int serverPort;
    private static int clientPort;
    private static String serverIP;
    private static String clientIP;


    private Main() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void main(String[] args) {
        Output output = new Output(System.out);
        initCommandList();
        try {
            initArgs(args);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            output.println("Found invalid arguments. Please use the program as \"java -jar <name> <server port> <client port> <clientIp> <serverIp>\"");
            return;
        }
        try {
            Client client = new Client(clientPort, serverPort, clientIP, serverIP);
            new Console(output, new Input(System.in), client, COMMANDLIST).start();
        } catch (ClassNotFoundException | IOException e) {
            output.print(e.getMessage());
        }
    }

    private static void initCommandList() {
        Main.COMMANDLIST.add("add");
        Main.COMMANDLIST.add("clear");
        Main.COMMANDLIST.add("execute_script");
        Main.COMMANDLIST.add("exit");
        Main.COMMANDLIST.add("help");
        Main.COMMANDLIST.add("info");
        Main.COMMANDLIST.add("filter_starts_with_name");
        Main.COMMANDLIST.add("print_field_ascending_students_count");
        Main.COMMANDLIST.add("remove_at");
        Main.COMMANDLIST.add("remove_by_id");
        Main.COMMANDLIST.add("remove_greater");
        Main.COMMANDLIST.add("remove_last");
        Main.COMMANDLIST.add("save");
        Main.COMMANDLIST.add("sum_of_expelled_students");
        Main.COMMANDLIST.add("update_id");
        Main.COMMANDLIST.add("show");
    }
    private static void initArgs(String[] args) throws IllegalArgumentException, IndexOutOfBoundsException {

        serverPort = Integer.parseInt(args[0]);
        clientPort = Integer.parseInt(args[1]);
        if (serverPort > MAX_PORT || clientPort > MAX_PORT || serverPort < 0 || clientPort < 0) {
            throw new IllegalArgumentException("Port number must be in range 0 - 65535");
        }
        clientIP = args[2];
        final int three = 3;
        serverIP = args[three];
    }
}