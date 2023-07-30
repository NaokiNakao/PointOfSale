package com.nakao.pos.exception;

import com.nakao.pos.exception.common.ApiRequestException;
import org.springframework.http.HttpStatus;

/**
 * @author Naoki Nakao on 7/20/2023
 * @project POS
 */
public class StockReplenishmentProcessingException extends ApiRequestException {

    public StockReplenishmentProcessingException(String message) {
        super(message);
        setHttpStatus(HttpStatus.CONFLICT);
    }

}
