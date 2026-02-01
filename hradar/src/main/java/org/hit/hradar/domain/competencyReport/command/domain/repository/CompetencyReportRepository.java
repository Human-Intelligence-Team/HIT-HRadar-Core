package org.hit.hradar.domain.competencyReport.command.domain.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggregate.CompetencyReport;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetencyReportRepository {

  CompetencyReport save(CompetencyReport competencyReport);
}
