package server;

import server.services.Subject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class Server {
    private List<ClientHandler> clients;

    void start() throws IOException {
        Subject subject = new Subject();
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket clientSocket;
        clients = new ArrayList<ClientHandler>();
        int counter = 0;
        System.out.println(" >> " + "Server Started");
        while (true) {
            counter += 1;
            clientSocket = serverSocket.accept();
            System.out.println(" >> " + "Client No:" + counter + " started!");
            ClientHandler client = new ClientHandler(this,clientSocket, subject);
            clients.add(client);
            Thread clientThread = new Thread(client);
            clientThread.start();
        }
    }

    void notifyObservers() throws IOException {
        for (ClientHandler c : clients) {
            c.notifyObserver();
        }
    }
}

