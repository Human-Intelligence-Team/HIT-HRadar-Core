package org.hit.hradar.domain.goal;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum GoalErrorCode implements ErrorCode {

    /* ===== 조회 / 존재 ===== */
    GOAL_NOT_FOUND(
            "GOAL_001",
            "존재하지 않는 목표입니다.",
            HttpStatus.NOT_FOUND
    ),

    GOAL_ALREADY_DELETED(
            "GOAL_002",
            "이미 삭제된 목표입니다.",
            HttpStatus.GONE
    ),

    /* ===== 권한 / 접근  ===== */
    GOAL_ACCESS_DENIED(
            "GOAL_003",
            "해당 목표에 접근할 권한이 없습니다.",
            HttpStatus.FORBIDDEN
    ),

    /* ===== 구조 / 타입 규칙 ===== */
    INVALID_GOAL_SCOPE(
            "GOAL_004",
            "목표 범위가 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    GOAL_DEPTH_EXCEED(
            "GOAL_005",
            "목표는 최대 3단계까지만 생성할 수 있습니다.",
            HttpStatus.BAD_REQUEST
    ),

    INVALID_PARENT_GOAL_TYPE(
            "GOAL_006",
            "상위 목표의 유형과 하위 목표의 유형이 일치하지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    NOT_KPI_GOAL(
            "GOAL_007",
            "KPI 목표가 아닌 경우 KPI를 생성할 수 없습니다.",
            HttpStatus.BAD_REQUEST
    ),

    /* ===== 상태 ===== */
    GOAL_ALREADY_APPROVED(
            "GOAL_008",
            "승인 완료된 목표는 수정하거나 하위 목표를 생성할 수 없습니다.",
            HttpStatus.CONFLICT
    ),
    Goal_NOT_APPROVED(
            "GOAL_009",
            "목표가 승인되지 않았습니다.",
            HttpStatus.BAD_REQUEST
    ),

    /* ===== 기간 ===== */
    INVALID_GOAL_PERIOD(
            "GOAL_010",
            "목표 종료일은 시작일보다 빠를 수 없습니다.",
            HttpStatus.BAD_REQUEST
    ),

    CHILD_GOAL_PERIOD_OUT_OF_RANGE(
            "GOAL_011",
            "하위 목표의 기간은 상위 목표 기간 내에 있어야 합니다.",
            HttpStatus.BAD_REQUEST
    ),

    /* ===== 수정/재등록 ===== */
    GOAL_NOT_EDITABLE(
        "GOAL_012",
                "현재 상태에서는 목표를 수정할 수 없습니다.",
        HttpStatus.CONFLICT
        ),

    GOAL_NOT_RESUBMITTABLE(
        "GOAL_013",
                "반려(REJECTED) 상태인 목표만 재등록할 수 있습니다.",
        HttpStatus.BAD_REQUEST
        ),

    GOAL_EDIT_FORBIDDEN(
        "GOAL_014",
                "목표를 수정할 권한이 없습니다.",
        HttpStatus.FORBIDDEN
        ),

    /* ===== KPI / OKR (수정에서 필요) ===== */
    KPI_NOT_FOUND(
        "KPI_001",
                "존재하지 않는 KPI입니다.",
        HttpStatus.NOT_FOUND
        ),

    KR_NOT_FOUND(
        "OKR_001",
                "존재하지 않는 Key Result입니다.",
        HttpStatus.NOT_FOUND
        ),
    INVALID_GOAL_KPI_RELATION(
            "KPI_002",
                "목표와 KPI가 연관되어있지 않습니다." ,
            HttpStatus.BAD_REQUEST
        ),
    INVALID_GOAL_OKR_RELATION(
            "OKR_002",
                "목표와 KR이 연관되어있지 않습니다." ,
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
