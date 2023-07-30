package com.nakao.pos.exception;

import com.nakao.pos.exception.common.ApiRequestException;
import org.springframework.http.HttpStatus;

/**
 * @author Naoki Nakao on 7/23/2023
 * @project POS
 */
public class DeletionException extends ApiRequestException {

    public DeletionException(String message) {
        super(message);
        setHttpStatus(HttpStatus.CONFLICT);
    }

}
