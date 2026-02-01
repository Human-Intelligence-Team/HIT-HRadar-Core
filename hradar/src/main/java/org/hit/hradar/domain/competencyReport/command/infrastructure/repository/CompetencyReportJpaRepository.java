package org.hit.hradar.domain.competencyReport.command.infrastructure.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggregate.CompetencyReport;
import org.hit.hradar.domain.competencyReport.command.domain.repository.CompetencyReportRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetencyReportJpaRepository extends CompetencyReportRepository ,
    JpaRepository<CompetencyReport, Integer> {

}
