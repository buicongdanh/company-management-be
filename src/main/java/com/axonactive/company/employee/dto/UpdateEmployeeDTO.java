package com.axonactive.company.employee.dto;

import com.axonactive.company.base.validations.ValueOfEnum;
import com.axonactive.company.enums.Gender;
import com.axonactive.company.enums.Status;
import lombok.*;
import org.jetbrains.annotations.Nullable;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class UpdateEmployeeDTO {
    @Nullable
    private String firstName;

    @Nullable
    private String lastName;

    @ValueOfEnum(enumClass = Gender.class)
    private String gender;

    @Nullable
    private String middleName;

    @Nullable
    private Double salary;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Nullable
    private Long departmentId;

    @NotNull
    private Status status;
}
