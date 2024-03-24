package com.axonactive.company.department.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DepartmentListResponseDTO {
    List<DepartmentDTO> departments;
    Long totalCount;
    Integer lastPage;
}
