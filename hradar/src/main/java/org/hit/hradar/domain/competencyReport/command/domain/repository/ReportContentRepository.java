package org.hit.hradar.domain.competencyReport.command.domain.repository;

import java.util.List;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ReportContent;

public interface ReportContentRepository {

  void saveAll(List<ReportContent> reportContents);
}
