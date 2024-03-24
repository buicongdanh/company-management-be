package com.axonactive.company.employee.service.mapper;

import com.axonactive.company.employee.dto.CreateEmployeeRequestDTO;
import com.axonactive.company.employee.dto.EmployeeDTO;
import com.axonactive.company.employee.dto.UpdateEmployeeDTO;
import com.axonactive.company.employee.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {
    Employee toEmployee(EmployeeDTO employeeDTO);
    Employee toEmployee(CreateEmployeeRequestDTO createEmployeeRequestDTO);
    List<Employee> toEmployees(List<EmployeeDTO> employeeDTOS);

    EmployeeDTO toEmployeeDTO(Employee employee);

    List<EmployeeDTO> toEmployeeDTOs(List<Employee> employees);

    void updateEmployee(@MappingTarget Employee employee, UpdateEmployeeDTO updateEmployeeDTO);
}
