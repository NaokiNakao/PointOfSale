package com.nakao.pointofsale.repository;

import com.nakao.pointofsale.model.Employee;
import org.springframework.data.jdbc.repository.query.Modifying;
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

    @Modifying
    @Query("UPDATE Employee e " +
            "SET password = :password " +
            "WHERE e.email = :email")
    void updatePasswordByEmail(String email, String password);

}
