package server.services;

import model.ParkingSpace;
import model.Reservation;
import model.Site;
import model.User;

import java.sql.Timestamp;
import java.util.List;

public class Subject {
    private UserService userService;
    private SiteService siteService;
    private ParkingSpaceService parkingSpaceService;
    private ReservationService reservationService;

    public Subject() {
        userService = new UserService();
        siteService = new SiteService();
        parkingSpaceService = new ParkingSpaceService();
        reservationService = new ReservationService();
    }

    public List<Site> selectAllSites() {
        return siteService.selectAll();
    }

    public boolean findUser(User user) {
        return userService.findUser(user);
    }

    public List<Reservation> selectAllReservations(String username) {
        return reservationService.selectAll(username);
    }

    public List<Reservation> selectAllReservations() {
        return reservationService.selectAll();
    }

    public void insertReservation(String username, Reservation reservation) {
        reservationService.insert(username, reservation);
    }

    public void deleteReservation(String username, int id) {
        reservationService.delete(username, id);
    }

    public List<ParkingSpace> selectAvailableParkingSpaces(int siteId, Timestamp startDate, Timestamp endDate) {
        return parkingSpaceService.selectAvailable(siteId, startDate, endDate);
    }
}
