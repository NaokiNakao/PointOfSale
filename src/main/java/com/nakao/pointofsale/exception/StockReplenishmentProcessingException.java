package com.nakao.pointofsale.exception;
import com.nakao.pointofsale.exception.common.ApiRequestException;
import org.springframework.http.HttpStatus;

public class StockReplenishmentProcessingException extends ApiRequestException {

    public StockReplenishmentProcessingException(String message) {
        super(message);
        setHttpStatus(HttpStatus.CONFLICT);
    }

}
