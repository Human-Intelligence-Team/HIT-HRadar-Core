package org.hit.gateway.filter;

import io.jsonwebtoken.Claims;
import org.hit.gateway.jwt.JwtTokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter
        extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            String token;
            if (authHeader == null) {
                return unauthorized(exchange);
            }

            if (authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            } else {
                // polyfill / SSE 보정
                token = authHeader;
            }

            if (!jwtTokenProvider.validateToken(token)) {
                return unauthorized(exchange);
            }

            Claims claims = jwtTokenProvider.parseClaims(token);

            ServerHttpRequest mutatedRequest = request.mutate()
                    .headers(headers -> headers.remove(HttpHeaders.AUTHORIZATION))
                    .header("X-User-Id", claims.getSubject())
                    .header("X-User-Role", claims.get("role", String.class))
                    .header("X-Company-Id", claims.get("companyId", String.class))
                    .header("X-Employee-Id", claims.get("employeeId", String.class))
                    .build();

            return chain.filter(
                    exchange.mutate()
                            .request(mutatedRequest)
                            .build()
            );
        };
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
    }
}
