package org.hit.hradar.global.file;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum FileErrorCode implements ErrorCode {

    EMPTY_FILE("FILE_001", "비어있는 파일입니다.", HttpStatus.BAD_REQUEST),
    FAIL_UPLOAD("FILE_002", "파일 업로드에 실패했습니다.", HttpStatus.BAD_REQUEST),
    INVALID_EXTENSION("FILE_003", "유효한 확장자명이 아닙니다.", HttpStatus.BAD_REQUEST),
    FILE_SIZE_EXCEEDED("FILE_004", "허용된 용량을 초과했습니다. (이미지 5MB, 첨부 파일 20MB)", HttpStatus.BAD_REQUEST),;

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    FileErrorCode(String errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatusCode() {
        return httpStatus;
    }
}
