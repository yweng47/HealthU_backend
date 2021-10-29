package edu.uwo.health.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "username is required")
    @Column(unique = true)
    private String username;
    @NotEmpty(message = "password is required")
    private String password;
    private String name;
    @Column(name = "health_status", columnDefinition = "integer default 25")
    private int healthStatus;

    public User() {}

    public User(Integer id, String username, String password, String name, int healthStatus) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.healthStatus = healthStatus;
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

    public int getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(int healthStatus) {
        this.healthStatus = healthStatus;
    }
}
