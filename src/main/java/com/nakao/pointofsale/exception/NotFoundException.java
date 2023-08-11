package com.nakao.pointofsale.exception;

import com.nakao.pointofsale.exception.common.ApiRequestException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiRequestException {

    public NotFoundException(String message) {
        super(message);
        setHttpStatus(HttpStatus.NOT_FOUND);
    }
}
