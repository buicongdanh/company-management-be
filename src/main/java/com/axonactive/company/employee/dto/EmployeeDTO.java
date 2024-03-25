package com.axonactive.company.employee.dto;

import com.axonactive.company.department.entity.Department;
import com.axonactive.company.enums.Gender;
import com.axonactive.company.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Double salary;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private Gender gender;
    private Department department;
    private Status status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalWorkingHours;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalWorkingProjects;

    public EmployeeDTO(Long id, String firstName, String lastName, Double salary, LocalDate dateOfBirth, Gender gender, Department department, Status status, Long totalWorkingHours, Long totalWorkingProjects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.department = department;
        this.status = status;
        this.totalWorkingHours = totalWorkingHours;
        this.totalWorkingProjects = totalWorkingProjects;
    }
}
