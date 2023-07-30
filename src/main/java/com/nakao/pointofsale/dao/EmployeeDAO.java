package com.nakao.pos.dao;

import com.nakao.pos.dto.EmployeeDTO;
import com.nakao.pos.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Naoki Nakao on 7/23/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class EmployeeDAO implements DAO<Employee> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Employee employee) {
        String sql = "INSERT INTO employee (id, first_name, last_name, email, password, phone, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Object[] params = {employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(),
        employee.getPassword(), employee.getPhone(), employee.getRole()};

        jdbcTemplate.update(sql, params);
    }

}
