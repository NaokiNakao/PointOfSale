package com.nakao.pos.controller;

import com.nakao.pos.dto.EmployeeDTO;
import com.nakao.pos.model.Employee;
import com.nakao.pos.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Naoki Nakao on 7/23/2023
 * @project POS
 */

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        List<EmployeeDTO> employees = employeeService.getEmployees(pageNumber, pageSize);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody @Valid Employee employee) {
        employeeService.createEmployee(employee);
        return new ResponseEntity<>("Employee created", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable String id, @RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(id, employeeDTO);
        return new ResponseEntity<>("Employee updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
