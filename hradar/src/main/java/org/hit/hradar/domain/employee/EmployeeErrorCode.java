package org.hit.hradar.domain.employee;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum EmployeeErrorCode implements ErrorCode {
    EMPLOYEE_NOT_FOUND("EMP_001", "해당 사원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_EMPLOYEE_NO_OR_EMAIL("EMP_002", "이미 사용중인 사원번호 또는 이메일입니다.", HttpStatus.CONFLICT),
    INVALID_DEPARTMENT("EMP_003", "유효하지 않은 부서입니다.", HttpStatus.BAD_REQUEST),
    INVALID_POSITION("EMP_004", "유효하지 않은 직책입니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    EmployeeErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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