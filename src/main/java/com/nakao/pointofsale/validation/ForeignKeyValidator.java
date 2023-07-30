package com.nakao.pos.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Naoki Nakao on 7/20/2023
 * @project POS
 */
public class ForeignKeyValidator implements ConstraintValidator<ForeignKeyValidation, Object> {

    private String tableName;
    private String fieldName;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ForeignKeyValidator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void initialize(ForeignKeyValidation constraintAnnotation) {
        tableName = constraintAnnotation.tableName();
        fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + fieldName + " = ?";
        int count = 0;

        try {
            count = jdbcTemplate.queryForObject(query, Integer.class, value);
        }
        catch (NullPointerException ignored) {}

        return count > 0;
    }

}
