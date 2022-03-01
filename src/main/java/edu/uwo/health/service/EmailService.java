package edu.uwo.health.service;

public interface EmailService {
    public void sendSimpleMessage(String to, String verifyCode);
}
