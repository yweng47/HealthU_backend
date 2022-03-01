package edu.uwo.health.service.impl;

import edu.uwo.health.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final String subject = "Verify Register";
    private final String text = "Hello! Welcome to register, your verification code is: ";

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String verifyCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("legendaryse4450@outlook.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text + verifyCode);
        emailSender.send(message);
    }

}
