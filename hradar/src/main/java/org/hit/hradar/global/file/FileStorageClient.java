package org.hit.hradar.global.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageClient {

    StoredFile upload(org.springframework.web.multipart.MultipartFile file, FileType type);

    void delete(String storedName);

    String generatePresignedUrl(String storedName);

    String extractStoredName(String urlOrName);

    java.io.InputStream download(String storedName);
}
