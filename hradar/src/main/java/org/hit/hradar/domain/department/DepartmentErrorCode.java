package org.hit.hradar.domain.department;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum DepartmentErrorCode implements ErrorCode {
    DEPARTMENT_NOT_FOUND("DEPT_001", "해당 부서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_DEPARTMENT_NAME("DEPT_002", "이미 사용중인 부서명입니다.", HttpStatus.CONFLICT),
    INVALID_PARENT_DEPARTMENT("DEPT_003", "상위 부서가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    DepartmentErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
