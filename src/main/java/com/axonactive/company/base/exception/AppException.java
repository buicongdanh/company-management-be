package com.axonactive.company.base.exception;

import com.axonactive.company.base.entity.ExceptionContent;
import lombok.Getter;

import javax.ejb.ApplicationException;

@Getter
@ApplicationException
public abstract class AppException extends Exception {
    private ExceptionContent content;

    public AppException(int statusCode, String errorKey, String message) {
        super(message);
        this.content = new ExceptionContent(false, statusCode, errorKey, message, null);
    }
}
