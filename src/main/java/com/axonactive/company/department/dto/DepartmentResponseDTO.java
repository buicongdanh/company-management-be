package com.axonactive.company.department.dto;

import com.axonactive.company.enums.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DepartmentResponseDTO {
    private Long id;
    private String departmentName;
    private LocalDate startDate;
    private Status status;
}
