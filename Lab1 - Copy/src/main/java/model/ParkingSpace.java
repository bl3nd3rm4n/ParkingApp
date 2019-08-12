package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ParkingSpace {
    private int id;
    private String code;
    private int siteId;

    public ParkingSpace() {
    }

    public ParkingSpace(int id, String code, int siteId) {
        this.id = id;
        this.code = code;
        this.siteId = siteId;
    }

    @JsonGetter
    public int getId() {
        return id;
    }

    @JsonGetter
    public String getCode() {
        return code;
    }

    @JsonGetter
    public int getSiteId() {
        return siteId;
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
