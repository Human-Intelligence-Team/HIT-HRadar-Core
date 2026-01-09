package org.hit.hradar.domain.goal;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum GoalErrorCode implements ErrorCode {
    INVALID_GOAL_SCOPE(
            "GOAL_001",
            "목표 범위가 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ), GOAL_DEPTH_EXCEED(
            "GOAL_002",
            "목표는 최대 3단계까지만 생성할 수 있습니다.",
            HttpStatus.BAD_REQUEST
    );

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    GoalErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
