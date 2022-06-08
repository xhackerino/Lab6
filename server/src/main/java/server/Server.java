package server;

import commands.*;
import commands.base.Command;
import commands.base.CommandRequestContainer;
import commands.base.CommandResponseContainer;
import commands.base.CommandResult;
import exception.CommandException;
import manager.CollectionManager;
import manager.CollectionManagerImplServer;
import manager.FileManager;


import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;

import static manager.ConsoleManager.print;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server started");
        String file_name = "FILE_NAME";
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        if (System.getenv(file_name) == null) {
            print("No file name specified.\n");
        }
        String fileName = System.getenv(file_name);
        FileManager fm = new FileManager(fileName);
        CollectionManager manager = new CollectionManagerImplServer(fm);
        HashMap<String, Command> commandHashMap = new HashMap<>();

        commandHashMap.put("add", new AddCommand(manager));
        commandHashMap.put("info", new Info(manager));
        commandHashMap.put("show", new Show(manager));
        commandHashMap.put("exit", new Exit(manager));
        commandHashMap.put("clear", new Clear(manager));
        commandHashMap.put("save", new SaveCommand(manager));
        commandHashMap.put("filter_starts_with_name", new FilterStartsWithName(manager));
        commandHashMap.put("print_field_ascending_students_count", new PrintFieldAscendingStudentsCount(manager));
        commandHashMap.put("remove_last", new RemoveLast(manager));
        commandHashMap.put("remove_at", new RemoveAt(manager));
        commandHashMap.put("remove_by_id", new RemoveByIdCommand(manager));
        commandHashMap.put("remove_greater", new RemoveGreater(manager));
        commandHashMap.put("sum_of_expelled_students", new SumOfExpelledStudents(manager));
        commandHashMap.put("update", new UpdateIdCommand(manager));

        InetAddress hostIP = InetAddress.getLocalHost();
        InetSocketAddress address = new InetSocketAddress(hostIP, 8999);
        DatagramChannel datagramChannel = DatagramChannel.open();
        DatagramSocket datagramSocket = datagramChannel.socket();
        datagramSocket.bind(address);

        ByteBuffer buffer = ByteBuffer.allocate(4096 * 4);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                commandHashMap.get("save").execute(new String[]{});
                System.out.println("Collection saved. Server stopped.");
            } catch (CommandException e) {
                e.printStackTrace();
            }
        }));
        // exit command thread
            Thread exitThread = new Thread(() -> {
                while (true) {
                    try {
                        String line = scanner.nextLine();
                        if (line.equals("exit")) {
                            System.exit(0);
                        } else if (line.equals("save")) {
                            try {
                                commandHashMap.get("save").execute(new String[]{});
                                System.out.println("Collection saved");
                            } catch (CommandException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.err.println("Wrong command. You can use only 'exit' command to stop server or 'save' to save collection.");
                        }
                    } catch (NoSuchElementException e) {
                        System.err.println("What are you doing? Stop that! Don't Ctrl+D my program, you are not good..." + "\n" +
                                "Restart the program and be polite.");
                    }
                }
            });
            exitThread.start();

        while (true) {
            SocketAddress from = datagramChannel.receive(buffer);
            System.out.println(from);
            buffer.flip();
            System.out.print("\nData...: ");
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);

            ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getMimeDecoder().decode(bytes));
            ObjectInputStream objectInputStream = new ObjectInputStream(bais);
            CommandRequestContainer inputContainer = (CommandRequestContainer) objectInputStream.readObject();

            String command = inputContainer.getCommandName();
            buffer.clear();

            try {
                if (commandHashMap.containsKey(command)) {
                    CommandResult result = commandHashMap.get(command).execute(inputContainer.getArgs(), inputContainer.getInput());
                    System.out.println(result.getResult());

                    CommandResponseContainer commandResponseContainer = new CommandResponseContainer(result.getResult());

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
                    objectOutputStream.writeObject(commandResponseContainer);

                    datagramChannel.send(ByteBuffer.wrap(Base64.getMimeEncoder().encode(baos.toByteArray())), from);
                } else {
                    System.err.println("Command not found");
                }
            } catch (CommandException e) {
                CommandResponseContainer commandResponseContainer = new CommandResponseContainer(e.getMessage());

                ByteArrayOutputStream bass = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(bass);
                objectOutputStream.writeObject(commandResponseContainer);

                datagramChannel.send(ByteBuffer.wrap(Base64.getMimeEncoder().encode(bass.toByteArray())), from);
            }
        }
    }
}
