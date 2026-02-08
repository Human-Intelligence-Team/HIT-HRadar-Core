package org.hit.hradar.global.file;

import java.util.Set;

public enum FileType {

    IMAGE(
            Set.of("jpg", "jpeg", "png", "gif", "webp"),
            5 * 1024 * 1024, // 5MB
            "images"),

    PROFILE_IMAGE(
            Set.of("jpg", "jpeg", "png", "gif", "webp"),
            5 * 1024 * 1024, // 5MB
            "images"),

    ATTACHMENT(
            Set.of("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "zip", "txt", "csv", "png", "jpg", "jpeg"),
            20 * 1024 * 1024, // 20MB
            "attachments");

    private final Set<String> allowedExtensions;
    private final long maxSize;
    private final String path;

    FileType(Set<String> allowedExtensions, long maxSize, String path) {
        this.allowedExtensions = allowedExtensions;
        this.maxSize = maxSize;
        this.path = path;
    }

    public Set<String> allowedExtensions() {
        return allowedExtensions;
    }

    public long maxSize() {
        return maxSize;
    }

    public String getPath() {
        return path;
    }
}
