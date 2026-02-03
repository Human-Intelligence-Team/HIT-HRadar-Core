package org.hit.hradar.domain.positions;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum PositionErrorCode implements ErrorCode {
    POSITION_NOT_FOUND("POS_001", "해당 직책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_POSITION_NAME("POS_002", "이미 사용중인 직책명입니다.", HttpStatus.CONFLICT);

    private final String errorCode;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    PositionErrorCode(String errorCode, String message, HttpStatusCode httpStatusCode) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
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
    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }
}
