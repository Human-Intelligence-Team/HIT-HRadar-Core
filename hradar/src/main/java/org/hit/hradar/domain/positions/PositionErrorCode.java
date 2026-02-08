package org.hit.hradar.domain.positions;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum PositionErrorCode implements ErrorCode {
    POSITION_NOT_FOUND("POS_001", "해당 직책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_POSITION_NAME("POS_002", "이미 사용중인 직책명입니다.", HttpStatus.CONFLICT),
    CANNOT_DELETE_HAS_EMPLOYEES("POS_003", "해당 직책을 가진 사원이 존재하여 삭제할 수 없습니다.", HttpStatus.CONFLICT),
    INVALID_POSITION_RANK("POS_004", "직위 순서는 0보다 커야 합니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_POSITION_RANK("POS_005", "이미 존재하는 직위 순서입니다.", HttpStatus.CONFLICT),
    DUPLICATE_POSITION("POS_006", "이미 같은 이름과 순서를 가진 직위가 존재합니다.", HttpStatus.CONFLICT),
    ;

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
