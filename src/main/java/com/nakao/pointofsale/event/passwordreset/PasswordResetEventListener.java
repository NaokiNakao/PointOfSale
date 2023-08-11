package com.nakao.pointofsale.event.passwordreset;

import com.nakao.pointofsale.model.PasswordReset;
import com.nakao.pointofsale.service.EmailSenderService;
import com.nakao.pointofsale.util.EmailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetEventListener {
    private final EmailSenderService emailSenderService;

    @Value("${spring.mail.username}")
    private String from;

    public PasswordResetEventListener(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @EventListener
    public void onPasswordResetEvent(PasswordResetEvent passwordResetEvent) {
        EmailMessage emailMessage = buildEmailMessage(passwordResetEvent.getPasswordReset());
        emailSenderService.sendMail(emailMessage);
    }

    private EmailMessage buildEmailMessage(PasswordReset passwordReset) {
        String subject = "Password Reset";
        String text = "Password Reset token: " + passwordReset.getToken();

        return new EmailMessage(passwordReset.getEmail(), from, subject, text);
    }

}
