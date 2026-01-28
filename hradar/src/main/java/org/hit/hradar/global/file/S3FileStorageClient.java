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

    private static final String BUCKET_NAME = "bucket";
    private static final String BUCKET_URL = "https://s3.amazonaws.com/bucket";

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "jpg", "jpeg", "png", "gif", "webp",
            "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx"
    );
//
//    private final AmazonS3 amazonS3;
//
//    public S3FileStorageClient(AmazonS3 amazonS3) {
//        this.amazonS3 = amazonS3;
//    }

    @Override
    public StoredFile upload(MultipartFile file) {

        String originalName = file.getOriginalFilename();
        String extension = extractExtension(originalName);

        String storedName = UUID.randomUUID() + "." + extension;

        try {
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(file.getSize());
//            metadata.setContentType(file.getContentType());
//
//            amazonS3.putObject(
//                    BUCKET_NAME,
//                    storedName,
//                    file.getInputStream(),
//                    metadata
//            );
        } catch (Exception e) {
            throw new BusinessException(FileErrorCode.FAIL_UPLOAD, e);
        }

        String url = BUCKET_URL + "/" + storedName;
        return new StoredFile(url, storedName);
    }

    @Override
    public void delete(String storedName) {
//        try {
//            // 이미 없으면 그냥 통과 (idempotent)
//            if (!amazonS3.doesObjectExist(BUCKET_NAME, storedName)) {
//                return;
//            }
//
//            amazonS3.deleteObject(BUCKET_NAME, storedName);
//
//        } catch (Exception e) {
//            throw new BusinessException(FileErrorCode.FAIL_DELETE, e);
//        }
    }

    /* ---------- private ---------- */

    private String extractExtension(String filename) {
        if (filename == null) return "";
        int idx = filename.lastIndexOf('.');
        return (idx > -1) ? filename.substring(idx + 1).toLowerCase() : "";
    }
}
