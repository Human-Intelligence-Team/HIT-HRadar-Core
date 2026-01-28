package org.hit.hradar.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final VectorIndexProperties vectorIndexProperties;

    @Bean
    public WebClient vectorIndexWebClient() {
        return WebClient.builder()
                .baseUrl(vectorIndexProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

  @Bean
  @Qualifier("authInternalWebClient")
  public WebClient authInternalWebClient(
      @Value("${auth.internal.base-url}") String baseUrl,
      @Value("${auth.internal.token}") String internalToken
  ) {
    return WebClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader("X-Internal-Token", internalToken)
        .build();
  }
}
