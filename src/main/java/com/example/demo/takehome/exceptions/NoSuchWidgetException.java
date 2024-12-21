package com.example.demo.takehome.exceptions;

public class NoSuchWidgetException extends RuntimeException {
    private String errorCode;

    public NoSuchWidgetException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

