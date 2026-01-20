package org.hit.hradar.domain.salary.query.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.command.domain.repository.BasicSalaryRepository;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.SalaryDTO;
import org.hit.hradar.domain.salary.query.dto.SalaryHistoryDTO;
import org.hit.hradar.domain.salary.query.dto.request.SalarySearchRequest;
import org.hit.hradar.domain.salary.query.dto.response.SalarySearchResponse;
import org.hit.hradar.domain.salary.query.mapper.SalaryMapper;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalaryService {

  private final SalaryMapper salaryMapper;
  private final BasicSalaryRepository  basicSalaryRepository;

  /**
   * 연봉 목록 조회(전체)
   * @return
   */
  public SalarySearchResponse salaries(
      SalarySearchRequest request
  ) {

    List<SalaryDTO> salaries = salaryMapper.findAllSalaries(request);
    return new SalarySearchResponse(salaries);
  }

  /**
   * 연봉 목록 조회(본인)
   * @return
   */
  public SalarySearchResponse getMySalaries() {

    // userId 가져오기
    Long empId = 1L;

    List<SalaryDTO> salaries = salaryMapper.findAllSalariesByEmpId(empId);
    return new SalarySearchResponse(salaries);
  }

  /**
   * 연봉 히스토리 (전년도/ 올해)
   * @param authUser
   * @param year
   * @return
   */
  public SalaryHistoryDTO getMySalaryHistory(AuthUser authUser, String year) {

    // auth User 확인(?)
    //Long empId = authUser.employeeId();
    Long empId = 1001L;

    // 전년도, 올해 연봉 정보
    Integer prevYear = Integer.valueOf(year) - 1;
    String prevYearStr = prevYear.toString();

    BasicSalaryDTO prevSalary = basicSalaryRepository.findByEmpIdAndYear(empId, prevYearStr);
    //BasicSalaryDTO currentSalary =




    return new SalaryHistoryDTO();
  }
}
