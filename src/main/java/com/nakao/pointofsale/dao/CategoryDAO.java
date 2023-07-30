package com.nakao.pos.dao;

import com.nakao.pos.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author Naoki Nakao on 7/18/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class CategoryDAO implements DAO<Category> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Category category) {
        String sql = "INSERT INTO category (id, name) " +
                "VALUES (?, ?)";

        Object[] params = {category.getId(), category.getName()};

        jdbcTemplate.update(sql, params);
    }

}
