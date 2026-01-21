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
    ),
    DISTRIBUTION_RULE_ALREADY_EXISTS(
            "GRADE_004",
            "이미 등급 배분 정책이 등록되어 있습니다.",
            HttpStatus.CONFLICT
    ),

    INVALID_DISTRIBUTION_RATIO(
            "GRADE_005",
            "배분 비율이 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    GRADE_COMPANY_MISMATCH(
            "GRADE_006",
            "해당 회사의 등급이 아닙니다.",
            HttpStatus.FORBIDDEN
    ),
    EXCEEDS_TOTAL_DISTRIBUTION_RATIO(
            "GRADE_007",
            "전체 등급 배분 비율의 합은 100%를 초과할 수 없습니다.",
            HttpStatus.BAD_REQUEST
    ),
    GRADE_CANNOT_BE_MODIFIED(
            "GRADE_008",
            "등급을 수정할 수 없습니다.",
            HttpStatus.BAD_REQUEST
    ),
    DISTRIBUTION_RULE_NOT_FOUND(
            "GRADE_009",
            "등급 정책을 찾을 수 없습니다.",
            HttpStatus.NOT_FOUND
    ),
    CYCLE_NOT_IN_PROGRESS(
            "GRADE_010",
            "평가가 진행 중인 회차에서만 등급을 부여할 수 있습니다.",
            HttpStatus.BAD_REQUEST
    ),

    DUPLICATE_DEPT_GRADE(
            "GRADE_011",
            "이미 해당 회차에 팀 등급이 부여되어 있습니다.",
            HttpStatus.CONFLICT
    ),
    NOT_ALLOWED(
            "GRADE_012",
            "허가 되지 않는 수정입니다.",
            HttpStatus.METHOD_NOT_ALLOWED
    ),
    DEPT_GRADE_NOT_FOUND(
            "GRADE_013",
            "팀 등급 정보를 찾을 수 없습니다.",
            HttpStatus.NOT_FOUND
    ),;



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
