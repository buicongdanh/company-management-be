package com.axonactive.dojo.employee.service;

import com.axonactive.dojo.base.exception.EntityNotFoundException;
import com.axonactive.dojo.base.message.DeleteSuccessMessage;
import com.axonactive.dojo.department.entity.Department;
import com.axonactive.dojo.department.message.DepartmentMessage;
import com.axonactive.dojo.employee.dto.*;
import com.axonactive.dojo.employee.entity.Employee;
import com.axonactive.dojo.employee.dao.EmployeeDAO;
import com.axonactive.dojo.department.dao.DepartmentDAO;
import com.axonactive.dojo.employee.mapper.EmployeeMapper;
import com.axonactive.dojo.employee.message.EmployeeMessage;
import com.axonactive.dojo.enums.Gender;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class EmployeeService {

    @Inject
    private EmployeeDAO employeeDAO;

    @Inject
    private DepartmentDAO departmentDAO;

    @Inject
    private EmployeeMapper employeeMapper;

    public EmployeeListResponseDTO findEmployeesByDepartment(Long departmentId) throws EntityNotFoundException {
        List<Employee> employees;
        List<EmployeeDTO> employeeDTOS;

        if(departmentId > 0) {
            Optional<Department> department = this.departmentDAO.findById(departmentId);

            if(department.isEmpty()) {
                throw new EntityNotFoundException(DepartmentMessage.NOT_FOUND_DEPARTMENT);
            }

            employees = this.employeeDAO.findEmployeesByDepartmentId(departmentId);
            employeeDTOS = this.employeeMapper.toListDTO(employees);
            return EmployeeListResponseDTO
                    .builder()
                    .employees(employeeDTOS)
                    .build();
        }
        else {
            throw new EntityNotFoundException(DepartmentMessage.NOT_FOUND_DEPARTMENT);
        }
    }

    public EmployeeListResponseDTO findAllEmployee() throws EntityNotFoundException {
        List<Employee> employees;
        List<EmployeeDTO> employeeDTOS;

        employees = this.employeeDAO.findAllEmployees();
        employeeDTOS = this.employeeMapper.toListDTO(employees);
        return EmployeeListResponseDTO
                .builder()
                .employees(employeeDTOS)
                .build();
    }

    public EmployeeDTO findEmployeeById(Long id) throws EntityNotFoundException {
        Optional<Employee> optionalEmployee = this.employeeDAO.findById(id);

        if(optionalEmployee.isEmpty()) {
            throw new EntityNotFoundException(EmployeeMessage.NOT_FOUND_EMPLOYEE);
        }

        return this.employeeMapper.toDTO(optionalEmployee.get());
    }

    public EmployeeDTO add(CreateEmployeeRequestDTO reqDTO) throws EntityNotFoundException {
        Optional<Department> department = this.departmentDAO.findById(reqDTO.getDepartmentId());

        if(department.isEmpty()) {
            throw new EntityNotFoundException(DepartmentMessage.NOT_FOUND_DEPARTMENT);
        }

        Employee newEmployee = Employee
                .builder()
                .firstName(reqDTO.getFirstName())
                .middleName(reqDTO.getMiddleName())
                .lastName(reqDTO.getLastName())
                .dateOfBirth(reqDTO.getDateOfBirth())
                .gender(Gender.valueOf(reqDTO.getGender()))
                .salary(reqDTO.getSalary())
                .department(department.get())
                .build();

        Employee employee = employeeDAO.add(newEmployee);

        return this.employeeMapper.toDTO(employee);
    }

    public EmployeeDTO update(UpdateEmployeeRequestDTO requestDTO) throws EntityNotFoundException {
        Optional<Employee> optionalEmployee = this.employeeDAO.findById(requestDTO.getId());

        if(optionalEmployee.isEmpty()) {
            throw new EntityNotFoundException(EmployeeMessage.NOT_FOUND_EMPLOYEE);
        }

        Optional<Department> department = this.departmentDAO.findById(requestDTO.getDepartmentId());

        if(department.isEmpty()) {
            throw new EntityNotFoundException(DepartmentMessage.NOT_FOUND_DEPARTMENT);
        }

        Employee employee = optionalEmployee.get();

        employee.setFirstName(requestDTO.getFirstName());
        employee.setLastName(requestDTO.getLastName());
        employee.setMiddleName(requestDTO.getMiddleName());
        employee.setGender(Gender.valueOf(requestDTO.getGender()));
        employee.setSalary(requestDTO.getSalary());
        employee.setDateOfBirth(requestDTO.getDateOfBirth());
        employee.setDepartment(department.get());

        Employee updatedEmployee = this.employeeDAO.update(employee);

        return this.employeeMapper.toDTO(updatedEmployee);
    }

    public DeleteSuccessMessage delete(DeleteEmployeeRequestDTO requestDTO) throws EntityNotFoundException {
        Optional<Employee> optionalEmployee = this.employeeDAO.findById(requestDTO.getId());

        if(optionalEmployee.isEmpty()) {
            throw new EntityNotFoundException(EmployeeMessage.NOT_FOUND_EMPLOYEE);
        }

        Employee employee = optionalEmployee.get();

        this.employeeDAO.delete(employee.getId());

        return EmployeeMessage.deleteSuccessMessage();
    }
}
