package cz.cvut.fel.esw;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Set;

public class ConnectionHandler implements Runnable {
    private final Socket clientSocket;
    private final Set<String> stringDatabase;
    public ConnectionHandler(Socket clientSocket, Set<String> stringDatabase) {
        this.clientSocket = clientSocket;
        this.stringDatabase = stringDatabase;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = this.clientSocket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Error connecting to sockets input stream", e);
        }
        try {
            OutputStream outputStream = this.clientSocket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("Error connecting to sockets output stream", e);
        }
        boolean connectionOK;
        do {
            connectionOK = this.handleConnection();
        } while (connectionOK);
    }

    private boolean handleConnection() {
        System.out.println("Got connection!");
        return false;
    }
}
