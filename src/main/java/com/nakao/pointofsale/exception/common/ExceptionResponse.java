package com.nakao.pos.exception.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author Naoki Nakao on 7/18/2023
 * @project POS
 */

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;
    Map<?, ?> data;

}
