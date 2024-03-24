package com.axonactive.company.department.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DepartmentDTO {

    private Long id;
    private String departmentName;
    private LocalDate startDate;
}
