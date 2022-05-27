//package Servers;
//
//import util.CollectionManager;
//import util.Module;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Server {
//    private int port;
//    private Socket socket;
//    private ServerSocket serverSocket;
//    private ObjectInputStream inputStream;
//    private ObjectOutputStream outputStream;
//    private InputStream stream;
//
//    public Server() throws IOException {
//        this.port = 2022;
//        boolean connected = false;
//        while (!connected) {
//            try {
//                serverSocket = new ServerSocket(port);
////                this.socket = this.serverSocket.accept();
////                this.inputStream = new ObjectInputStream(this.socket.getInputStream());
////                this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
////                this.input = this.socket.getInputStream();
//                connected = true;
//            } catch (Exception e) {
//                port = (int) (Math.random() * 10000);
//            }
//        }
//        stream = System.in;
//        Module.setCollectionManager(new CollectionManager());
//    }
//    public void run() {
//        try {
//            connect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

