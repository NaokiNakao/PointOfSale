package com.nakao.pointofsale.dao;

import com.nakao.pointofsale.model.PasswordReset;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetDAO implements DAO<PasswordReset> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(PasswordReset passwordReset) {
        String sql = "INSERT INTO password_reset_request (token, email, expiration_date) " +
                "VALUES (?, ?, ?)";

        Object[] params = {passwordReset.getToken(), passwordReset.getEmail(),
                passwordReset.getExpirationDate()};

        jdbcTemplate.update(sql, params);
    }

}
