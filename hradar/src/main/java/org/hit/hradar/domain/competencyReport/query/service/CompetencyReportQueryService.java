package org.hit.hradar.domain.competencyReport.query.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.CompetencyReportDTO;
import org.hit.hradar.domain.competencyReport.query.dto.ContentDTO;
import org.hit.hradar.domain.competencyReport.query.dto.ContentRowDTO;
import org.hit.hradar.domain.competencyReport.query.dto.CycleDTO;
import org.hit.hradar.domain.competencyReport.query.dto.TagDTO;
import org.hit.hradar.domain.competencyReport.query.dto.request.CompetencyReportSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.request.CompReportCycleSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.response.CompetencyReportDetailResponse;
import org.hit.hradar.domain.competencyReport.query.dto.response.CompetencyReportSearchResponse;
import org.hit.hradar.domain.competencyReport.query.dto.response.CycleSearchResponse;
import org.hit.hradar.domain.competencyReport.query.mapper.CompetencyReportMapper;
import org.hit.hradar.domain.competencyReport.query.mapper.ContentMapper;
import org.hit.hradar.domain.competencyReport.query.service.support.CommonQueryService;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.query.service.provider.EmployeeProviderService;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetencyReportQueryService {

  private final CompetencyReportMapper competencyReportMapper;
  private final ContentMapper contentMapper;
  private final EmployeeProviderService employeeProviderService;
  private final CommonQueryService commonQueryService;
  /**
   * 역량 강화 리포트 목록 조회 (본인)
   * @param request
   * @return
   */
  public CompetencyReportSearchResponse getMyCompetencyReport(
      AuthUser authUser, CompetencyReportSearchRequest request) {

    // 나중에 userId 가져오기
   // Long userId = 1L;

    Long empId = authUser.empId();

    List<CompetencyReportDTO> reports = competencyReportMapper.findAllByEmpId(empId, request);
    return new  CompetencyReportSearchResponse(reports);
  }

  /**
   * 역량 강화 리포트 목록 조회 (부서)
   * @param request
   * @return
   */
  public CompetencyReportSearchResponse getCompetencyReportByDeptId(CompetencyReportSearchRequest request) {

    // 나중에 userId 가져오기
    Long userId = 1L;

    // userId의 depthId를 가져오기
    Employee user = employeeProviderService.getEmployee(userId);
    request.setDeptId(user.getDeptId());

    List<CompetencyReportDTO> reports = competencyReportMapper.findAllByDepthId(request);
    return new CompetencyReportSearchResponse(reports);

  }

  /**
   * 역량 강화 회차 목록(회차)
   * @param request
   * @return
   */
  public CycleSearchResponse getCycles(CompReportCycleSearchRequest request) {

    List<CycleDTO> cycles = competencyReportMapper.findAllCycle(request);
    return new CycleSearchResponse(cycles);
  }

  /**
   * 역량 강화 리포트 목록 (전체)
   * @param request
   * @return
   */
  public CompetencyReportSearchResponse getCompetencyReportsByAll(CompReportCycleSearchRequest request) {

    List<CompetencyReportDTO> reports = competencyReportMapper.findAllByCycleId(request);
    return new CompetencyReportSearchResponse(reports);
  }

  /**
   * 역량 강화 리포트 상세
   * @param id
   * @return
   */
  public CompetencyReportDetailResponse getCompetencyReportsById(Long id) {

    Long competencyReportId = id;

    // 회차, 사원정보, kpi/okr, 등급 평가 내용
    CompetencyReportDTO report = competencyReportMapper.findByCompetencyReportId(competencyReportId);

    // 학습 컨텐츠, 태그 리스트
    List<ContentRowDTO>  contentAndTagRows = contentMapper.findContentByCompetencyReportId(competencyReportId);

    // 학습 컨텐츠 변환
    List<ContentDTO> result = commonQueryService.getContents(contentAndTagRows);

    return new CompetencyReportDetailResponse(report, result);
  }


}
