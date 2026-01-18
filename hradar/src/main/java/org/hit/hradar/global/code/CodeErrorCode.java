package org.hit.hradar.global.code;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum CodeErrorCode implements ErrorCode {

    GROUP_EXIST(
            "CODE_ERROR_001",
            "이미 존재하는 코드 그룹입니다.",
            HttpStatus.BAD_REQUEST
    ),GROUP_NOT_FOUND(
            "CODE_ERROR_002",
            "존재하지 않는 코드 그룹입니다.",
            HttpStatus.NOT_FOUND
    ),CODE_EXIST(
            "CODE_ERROR_003",
            "이미 존재하는 공통 코드입니다.",
            HttpStatus.BAD_REQUEST
    ),
    CODE_NOT_FOUND(
            "CODE_ERROR_004",
            "존재하지 않는 공통 코드입니다.",
            HttpStatus.NOT_FOUND
    );



    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    CodeErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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

