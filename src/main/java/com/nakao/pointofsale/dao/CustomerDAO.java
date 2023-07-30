package com.nakao.pos.dao;

import com.nakao.pos.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Naoki Nakao on 7/22/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class CustomerDAO implements DAO<Customer> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Customer customer) {
        String sql = "INSERT INTO customer (id, first_name, last_name, phone, address, birthday) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Object[] params = {customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getPhone(),
        customer.getAddress(), customer.getBirthday()};

        jdbcTemplate.update(sql, params);
    }

}
