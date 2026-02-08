package org.hit.hradar.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsS3Config {

        @Value("${file.s3.access-key:}")
        private String accessKey;

        @Value("${file.s3.secret-key:}")
        private String secretKey;

        @Value("${file.s3.region}")
        private String region;

        @Bean
        public S3Client s3Client() {
                if (accessKey.isEmpty() || secretKey.isEmpty()) {
                        return S3Client.builder()
                                        .region(Region.of(region))
                                        .build();
                }

                return S3Client.builder()
                                .region(Region.of(region))
                                .credentialsProvider(StaticCredentialsProvider.create(
                                                AwsBasicCredentials.create(accessKey, secretKey)))
                                .build();
        }

}
