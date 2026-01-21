package org.hit.hradar.domain.grading.port;

public interface EvaluationCycleStatusProvider {

    //현재 회사에서 진행 중(IN_PROGRESS)인 평가 회차가 존재하는지 여부
    boolean existsInProgressCycle(Long companyId);
}
