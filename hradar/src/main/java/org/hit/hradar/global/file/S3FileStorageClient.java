package org.hit.hradar.global.file;

import org.hit.hradar.global.exception.BusinessException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;

@Component
@Profile("prod")
public class S3FileStorageClient implements FileStorageClient {

    private static final String BUCKET_URL = "https://s3.amazonaws.com/bucket";

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "jpg", "jpeg", "png", "gif", "webp",
            "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx"
    );

    @Override
    public StoredFile upload(MultipartFile file) {

        String originalName = file.getOriginalFilename();
        String extension = extractExtension(originalName);

        // 화이트리스트 검증
        validateExtension(extension);

        // 저장용 이름
        String storedName = UUID.randomUUID() + "." + extension;

        try {
            // 실제 구현 시 여기에 S3 putObject 들어감
            // s3Client.putObject(bucket, storedName, file.getInputStream(), metadata);

        } catch (Exception e) {
            throw new BusinessException(FileErrorCode.FAIL_UPLOAD, e);
        }

        String url = BUCKET_URL + "/" + storedName;

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
