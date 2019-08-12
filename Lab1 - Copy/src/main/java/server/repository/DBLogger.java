package server.repository;

import model.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBLogger {
    private static final Logger logger = LogManager.getLogger(DBLogger.class);

    public static void insert(String user, Reservation reservation) {
        logger.traceEntry(user + " inserted " + reservation);
    }

    public static void delete(String user, Reservation reservation) {
        logger.traceEntry(user + " deleted " + reservation);
    }
}
