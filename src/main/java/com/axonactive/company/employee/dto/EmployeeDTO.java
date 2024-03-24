package com.axonactive.company.employee.dto;

import com.axonactive.company.department.entity.Department;
import com.axonactive.company.enums.Gender;
import com.axonactive.company.enums.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Double salary;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Department department;
    private Status status;
}
