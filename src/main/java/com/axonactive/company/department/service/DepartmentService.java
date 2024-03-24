package com.axonactive.company.department.service;

import com.axonactive.company.base.exception.BadRequestException;
import com.axonactive.company.base.exception.EntityNotFoundException;
import com.axonactive.company.base.message.DeleteSuccessMessage;
import com.axonactive.company.department.message.DepartmentMessage;
import com.axonactive.company.department.dto.*;
import com.axonactive.company.department.entity.Department;
import com.axonactive.company.department.dao.DepartmentDAO;
import com.axonactive.company.department.service.mapper.DepartmentMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class DepartmentService {
    @Inject
    private DepartmentDAO departmentDAO;

    @Inject
    private DepartmentMapper departmentMapper;

    public List<DepartmentDTO> findAll() {
        List<Department> departments = this.departmentDAO.findAll();
        return this.departmentMapper.toDepartmentDTOs(departments);
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = this.departmentDAO.findDepartments();
        return this.departmentMapper.toDepartmentDTOs(departments);
    }

    public DepartmentDTO findById (Long id) throws EntityNotFoundException {
        Optional<Department> department = this.departmentDAO.findById(id);

        if(department.isEmpty()) {
            throw new EntityNotFoundException(DepartmentMessage.DEPARTMENT_NOT_FOUND);
        }

        return this.departmentMapper.toDepartmentDTO(department.get());
    }


    public DepartmentDTO add(CreateDepartmentRequestDTO createDepartmentRequestDTO) throws BadRequestException {
        Optional<Department> optionalDepartment = this.departmentDAO.findByDepartmentName(
                createDepartmentRequestDTO.getDepartmentName().toLowerCase().trim());

        if(optionalDepartment.isPresent()) {
            throw new BadRequestException(DepartmentMessage.DEPARTMENT_EXISTED_NAME);
        }

        Department newDepartment = Department.builder()
                .departmentName(createDepartmentRequestDTO.getDepartmentName().trim())
                .startDate(createDepartmentRequestDTO.getStartDate())
                .build();

        Department department = this.departmentDAO.add(newDepartment);

        return this.departmentMapper.toDepartmentDTO(department);
    }

    public DepartmentDTO update(UpdateDepartmentRequestDTO updateDepartmentRequestDTO) throws EntityNotFoundException, BadRequestException {
        Optional<Department> optionalDepartment = this.departmentDAO.findById(updateDepartmentRequestDTO.getId());

        if(optionalDepartment.isEmpty()) {
            throw new EntityNotFoundException(DepartmentMessage.DEPARTMENT_NOT_FOUND);
        }

        Optional<Department> optionalDepartment1 = this.departmentDAO.findByDepartmentName(updateDepartmentRequestDTO.getDepartmentName().toLowerCase().trim());

        if(optionalDepartment1.isPresent()) {
            throw new BadRequestException(DepartmentMessage.DEPARTMENT_EXISTED_NAME);
        }

        Department department = optionalDepartment.get();

        department.setDepartmentName(updateDepartmentRequestDTO.getDepartmentName());
        department.setStartDate(updateDepartmentRequestDTO.getStartDate());



        Department updatedDepartment = this.departmentDAO.update(department);

        return this.departmentMapper.toDepartmentDTO(updatedDepartment);
    }

    public DeleteSuccessMessage delete(DeleteDepartmentRequestDTO deleteDepartmentRequestDTO) throws EntityNotFoundException {

        Optional<Department> optionalDepartment = this.departmentDAO.findById(deleteDepartmentRequestDTO.getId());

        if(optionalDepartment.isEmpty()) {
            throw new EntityNotFoundException(DepartmentMessage.DEPARTMENT_NOT_FOUND);
        }

        Department department = optionalDepartment.get();

        this.departmentDAO.delete(department);

        return DepartmentMessage.deleteSuccessMessage();
    }
}
