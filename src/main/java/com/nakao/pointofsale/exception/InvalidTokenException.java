package com.nakao.pointofsale.exception;

import com.nakao.pointofsale.exception.common.ApiRequestException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ApiRequestException {

    public InvalidTokenException(String message) {
        super(message);
        setHttpStatus(HttpStatus.UNAUTHORIZED);
    }
}
