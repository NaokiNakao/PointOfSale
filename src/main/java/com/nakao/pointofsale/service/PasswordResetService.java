package com.nakao.pointofsale.service;

import com.nakao.pointofsale.dao.PasswordResetDAO;
import com.nakao.pointofsale.event.passwordreset.PasswordResetEvent;
import com.nakao.pointofsale.exception.InvalidTokenException;
import com.nakao.pointofsale.exception.NotFoundException;
import com.nakao.pointofsale.exception.NotMatchingPasswordsException;
import com.nakao.pointofsale.model.PasswordReset;
import com.nakao.pointofsale.repository.PasswordResetRepository;
import com.nakao.pointofsale.util.PasswordConfirmation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetRepository passwordResetRepository;
    private final PasswordResetDAO passwordResetDAO;
    private final EmployeeService employeeService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void createPasswordReset(String email) {
        PasswordReset passwordReset = PasswordReset.builder()
                .token(UUID.randomUUID().toString())
                .email(email)
                .expirationDate(LocalDateTime.now().plusHours(24))
                .build();

        passwordResetDAO.insert(passwordReset);
        PasswordResetEvent passwordResetEvent = new PasswordResetEvent(passwordReset);
        applicationEventPublisher.publishEvent(passwordResetEvent);
    }

    public void confirmPasswordReset(String token, PasswordConfirmation passwordConfirmation) {
        PasswordReset passwordReset = getPasswordResetByToken(token);
        validateExpirationDate(passwordReset.getExpirationDate());
        validateNewPassword(passwordConfirmation);
        employeeService.updateEmployeePassword(passwordReset.getEmail(), passwordConfirmation.getPassword());
        deletePasswordReset(token);
    }

    private PasswordReset getPasswordResetByToken(String token) {
        return passwordResetRepository.findById(token)
                .orElseThrow(() -> new NotFoundException("Password Reset not found with token: " + token));
    }

    private void validateExpirationDate(LocalDateTime expirationDate) {
        if (expirationDate.isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Token has expired");
        }
    }

    private void validateNewPassword(PasswordConfirmation passwordConfirmation) {
        if (!passwordConfirmation.getPassword().equals(passwordConfirmation.getConfirmPassword())) {
            throw new NotMatchingPasswordsException("Passwords do not match");
        }
    }

    private void deletePasswordReset(String token) {
        passwordResetRepository.deleteById(token);
    }

}
