package com.nakao.pointofsale.dao;

import com.nakao.pointofsale.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
