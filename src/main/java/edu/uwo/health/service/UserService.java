package edu.uwo.health.service;

import edu.uwo.health.entity.User;

public interface UserService {
    public User getUserByUsername(String username);

    public org.springframework.security.core.userdetails.User getUserByUsernameAndPassword(String username, String password);

    public User createUser(User user);
}
