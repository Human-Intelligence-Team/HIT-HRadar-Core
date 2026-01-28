package org.hit.hradar.global.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileValidator validator;
    private final FileStorageClient storageClient;

    public StoredFile upload(MultipartFile file, FileType type) {

        // 정책 검증
        validator.validate(file, type);

        // 실제 저장
        return storageClient.upload(file);
    }

    public void delete(String storedName) {
        storageClient.delete(storedName);
    }
}

