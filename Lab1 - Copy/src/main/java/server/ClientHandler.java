package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Reservation;
import model.User;
import server.services.Subject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

public class ClientHandler implements Runnable {
    private Server server;
    private Subject subject;
    private Socket clientSocket;
    private String username;

    ClientHandler(Server server, Socket clientSocket, Subject subject) {
        this.server = server;
        this.subject = subject;
        this.clientSocket = clientSocket;
    }

    void notifyObserver() throws IOException {
        byte[] sendBytes = null;
        String serverResponse = null;
        OutputStream outputStream = clientSocket.getOutputStream();
        if (!clientSocket.isClosed()) {
            serverResponse = "Notify$";
            sendBytes = serverResponse.getBytes(StandardCharsets.UTF_8);
            outputStream.write(sendBytes, 0, sendBytes.length);
            outputStream.flush();
        }
    }

    @Override
    public void run() {
        byte[] bytesFrom = new byte[4096];
        String dataFromClient = null;
        byte[] sendBytes = null;
        String serverResponse = null;
        while (clientSocket.isConnected() && !clientSocket.isClosed())
            try {
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
                inputStream.read(bytesFrom, 0, bytesFrom.length);
                dataFromClient = new String(bytesFrom, StandardCharsets.US_ASCII);
                dataFromClient = dataFromClient.substring(0, dataFromClient.indexOf("$"));
                System.out.println(dataFromClient);
                if (dataFromClient.startsWith("LogIn")) {
                    if (subject.findUser(new ObjectMapper().readValue(dataFromClient.substring(5), User.class))) {
                        serverResponse = "true";
                        username = new ObjectMapper().readValue(dataFromClient.substring(5), User.class).getUsername();
                    } else
                        serverResponse = "false";
                } else if (dataFromClient.startsWith("SelectAllR")) {
                    if (username.equals("admin")) {
                        serverResponse = "SR" + new ObjectMapper().writeValueAsString(subject.selectAllReservations()) + "$";
                    } else {
                        serverResponse = "SR" + new ObjectMapper().writeValueAsString(subject.selectAllReservations(username)) + "$";
                    }
                } else if (dataFromClient.startsWith("SelectAllS")) {
                    serverResponse = "SS" + new ObjectMapper().writeValueAsString(subject.selectAllSites()) + "$";
                } else if (dataFromClient.startsWith("SelectAllP")) {
                    String[] s = dataFromClient.substring(10).split("\\|");
                    serverResponse = "SP" + new ObjectMapper().writeValueAsString(subject.selectAvailableParkingSpaces(
                            new ObjectMapper().readValue(s[0], int.class),
                            new ObjectMapper().readValue(s[0], Timestamp.class),
                            new ObjectMapper().readValue(s[0], Timestamp.class)
                    )) + "$";
                } else if (dataFromClient.startsWith("Reserve")) {
                    subject.insertReservation(username, new ObjectMapper().readValue(dataFromClient.substring(7), Reservation.class));
                    server.notifyObservers();
                } else if (dataFromClient.startsWith("CancelReservation")) {
                    subject.deleteReservation(username, new ObjectMapper().readValue(dataFromClient.substring(17), int.class));
                    server.notifyObservers();
                }
                if (!serverResponse.equals("") && !dataFromClient.equals("")) {
                    System.out.println(serverResponse);
                    sendBytes = serverResponse.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(sendBytes, 0, sendBytes.length);
                    outputStream.flush();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                break;
            }
    }
}


