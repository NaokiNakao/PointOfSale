package com.nakao.pointofsale.util.builder;

import com.nakao.pointofsale.exception.common.ExceptionResponse;
import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;
import java.util.Map;

public class ExceptionResponseBuilder {
    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;
    private Map<?, ?> data;

    public ExceptionResponseBuilder() {
    }

    public ExceptionResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ExceptionResponseBuilder httpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public ExceptionResponseBuilder timestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ExceptionResponseBuilder data(Map<?, ?> data) {
        this.data = data;
        return this;
    }

    public ExceptionResponse build() {
        return new ExceptionResponse(message, httpStatus, timestamp, data);
    }
}

