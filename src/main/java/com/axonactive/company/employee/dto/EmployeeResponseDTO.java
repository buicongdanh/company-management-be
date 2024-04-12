package com.axonactive.company.employee.dto;

import com.axonactive.company.department.dto.DepartmentResponseDTO;
import com.axonactive.company.enums.Gender;
import com.axonactive.company.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmployeeResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private double salary;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Status status;
    private DepartmentResponseDTO department;
}
