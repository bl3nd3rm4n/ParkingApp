package server.repository;

import model.ParkingSpace;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingSpaceRepository {

    public List<ParkingSpace> selectAvailable(int siteId, Timestamp startDate, Timestamp endDate) {
        DBUtils connector = DBUtils.getInstance();
        List<ParkingSpace> parkingSpaceList = new ArrayList<>();
        String sql = "SELECT parkingSpaceId,code,siteId FROM parkingSpaces where siteId=?";
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, siteId);
            ResultSet rs = pstmt.executeQuery();
            // loop through the result set
            while (rs.next())
                parkingSpaceList.add(new ParkingSpace(rs.getInt("parkingSpaceId"),
                        rs.getString("code"),
                        rs.getInt("siteId")
                ));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return parkingSpaceList;
    }
}
