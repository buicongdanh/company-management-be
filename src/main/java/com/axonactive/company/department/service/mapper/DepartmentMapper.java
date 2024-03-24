package com.axonactive.company.department.service.mapper;
import com.axonactive.company.department.dto.DepartmentDTO;
import com.axonactive.company.department.entity.Department;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface DepartmentMapper {
    Department toDepartment(DepartmentDTO departmentDTO);
    List<Department> toDepartments(List<DepartmentDTO> departmentDTOs);
    DepartmentDTO toDepartmentDTO(Department department);
    List<DepartmentDTO> toDepartmentDTOs(List<Department> departmentDTOs);
//    Department convertCreateRequestToEntity(CreateDepartmentRequestDTO createDepartmentRequestDTO);
}
