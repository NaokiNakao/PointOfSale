package com.nakao.pointofsale.service;

import com.nakao.pointofsale.util.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public void sendMail(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailMessage.getTo());
        message.setFrom(emailMessage.getFrom());
        message.setSubject(emailMessage.getSubject());
        message.setText(emailMessage.getText());

        mailSender.send(message);
    }

}
