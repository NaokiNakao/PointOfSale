package com.nakao.pointofsale.dao;

import com.nakao.pointofsale.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
