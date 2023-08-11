package com.nakao.pointofsale.dao;

import com.nakao.pointofsale.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class CustomerDAO implements DAO<Customer> {
    private final JdbcTemplate jdbcTemplate;

    public CustomerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Customer customer) {
        String sql = "INSERT INTO customer (id, first_name, last_name, phone, address, birthday) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Object[] params = {customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getPhone(),
        customer.getAddress(), customer.getBirthday()};

        jdbcTemplate.update(sql, params);
    }
}
