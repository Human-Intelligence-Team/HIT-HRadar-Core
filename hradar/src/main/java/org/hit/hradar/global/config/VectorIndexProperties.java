package org.hit.hradar.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "external.vector-index")
public class VectorIndexProperties {

    private String baseUrl;
    private Timeout timeout = new Timeout();

    @Getter
    @Setter
    public static class Timeout {
        private Duration connect;
        private Duration read;
    }
}
