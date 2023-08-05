package com.nakao.pointofsale.event.passwordreset;

import com.nakao.pointofsale.model.PasswordReset;

public class PasswordResetEvent {

    private final PasswordReset passwordReset;

    public PasswordResetEvent(PasswordReset passwordReset) {
        this.passwordReset = passwordReset;
    }

    public PasswordReset getPasswordReset() {
        return passwordReset;
    }

}
