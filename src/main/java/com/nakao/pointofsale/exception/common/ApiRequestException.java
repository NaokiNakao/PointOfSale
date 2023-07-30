package com.nakao.pointofsale.exception.common;

import org.springframework.http.HttpStatus;

public abstract class ApiRequestException extends RuntimeException {

    private HttpStatus httpStatus;

    public ApiRequestException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
