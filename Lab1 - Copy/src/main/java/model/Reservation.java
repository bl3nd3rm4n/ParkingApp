package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Timestamp;

public class Reservation {
    private int reservationId;
    private int userId;
    private int parkingSpaceId;
    private Timestamp startDate;
    private Timestamp endDate;

    public Reservation() {
    }

    public Reservation(int reservationId, int userId, int parkingSpaceId, Timestamp startDate, Timestamp endDate) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.parkingSpaceId = parkingSpaceId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @JsonGetter
    public int getReservationId() {
        return reservationId;
    }

    @JsonGetter
    public int getUserId() {
        return userId;
    }

    @JsonGetter
    public int getParkingSpaceId() {
        return parkingSpaceId;
    }

    @JsonGetter
    public Timestamp getStartDate() {
        return startDate;
    }

    @JsonGetter
    public Timestamp getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
