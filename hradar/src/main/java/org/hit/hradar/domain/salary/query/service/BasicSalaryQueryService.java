package org.hit.hradar.domain.salary.query.service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.command.domain.repository.BasicSalaryRepository;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryHistoryDTO;
import org.hit.hradar.domain.salary.query.dto.BasicSalarySummaryDTO;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalDTO;
import org.hit.hradar.domain.salary.query.dto.request.BasicSalarySearchRequest;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalRequest;
import org.hit.hradar.domain.salary.query.dto.response.BasicSalaryHistoryResponse;
import org.hit.hradar.domain.salary.query.dto.response.BasicSalarySearchResponse;
import org.hit.hradar.domain.salary.query.dto.response.SalaryApprovalResponse;
import org.hit.hradar.domain.salary.query.mapper.BasicSalaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicSalaryQueryService {

  private final BasicSalaryMapper basicSalaryMapper;

  /**
   * 연봉 결재 목록
   * @param comId
   * @param request
   * @return
   */
  public SalaryApprovalResponse approvedBasicSalaries(Long comId, SalaryApprovalRequest request) {
    // 1. 초기값 설정
    request.setComId(comId);
    String targetYearStr = request.getYear(); // 조회하고자 하는 기준 연도 보관
    int targetYearInt = Integer.parseInt(targetYearStr);

    // 2. 기본급 목록 조회 (기준 연도 데이터)
    List<SalaryApprovalDTO> salaries = basicSalaryMapper.findAllByBasicSalary(request);

    // 3. 날짜 범위 계산 (startDate, endDate)
    String startDate = targetYearStr + "-01-01";
    String endDate = targetYearStr + "-12-31";

    LocalDate today = LocalDate.now();
    LocalDate deadLine = LocalDate.of(targetYearInt, 12, 31);

    if (today.isAfter(deadLine) || today.getYear() == targetYearInt) {
      endDate = today.toString();
    }

    // 4. 최근 5년간 기본급 통계 조회
    List<BasicSalarySummaryDTO> summary = basicSalaryMapper.findBasicSalarySummaryByYear(request);

    return new SalaryApprovalResponse(salaries, startDate, endDate, summary);
  }
  /**
   * 연봉 목록 조회(전체)
   * @return
   */
  public BasicSalarySearchResponse basicSalaries(
      BasicSalarySearchRequest request, Long docId, Long comId
  ) {

    request.setDocId(docId);
    request.setComId(comId);
    List<BasicSalaryDTO> basicSalaries = basicSalaryMapper.findAllBasicSalaries(request);

    // 제목
    SalaryApprovalRequest dto = new SalaryApprovalRequest(comId, docId);
    SalaryApprovalDTO salaryApproval =  basicSalaryMapper.findAllByBasicSalaryByDocId(dto);


    return new BasicSalarySearchResponse(basicSalaries,salaryApproval);
  }

  /**
   * 연봉 목록 조회(본인)
   * @return
   */
  public BasicSalarySearchResponse getMyBasicSalaries(Long empId, Long comId, SalaryApprovalRequest request) {

    request.setComId(comId);
    request.setEmpId(empId);
    List<BasicSalaryDTO> basicSalaries = basicSalaryMapper.findAllBasicSalariesByEmpId(request);
    return new BasicSalarySearchResponse(basicSalaries, null);
  }

  /**
   * 연봉 히스토리 (전년도/ 올해)
   * @param empId
   * @param year
   * @return
   */
  public BasicSalaryHistoryResponse getMyBasicSalarySummary(Long empId, String year) {
    String prevYearStr = String.valueOf(Integer.parseInt(year) - 1);

    // Optional로 감싸서 null 처리를 우아하게 준비합니다.
    Optional<BasicSalaryDTO> currentOpt = Optional.ofNullable(basicSalaryMapper.findBasicSalarySummaryByEmpIdAndYear(empId, year));
    Optional<BasicSalaryDTO> prevOpt = Optional.ofNullable(basicSalaryMapper.findBasicSalarySummaryByEmpIdAndYear(empId, prevYearStr));

    Long currentBasicSalary = currentOpt.map(BasicSalaryDTO::getBasicSalary).orElse(0L);
    Long prevBasicSalary = prevOpt.map(BasicSalaryDTO::getBasicSalary).orElse(0L);

    BasicSalaryHistoryDTO result = new BasicSalaryHistoryDTO(
        empId,
        year,
        currentOpt.map(BasicSalaryDTO::getTitle).orElse("데이터 없음"),
        prevBasicSalary,
        currentBasicSalary,
        currentOpt.map(BasicSalaryDTO::getIncreaseRate).orElse(BigDecimal.valueOf(0.0)),
        currentOpt.map(BasicSalaryDTO::getApprovedAt).orElse(null),
        currentOpt.map(BasicSalaryDTO::getSalaryIncreaseType).orElse(null)
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
  public BasicSalaryDTO getEmployeeBasicSalary(Long empId, String year, Long comId) {

    BasicSalaryDTO basic = basicSalaryMapper.findEmployeeBasicSalaryByEmpIdAndYear(empId,year);
    return basic;
  }

}
