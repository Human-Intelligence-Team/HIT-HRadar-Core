package org.hit.hradar.global.file;

import java.util.Set;

public enum FileType {

    IMAGE(
            Set.of("jpg", "jpeg", "png", "gif", "webp"),
            5 * 1024 * 1024 // 5MB
    ),

    ATTACHMENT(
            Set.of("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx"),
            20 * 1024 * 1024 // 20MB
    );

    private final Set<String> allowedExtensions;
    private final long maxSize;

    FileType(Set<String> allowedExtensions, long maxSize) {
        this.allowedExtensions = allowedExtensions;
        this.maxSize = maxSize;
    }

    public Set<String> allowedExtensions() {
        return allowedExtensions;
    }

    public long maxSize() {
        return maxSize;
    }
}
