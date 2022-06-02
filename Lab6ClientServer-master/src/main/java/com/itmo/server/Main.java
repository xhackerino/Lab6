package com.itmo.server;

import com.itmo.app.Application;
import com.itmo.app.Collection;
import com.itmo.app.FileWorker;
import com.itmo.exceptions.WithoutArgumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Stack;

public class Main {
    public static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            if (args.length == 0) throw new WithoutArgumentException();
            int port = Integer.parseInt(args[0]);
            Application application = new Application(new Stack<>());
            Collection collection = FileWorker.getCollection("FILE_NAME", "input.xml");
            if (collection != null) application = new Application(collection.get());
            else log.info("Error with input file, collection is empty");
            System.out.println("Server is running...");
            Server server = new Server();
            server.connect(port);
            log.info("Connection is established, listen port: " + port);
            server.run(application);
        } catch (WithoutArgumentException e) {
            System.out.println("Error: no port entered");
        } catch (NumberFormatException e) {
            System.out.println("Error: port must be integer");
        } catch (IOException e) {
            System.out.println("Error: connection is not established");
        }
    }
}
