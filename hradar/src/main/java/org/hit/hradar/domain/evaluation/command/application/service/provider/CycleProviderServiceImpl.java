package org.hit.hradar.domain.evaluation.command.application.service.provider;

import lombok.AllArgsConstructor;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CycleProviderServiceImpl implements CycleProviderService {

  public final CycleRepository cycleRepository;
  @Override
  public void fetchCompetencyReportGeneratedById(Long cycleId) {

    Cycle cycle = cycleRepository.findById(cycleId)
        .orElseThrow(() -> new BusinessException(EvaluationErrorCode.CYCLE_CONFIGURATION_NOT_ALLOWED));

    cycle.updateIsReportGenerated('Y');

  }

}
