package com.nakao.pos.dtomapper;

import com.nakao.pos.dto.EmployeeDTO;
import com.nakao.pos.model.Employee;
import org.springframework.beans.BeanUtils;

/**
 * @author Naoki Nakao on 7/24/2023
 * @project POS
 */
public class EmployeeDTOMapper {

    public static EmployeeDTO fromEmployee(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

}
