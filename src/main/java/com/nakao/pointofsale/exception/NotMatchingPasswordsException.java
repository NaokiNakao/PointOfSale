package com.nakao.pointofsale.exception;

import com.nakao.pointofsale.exception.common.ApiRequestException;
import org.springframework.http.HttpStatus;

public class NotMatchingPasswordsException extends ApiRequestException {

    public NotMatchingPasswordsException(String message) {
        super(message);
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }

}
