package server.repository;

import model.Reservation;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class ReservationRepository {

    public ReservationRepository() {
    }

    public List<Reservation> selectAll(String username) {
        DBUtils connector = DBUtils.getInstance();
        List<Reservation> reservationList = new ArrayList<>();
        String sql = "SELECT reservationId,reservations.userId,parkingSpaceId,startDate,endDate FROM reservations inner join users on users.userId=reservations.userId  where username=?";
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            // loop through the result set
            while (rs.next())
                reservationList.add(new Reservation(rs.getInt("reservationId"),
                        rs.getInt("userId"),
                        rs.getInt("parkingSpaceId"),
                        Timestamp.valueOf(rs.getString("startDate")),
                        Timestamp.valueOf(rs.getString("endDate"))
                ));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservationList;
    }

    public List<Reservation> selectAll() {
        DBUtils connector = DBUtils.getInstance();
        List<Reservation> reservationList = new ArrayList<>();
        String sql = "SELECT reservationId,userId,parkingSpaceId,startDate,endDate FROM reservations";
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            ResultSet rs = pstmt.executeQuery();
            // loop through the result set
            while (rs.next()){
                System.out.println(rs.getString("startDate"));
                reservationList.add(new Reservation(rs.getInt("reservationId"),
                        rs.getInt("userId"),
                        rs.getInt("parkingSpaceId"),
                        Timestamp.valueOf(rs.getString("startDate")),
                        Timestamp.valueOf(rs.getString("endDate"))
                ));}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservationList;
    }

    public void insert(String username, Reservation reservation) {
        if (doInsert(reservation.getUserId(), reservation.getParkingSpaceId(), reservation.getStartDate(), reservation.getEndDate())) {
            DBLogger.insert(username, reservation);
        }
    }

    public void delete(String username, int id) {
        Reservation reservation = selectOne(id);
        if (reservation != null && doDelete(id)) {
            DBLogger.delete(username, reservation);
        }
    }

    public boolean doInsert(int userId, int parkingSpaceId, Timestamp startDate, Timestamp endDate) {
        DBUtils connector = DBUtils.getInstance();
        String sql = "INSERT INTO reservations VALUES(null,?,?,?,?)";
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, parkingSpaceId);
            pstmt.setString(3, startDate.toString());
            pstmt.setString(4, endDate.toString());
            pstmt.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Reservation selectOne(int id) {
        DBUtils connector = DBUtils.getInstance();
        Reservation reservation = null;
        String sql = "SELECT reservationId,userId,parkingSpaceId,startDate,endDate FROM reservations where reservationId=" + id;
        try (Connection conn = connector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            reservation = new Reservation(rs.getInt("reservationId"),
                    rs.getInt("userId"),
                    rs.getInt("parkingSpaceId"),
                    Timestamp.valueOf(rs.getString("startDate")),
                    Timestamp.valueOf(rs.getString("endDate")
                    ));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservation;
    }

    public boolean doDelete(int id) {
        DBUtils connector = DBUtils.getInstance();
        String sql = "Delete from reservations where reservationId=? ";
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
