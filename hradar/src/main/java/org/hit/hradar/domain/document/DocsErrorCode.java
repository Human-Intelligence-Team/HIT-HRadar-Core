package org.hit.hradar.domain.document;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum DocsErrorCode implements ErrorCode {

    /* ===== 조회 / 존재 ===== */
    EMPTY_FILE("DOCUMENT_001", "파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_HEADER("DOCUMENT_002", "CSV 헤더가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    NO_VALID_CONTENT("DOCUMENT_003", "유효한 문서 내용이 없습니다.", HttpStatus.BAD_REQUEST),
    DOC_NOT_EDITABLE("DOCUMENT_004", "문서를 수정할 수 없는 상태입니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    DocsErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
