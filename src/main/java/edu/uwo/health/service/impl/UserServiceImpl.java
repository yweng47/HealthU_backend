package edu.uwo.health.service.impl;

import edu.uwo.health.dao.UserRepository;
import edu.uwo.health.entity.User;
import edu.uwo.health.service.UserService;
import edu.uwo.health.utils.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public org.springframework.security.core.userdetails.User getUserByUsernameAndPassword(String username, String password) {
        org.springframework.security.core.userdetails.User loginUser = null;
        try {
            String decryptPassword = RSAUtils.decrypt(password, RSAUtils.getPrivateKey());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, decryptPassword));
            loginUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        } catch (Exception e) {}
        return loginUser;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
