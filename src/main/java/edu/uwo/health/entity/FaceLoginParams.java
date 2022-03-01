package edu.uwo.health.entity;

import java.io.Serializable;

public class FaceLoginParams implements Serializable {
    private String username;

    public FaceLoginParams() {}

    public FaceLoginParams(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
