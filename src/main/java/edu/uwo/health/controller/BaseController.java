package edu.uwo.health.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;
}
