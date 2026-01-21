package org.hit.hradar.domain.grading;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum GradingErrorCode implements ErrorCode {
    GRADE_NOT_FOUND(
            "GRADE_001",
            "존재하지 않는 등급입니다.",
            HttpStatus.NOT_FOUND
    ),

    DUPLICATE_GRADE_NAME(
            "GRADE_002",
            "이미 등록된 등급명입니다.",
            HttpStatus.CONFLICT
    ),

    DUPLICATE_GRADE_ORDER(
            "GRADE_003",
            "이미 사용 중인 등급 순서입니다.",
            HttpStatus.CONFLICT
    );



    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    GradingErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
