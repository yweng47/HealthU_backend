package edu.uwo.health.service;

import edu.uwo.health.entity.TempUser;

public interface TempUserService {
    public TempUser getTempUserByUsername(String username);

    public TempUser createTempUser(TempUser user);

    public int deleteTempUser(String username);

    public int updateTempUser(String username, String verifyCode);
}
