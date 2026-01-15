package org.hit.hradar.domain.competencyReport.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.CompetencyReportDTO;
import org.hit.hradar.domain.competencyReport.query.dto.CycleDTO;
import org.hit.hradar.domain.competencyReport.query.dto.request.CompetencyReportSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.request.CompReportCycleSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.response.CompetencyReportSearchResponse;
import org.hit.hradar.domain.competencyReport.query.dto.response.CycleSearchResponse;
import org.hit.hradar.domain.competencyReport.query.mapper.CompetencyReportMapper;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.query.service.provider.EmployeeProviderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetencyReportQueryService {

  private final CompetencyReportMapper competencyReportMapper;
  private final EmployeeProviderService employeeProviderService;
  /**
   * 역량 강화 리포트 목록 조회 (사원)
   * @param request
   * @return
   */
  public CompetencyReportSearchResponse getCompetencyReportsByEmployee(CompetencyReportSearchRequest request) {

    // 나중에 userId 가져오기
    Long userId = 1L;

    List<CompetencyReportDTO> reports = competencyReportMapper.findAllByUserId(userId, request);
    return new  CompetencyReportSearchResponse(reports);
  }

  /**
   * 역량 강화 리포트 목록 조회 (팀장)
   * @param request
   * @return
   */
  public CompetencyReportSearchResponse getCompetencyReportsByLeader(CompetencyReportSearchRequest request) {

    // 나중에 userId 가져오기
    Long userId = 1L;

    // userId의 depthId를 가져오기
    Employee user = employeeProviderService.getEmployee(userId);
    request.setDeptId(user.getDeptId());

    List<CompetencyReportDTO> reports = competencyReportMapper.findAllByDepthId(request);
    return new CompetencyReportSearchResponse(reports);

  }

  /**
   * 역량 강화 회차 목록(인사팀)
   * @param request
   * @return
   */
  public CycleSearchResponse getCycles(CompReportCycleSearchRequest request) {

    List<CycleDTO> cycles = competencyReportMapper.findAllCycle(request);
    return new CycleSearchResponse(cycles);
  }

  /**
   * 역량 강화 리포트 목록 (인사팀)
   * @param request
   * @return
   */
  public CompetencyReportSearchResponse getCompetencyReportsByHr(CompReportCycleSearchRequest request) {

    List<CompetencyReportDTO> reports = competencyReportMapper.findAllByCycleId(request);
    return new CompetencyReportSearchResponse(reports);
  }
}
