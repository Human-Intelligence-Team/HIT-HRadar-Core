package org.hit.hradar.domain.approval.command.domain.aggregate;

public enum ApprovalAttendanceCategory {
    NONE, // 일반 (근태 반영 없음)
    WORK_OFFICE, // 내근
    WORK_REMOTE, // 재택
    WORK_FIELD, // 외근
    WORK_TRIP, // 출장
    VACATION, // 휴가 (추후 확장용)
    OVERTIME // 초과근무
}
