package com.innowise.gatewayservice.exception;

import java.io.Serial;

public class TokenExpiredException extends RuntimeException {
    private static final String DEFAULT_MESSAGE="Token was expired";
    @Serial
    private static final long serialVersionUID = 4802114175579360305L;

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
