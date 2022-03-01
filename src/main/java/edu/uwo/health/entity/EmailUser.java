package edu.uwo.health.entity;

import java.io.Serializable;

public class EmailUser implements Serializable {
    private String username;
    private String password;
    private String name;
    private String studentID;

    public EmailUser() {}

    public EmailUser(String username, String password, String name, String studentID) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.studentID = studentID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
