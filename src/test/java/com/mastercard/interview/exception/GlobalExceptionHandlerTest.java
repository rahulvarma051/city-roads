package com.mastercard.interview.exception;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @Before
    public void setUp() throws Exception {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void handleCustomException() {
        //arrange
        String message = "Custom exception occurred!!!";
        CustomException exception = new CustomException(message);
        //act
        GlobalExceptionHandler.ErrorResponse errorResponse= globalExceptionHandler.handleCustomException(exception);
        //assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), (int)errorResponse.getCode());
        assertEquals(message, errorResponse.getMessage());
    }
}