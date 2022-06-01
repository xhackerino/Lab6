package Client;

import Client.util.Pair;
import Client.util.CommandFromClient;
import Client.util.CommandResult;
import Exception.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;

@SuppressWarnings("FieldCanBeLocal")
public final class Client {
    private final int clientPort;
    private final int serverPort;
    private final String clientIP;
    private final String serverIP;
    private final int waitingTime = 300;
    private final int countOfBytesForSize = 4;
    private final int timeoutToSend = 10;

    public Client(int clientPort, int serverPort, String clientIP, String serverIP) {
        this.clientPort = clientPort;
        this.serverPort = serverPort;
        this.clientIP = clientIP;
        this.serverIP = serverIP;
    }

    public CommandResult sendCommand(CommandFromClient commandFromClient) throws NoDataException {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            datagramChannel.configureBlocking(false); // нужно, чтобы в случае, если от сервера не придет никакого ответа не блокироваться навсегда
            send(datagramChannel, commandFromClient);
            return receiveCommandResult(datagramChannel);
        } catch (BindException e) {
            return new CommandResult("Could not bind to port " + clientPort);
        } catch (IOException e) {
            return new CommandResult("Could not send command to server, the message from it is: " + e.getMessage());
        }

    }

    private void send(DatagramChannel datagramChannel, CommandFromClient commandFromClient) throws IOException, NoDataException {

        datagramChannel.bind(new InetSocketAddress(clientIP, clientPort));

        SocketAddress serverSocketAddress = new InetSocketAddress(serverIP, serverPort);

        Pair<byte[], byte[]> pair = serialize(commandFromClient);

        byte[] sendDataBytes = pair.getFirst();
        byte[] sendDataAmountBytes = pair.getSecond();

        try {
            ByteBuffer sendDataBuffer = ByteBuffer.wrap(sendDataAmountBytes);
            int limit = timeoutToSend;
            while (datagramChannel.send(sendDataBuffer, serverSocketAddress) < sendDataAmountBytes.length) {
                limit -= 1;
                System.out.println("Could not send a package, re-trying");
                if (limit == 0) {
                    throw new NoDataException();
                }
            } // сначала отправляем число-количество байтов в основном массиве байтов
            ByteBuffer sendBuffer = ByteBuffer.wrap(sendDataBytes);
            while (datagramChannel.send(sendBuffer, serverSocketAddress) < sendDataBytes.length) {
                limit -= 1;
                System.out.println("Could not send a package, re-trying");
                if (limit == 0) {
                    throw new NoDataException();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not resolve address you entered, please check it and try again");
        } catch (UnresolvedAddressException e) {
            System.out.println("Could not send data to server because it is too big");
        }


    }

    private CommandResult receiveCommandResult(DatagramChannel datagramChannel) throws IOException {
        byte[] amountOfBytesHeader = new byte[countOfBytesForSize];
        ByteBuffer amountOfBytesHeaderWrapper = ByteBuffer.wrap(amountOfBytesHeader);
        try {
            receiveToBuffer(datagramChannel, amountOfBytesHeaderWrapper, waitingTime);
            byte[] dataBytes = new byte[bytesToInt(amountOfBytesHeader)];


            ByteBuffer dataBytesWrapper = ByteBuffer.wrap(dataBytes);

            receiveToBuffer(datagramChannel, dataBytesWrapper, 1);

            return (CommandResult) deserialize(dataBytes);

        } catch (NoAnswerGotException e) {
            return new CommandResult("No answer from server");
        } catch (ClassNotFoundException e) {
            return new CommandResult("Could not understand answer from server");
        }
    }

    private void receiveToBuffer(DatagramChannel datagramChannel, ByteBuffer receiverBuffer, int timeout) throws NoAnswerGotException, IOException {
        int timeoutToSend = timeout;
        SocketAddress checkingAddress = null;

        while (checkingAddress == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace(); // never thrown
            }
            checkingAddress = datagramChannel.receive(receiverBuffer);
            if (timeoutToSend == 0) {
                throw new NoAnswerGotException();
            }
            timeoutToSend--;
        }
    }

    /**
     * @return first - data itself, second - amount of bytes in data
     */
    private Pair<byte[], byte[]> serialize(Object obj) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(obj);
        byte[] sizeBytes = ByteBuffer.allocate(countOfBytesForSize).putInt(byteArrayOutputStream.size()).array();

        return new Pair<>(byteArrayOutputStream.toByteArray(), sizeBytes); // в первых 4 байтах будет храниться число-количество данных отправления
    }

    public static int bytesToInt(byte[] bytes) {
        final int eight = 8;
        final int ff = 0xFF;

        int value = 0;
        for (byte b : bytes) {
            value = (value << eight) + (b & ff);
        }
        return value;
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}