package org.hit.hradar.global.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthInternalClient {

  private final WebClient webClient;

  public AuthInternalClient(@Qualifier("authInternalWebClient") WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<Void> revokeRefreshToken(Long accId) {
    return webClient.post()
        .uri("/api/auth/internal/tokens/revoke")
        .bodyValue(new RevokeTokenRequest(accId))
        .retrieve()
        .bodyToMono(Void.class);
  }

  public record RevokeTokenRequest(Long userId) {}
}
