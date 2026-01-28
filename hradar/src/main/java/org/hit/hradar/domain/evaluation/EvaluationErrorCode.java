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
    ),
    CYCLE_CONFIGURATION_NOT_ALLOWED(
            "CYCLE_007",
            "이미 승인 완료되어 구성 추가가 불가능 합니다.",
            HttpStatus.BAD_REQUEST
    ), EVALUATION_TYPE_ALREADY_EXISTS("EVAL_TYPE_001", "이미 포함된 유형입니다.", HttpStatus.BAD_REQUEST),
    EVALUATION_TYPE_NOT_FOUND("EVAL_TYPE_002", "해당 유형을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    EVALUATION_SECTION_NOT_FOUND("EVAL_SECTION_001","해당 섹션을 찾을 수 없습니다" , HttpStatus.NOT_FOUND),
    EVALUATION_QUESTION_INVALID_TYPE("EVAL_Q_001", "유효하지 않은 문항 유형입니다." , HttpStatus.BAD_REQUEST ),
    EVALUATION_QUESTION_INVALID_RATING_SCALE("EVAL_Q_002", "유효하지 않은 척도 입니다.", HttpStatus.BAD_REQUEST ),
    EVALUATION_QUESTION_OPTIONS_NOT_ALLOWED("EVAL_Q_003", "객관식 유형이 허용되지 않습니다.", HttpStatus.METHOD_NOT_ALLOWED ),
    EVALUATION_QUESTION_OPTIONS_REQUIRED("EVAL_Q_004", "객관식 선택지가 필요합니다.", HttpStatus.BAD_REQUEST),
    EVALUATION_QUESTION_RATING_NOT_ALLOWED("EVAL_Q_005","평가 척도가 허용되지 않습니다." , HttpStatus.BAD_REQUEST ),
    EVALUATION_QUESTION_NOT_FOUND("EVAL_Q_006", "해당 문항을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    EVALUATION_ASSIGNMENT_ALREADY_EXISTS(
            "EVAL_ASSIGN_001",
            "이미 존재하는 평가 배정입니다.",
            HttpStatus.CONFLICT
    ),

    EVALUATION_ASSIGNMENT_NOT_FOUND(
            "EVAL_ASSIGN_002",
            "평가 배정을 찾을 수 없습니다.",
            HttpStatus.NOT_FOUND
    ),

    EVALUATION_ASSIGNMENT_SELF_NOT_ALLOWED(
            "EVAL_ASSIGN_003",
            "자기 자신에 대한 평가는 배정할 수 없습니다.",
            HttpStatus.BAD_REQUEST
    ),

    EVALUATION_ASSIGNMENT_CYCLE_NOT_CONFIGURABLE(
            "EVAL_ASSIGN_004",
            "평가 배정은 승인 전 회차에서만 가능합니다.",
            HttpStatus.BAD_REQUEST
    ),
    EVALUATION_ASSIGNMENT_ALREADY_SUBMITTED(
            "EVAL_ASSIGN_005",
            "이미 제출된 평가는 취소할 수 없습니다.",
            HttpStatus.CONFLICT
    ),
    EVALUATION_ASSIGNMENT_ALREADY_CANCELED(
            "EVAL_ASSIGN_006",
            "이미 취소된 평가 배정입니다.",
            HttpStatus.BAD_REQUEST
    ), EVALUATION_ASSIGNMENT_DELETED(
            "EVAL_ASSIGN_007",
            "평가 배정이 취소 되었습니다.",
            HttpStatus.BAD_REQUEST),
    EVALUATION_ASSIGNMENT_FORBIDDEN("EVAL_ASSIGN_008", "평가가 할당되지 않았습니다.", HttpStatus.FORBIDDEN ),
    EVALUATION_OPTION_NOT_FOUND("EVAL_OPTION_001", "해당 옵션을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    EVALUATION_ASSIGNMENT_INCOMPLETE(
            "EVAL_ASSIGN_005",
            "필수 문항에 대한 응답이 누락되었습니다.",
            HttpStatus.BAD_REQUEST
    ), DUPLICATE_EVALUATION_TYPE("Eval_Type_001", "중복된 평가 유형입니다", HttpStatus.BAD_REQUEST);

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
