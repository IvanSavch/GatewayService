package com.innowise.gatewayservice.exception;

import com.innowise.gatewayservice.model.ErrorResponse;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component

public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {
    public GlobalExceptionHandler(ErrorAttributes errorAttributes,
                                  WebProperties webProperties,
                                  ApplicationContext applicationContext,
                                  ServerCodecConfigurer codecConfigurer) {
        super(errorAttributes, webProperties.getResources(), applicationContext);

        this.setMessageReaders(codecConfigurer.getReaders());
        this.setMessageWriters(codecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }
    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable error = getError(request);
        ErrorResponse errorResponse = createErrorResponse(request, error);

        return ServerResponse
                .status(errorResponse.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }

    private ErrorResponse createErrorResponse(ServerRequest request, Throwable ex) {
        ErrorResponse.ErrorResponseBuilder errorResponse = ErrorResponse.builder().path(request.path())
                .timestamp(LocalDateTime.now());

        if (ex instanceof InvalidTokenException || ex instanceof TokenExpiredException) {
            errorResponse.code(HttpStatus.UNAUTHORIZED);
            errorResponse.status(HttpStatus.UNAUTHORIZED.toString());
            errorResponse.message(ex.getMessage());
            return errorResponse.build();
        }

        return errorResponse.code(HttpStatus.INTERNAL_SERVER_ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .message("Internal server error").build();
    }


}
