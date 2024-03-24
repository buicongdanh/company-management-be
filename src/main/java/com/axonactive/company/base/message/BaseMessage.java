package com.axonactive.company.base.message;

public class BaseMessage {

    public static DeleteSuccessMessage deleteSuccessMessage() {
        return new DeleteSuccessMessage();
    }
}
