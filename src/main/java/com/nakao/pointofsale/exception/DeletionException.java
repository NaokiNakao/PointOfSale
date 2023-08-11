package com.nakao.pointofsale.exception;

import com.nakao.pointofsale.exception.common.ApiRequestException;
import org.springframework.http.HttpStatus;

public class DeletionException extends ApiRequestException {

    public DeletionException(String message) {
        super(message);
        setHttpStatus(HttpStatus.CONFLICT);
    }
}
