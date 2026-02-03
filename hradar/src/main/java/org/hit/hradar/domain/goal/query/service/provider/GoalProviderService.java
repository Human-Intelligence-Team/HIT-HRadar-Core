package org.hit.hradar.domain.goal.query.service.provider;


import java.time.LocalDate;
import java.util.List;
import org.hit.hradar.domain.goal.query.dto.response.CyclePeriodGoalsRow;

public interface GoalProviderService {

  List<CyclePeriodGoalsRow> getGoalsForCyclePeriod(LocalDate startDate, LocalDate endDate);

}
