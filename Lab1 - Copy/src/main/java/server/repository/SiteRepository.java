package server.repository;

import model.Site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SiteRepository {

    public List<Site> selectAll() {
        DBUtils connector = DBUtils.getInstance();
        List<Site> siteList = new ArrayList<>();
        String sql = "SELECT siteId,address FROM sites";
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            ResultSet rs = pstmt.executeQuery();
            // loop through the result set
            while (rs.next())
                siteList.add(new Site(rs.getInt("siteId"),
                        rs.getString("address")
                ));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return siteList;
    }
}
