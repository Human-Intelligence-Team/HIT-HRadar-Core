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
        // 부모(리포트) 저장
        CompetencyReport savedReport = competencyReportRepository.save(report);

        // 자식(추천 콘텐츠) 매핑 및 저장
        if (contents != null && !contents.isEmpty()) {

            contents.forEach(content -> content.updateReportId(savedReport.getCompetencyReportId()));
            reportContentRepository.saveAllIfNotEmpty(contents);
        }
        return savedReport.getCompetencyReportId();
    }
}
