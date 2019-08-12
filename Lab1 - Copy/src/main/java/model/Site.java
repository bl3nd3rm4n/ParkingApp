package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Site {
    private int id;
    private String address;

    public Site() {
    }

    public Site(int id, String address) {
        this.id = id;
        this.address = address;
    }

    @JsonGetter
    public int getId() {
        return id;
    }

    @JsonGetter
    public String getAddress() {
        return address;
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
