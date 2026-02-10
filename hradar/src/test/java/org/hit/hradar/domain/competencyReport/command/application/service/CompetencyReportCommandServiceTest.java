package org.hit.hradar.domain.competencyReport.command.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.List;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CompetencyReportCreateRequest;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.CompetencyReport;
import org.hit.hradar.domain.competencyReport.command.domain.repository.CompetencyReportRepository;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ReportContentRepository;
import org.hit.hradar.domain.competencyReport.gemini.dto.OutputResultDTO;
import org.hit.hradar.domain.competencyReport.gemini.service.GeminiService;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalType;
import org.hit.hradar.domain.goal.query.dto.response.CyclePeriodGoalsRow;
import org.hit.hradar.domain.goal.query.service.provider.GoalProviderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompetencyReportCommandServiceTest {
  @InjectMocks
  private CompetencyReportCommandService competencyReportCommandService;

  @Mock
  private GoalProviderService goalProviderService;

  @Mock
  private GeminiService geminiService;

  @Mock
  private CompetencyReportRepository competencyReportRepository;

  @Mock
  private ReportContentRepository reportContentRepository;

  @Test
  @DisplayName("리포트 생성 성공: 목표 데이터를 조회하여 AI 분석 후 리포트와 추천 콘텐츠를 저장한다.")
  void createReport_Success() {
    // given
    Long comId = 1L;
    CompetencyReportCreateRequest request = new CompetencyReportCreateRequest(
        10L, "2024-01-01", "2024-03-31"
    );

    // 1. GoalProviderService 결과 Mocking
    CyclePeriodGoalsRow row = mock(CyclePeriodGoalsRow.class);
    given(row.getOwnerId()).willReturn(500L);
    given(row.getEmployeeName()).willReturn("홍길동");
    given(row.getType()).willReturn(GoalType.KPI);
    given(goalProviderService.getGoalsForCyclePeriod(any(), any())).willReturn(List.of(row));

    // given 절 내부 수정
    OutputResultDTO aiResult = new OutputResultDTO(
        row.getOwnerId(),             // 1. ownerId
        request.getCycleId(),         // 2. cycleId
        "성과 요약입니다.",             // 3. kpiOkrResultSummary
        "원인 분석입니다.",             // 4. goalFailureAnalysis
        List.of(new OutputResultDTO.ContentResultDTO(1L, "추천 이유")) // 5. contentRow
    );
    given(geminiService.getGeminiData(any())).willReturn(aiResult);

    // 3. Repository 저장 시 엔티티 Mocking (ID 반환을 위함)
    CompetencyReport savedReport = mock(CompetencyReport.class);
    given(savedReport.getCompetencyReportId()).willReturn(777L);
    given(competencyReportRepository.save(any(CompetencyReport.class))).willReturn(savedReport);

    // when
    //competencyReportCommandService.createReport(comId, request);

    // then
    // - 목표 데이터 조회가 일어났는가?
    then(goalProviderService).should(times(1)).getGoalsForCyclePeriod(any(), any());

    // - AI 서비스에 분석을 요청했는가?
    then(geminiService).should(times(1)).getGeminiData(any());

    // - 리포트(부모)가 저장되었는가?
    then(competencyReportRepository).should(times(1)).save(any(CompetencyReport.class));

    // - 추천 콘텐츠(자식)들이 저장되었는가?
    then(reportContentRepository).should(times(1)).saveAll(anyList());
  }
}