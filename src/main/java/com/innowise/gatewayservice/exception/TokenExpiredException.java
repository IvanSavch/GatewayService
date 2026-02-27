package com.innowise.gatewayservice.exception;

public class TokenExpiredException extends RuntimeException {
    private static final String DEFAULT_MESSAGE="Token was expired";
    public TokenExpiredException() {
        super(DEFAULT_MESSAGE);
    }

    public TokenExpiredException(String message) {
        super(message);
    }

    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenExpiredException(Throwable cause) {
        super(cause);
    }
}
