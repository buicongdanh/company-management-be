package com.axonactive.company.employee.dao;

import com.axonactive.company.base.dao.BaseDAO;
import com.axonactive.company.employee.dto.EmployeeDTO;
import com.axonactive.company.employee.entity.Employee;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class EmployeeDAO extends BaseDAO<Employee> {
    public EmployeeDAO() {
        super(Employee.class);
    }

    public List<Employee> findEmployeesByDepartmentId (Long departmentId) {
        return entityManager.createQuery("select e " +
                        "from Employee e " +
                        "where e.department.id = :departmentId", Employee.class)
                .setParameter("departmentId", departmentId)
                .getResultList();
    }

    public List<Employee> findAllEmployees () {
        return entityManager.createQuery("select e from Employee e", Employee.class)
                .getResultList();
    }

    public List<EmployeeDTO> findAllEmployeesWithTotalWorkingHourAndProjects () {
        return entityManager.createNamedQuery("Employee.findAllEmployeesWithTotalWorkingHourAndProjects", EmployeeDTO.class).getResultList();
    }
}
