package org.hit.hradar.domain.evaluation;

import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum EvaluationErrorCode implements ErrorCode {
    CYCLE_NOT_FOUND (
            "CYCLE_001",
            "존재하지 않는 회차 입니다.",
            HttpStatus.NOT_FOUND
    ),
    CYCLE_INVALID_PERIOD(
            "CYCLE_002",
            "종료일은 시작일 이후여야 합니다.",
            HttpStatus.BAD_REQUEST
    ),

    CYCLE_ALREADY_CLOSED(
            "CYCLE_003",
            "이미 마감된 평가 회차입니다.",
            HttpStatus.CONFLICT
    ),
    CYCLE_DELETED(
            "CYCLE_004",
            "삭제된 회차입니다.",
            HttpStatus.BAD_REQUEST
    ), NOT_CONFIRMED(
            "CYCLE_005",
            "승인되지 않았습니다.",
            HttpStatus.BAD_REQUEST
    ),
    CYCLE_NOT_OPEN(
            "CYCLE_006",
            "평가 기간이 아닙니다.",
            HttpStatus.FORBIDDEN
    ),;

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    EvaluationErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
    public HttpStatusCode getHttpStatusCode() {
        return httpStatus;
    }
}
