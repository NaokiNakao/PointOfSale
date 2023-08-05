package com.nakao.pointofsale.exception;

import com.nakao.pointofsale.exception.common.ApiRequestException;
import org.springframework.http.HttpStatus;

public class BusinessLogicException extends ApiRequestException {

    public BusinessLogicException(String message) {
        super(message);
        setHttpStatus(HttpStatus.CONFLICT);
    }

}
