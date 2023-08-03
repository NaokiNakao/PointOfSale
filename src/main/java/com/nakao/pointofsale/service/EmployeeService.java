package com.nakao.pointofsale.service;

import com.nakao.pointofsale.dao.EmployeeDAO;
import com.nakao.pointofsale.dto.EmployeeDTO;
import com.nakao.pointofsale.mapper.EmployeeDTOMapper;
import com.nakao.pointofsale.exception.DeletionException;
import com.nakao.pointofsale.exception.NotFoundException;
import com.nakao.pointofsale.exception.UniqueIdentifierGenerationException;
import com.nakao.pointofsale.model.Employee;
import com.nakao.pointofsale.repository.EmployeeRepository;
import com.nakao.pointofsale.repository.OrderRepository;
import com.nakao.pointofsale.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDAO employeeDAO;
    private final OrderRepository orderRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<EmployeeDTO> getEmployees(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Employee> page = employeeRepository.findAll(pageable);

        return page.getContent().stream()
                .map(EmployeeDTOMapper::fromEmployee)
                .toList();
    }

    public EmployeeDTO getEmployeeById(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with ID: " + id));

        return EmployeeDTOMapper.fromEmployee(employee);
    }

    public String createEmployee(Employee employee) {
        employee.setId(IdentifierGenerator.generateIdentifier(Employee.ID_PATTERN));
        uniqueIdVerification(employee.getId());

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        employeeDAO.insert(employee);
        return employee.getId();
    }

    public void updateEmployee(String id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with ID: " + id));

        BeanUtils.copyProperties(employeeDTO, employee);

        employeeRepository.save(employee);
    }

    public void deleteEmployee(String id) {
        if (employeeRepository.existsById(id)) {
            if (isValidEmployeeDeletion(id)) {
                employeeRepository.deleteById(id);
            } else {
                throw new DeletionException("Unable to delete Employee with ID: " + id);
            }
        }
        else {
            throw new NotFoundException("Employee not found with ID: " + id);
        }
    }

    public List<Employee> getEmployeesByRole(String role) {
        return employeeRepository.getAllByRole(role);
    }

    private void uniqueIdVerification(String id) {
        if (employeeRepository.existsById(id)) {
            throw new UniqueIdentifierGenerationException("Error generating unique identifier for Employee");
        }
    }

    private Boolean isValidEmployeeDeletion(String id) {
        return orderRepository.countByEmployeeId(id) == 0;
    }

}
