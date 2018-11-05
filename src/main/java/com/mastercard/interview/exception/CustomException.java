package com.mastercard.interview.exception;

/**
 * Exception class to handle Custom Exceptions
 */
public class CustomException extends RuntimeException {

    private String customMessage;

    public CustomException(String customMessage) {
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}
