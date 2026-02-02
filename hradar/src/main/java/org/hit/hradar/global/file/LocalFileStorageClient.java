package org.hit.hradar.global.file;

import org.hit.hradar.global.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
@ConditionalOnProperty(name = "file.storage.type", havingValue = "local")
public class LocalFileStorageClient implements FileStorageClient {

    @Value("${file.local.base-dir}")
    private String baseDir;

    @Value("${file.local.base-url}")
    private String baseUrl;

    @Override
    public StoredFile upload(MultipartFile file, FileType type) {

        String originalName = file.getOriginalFilename();
        String extension = extractExtension(originalName);

        // 저장용 파일명 (점 포함)
        String storedName = UUID.randomUUID() + "." + extension;

        try {
            Path dirPath = Path.of(baseDir);
            Files.createDirectories(dirPath);

            Path target = dirPath.resolve(storedName);
            try (InputStream in = file.getInputStream()) {
                Files.copy(in, target);
            }

        } catch (IOException e) {
            throw new BusinessException(FileErrorCode.FAIL_UPLOAD, e);
        }

        // 접근 URL
        String typePath = (type == FileType.IMAGE) ? "images" : "attachments";
        String url = "/api/v1/files/" + typePath + "/" + storedName;

        return new StoredFile(url, storedName);
    }

    @Override
    public void delete(String storedName) {
        try {
            Path target = Path.of(baseDir).resolve(storedName);

            // 이미 삭제된 경우 조용히 종료
            if (!Files.exists(target)) {
                return;
            }

            Files.delete(target);
        } catch (IOException e) {
            throw new BusinessException(FileErrorCode.FAIL_DELETE, e);
        }
    }

    @Override
    public String generatePresignedUrl(String storedName) {
        return baseUrl + "/" + storedName;
    }

    @Override
    public String extractStoredName(String urlOrName) {
        if (urlOrName == null)
            return null;
        if (urlOrName.contains("/")) {
            return urlOrName.substring(urlOrName.lastIndexOf("/") + 1);
        }
        return urlOrName;
    }

    @Override
    public java.io.InputStream download(String storedName) {
        try {
            return java.nio.file.Files.newInputStream(java.nio.file.Paths.get(baseDir, storedName));
        } catch (java.io.IOException e) {
            throw new org.hit.hradar.global.exception.BusinessException(FileErrorCode.NOT_FOUND_FILE, e);
        }
    }

    private String extractExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int idx = filename.lastIndexOf('.');
        return (idx > -1)
                ? filename.substring(idx + 1).toLowerCase()
                : "";
    }
}
