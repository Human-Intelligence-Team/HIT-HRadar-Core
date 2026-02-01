package org.hit.hradar.domain.competencyReport.command.application.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.KpiDataDTO;
import org.hit.hradar.domain.competencyReport.command.application.dto.OkrDataDTO;
import org.hit.hradar.domain.competencyReport.command.application.dto.PersonalCompetencySourceDTO;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CompetencyReportCreateRequest;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.CompetencyReport;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ReportContent;
import org.hit.hradar.domain.competencyReport.command.domain.repository.CompetencyReportRepository;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ReportContentRepository;
import org.hit.hradar.domain.competencyReport.gemini.dto.OutputResultDTO;
import org.hit.hradar.domain.competencyReport.gemini.service.GeminiService;
import org.hit.hradar.domain.goal.query.dto.response.CyclePeriodGoalsRow;
import org.hit.hradar.domain.goal.query.service.provider.GoalProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompetencyReportCommandService {

  private final GoalProviderService goalProviderService;
  private final GeminiService geminiService;
  private final CompetencyReportRepository competencyReportRepository;
  private final ReportContentRepository reportContentRepository;

  /**
   * 역량 강화 리포트 생성
   * @param comId
   */
  @Transactional
  public void createReport(Long comId, CompetencyReportCreateRequest request) {

    // setting
    Long cycleId = request.getCycleId(); // 회차
    LocalDate start = LocalDate.parse(request.getStartDate()); // 시작일
    LocalDate end = LocalDate.parse(request.getEndDate());  // 종료일

    // 시작일 종료일에 맞춰  okr/ kpi의 종료일에 맞춰서 가져오기!
    List<CyclePeriodGoalsRow> rows = goalProviderService.getGoalsForCyclePeriod(start, end);

    // 사원에 맞춰 가공
    List<PersonalCompetencySourceDTO> sources =
        rows.stream()
            .collect(Collectors.groupingBy(CyclePeriodGoalsRow::getOwnerId))
            .entrySet()
            .stream()
            .map(entry -> {

              Long empId = entry.getKey();  // 사원
              List<CyclePeriodGoalsRow> employeeRows = entry.getValue();
              String employeeName = employeeRows.get(0).getEmployeeName();
              String deptName = employeeRows.get(0).getDeptName();
              String positionName = employeeRows.get(0).getPositionName();
              Long departmentId = employeeRows.get(0).getDepartmentId(); // 부서

              List<KpiDataDTO> kpiList = new ArrayList<>();
              List<OkrDataDTO> okrList = new ArrayList<>();

              employeeRows.forEach(f -> {
                switch (f.getType()) {
                  case KPI -> kpiList.add(kpiDataDTO(f));
                  case OKR -> okrList.add(okrDataDTO(f));
                }
              });

              return new PersonalCompetencySourceDTO(
                  comId,
                  empId,
                  employeeName,
                  departmentId,
                  deptName,
                  positionName,
                  cycleId,
                  start,
                  end,
                  kpiList,
                  okrList
              );
            })
            .toList();

    // llm으로 데이터 정리
    sources.forEach(source -> {
      // AI 분석 호출
      OutputResultDTO aiResult = geminiService.getGeminiData(source);
      // 부모(리포트) 저장
      CompetencyReport report = competencyReportRepository.save(new CompetencyReport(
          source.getOwnerId(),
          cycleId,
          start,
          end,
          aiResult.getKpiOkrResultSummary(),
          aiResult.getGoalFailureAnalysis(),
          null
      ));

      // 자식(추천 콘텐츠) 매핑 및 저장
      if (aiResult.getContentRow() != null && !aiResult.getContentRow().isEmpty()) {
        List<ReportContent> contents = aiResult.getContentRow().stream()
            .map(f -> new ReportContent(report.getCompetencyReportId(), f.getContentId(), f.getReason()))
            .toList();
        reportContentRepository.saveAll(contents);
      }
    });

  }

  private KpiDataDTO kpiDataDTO(CyclePeriodGoalsRow f ) {
    return new KpiDataDTO(
        f.getGoalId(),
        f.getParentGoalId(),
        f.getDepth(),
        f.getType(),
        f.getTitle(),
        f.getDescription(),
        f.getStartDate(),
        f.getEndDate(),
        f.getApproveStatus(),
        f.getProgressStatus(),
        f.getKpiMetricName(),
        f.getKpiStartValue(),
        f.getKpiTargetValue()
    );

  }

  private OkrDataDTO okrDataDTO(CyclePeriodGoalsRow f) {
    return new OkrDataDTO(
        f.getGoalId(),
        f.getParentGoalId(),
        f.getDepth(),
        f.getType(),
        f.getTitle(),
        f.getDescription(),
        f.getStartDate(),
        f.getEndDate(),
        f.getApproveStatus(),
        f.getProgressStatus(),
        f.getKeyResultContent(),
        f.getOkrMetricName(),
        f.getKeyTargetValue(),
        f.getIsAchieved()
    );
  }

}
