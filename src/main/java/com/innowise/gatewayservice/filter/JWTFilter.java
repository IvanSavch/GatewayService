package com.innowise.gatewayservice.filter;

import com.innowise.gatewayservice.exception.InvalidTokenException;
import com.innowise.gatewayservice.exception.TokenExpiredException;
import com.innowise.gatewayservice.service.JWTService;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;


@Component
public class JWTFilter implements GlobalFilter {
    private final JWTService jwtService;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        if (path.startsWith("/auth/login") || path.startsWith("/auth/registration")) {
            return chain.filter(exchange);
        }

        String token = jwtService.resolveToken(exchange);

        Claims claims = jwtService.validateToken(token);
        if (claims == null) {
            throw new InvalidTokenException();
        }

        if (claims.getExpiration().before(new Date())){
            throw new TokenExpiredException();
        }

        ServerWebExchange mutatedExchange = exchange.mutate().request(r -> r
                        .header("UserId", String.valueOf(claims.get("id", Long.class)))
                        .header("UserRoles", claims.get("role", String.class)))
                .build();

        return chain.filter(mutatedExchange);
    }
}
