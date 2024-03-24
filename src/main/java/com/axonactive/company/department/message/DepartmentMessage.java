package com.axonactive.company.department.message;

import com.axonactive.company.base.message.BaseMessage;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class DepartmentMessage extends BaseMessage {

    public static final String DEPARTMENT_EXISTED_NAME = "This department name is already existed";

    public static final String DEPARTMENT_NOT_FOUND = "This department is not existed";
    public static final String DEPARTMENT_INVALID = "Invalid department";

}
