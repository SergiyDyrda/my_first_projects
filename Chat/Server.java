package com.javarush.test.level30.lesson15.big01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Serj on 08.01.2016.
 */
public class Server {

    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Enter workable port:");
        int port = ConsoleHelper.readInt();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            ConsoleHelper.writeMessage("Server is up!");
            while (true) {
                new Handler(serverSocket.accept()).start();
            }
        }
        catch (Exception e) {
            ConsoleHelper.writeMessage("Error!");
        }
        finally {
            try {
                serverSocket.close();
            }
            catch (IOException e) {
                ConsoleHelper.writeMessage("Can`t close serverSocket!");
            }
        }
    }

    public static void sendBroadcastMessage(Message message) {
        List<Connection> connections = new LinkedList<>();
        connections.addAll(connectionMap.values());
        for (Connection connection : connections) {
            try {
                connection.send(message);
            }
            catch (IOException e) {
                ConsoleHelper.writeMessage("Can`t send message");
            }
        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }


        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message clientResponse = connection.receive();
                if (clientResponse.getType() == MessageType.USER_NAME) {
                    if (clientResponse.getData() != null && !clientResponse.getData().isEmpty()) {
                        if (!connectionMap.containsKey(clientResponse.getData())) {
                            connectionMap.put(clientResponse.getData(), connection);
                            connection.send(new Message(MessageType.NAME_ACCEPTED));
                            return clientResponse.getData();
                        }
                    }
                }
            }
        }

        private void sendListOfUsers(Connection connection, String userName) throws IOException {
            for (Map.Entry<String, Connection> entry : connectionMap.entrySet()) {
                if (!entry.getKey().equals(userName)) {
                    connection.send(new Message(MessageType.USER_ADDED, entry.getKey()));
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            Message userMessage;
            while (true) {
                userMessage = connection.receive();
                if (userMessage.getType() == MessageType.TEXT)
                    sendBroadcastMessage(new Message(MessageType.TEXT, userName + ": " + userMessage.getData()));
                else
                    ConsoleHelper.writeMessage("MessageType error!");
            }
        }

        @Override
        public void run() {
            String userName = null;
            Connection newConnection = null;
            try {
                newConnection = new Connection(socket);
                ConsoleHelper.writeMessage("New connection established with " + newConnection.getRemoteSocketAddress());
                userName = serverHandshake(newConnection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                sendListOfUsers(newConnection, userName);
                serverMainLoop(newConnection, userName);

            }
            catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Information exchange error with remote address");
                if (userName != null) {
                    connectionMap.remove(userName);
                    sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
                }
                try {
                    newConnection.close();
                    ConsoleHelper.writeMessage(String.format("Connection with %s is closed", newConnection.getRemoteSocketAddress()));
                }
                catch (Exception e1) {
                    ConsoleHelper.writeMessage("Can`t close connection");
                }
            }
        }
    }
}
