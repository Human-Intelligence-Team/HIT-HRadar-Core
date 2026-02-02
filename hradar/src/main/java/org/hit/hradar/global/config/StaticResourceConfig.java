package org.hit.hradar.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${file.local.base-dir}")
    private String baseDir;

    @Value("${file.local.base-url}")
    private String baseUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = "file:" + Path.of(
                System.getProperty("user.dir"),
                baseDir).toAbsolutePath() + "/";
        System.out.println(location);
        registry.addResourceHandler("/files/**")
                .addResourceLocations(location);

        registry.addResourceHandler(baseUrl + "/**")
                .addResourceLocations(location);
    }
}
