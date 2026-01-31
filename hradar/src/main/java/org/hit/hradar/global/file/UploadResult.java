package org.hit.hradar.global.file;

public record UploadResult(
        String originalName,
        boolean success,
        StoredFile storedFile,
        String errorMessage
) {
    public static UploadResult success(String originalName, StoredFile file) {
        return new UploadResult(originalName, true, file, null);
    }

    public static UploadResult fail(String originalName, String message) {
        return new UploadResult(originalName, false, null, message);
    }
}
