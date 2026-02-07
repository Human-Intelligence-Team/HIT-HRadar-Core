package org.hit.hradar.domain.salary.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalDTO;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalRequest;
import org.hit.hradar.domain.salary.query.dto.response.CompensationSearchResponse;
import org.hit.hradar.domain.salary.query.dto.CompensationHistoryDTO;
import org.hit.hradar.domain.salary.query.dto.CompensationSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.request.CompensationSearchRequest;
import org.hit.hradar.domain.salary.query.dto.request.CompensationHistorySearchRequest;
import org.hit.hradar.domain.salary.query.dto.response.CompensationHistorySearchResponse;
import org.hit.hradar.domain.salary.query.dto.response.CompensationSummaryResponse;
import org.hit.hradar.domain.salary.query.dto.response.SalaryApprovalResponse;
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
  public CompensationHistorySearchResponse getCompensationHistory(Long empId, CompensationHistorySearchRequest request, Long comId) {

    // request 에 empId 추가
    request.setEmpId(empId);
    request.setComId(comId);
    List<CompensationHistoryDTO> compensationSalaries = compensationSalaryMapper.findAllCompensationHistory(request);

    return new CompensationHistorySearchResponse(compensationSalaries);

  }

  /**
   * 사원의 변동보상 총합
   * @param empId
   * @param year
   * @return
   */
  public CompensationSalaryDTO getEmployeeCompensationSalarySummary(Long empId, String year, Long comId) {

    String startDate = year + "-01-01";
    String endDate = year + "-12-31";

    CompensationSearchRequest request = new CompensationSearchRequest(empId, comId, startDate, endDate);
    CompensationSalaryDTO summary = compensationSalaryMapper.findCompensationSalaries(request);

    return summary;
  }


  /**
   * 변동 보상 내역 조회 (전체)
   * @param request
   * @return
   */
  public CompensationSearchResponse compensationSalaries(CompensationSearchRequest request, Long docId, Long comId) {

    request.setDocId(docId);
    request.setComId(comId);
    List<CompensationSalaryDTO> compensationSalaries = compensationSalaryMapper.findAllCompensationSalaries(request);

    // 제목
    SalaryApprovalRequest dto = new SalaryApprovalRequest(comId, docId);
    SalaryApprovalDTO salaryApproval =  compensationSalaryMapper.findAllByCompensationSalariesByDocId(dto);

    return new CompensationSearchResponse(compensationSalaries, salaryApproval);
  }

  /**
   * 변동 보상 총 금액 요약
   * @param request
   * @return
   */
  public CompensationSummaryResponse getCompensationSalariesSummary(CompensationSearchRequest request, Long comId) {

    // 날짜 데이터
    String endDate = request.getEndDate();
    String startDate = endDate.split("-")[0] + "-01-01";

    request.setStartDate(startDate);
    request.setEndDate(endDate);
    request.setComId(comId);

    CompensationSalaryDTO summary = compensationSalaryMapper.findCompensationSalaries(request);
    if (summary == null) {
      summary = new CompensationSalaryDTO(0L,0L,0L,0L,0L, 0L);
    }
    return new CompensationSummaryResponse(startDate, endDate, summary);

  }

  /**
   * 변동보상 결재 목록 조회
   *
   * @return
   */
  public SalaryApprovalResponse approvedCompensationSalaries(Long comId, SalaryApprovalRequest request) {
    request.setComId(comId);
    List<SalaryApprovalDTO> salaries = compensationSalaryMapper.findAllByCompensationSalaries(request);


    return new SalaryApprovalResponse(salaries);
  }
}
