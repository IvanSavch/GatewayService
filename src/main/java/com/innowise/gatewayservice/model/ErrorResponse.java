package com.innowise.gatewayservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorResponse implements Serializable {
    private String status;
    private HttpStatus code;
    private String message;
    private String path;
    private LocalDateTime timestamp;
}
