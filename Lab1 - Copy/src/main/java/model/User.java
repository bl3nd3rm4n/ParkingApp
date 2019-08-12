package model;

import com.fasterxml.jackson.annotation.JsonGetter;

public class User {
    private int id;
    private String username;
    private String password;

    public User() {
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.id = 0;
        this.username = username;
        this.password = password;
    }

    @JsonGetter
    public int getId() {
        return id;
    }

    @JsonGetter
    public String getUsername() {
        return username;
    }

    @JsonGetter
    public String getPassword() {
        return password;
    }
}
