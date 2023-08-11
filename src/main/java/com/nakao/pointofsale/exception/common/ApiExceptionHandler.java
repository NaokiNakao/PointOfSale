package com.nakao.pointofsale.exception.common;

import com.nakao.pointofsale.util.builder.ExceptionResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<ExceptionResponse> handleApiRequestException(ApiRequestException e) {
        ExceptionResponse response = new ExceptionResponseBuilder()
                .message(e.getMessage())
                .httpStatus(e.getHttpStatus())
                .timestamp(ZonedDateTime.now())
                .build();

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ExceptionResponse response = new ExceptionResponseBuilder()
                .message("Not valid arguments")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timestamp(ZonedDateTime.now())
                .data(errors)
                .build();

        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
