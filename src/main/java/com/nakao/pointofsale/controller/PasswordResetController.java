package com.nakao.pointofsale.controller;

import com.nakao.pointofsale.service.PasswordResetService;
import com.nakao.pointofsale.util.PasswordConfirmation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/password-reset")
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping
    public ResponseEntity<?> requestPasswordReset(@RequestParam String email) {
        passwordResetService.createPasswordReset(email);
        return new ResponseEntity<>("Password Reset requested", HttpStatus.CREATED);
    }

    @PostMapping("/{token}/confirm")
    public ResponseEntity<?> confirmPasswordReset(@PathVariable String token,
                                                  @RequestBody @Valid PasswordConfirmation passwordConfirmation) {
        passwordResetService.confirmPasswordReset(token, passwordConfirmation);
        return new ResponseEntity<>("Password updated", HttpStatus.OK);
    }

}
