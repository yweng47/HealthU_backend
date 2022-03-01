package edu.uwo.health.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
public class TempUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "username is required")
    @Column(unique = true)
    private String username;
    @NotEmpty(message = "password is required")
    private String password;
    @NotEmpty(message = "name is required")
    private String name;
    @Column(name = "student_id")
    @NotEmpty(message = "student id is required")
    private String studentID;
    @Column(name = "verify_code")
    @NotEmpty(message = "verify code is required")
    private String verifyCode;

    public TempUser() {}

    public TempUser(Integer id, String username, String password, String name, String studentID, String verifyCode) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.verifyCode = verifyCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
