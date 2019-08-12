package server.services;

import model.Reservation;
import model.User;
import server.repository.ReservationRepository;

import java.util.List;

public class ReservationService {
    private ReservationRepository reservationRepository;

    public ReservationService() {
        this.reservationRepository = new ReservationRepository();
    }

    public List<Reservation> selectAll(String username) {
        return reservationRepository.selectAll(username);
    }

    public List<Reservation> selectAll() {
        return reservationRepository.selectAll();
    }

    public void insert(String username, Reservation reservation) {
        reservationRepository.insert(username, reservation);
    }

    public void delete(String username, int id) {
        reservationRepository.delete(username, id);
    }
}


