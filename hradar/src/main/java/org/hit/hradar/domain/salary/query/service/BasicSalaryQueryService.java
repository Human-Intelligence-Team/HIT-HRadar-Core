package org.hit.hradar.domain.salary.query.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.command.domain.repository.BasicSalaryRepository;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryHistoryDTO;
import org.hit.hradar.domain.salary.query.dto.request.BasicSalarySearchRequest;
import org.hit.hradar.domain.salary.query.dto.response.BasicSalaryHistoryResponse;
import org.hit.hradar.domain.salary.query.dto.response.BasicSalarySearchResponse;
import org.hit.hradar.domain.salary.query.mapper.BasicSalaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicSalaryQueryService {

  private final BasicSalaryMapper basicSalaryMapper;

  /**
   * 연봉 목록 조회(전체)
   * @return
   */
  public BasicSalarySearchResponse basicSalaries(
      BasicSalarySearchRequest request
  ) {

    List<BasicSalaryDTO> basicSalaries = basicSalaryMapper.findAllBasicSalaries(request);
    return new BasicSalarySearchResponse(basicSalaries);
  }

  /**
   * 연봉 목록 조회(본인)
   * @return
   */
  public BasicSalarySearchResponse getMyBasicSalaries(Long empId) {

    List<BasicSalaryDTO> basicSalaries = basicSalaryMapper.findAllBasicSalariesByEmpId(empId);
    return new BasicSalarySearchResponse(basicSalaries);
  }

  /**
   * 연봉 히스토리 (전년도/ 올해)
   * @param empId
   * @param year
   * @return
   */
  public BasicSalaryHistoryResponse getMyBasicSalarySummary(Long empId, String year) {

    // 전년도
    Integer prevYear = Integer.valueOf(year) - 1;
    String prevYearStr = prevYear.toString();

    // 올해/전년도 기본급 조회
    BasicSalaryDTO currentSalary = basicSalaryMapper.findBasicSalarySummaryByEmpIdAndYear(empId, year);
    BasicSalaryDTO prevSalary = basicSalaryMapper.findBasicSalarySummaryByEmpIdAndYear(empId, prevYearStr);

    Long currentBasicSalary = currentSalary != null ? currentSalary.getBasicSalary() : 0L;
    Long prevBasicSalary = prevSalary != null ? prevSalary.getBasicSalary() : 0L;

    BasicSalaryHistoryDTO result = new BasicSalaryHistoryDTO(
        empId,
        year,
        currentSalary != null ? currentSalary.getTitle() : null,
        prevBasicSalary,
        currentBasicSalary,
        currentSalary != null ? currentSalary.getIncreaseRate() : null,
        currentSalary != null ? currentSalary.getApprovedAt() : null
    );

    return new BasicSalaryHistoryResponse(result, null);
  }


  /**
   * 사원의 연봉 히스토리
   * @param empId
   * @return
   */
  public BasicSalaryHistoryResponse getBasicSalaryHistory(Long empId) {

    List<BasicSalaryHistoryDTO> history = basicSalaryMapper.findAllBasicSalariesHistoryByEmpId(empId);
    return new BasicSalaryHistoryResponse(null, history);
  }


  /**
   * 사원의 기본급
   * @param empId
   * @return
   */
  public BasicSalaryDTO getEmployeeBasicSalary(Long empId, String year) {

    BasicSalaryDTO basic = basicSalaryMapper.findEmployeeBasicSalaryByEmpIdAndYear(empId,year);
    return basic;
  }

}
