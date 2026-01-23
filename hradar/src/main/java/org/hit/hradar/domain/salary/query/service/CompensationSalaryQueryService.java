package org.hit.hradar.domain.salary.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.query.dto.CompensationHistoryDTO;
import org.hit.hradar.domain.salary.query.dto.CompensationSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.request.CompensationHistorySearchRequest;
import org.hit.hradar.domain.salary.query.dto.response.CompensationHistorySearchResponse;
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
  public List<CompensationSalaryDTO> getEmployeeCompensationSalarySummary(Long empId, String year) {

    return compensationSalaryMapper.findEmployeeCompensationSalarySummary(empId, year);
  }


}
