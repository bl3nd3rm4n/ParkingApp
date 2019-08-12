package server.repository;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
    private static DBUtils instance;
    private String url;

    private DBUtils() {
        Properties serverProps = new Properties();
        try {
            serverProps.load(new FileReader("bd.config"));
            System.out.println("Properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        url = serverProps.getProperty("Lab1.jdbc.url");
    }

    public static DBUtils getInstance() {
        if (instance == null)
            instance = new DBUtils();
        return instance;
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            conn.createStatement().execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
