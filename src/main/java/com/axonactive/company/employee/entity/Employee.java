package com.axonactive.company.employee.entity;

import com.axonactive.company.base.entity.BaseEntity;
import com.axonactive.company.department.entity.Department;
import com.axonactive.company.enums.Gender;
import com.axonactive.company.enums.Status;
import jakarta.persistence.Column;
import lombok.*;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Employee extends BaseEntity {


    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String middleName;
    private Double salary;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    private Status status;
}
