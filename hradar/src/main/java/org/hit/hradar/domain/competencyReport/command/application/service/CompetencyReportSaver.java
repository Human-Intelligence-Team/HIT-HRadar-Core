package org.hit.hradar.domain.competencyReport.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.CompetencyReport;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ReportContent;
import org.hit.hradar.domain.competencyReport.command.domain.repository.CompetencyReportRepository;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ReportContentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetencyReportSaver {

    private final CompetencyReportRepository competencyReportRepository;
    private final ReportContentRepository reportContentRepository;

    @Transactional
    public Long saveReport(CompetencyReport report, List<ReportContent> contents) {
      // 1. 부모 저장
      CompetencyReport savedReport = competencyReportRepository.save(report);

      // 2. 자식 ID 매핑 및 저장
      if (contents != null && !contents.isEmpty()) {
        Long reportId = savedReport.getCompetencyReportId();
        contents.forEach(content -> content.updateReportId(reportId)); // 이 부분
        reportContentRepository.saveAllIfNotEmpty(contents);
      }

      return savedReport.getCompetencyReportId();
    }
}
