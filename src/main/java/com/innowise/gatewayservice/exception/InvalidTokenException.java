package com.innowise.gatewayservice.exception;



import java.io.Serial;

public class InvalidTokenException extends RuntimeException {

    private static final String DEFAULT_MESSAGE="Invalid token";
    @Serial
    private static final long serialVersionUID = -3879104521199285129L;

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
