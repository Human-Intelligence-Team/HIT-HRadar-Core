package org.hit.hradar.global.file;

import org.hit.hradar.global.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

@Component
@Profile("local")
public class LocalFileStorageClient implements FileStorageClient {

    @Value("${file.local.base-dir}")
    private String baseDir;

    @Value("${file.local.base-url}")
    private String baseUrl;

    @Override
    public StoredFile upload(MultipartFile file) {

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
        String url = baseUrl + "/" + storedName;

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
