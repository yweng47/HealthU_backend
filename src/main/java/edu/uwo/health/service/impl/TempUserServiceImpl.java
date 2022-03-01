package edu.uwo.health.service.impl;

import edu.uwo.health.dao.TempUserRepository;
import edu.uwo.health.entity.TempUser;
import edu.uwo.health.service.TempUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempUserServiceImpl implements TempUserService {
    @Autowired
    private TempUserRepository tempUserRepository;


    @Override
    public TempUser getTempUserByUsername(String username) {
        return tempUserRepository.findByUsername(username);
    }

    @Override
    public TempUser createTempUser(TempUser tempUser) {
        return tempUserRepository.save(tempUser);
    }

    @Override
    public int deleteTempUser(String username) {
        return tempUserRepository.deleteTempUserByUsername(username);
    }

    @Override
    public int updateTempUser(String username, String verifyCode) {
        return tempUserRepository.updateTempUserByUsername(username, verifyCode);
    }
}
