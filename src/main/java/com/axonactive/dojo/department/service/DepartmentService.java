package com.axonactive.dojo.department.service;

import com.axonactive.dojo.base.exception.BadRequestException;
import com.axonactive.dojo.base.exception.EntityNotFoundException;
import com.axonactive.dojo.base.message.DeleteSuccessMessage;
import com.axonactive.dojo.department.message.DepartmentMessage;
import com.axonactive.dojo.department.dto.*;
import com.axonactive.dojo.department.entity.Department;
import com.axonactive.dojo.department.dao.DepartmentDAO;
import com.axonactive.dojo.department.mapper.DepartmentMapper;
import com.axonactive.dojo.employee.dto.EmployeeDTO;
import com.axonactive.dojo.enums.Status;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
        return this.departmentMapper.toListDTO(departments);
    }

    public DepartmentListResponseDTO findDepartments() {
        List<Department> departments = this.departmentDAO.findDepartments();
        List<DepartmentDTO> departmentDTOS = this.departmentMapper.toListDTO(departments);

        DepartmentListResponseDTO departmentListResponseDTO = DepartmentListResponseDTO
                                                                        .builder()
                                                                        .departments(departmentDTOS)
                                                                        .build();
        return departmentListResponseDTO;
    }

    public DepartmentDTO findById (Long id) throws EntityNotFoundException {
        Optional<Department> department = this.departmentDAO.findById(id);

        if(department.isEmpty()) {
            throw new EntityNotFoundException(DepartmentMessage.NOT_FOUND_DEPARTMENT);
        }

        return this.departmentMapper.toDTO(department.get());
    }


    public DepartmentDTO add(CreateDepartmentRequestDTO createDepartmentRequestDTO) throws BadRequestException {
        Optional<Department> optionalDepartment = this.departmentDAO.findByDepartmentName(
                createDepartmentRequestDTO.getDepartmentName().toLowerCase().trim());

        if(optionalDepartment.isPresent()) {
            throw new BadRequestException(DepartmentMessage.EXISTED_NAME);
        }

        Department newDepartment = Department.builder()
                .departmentName(createDepartmentRequestDTO.getDepartmentName().trim())
                .startDate(createDepartmentRequestDTO.getStartDate())
                .build();

        Department department = this.departmentDAO.add(newDepartment);

        return this.departmentMapper.toDTO(department);
    }

    public DepartmentDTO update(UpdateDepartmentRequestDTO updateDepartmentRequestDTO) throws EntityNotFoundException, BadRequestException {
        Optional<Department> optionalDepartment = this.departmentDAO.findById(updateDepartmentRequestDTO.getId());

        if(optionalDepartment.isEmpty()) {
            throw new EntityNotFoundException(DepartmentMessage.NOT_FOUND_DEPARTMENT);
        }

        Optional<Department> optionalDepartment1 = this.departmentDAO.findByDepartmentName(updateDepartmentRequestDTO.getDepartmentName().toLowerCase().trim());

        if(optionalDepartment1.isPresent()) {
            throw new BadRequestException(DepartmentMessage.EXISTED_NAME);
        }

        Department department = optionalDepartment.get();

        department.setDepartmentName(updateDepartmentRequestDTO.getDepartmentName());
        department.setStartDate(updateDepartmentRequestDTO.getStartDate());

        Department updatedDepartment = this.departmentDAO.update(department);

        return this.departmentMapper.toDTO(updatedDepartment);
    }

    public DeleteSuccessMessage delete(DeleteDepartmentRequestDTO deleteDepartmentRequestDTO) throws EntityNotFoundException {

        Optional<Department> optionalDepartment = this.departmentDAO.findById(deleteDepartmentRequestDTO.getId());

        if(optionalDepartment.isEmpty()) {
            throw new EntityNotFoundException(DepartmentMessage.NOT_FOUND_DEPARTMENT);
        }

        Department department = optionalDepartment.get();

        this.departmentDAO.delete(department.getId());

        return DepartmentMessage.deleteSuccessMessage();
    }
}
