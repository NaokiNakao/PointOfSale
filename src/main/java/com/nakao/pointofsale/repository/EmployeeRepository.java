package com.nakao.pos.repository;

import com.nakao.pos.model.Employee;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Naoki Nakao on 7/23/2023
 * @project POS
 */

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String >,
        PagingAndSortingRepository<Employee, String> {

    @Query("SELECT e.* " +
            "FROM Employee e " +
            "WHERE e.role = :role")
    List<Employee> getAllByRole(String role);
}
