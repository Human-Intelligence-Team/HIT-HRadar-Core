package org.hit.hradar.global.file;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileProxyController {

    private final FileStorageClient storageClient;

    @GetMapping("/{type}/**")
    public ResponseEntity<Resource> proxyFile(
            @PathVariable String type,
            HttpServletRequest request) {

        String path = (String) request
                .getAttribute(org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request
                .getAttribute(org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String storedName = type + "/"
                + new AntPathMatcher().extractPathWithinPattern(bestMatchPattern,
                        path);

        // 1. Try to generate a Presigned URL (specifically for S3)
        // S3FileStorageClient implements this to return the S3 URL.
        // LocalFileStorageClient implements this to typically return null.
        String presignedUrl = storageClient.generatePresignedUrl(storedName);
        if (presignedUrl != null) {
            log.info("Redirecting to S3 Presigned URL for: {}", storedName);
            return ResponseEntity.status(HttpStatus.FOUND) // 302 Found
                    .location(URI.create(presignedUrl))
                    .build();
        }

        // 2. Fallback to direct streaming (Local storage or S3 internal fallback)
        log.info("Streaming file directly for: {}", storedName);
        InputStream inputStream = storageClient.download(storedName);
        InputStreamResource resource = new InputStreamResource(inputStream);

        String contentType = "application/octet-stream";
        String lowerName = storedName.toLowerCase();
        if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg"))
            contentType = "image/jpeg";
        else if (lowerName.endsWith(".png"))
            contentType = "image/png";
        else if (lowerName.endsWith(".gif"))
            contentType = "image/gif";
        else if (lowerName.endsWith(".pdf"))
            contentType = "application/pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + storedName + "\"")
                .body(resource);
    }
}
