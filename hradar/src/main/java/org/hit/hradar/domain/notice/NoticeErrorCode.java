package org.hit.hradar.domain.notice;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum NoticeErrorCode implements ErrorCode {

    DUPLICATED_CATEGORY_NAME("NOTICE_CATEGORY_001", "카테고리 이름이 중복됩니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_CATEGORY("NOTICE_CATEGORY_002", "카테고리가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_IMAGE("NOTICE_IMAGE_001","이미지가 존재하지 않습니다." , HttpStatus.BAD_REQUEST),
    NOT_FOUND_NOTICE("NOTICE_001", "공지사항을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),;

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    NoticeErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
