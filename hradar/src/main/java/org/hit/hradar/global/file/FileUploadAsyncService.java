package org.hit.hradar.global.file;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileUploadAsyncService {

    private final FileUploadService uploadService;

    @Async("fileUploadExecutor")
    public CompletableFuture<UploadResult> uploadAsync(
            MultipartFile file,
            FileType type
    ) {
        try {
            StoredFile storedFile = uploadService.upload(file, type);
            return CompletableFuture.completedFuture(
                    UploadResult.success(file.getOriginalFilename(), storedFile)
            );
        } catch (Exception e) {
            return CompletableFuture.completedFuture(
                    UploadResult.fail(file.getOriginalFilename(), e.getMessage())
            );
        }
    }
}

