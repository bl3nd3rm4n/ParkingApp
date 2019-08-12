package client.ui;

import client.controller.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.ParkingSpace;
import model.Reservation;
import model.Site;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
public class FormController implements Initializable {
    public Controller controller;
    @FXML
    public ListView siteView;
    @FXML
    public DatePicker endCalendar;
    @FXML
    public ListView parkingSpaceView;
    @FXML
    private ListView reservationView;
    @FXML
    private DatePicker startCalendar;
    @FXML
    private GridPane loggedOutPane;
    @FXML
    private GridPane loggedInPane;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new Controller();
        controller.formController = this;
    }

    @FXML
    public void requestAllReservations() throws IOException {
        controller.requestAllReservations();
    }

    public void requestAllSites() throws IOException {
        controller.requestAllSites();
    }

    public void requestAvailableParkingSpaces() throws IOException {
        if (startCalendar.getValue() != null && endCalendar.getValue() != null) {
            LocalDate localDate = startCalendar.getValue();
            Timestamp startTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
            localDate = endCalendar.getValue();
            Timestamp endTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
            controller.requestAvailableParkingSpaces(((new ObjectMapper().readValue(siteView.getSelectionModel().getSelectedItem().toString(), Site.class)).getId()),
                    startTimestamp, endTimestamp);
        }
    }

    public void displayAllReservations(List<Reservation> list) {
        reservationView.setCellFactory((Callback<ListView<String>, ListCell<String>>) list1 -> new YourFormatCell());
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.setAll(list.stream().map(this::displayReservation).collect(Collectors.toList()));
        reservationView.setItems(observableList);
    }

    public void displayAllSites(List<Site> list) {
        siteView.setCellFactory((Callback<ListView<String>, ListCell<String>>) list1 -> new YourFormatCell());
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.setAll(list.stream().map(this::displaySite).collect(Collectors.toList()));
        siteView.setItems(observableList);
    }

    public void displayAvailableParkingSpaces(List<ParkingSpace> list) {
        parkingSpaceView.setCellFactory((Callback<ListView<String>, ListCell<String>>) list1 -> new YourFormatCell());
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.setAll(list.stream().map(this::displayParkingSpace).collect(Collectors.toList()));
        parkingSpaceView.setItems(observableList);
    }

    public String displayReservation(Reservation reservation) {
        return reservation.toString();
    }

    public String displayParkingSpace(ParkingSpace parkingSpace) {
        return parkingSpace.toString();
    }

    public String displaySite(Site site) {
        return site.toString();
    }

    @FXML
    private void logOut() {
        loggedInPane.setVisible(false);
        loggedOutPane.setVisible(true);
        passwordField.setText("");
    }

    @FXML
    private void logIn() throws IOException {
        if (controller.logIn(new User(usernameField.getText(), passwordField.getText()))) {
            refresh();
            loggedOutPane.setVisible(false);
            loggedInPane.setVisible(true);
        }
    }

    public void refresh() throws IOException {
        requestAllReservations();
        requestAllSites();
    }

    private boolean soldOut(String show) {
        return show.contains(" 0-");
    }

    @FXML
    public void reserve() throws IOException {
        if (startCalendar.getValue() != null && endCalendar.getValue() != null) {
            LocalDate localDate = startCalendar.getValue();
            Timestamp startTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
            localDate = endCalendar.getValue();
            Timestamp endTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
            controller.reserve(new Reservation(0, 1, (new ObjectMapper().readValue(parkingSpaceView.getSelectionModel().getSelectedItem().toString(), ParkingSpace.class)).getId(), startTimestamp, endTimestamp));
        }
        requestAllReservations();
    }

    public void cancelReservation() throws IOException {
        controller.cancelReservation(new ObjectMapper().readValue(reservationView.getSelectionModel().getSelectedItem().toString(), Reservation.class).getReservationId());
        requestAllReservations();
    }

    public class YourFormatCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            if (item != null) {
                super.updateItem(item, empty);
                setText(item);
                setTextFill(soldOut(item) ? Color.RED : Color.BLACK);
            }
        }
    }
}
