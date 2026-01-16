package org.hit.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GatewayRequestLoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long start = System.currentTimeMillis();

        ServerHttpRequest request = exchange.getRequest();

        String path = request.getURI().getPath();
        String method = request.getMethod().name();
        String requestId = exchange.getRequest().getId();

        return chain.filter(exchange).doFinally(signalType -> {
            long duration = System.currentTimeMillis() - start;

            HttpStatusCode status = exchange.getResponse().getStatusCode();

            log.info(
                    "[GATEWAY] {} {} status={} duration={}ms userId={} requestId={}",
                    method,
                    path,
                    status != null ? status.value() : "UNKNOWN",
                    duration,
                    requestId
            );

            log.info("METHOD={}", request.getMethod());
            log.info("AUTH={}", request.getHeaders().getFirst("Authorization"));
            log.info("ALL_HEADERS={}", request.getHeaders());
        });
    }

    @Override
    public int getOrder() {
        return -1; // 가장 앞단
    }
}
