package org.hit.hradar.global.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageClient {

    StoredFile upload(MultipartFile file);
    void delete(String storedName);
}

