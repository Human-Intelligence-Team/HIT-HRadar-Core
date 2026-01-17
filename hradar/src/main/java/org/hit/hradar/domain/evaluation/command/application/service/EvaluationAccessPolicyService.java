package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleStatus;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationAccessPolicyService {

    /*
    * “이 사용자가 지금 이 평가 회차에 접근해도 되는가?”
        → 모든 진입점에서 공통으로 쓰는 Gatekeeper
    * OPEN일때만 통과
    */

    public void validateCycleIsOpen(Cycle cycle) {

        //삭제 여부
        if (cycle.getIsDeleted() == 'Y') {
            throw new BusinessException(EvaluationErrorCode.CYCLE_NOT_FOUND);
        }

        //OPEN 상태만 허용
        if (cycle.getStatus() != CycleStatus.IN_PROGRESS) {
            throw new BusinessException(EvaluationErrorCode.CYCLE_NOT_OPEN);
        }
    }

}
