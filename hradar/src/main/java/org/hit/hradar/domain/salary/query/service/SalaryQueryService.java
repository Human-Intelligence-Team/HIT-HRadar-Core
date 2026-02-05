package org.hit.hradar.domain.salary.query.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalDTO;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalRequest;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalTargetRequest;
import org.hit.hradar.domain.salary.query.dto.response.SalaryApprovalResponse;
import org.hit.hradar.domain.salary.query.dto.response.SalaryApprovalTargetResponse;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.CompensationSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalTargetDTO;
import org.hit.hradar.domain.salary.query.dto.response.AnnualCompensationSummaryResponse;
import org.hit.hradar.domain.salary.query.mapper.SalaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalaryQueryService {

  private final BasicSalaryQueryService basicSalaryQueryService;
  private final CompensationSalaryQueryService  compensationSalaryQueryService;
  private final SalaryMapper salaryMapper;

  /**
   * 사원 연간 총 연봉 조회
   *
   * @param empId
   * @return
   */
  public AnnualCompensationSummaryResponse getEmployeeAnnualSalarySummary(Long empId) {

    // 기본급 조회
    Long basic = 0L;
    int year = LocalDate.now().getYear() - 1;
    String yearStr = String.valueOf(year);
    BasicSalaryDTO basicSalary = basicSalaryQueryService.getEmployeeBasicSalary(empId, yearStr);
    if (basicSalary != null) {
      basicSalary.getBasicSalary();
    }

    // 변동 보상 조회 (각각 총 금액)
    CompensationSalaryDTO summary = compensationSalaryQueryService.getEmployeeCompensationSalarySummary(empId, yearStr);

    Long totalCompensation = summary == null ? 0L : summary.getTotalCompensation();
    Long totalBonus = summary == null ? 0L : summary.getTotalBonus();
    Long totalPerformance = summary == null ? 0L : summary.getTotalPerformance();
    Long totalIncentive = summary == null ? 0L : summary.getTotalIncentive();
    Long totalAllowance = summary == null ? 0L : summary.getTotalAllowance();

    Long salary = basic + totalCompensation;
    return new AnnualCompensationSummaryResponse(
        basic
        , totalBonus
        , totalPerformance
        , totalIncentive
        , totalAllowance
        , salary
    );
  }

  /**
   * 결재 사원 조회
   * @return
   */
  public SalaryApprovalTargetResponse getSalaryApprovalTargets(SalaryApprovalTargetRequest request) {

    List<SalaryApprovalTargetDTO> compensationSalary = salaryMapper.findSalaryApprovalTargets(request);
    return new SalaryApprovalTargetResponse(compensationSalary);
  }


}
