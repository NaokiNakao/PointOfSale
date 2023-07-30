package com.nakao.pointofsale.repository;

import com.nakao.pointofsale.model.Employee;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String >,
        PagingAndSortingRepository<Employee, String> {

    @Query("SELECT e.* " +
            "FROM Employee e " +
            "WHERE e.role = :role")
    List<Employee> getAllByRole(String role);
}
