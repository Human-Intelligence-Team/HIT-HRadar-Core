package org.hit.hradar.domain.goal.query.service.provider;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.CyclePeriodGoalsRow;
import org.hit.hradar.domain.goal.query.mapper.GoalMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoalProviderServiceImpl implements GoalProviderService {

  private final GoalMapper goalMapper;

  @Override
  public List<CyclePeriodGoalsRow> getGoalsForCyclePeriod(LocalDate startDate, LocalDate endDate){
    return goalMapper.findAllByCyclePeriod(startDate,endDate);
  }

}
