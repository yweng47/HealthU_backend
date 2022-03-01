package edu.uwo.health.entity;

import java.io.Serializable;

public class RegisterParams implements Serializable {
    private String username;
    private String verifyCode;

    public RegisterParams() {}

    public RegisterParams(String username, String verifyCode) {
        this.username = username;
        this.verifyCode = verifyCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
