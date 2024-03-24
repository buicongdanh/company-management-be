package com.axonactive.company.department.dao;

import com.axonactive.company.base.dao.BaseDAO;
import com.axonactive.company.department.entity.Department;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Stateless
public class DepartmentDAO extends BaseDAO<Department> {
    public DepartmentDAO() {
        super(Department.class);
    }

    public Optional<Department> findByDepartmentName(String departmentName) {
        Query query = entityManager.createQuery("select d from Department d where lower(d.departmentName) = :departmentName", Department.class);
        query.setParameter("departmentName", departmentName);

        List<Department> l = query.getResultList();

        if(l.isEmpty()) {
            return Optional.empty();
        }

        return  Optional.of(l.get(0));
    }

    public List<Department> findDepartments() {
        Query query = entityManager.createQuery("from Department d order by lower(d.departmentName)");

        return query.getResultList();
    }
}
