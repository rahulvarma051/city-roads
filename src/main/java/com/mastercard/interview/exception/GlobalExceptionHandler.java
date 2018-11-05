package com.mastercard.interview.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * GlobalExceptionHandler is used to handle exceptions that are thrown in the controller.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCustomException(CustomException throwable) {
        log.error("Invalid Request error: " + throwable.getMessage(), throwable);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), throwable.getCustomMessage());
    }

    class ErrorResponse {

        private Integer code;
        private String message;

        ErrorResponse(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
