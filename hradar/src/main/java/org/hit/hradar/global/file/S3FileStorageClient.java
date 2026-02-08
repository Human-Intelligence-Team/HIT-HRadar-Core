package org.hit.hradar.global.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "file.storage.type", havingValue = "s3")
public class S3FileStorageClient implements FileStorageClient {

    private final S3Client s3Client;

    @Value("${file.s3.bucket}")
    private String bucket;

    @Value("${file.s3.prefix:}")
    private String prefix;


    @Override
    public StoredFile upload(MultipartFile file, FileType type) {
        String originalName = file.getOriginalFilename();
        String extension = extractExtension(originalName);
        String savedFileName = UUID.randomUUID() + "." + extension;

        // Structured Key: prefix/images/uuid.ext or prefix/attachments/uuid.ext
        String typePath = type.getPath();
        String key = prefix + typePath + "/" + savedFileName;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            log.info("Uploading object to S3 - Bucket: {}, Key: {}", bucket, key);
            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        } catch (IOException e) {
            throw new BusinessException(FileErrorCode.FAIL_UPLOAD, e);
        }

        // Return Proxy URL: /api/v1/files/{typePath}/{savedFileName}
        String proxyUrl = "/api/v1/files/" + typePath + "/" + savedFileName;

        // Store the relative path (typePath/savedFileName) to keep 'prefix'
        // configurable
        String storedName = typePath + "/" + savedFileName;
        return new StoredFile(proxyUrl, storedName);
    }

    @Override
    public void delete(String storedName) {
        String key = prefix + storedName;
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            log.info("Deleting object from S3 - Bucket: {}, Key: {}", bucket, key);
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new BusinessException(FileErrorCode.FAIL_DELETE, e);
        }
    }

    @Override
    public String extractStoredName(String urlOrName) {
        if (urlOrName == null)
            return null;
        if (urlOrName.contains("/")) {
            String lastPart = urlOrName.substring(urlOrName.lastIndexOf("/") + 1);
            if (lastPart.contains("?")) {
                lastPart = lastPart.substring(0, lastPart.indexOf("?"));
            }
            return lastPart;
        }
        return urlOrName;
    }

    @Override
    public InputStream download(String storedName) {
        // Fallback or internal use only
        String key = prefix + storedName;
        log.info("Downloading object from S3 - Bucket: {}, Key: {}", bucket, key);
        return s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build());
    }

    @Override
    public String generatePresignedUrl(String storedName) {
        // Disabled due to dependency resolution issues with s3-presigner
        // Returning null triggers the generic streaming fallback in FileProxyController
        log.warn("Presigned URL generation requested but S3Presigner is disabled. Falling back to stream.");
        return null;
    }

    private String extractExtension(String filename) {
        if (filename == null)
            return "";
        int idx = filename.lastIndexOf('.');
        return (idx > -1) ? filename.substring(idx + 1).toLowerCase() : "";
    }
}
