package cz.cvut.fel.esw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements Runnable {
    private final int port;
    private final Set<String> stringDatabase;
    private final ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
        this.stringDatabase = ConcurrentHashMap.newKeySet(Utils.INITIAL_DATABASE_CAPACITY);
        this.serverSocket = this.openServerSocket(port);
    }

    public Server(int port, int initDatabaseCapacity) {
        this.port = port;
        this.stringDatabase = ConcurrentHashMap.newKeySet(initDatabaseCapacity);
        this.serverSocket = this.openServerSocket(port);
    }

    private ServerSocket openServerSocket(int port){
        try {
            return new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException("Targeted port: " + port + " cannot be opened", e);
        }
    }

    @Override
    public void run() {
        System.out.println("Server running on port " + this.port);
        while (true) {
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException("Error while handling client connection", e);
            }
            new Thread(new ConnectionHandler(clientSocket, this.stringDatabase)).start();
        }
    }

    @Override
    public String toString() {
        return "Server{" +
                "port=" + port +
                '}';
    }
}
