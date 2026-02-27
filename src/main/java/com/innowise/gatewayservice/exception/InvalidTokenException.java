package com.innowise.gatewayservice.exception;

public class InvalidTokenException extends RuntimeException {
    private static final String DEFAULT_MESSAGE="Invalid token";
    public InvalidTokenException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTokenException(Throwable cause) {
        super(cause);
    }
}
