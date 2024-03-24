package com.axonactive.company.employee.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeListResponseDTO {
    private List<EmployeeDTO> employees;
}
