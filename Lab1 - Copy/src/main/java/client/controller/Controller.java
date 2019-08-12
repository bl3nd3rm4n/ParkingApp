package client.controller;

import client.ServerConnector;
import client.ui.FormController;
import model.Reservation;
import model.User;

import java.io.IOException;
import java.sql.Timestamp;

public class Controller {
    public ServerConnector serverConnector;
    public FormController formController;

    public Controller() {
        this.serverConnector = new ServerConnector(this);
    }

    public void requestAllReservations() throws IOException {
        serverConnector.requestAllReservations();
    }

    public void requestAvailableParkingSpaces(int siteId, Timestamp startDate, Timestamp endDate) throws IOException {
        serverConnector.requestAvailableParkingSpaces(siteId, startDate, endDate);
    }

    public void requestAllSites() throws IOException {
        serverConnector.requestAllSites();
    }

    public boolean logIn(User user) throws IOException {
        if (serverConnector.logIn(user)) {
            return true;
        }
        return false;
    }
public void reserve(Reservation reservation) throws IOException {
        serverConnector.reserve(reservation);
}
    public void refresh() throws IOException {
        formController.refresh();
    }

    public void cancelReservation(int reservationId) throws IOException {
        serverConnector.cancelReservation(reservationId);
    }
}
