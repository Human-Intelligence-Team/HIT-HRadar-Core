package org.hit.hradar.domain.evaluation.command.application.provider;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleStatus;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.domain.grading.port.EvaluationCycleStatusProvider;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class EvaluationCycleStatusProviderImpl implements EvaluationCycleStatusProvider {

    private final CycleRepository cycleRepository;

    @Override
    public boolean existsInProgressCycle(Long companyId) {
        return cycleRepository.existsByCompanyIdAndStatus(
                companyId,
                CycleStatus.IN_PROGRESS
        );
    }
}
