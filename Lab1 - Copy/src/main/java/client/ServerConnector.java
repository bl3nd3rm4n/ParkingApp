package client;

import client.controller.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.ParkingSpace;
import model.Reservation;
import model.Site;
import model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ServerConnector {

    Controller observer;
    private Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ServerReader reader;

    public ServerConnector(Controller controller) {
        observer = controller;
    }

    public void requestAllReservations() throws IOException {
        byte[] outStream = ("SelectAllR$").getBytes(StandardCharsets.UTF_8);
        outputStream.write(outStream, 0, outStream.length);
        outputStream.flush();
    }

    public void requestAllSites() throws IOException {
        byte[] outStream = ("SelectAllS$").getBytes(StandardCharsets.UTF_8);
        outputStream.write(outStream, 0, outStream.length);
        outputStream.flush();
    }

    public void requestAvailableParkingSpaces(int siteId, Timestamp startDate, Timestamp endDate) throws IOException {
        byte[] outStream = ("SelectAllP" +
                new ObjectMapper().writeValueAsString(siteId) + "|" +
                new SimpleDateFormat("\"yyyy-MM-dd").format(startDate) + "T00:00:00\"" + "|" +
                new SimpleDateFormat("\"yyyy-MM-dd").format(startDate) + "T00:00:00\"$").getBytes(StandardCharsets.UTF_8);
        outputStream.write(outStream, 0, outStream.length);
        outputStream.flush();
    }

    public boolean logIn(User user) throws IOException {
        clientSocket = new Socket("localhost", 8888);
        outputStream = clientSocket.getOutputStream();
        inputStream = clientSocket.getInputStream();
        byte[] outStream = ("LogIn" + new ObjectMapper().writeValueAsString(user) + "$").getBytes(StandardCharsets.UTF_8);
        outputStream.write(outStream, 0, outStream.length);
        outputStream.flush();
        byte[] bytesFrom = new byte[4096];
        reader = new ServerReader(this, clientSocket);
        inputStream.read(bytesFrom, 0, bytesFrom.length);
        String returndata = new String(bytesFrom, StandardCharsets.US_ASCII);
        System.out.println(returndata);
        if (returndata.trim().equals("true")) {
            Thread readerThread = new Thread(reader);
            readerThread.start();
            return true;
        }
        return false;
    }

    void NotifyObserver() throws IOException {
        observer.refresh();
    }

    public void reserve(Reservation reservation) throws IOException {
        byte[] outStream = ("Reserve" +
                new ObjectMapper().writeValueAsString(reservation) + "$").getBytes(StandardCharsets.UTF_8);
        outputStream.write(outStream, 0, outStream.length);
        outputStream.flush();
    }

    public void cancelReservation(int reservationId) throws IOException {
        byte[] outStream = ("CancelReservation" +
                new ObjectMapper().writeValueAsString(reservationId) + "$").getBytes(StandardCharsets.UTF_8);
        outputStream.write(outStream, 0, outStream.length);
        outputStream.flush();
    }
}

class ServerReader implements Runnable {
    private ServerConnector connector;
    private Socket socket;

    ServerReader(ServerConnector connector, Socket socket) {
        this.connector = connector;
        this.socket = socket;
    }

    @Override
    public void run() {
        byte[] bytesFrom = new byte[4096];
        String dataFromServer;
        while (socket.isConnected() && !socket.isClosed())
            try {
                System.out.println("citesc");
                InputStream inputStream = socket.getInputStream();
                inputStream.read(bytesFrom, 0, bytesFrom.length);
                dataFromServer = new String(bytesFrom, StandardCharsets.US_ASCII);
                dataFromServer = dataFromServer.substring(0, dataFromServer.indexOf("$"));
                if (dataFromServer.startsWith("SR")) {
                    connector.observer.formController.displayAllReservations(Arrays.asList(new ObjectMapper().readValue((dataFromServer.substring(2)), Reservation[].class)));
                } else if (dataFromServer.startsWith("SS")) {
                    connector.observer.formController.displayAllSites(Arrays.asList(new ObjectMapper().readValue((dataFromServer.substring(2)), Site[].class)));
                } else if (dataFromServer.startsWith("SP")) {
                    connector.observer.formController.displayAvailableParkingSpaces(Arrays.asList(new ObjectMapper().readValue((dataFromServer.substring(2)), ParkingSpace[].class)));
                } else if (dataFromServer.startsWith("Notify")) {
                    connector.NotifyObserver();
                }
            } catch (Exception ex) {
                System.out.println(" >> " + ex.toString());
                break;
            }
    }
}
