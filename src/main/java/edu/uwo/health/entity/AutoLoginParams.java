package edu.uwo.health.entity;

import java.io.Serializable;

public class AutoLoginParams implements Serializable {
    private String username;
    private String autoLoginTicket;

    public AutoLoginParams() {}

    public AutoLoginParams(String username, String autoLoginTicket) {
        this.username = username;
        this.autoLoginTicket = autoLoginTicket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAutoLoginTicket() {
        return autoLoginTicket;
    }

    public void setAutoLoginTicket(String autoLoginTicket) {
        this.autoLoginTicket = autoLoginTicket;
    }
}
