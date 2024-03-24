package com.axonactive.company.department.entity;

import com.axonactive.company.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Department extends BaseEntity {

    private String departmentName;

    private LocalDate startDate;
}
