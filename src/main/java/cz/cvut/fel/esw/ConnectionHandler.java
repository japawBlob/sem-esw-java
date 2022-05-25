package cz.cvut.fel.esw;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import cz.cvut.fel.esw.proto.*;

import javax.xml.stream.FactoryConfigurationError;

public class ConnectionHandler implements Runnable {
    private final Socket clientSocket;
    private final Set<String> stringDatabase;
    public ConnectionHandler(Socket clientSocket, Set<String> stringDatabase) {
        this.clientSocket = clientSocket;
        this.stringDatabase = stringDatabase;
    }

    @Override
    public void run() {
        InputStream inputStream;
        OutputStream outputStream;
        try {
            inputStream = this.clientSocket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Error connecting to sockets input stream", e);
        }
        try {
            outputStream = this.clientSocket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("Error connecting to sockets output stream", e);
        }
        try {
            boolean connectionOK;
            do {
                connectionOK = this.handleConnection(inputStream, outputStream);
            } while (connectionOK);
        } catch (IOException e) {
            throw new RuntimeException("Error while handling connection");
        }

    }

    private boolean handleConnection(InputStream inputStream, OutputStream outputStream) throws IOException {
        int msgSize;
        try {
            msgSize = this.getMessageSize(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error getting message size...");
        }
        if (msgSize == -1){
            return false;
        }
        byte [] messageBody = getMessage(inputStream, msgSize);
        Request request = Request.parseFrom(messageBody);
        if (request.hasGetCount()) {
            handleGetCount(outputStream);
        } else {
            handlePostWords(request, outputStream);
        }
        return true;
    }

    private int getMessageSize(InputStream inputStream) throws IOException {
        byte sizeBytes [] = new byte [Utils.MESSAGE_LENGTH];
        inputStream.read(sizeBytes, 0, Utils.MESSAGE_LENGTH);
        return Utils.arrToInt(sizeBytes);
    }

    private byte [] getMessage(InputStream inputStream, int msgSize) throws IOException {
        byte [] messageBody = new byte[msgSize];
        int accepted_count = 0;
        while(accepted_count != msgSize){
            if (inputStream.available() > msgSize-accepted_count) {
                accepted_count += inputStream.read(messageBody, accepted_count, msgSize-accepted_count);
            } else {
                accepted_count += inputStream.read(messageBody, accepted_count, inputStream.available());
            }
        }
        return messageBody;
    }
    private void handleGetCount(OutputStream os) throws IOException {
        Response response = Response.newBuilder()
                .setCounter(this.stringDatabase.size())
                .setStatus(Response.Status.OK)
                .build();
        int sizeInt = response.getSerializedSize();
        byte[] sizeBytes = ByteBuffer.allocate(Utils.MESSAGE_LENGTH).putInt(sizeInt).array();
        os.write(sizeBytes);
        response.writeTo(os);
        this.stringDatabase.clear();
    }

    private void handlePostWords(Request request, OutputStream os) throws IOException {
        throw new EOFException ("To be implemented");
    }


}
