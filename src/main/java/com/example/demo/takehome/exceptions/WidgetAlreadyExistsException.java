package com.example.demo.takehome.exceptions;

public class WidgetAlreadyExistsException extends RuntimeException {
    private String errorCode;

    public WidgetAlreadyExistsException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

