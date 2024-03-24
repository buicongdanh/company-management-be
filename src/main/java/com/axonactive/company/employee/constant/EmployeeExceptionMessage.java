package com.axonactive.company.employee.constant;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeExceptionMessage {
    public static final String EMPLOYEE_NOT_EXISTED = "This employee is not existed";

    public static final String EMPLOYEE_NOT_FOUND = "No employee found";
}
