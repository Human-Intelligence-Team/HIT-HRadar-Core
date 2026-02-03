package org.hit.hradar.global.file;

import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {

    public void validate(MultipartFile file, FileType type) {

        if (file == null || file.isEmpty()) {
            throw new BusinessException(FileErrorCode.EMPTY_FILE);
        }

        validateSize(file, type);
        validateExtension(file, type);
    }

    private void validateSize(MultipartFile file, FileType type) {
        if (file.getSize() > type.maxSize()) {
            throw new BusinessException(FileErrorCode.FILE_SIZE_EXCEEDED);
        }
    }

    private void validateExtension(MultipartFile file, FileType type) {
        String ext = extractExtension(file.getOriginalFilename());
        if (!type.allowedExtensions().contains(ext)) {
            throw new BusinessException(FileErrorCode.INVALID_EXTENSION);
        }
    }

    private String extractExtension(String filename) {
        if (filename == null) return "";
        int idx = filename.lastIndexOf('.');
        return (idx > -1) ? filename.substring(idx + 1).toLowerCase() : "";
    }
}
