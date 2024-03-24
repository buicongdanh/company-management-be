package com.axonactive.company.base.exception;

import javax.ws.rs.core.Response;

public class UnauthorizedException extends AppException {

    public UnauthorizedException(String message) {
            super(Response.Status.UNAUTHORIZED.getStatusCode(),
                    Response.Status.UNAUTHORIZED.getReasonPhrase(), message);
    }
}
