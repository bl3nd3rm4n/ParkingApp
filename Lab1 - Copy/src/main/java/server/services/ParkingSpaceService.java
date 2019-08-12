package server.services;

import model.ParkingSpace;
import server.repository.ParkingSpaceRepository;

import java.sql.Timestamp;
import java.util.List;

public class ParkingSpaceService {
    private ParkingSpaceRepository parkingSpaceRepository;

    public ParkingSpaceService() {
        this.parkingSpaceRepository = new ParkingSpaceRepository();
    }

    public List<ParkingSpace> selectAvailable(int siteId, Timestamp startDate, Timestamp endDate) {
        return parkingSpaceRepository.selectAvailable(siteId, startDate, endDate);
    }
}


