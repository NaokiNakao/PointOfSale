package com.nakao.pointofsale.exception;

import com.nakao.pointofsale.exception.common.ApiRequestException;
import org.springframework.http.HttpStatus;

public class OrderAlreadyProcessedException extends ApiRequestException {

    public OrderAlreadyProcessedException(String message) {
        super(message);
        setHttpStatus(HttpStatus.CONFLICT);
    }

}
