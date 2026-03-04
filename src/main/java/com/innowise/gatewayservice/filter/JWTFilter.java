package com.innowise.gatewayservice.filter;


import com.innowise.gatewayservice.exception.InvalidTokenException;
import com.innowise.gatewayservice.exception.TokenExpiredException;
import com.innowise.gatewayservice.service.JWTService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
public class JWTFilter implements WebFilter {
    private final JWTService jwtService;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = jwtService.resolveToken(exchange);

        if (token == null) {
            return chain.filter(exchange);
        }

        try {
            Claims claims = jwtService.validateToken(token);

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(r -> r.headers(headers -> {
                        headers.set("UserId", String.valueOf(claims.get("id")));
                        headers.set("UserRoles", claims.get("role", String.class));
                    })).build();

            return chain.filter(mutatedExchange);

        } catch (InvalidTokenException | TokenExpiredException e) {
            return Mono.error(e);
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}


