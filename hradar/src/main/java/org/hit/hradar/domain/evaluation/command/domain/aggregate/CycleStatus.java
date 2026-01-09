package org.hit.hradar.domain.evaluation.command.domain.aggregate;

public enum CycleStatus {
    DRAFT,      // 작성 중
    OPEN,       // 진행 중
    CLOSED,     // 종료
    CONFIRMED   // 확정
}
