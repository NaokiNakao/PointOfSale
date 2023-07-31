package com.nakao.pointofsale.mapper;

import com.nakao.pointofsale.dto.EmployeeDTO;
import com.nakao.pointofsale.model.Employee;
import org.springframework.beans.BeanUtils;

public class EmployeeDTOMapper {

    public static EmployeeDTO fromEmployee(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

}
