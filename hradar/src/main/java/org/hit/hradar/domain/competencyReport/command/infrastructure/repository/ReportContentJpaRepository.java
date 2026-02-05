package org.hit.hradar.domain.competencyReport.command.infrastructure.repository;

import java.util.List;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentTag;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ReportContent;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ReportContentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportContentJpaRepository extends ReportContentRepository,
    JpaRepository<ReportContent, Integer> {

  default void saveAllIfNotEmpty(List<ReportContent> reportContents) {
    if (reportContents != null && !reportContents.isEmpty()) {
      this.saveAll(reportContents);
    }
  }

}
