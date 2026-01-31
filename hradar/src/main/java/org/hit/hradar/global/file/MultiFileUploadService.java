package org.hit.hradar.global.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MultiFileUploadService {

    private final FileUploadAsyncService asyncService;

    public List<UploadResult> uploadFiles(
            List<MultipartFile> files,
            FileType type
    ) {
        List<CompletableFuture<UploadResult>> futures =
                files.stream()
                        .map(file -> asyncService.uploadAsync(file, type))
                        .toList();

        // 전부 완료 대기
        CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        ).join();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }
}

