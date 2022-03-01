package edu.uwo.health.entity;

import java.io.Serializable;

public class ReportHealthParams implements Serializable {
    private String username;
    private int healthstatus;

    public ReportHealthParams() {}

    public ReportHealthParams(String username, int healthstatus) {
        this.username = username;
        this.healthstatus = healthstatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHealthstatus() {
        return healthstatus;
    }

    public void setHealthstatus(int healthstatus) {
        this.healthstatus = healthstatus;
    }
}
