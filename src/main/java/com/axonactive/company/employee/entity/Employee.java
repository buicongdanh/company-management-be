package com.axonactive.company.employee.entity;

import com.axonactive.company.base.entity.BaseEntity;
import com.axonactive.company.department.entity.Department;
import com.axonactive.company.employee.dto.EmployeeDTO;
import com.axonactive.company.enums.Gender;
import com.axonactive.company.enums.Status;
import jakarta.persistence.Column;
import lombok.*;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Generated
@SqlResultSetMapping(
        name = "employeeDTOWithNumberOfWorkingHoursAndNumberOfWorkingProject",
        classes = {
                @ConstructorResult(targetClass = EmployeeDTO.class, columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "created_at", type = LocalDateTime.class),
                        @ColumnResult(name = "updated_at", type = LocalDateTime.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                        @ColumnResult(name = "middle_name", type = String.class),
                        @ColumnResult(name = "salary", type = Double.class),
                        @ColumnResult(name = "gender", type = Enum.class),
                        @ColumnResult(name = "date_of_birth", type = LocalDate.class),
                        @ColumnResult(name = "department_id", type = Long.class),
                        @ColumnResult(name = "status", type = Enum.class),
                        @ColumnResult(name = "total_working_hours", type = Long.class),
                        @ColumnResult(name = "total_working_projects", type = Long.class),
                })
        }
)
@NamedNativeQuery(
        name = "Employee.findAllEmployeesWithTotalWorkingHourAndProjects",
        query = "SELECT e.*, " +
                "COALESCE(SUM(a.number_of_hour), 0) AS total_working_hours, " +
                "COALESCE(COUNT(a.project_id), 0) AS total_working_projects " +
                "FROM employee e left join assignment a on e.id = a.employee_id " +
                "GROUP BY e.id " +
                "ORDER BY e.id asc ",

        resultSetMapping = "employeeDTOWithNumberOfWorkingHoursAndNumberOfWorkingProject"
)
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    private Status status;
}
