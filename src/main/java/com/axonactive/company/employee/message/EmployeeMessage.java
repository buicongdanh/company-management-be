package com.axonactive.company.employee.message;

import com.axonactive.company.base.message.BaseMessage;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class EmployeeMessage extends BaseMessage {
    public static final String EMPLOYEE_NOT_EXISTED = "This employee is not existed";

    public static final String EMPLOYEE_NOT_FOUND = "No employee found";

}
