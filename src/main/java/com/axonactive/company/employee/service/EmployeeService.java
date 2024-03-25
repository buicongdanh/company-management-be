package com.axonactive.company.employee.service;

import com.axonactive.company.base.exception.EntityNotFoundException;
import com.axonactive.company.base.message.DeleteSuccessMessage;
import com.axonactive.company.department.entity.Department;
import com.axonactive.company.department.message.DepartmentMessage;
import com.axonactive.company.employee.dto.*;
import com.axonactive.company.employee.dao.EmployeeDAO;
import com.axonactive.company.department.dao.DepartmentDAO;
import com.axonactive.company.employee.entity.Employee;
import com.axonactive.company.employee.message.EmployeeMessage;
import com.axonactive.company.employee.service.mapper.EmployeeMapper;
import com.axonactive.company.enums.Status;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmployeeService {

    @Inject
    private EmployeeDAO employeeDAO;

    @Inject
    private DepartmentDAO departmentDAO;

    @Inject
    private EmployeeMapper employeeMapper;

    public EmployeeListResponseDTO getAllEmployeesByDepartment(Long departmentId) throws EntityNotFoundException {
        if(departmentId <= 0) {
            throw new EntityNotFoundException(DepartmentMessage.DEPARTMENT_INVALID);
        }

        this.departmentDAO.findById(departmentId).orElseThrow(
                () -> new EntityNotFoundException(DepartmentMessage.DEPARTMENT_NOT_FOUND));

        List<Employee> employees = this.employeeDAO.findEmployeesByDepartmentId(departmentId);

        return EmployeeListResponseDTO
                .builder()
                .employees(employeeMapper.toEmployeeDTOs(employees))
                .build();
    }

    public EmployeeListResponseDTO getAllEmployee() {
        List<Employee> employees = this.employeeDAO.findAll();

        return EmployeeListResponseDTO
                .builder()
                .employees(employeeMapper.toEmployeeDTOs(employees))
                .build();
    }

    public EmployeeDTO findEmployeeById(Long id) throws EntityNotFoundException {
        Employee employee = this.employeeDAO.findById(id).orElseThrow(
                () -> new EntityNotFoundException(DepartmentMessage.DEPARTMENT_NOT_FOUND));

        return this.employeeMapper.toEmployeeDTO(employee);
    }

    @Transactional(rollbackOn = {
            RuntimeException.class,
            EntityNotFoundException.class
    })
    public EmployeeDTO add(CreateEmployeeRequestDTO createEmployeeRequestDTO) throws EntityNotFoundException {
        Department department = this.departmentDAO.findById(createEmployeeRequestDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException(DepartmentMessage.DEPARTMENT_NOT_FOUND));

        Employee newEmployee = this.employeeMapper.toEmployee(createEmployeeRequestDTO);
        newEmployee.setStatus(Status.ACTIVE);

        return this.employeeMapper.toEmployeeDTO(employeeDAO.add(newEmployee));
    }

    public EmployeeDTO update(UpdateEmployeeDTO updateEmployeeDTO, long employeeID) throws EntityNotFoundException {
        Employee employee = this.employeeDAO.findById(employeeID)
                .orElseThrow(() -> new EntityNotFoundException(EmployeeMessage.EMPLOYEE_NOT_FOUND));

        if(updateEmployeeDTO.getDepartmentId() != null) {
            this.departmentDAO.findById(updateEmployeeDTO.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException(DepartmentMessage.DEPARTMENT_NOT_FOUND));
        }

        this.employeeMapper.updateEmployee(employee, updateEmployeeDTO);

        return this.employeeMapper.toEmployeeDTO(employee);
    }

    public DeleteSuccessMessage delete(long employeeID) throws EntityNotFoundException {
//        Optional<Employee> optionalEmployee = this.employeeDAO.findById(employeeID);
//
//        if(optionalEmployee.isEmpty()) {
//            throw new EntityNotFoundException(EmployeeMessage.EMPLOYEE_NOT_FOUND);
//        }
//
//        Employee employee = optionalEmployee.get();

        Employee deleteEmployee = this.employeeDAO.findById(employeeID)
                .orElseThrow(() -> new EntityNotFoundException(EmployeeMessage.EMPLOYEE_NOT_FOUND));

        deleteEmployee.setStatus(Status.DELETED);

        this.employeeDAO.update(deleteEmployee);

        return EmployeeMessage.deleteSuccessMessage();
    }

    public List<EmployeeDTO> getActiveEmployees() {
        return employeeMapper.toEmployeeDTOs(this.employeeDAO.findAll().stream()
                .filter(employee -> employee.getStatus().equals(Status.ACTIVE))
                .toList());
    }
}
