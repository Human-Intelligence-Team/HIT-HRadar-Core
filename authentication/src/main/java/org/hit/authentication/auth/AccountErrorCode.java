package org.hit.authentication.auth;

import org.hit.authentication.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum AccountErrorCode implements ErrorCode {
    ACCOUNT_NOT_FOUND("ACCOUNT_001", "계정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ACCOUNT_DUPLICATED("ACCOUNT_002", "중복된 계정입니다.", HttpStatus.BAD_REQUEST),
    ACCOUNT_INVALID_PASSWORD("ACCOUNT_003", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    ACCOUNT_CANCELED("ACCOUNT_004", "삭제된 계정입니다.", HttpStatus.NOT_ACCEPTABLE),
    DUPLICATE_LOGIN_ID("ACCOUNT_005", "중복된 아이디입니다.", HttpStatus.BAD_REQUEST),
    COMPANY_INVALID("ACCOUNT_006", "회사 코드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    EMAIL_REQUIRED("EMAIL_001", "이메일이 필요합니다.", HttpStatus.BAD_REQUEST),
    INVALID_PURPOSE("EMAIL_002", "이메일 인증 목적이 필요합니다.", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("EMAIL_003", "중복된 이메일입니다.", HttpStatus.BAD_REQUEST),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    AccountErrorCode(String errorCode, String message, HttpStatusCode httpStatusCode) {
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
