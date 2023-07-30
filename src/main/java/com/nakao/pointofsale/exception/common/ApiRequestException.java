package com.nakao.pos.exception.common;

import org.springframework.http.HttpStatus;

/**
 * @author Naoki Nakao on 7/18/2023
 * @project POS
 */
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
