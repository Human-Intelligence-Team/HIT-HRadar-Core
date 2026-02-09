package org.hit.hradar.domain.competencyReport.command.application.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.competencyReport.command.application.dto.KpiDataDTO;
import org.hit.hradar.domain.competencyReport.command.application.dto.OkrDataDTO;
import org.hit.hradar.domain.competencyReport.command.application.dto.PersonalCompetencySourceDTO;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CompetencyReportCreateRequest;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.CompetencyReport;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ReportContent;
import org.hit.hradar.domain.competencyReport.gemini.dto.OutputResultDTO;
import org.hit.hradar.domain.competencyReport.gemini.service.GeminiService;
import org.hit.hradar.domain.evaluation.command.application.service.provider.CycleProviderService;
import org.hit.hradar.domain.goal.query.dto.response.CyclePeriodGoalsRow;
import org.hit.hradar.domain.goal.query.service.provider.GoalProviderService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetencyReportCommandService {

  private final GoalProviderService goalProviderService;
  private final GeminiService geminiService;
  private final CompetencyReportSaver competencyReportSaver;
  private final CycleProviderService  cycleProviderService;

  /**
   * 역량 강화 리포트 생성
   * 
   * @param comId
   */
  public void createReport(Long comId, CompetencyReportCreateRequest request) {
    System.out.println(" 생성 1");
    // setting
    Long cycleId = request.getCycleId(); // 회차
    LocalDate start = LocalDate.parse(request.getStartDate()); // 시작일
    LocalDate end = LocalDate.parse(request.getEndDate()); // 종료일
    System.out.println(" 생성 1 cycleId - " + cycleId);
    System.out.println(" 생성 1 start - " + start);
    System.out.println(" 생성 1 end - " + end);
    // 시작일 종료일에 맞춰 okr/ kpi의 종료일에 맞춰서 가져오기!
    List<CyclePeriodGoalsRow> rows = goalProviderService.getGoalsForCyclePeriod(start, end);
    System.out.println(" 생성 2 rows" + rows.size());
    // 사원에 맞춰 가공
    List<PersonalCompetencySourceDTO> sources = rows.stream()
        .collect(Collectors.groupingBy(CyclePeriodGoalsRow::getOwnerId))
        .entrySet()
        .stream()
        .map(entry -> {

          Long empId = entry.getKey(); // 사원
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
          System.out.println(" 생성 3");
          return new PersonalCompetencySourceDTO(
              comId, empId, employeeName, departmentId, deptName, positionName, cycleId,
              start, end, kpiList, okrList);
        })
        .toList();

    System.out.println(" 생성 4  sources" + sources.size());
    // llm으로 데이터 정리
    for (PersonalCompetencySourceDTO source : sources) {
      processSingleReport(source);
    }
    System.out.println(" 생성 5");

    // 역량강화 리포트 생성 여부 수정

    cycleProviderService.fetchCompetencyReportGeneratedById(cycleId);
    System.out.println(" 생성 6");

  }

  private void processSingleReport(PersonalCompetencySourceDTO source) {
    // AI 분석 호출 (DB 트랜잭션 없이 실행)
    OutputResultDTO aiResult = geminiService.getGeminiData(source);

    // 부모(리포트) 객체 생성
    CompetencyReport report = new CompetencyReport(
        source.getOwnerId(),
        source.getCycleId(),
        source.getStartDate(),
        source.getEndDate(),
        aiResult.getKpiOkrResultSummary(),
        aiResult.getGoalFailureAnalysis(),
        null);

    // 자식(추천 콘텐츠) 매핑
    List<ReportContent> contents = new ArrayList<>();
    if (aiResult.getContentRow() != null && !aiResult.getContentRow().isEmpty()) {
      contents = aiResult.getContentRow().stream()
          .map(f -> new ReportContent(null, f.getContentId(), f.getReason()))
          .toList();
    }

    // 트랜잭션 저장 위임
    competencyReportSaver.saveReport(report, contents);

  }

  private KpiDataDTO kpiDataDTO(CyclePeriodGoalsRow f) {
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
        f.getKpiTargetValue());

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
        f.getIsAchieved());
  }

}
