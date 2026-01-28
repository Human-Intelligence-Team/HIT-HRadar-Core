package org.hit.hradar.global.file;

import org.hit.hradar.global.exception.BusinessException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

@Component
@Profile("local")
public class LocalFileStorageClient implements FileStorageClient {

    private static final String BASE_DIR = "/tmp/uploads";
    private static final String BASE_URL = "/files";

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "jpg", "jpeg", "png", "gif", "webp",
            "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx"
    );

    @Override
    public StoredFile upload(MultipartFile file) {

        String originalName = file.getOriginalFilename();
        String extension = extractExtension(originalName);

        validateExtension(extension);

        // 저장용 파일명 생성 (핵심)
        String storedName = UUID.randomUUID() + extension;

        try {
            Path baseDir = Path.of(BASE_DIR);
            Files.createDirectories(baseDir);

            Path target = baseDir.resolve(storedName);
            file.transferTo(target.toFile());
        } catch (IOException e) {
            throw new BusinessException(FileErrorCode.FAIL_UPLOAD);
        }

        String url = BASE_URL + "/" + storedName;

        return new StoredFile(
                url,
                storedName
        );
    }

    private String extractExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int idx = filename.lastIndexOf('.');
        return (idx > -1) ? filename.substring(idx + 1).toLowerCase() : "";
    }

    private void validateExtension(String ext) {
        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            throw new BusinessException(FileErrorCode.INVALID_EXTENSION);
        }
    }
}