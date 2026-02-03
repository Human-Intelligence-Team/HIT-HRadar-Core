package org.hit.hradar.domain.salary.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.query.dto.response.CompensationSearchResponse;
import org.hit.hradar.domain.salary.query.dto.CompensationHistoryDTO;
import org.hit.hradar.domain.salary.query.dto.CompensationSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.request.CompensationSearchRequest;
import org.hit.hradar.domain.salary.query.dto.request.CompensationHistorySearchRequest;
import org.hit.hradar.domain.salary.query.dto.response.CompensationHistorySearchResponse;
import org.hit.hradar.domain.salary.query.dto.response.CompensationSummaryResponse;
import org.hit.hradar.domain.salary.query.mapper.CompensationSalaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompensationSalaryQueryService {

  private final CompensationSalaryMapper compensationSalaryMapper;

  /**
   * 변동 보상 히스토리 (본인)
   * @param empId
   * @param request
   * @return
   */
  public CompensationHistorySearchResponse getCompensationHistory(Long empId, CompensationHistorySearchRequest request) {

    // request 에 empId 추가
    request.setEmpId(empId);
    List<CompensationHistoryDTO> compensationSalaries = compensationSalaryMapper.findAllCompensationHistory(request);

    return new CompensationHistorySearchResponse(compensationSalaries);

  }

  /**
   * 사원의 변동보상 총합
   * @param empId
   * @param year
   * @return
   */
  public CompensationSalaryDTO getEmployeeCompensationSalarySummary(Long empId, String year) {

    String startDate = year + "-01-01";
    String endDate = year + "-12-31";
    CompensationSalaryDTO summary = compensationSalaryMapper.findCompensationSalaries(startDate, endDate, empId);

    return summary;
  }


  /**
   * 변동 보상 내역 조회 (전체)
   * @param request
   * @return
   */
  public CompensationSearchResponse compensationSalaries(CompensationSearchRequest request) {

    List<CompensationSalaryDTO> compensationSalaries = compensationSalaryMapper.findAllCompensationSalaries(request);
    return new CompensationSearchResponse(compensationSalaries);
  }

  /**
   * 변동 보상 총 금액 요약
   * @param request
   * @return
   */
  public CompensationSummaryResponse getCompensationSalariesSummary(CompensationSearchRequest request) {

    // 날짜 데이터
    String endDate = request.getEndDate();
    String startDate = endDate.split("-")[0] + "-01-01";

    CompensationSalaryDTO summary = compensationSalaryMapper.findCompensationSalaries(startDate, endDate, null);
    return new CompensationSummaryResponse(startDate, endDate, summary);

  }
}
