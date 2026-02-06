package org.hit.hradar.domain.salary.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryHistoryDTO;
import org.hit.hradar.domain.salary.query.dto.BasicSalarySummaryDTO;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalDTO;
import org.hit.hradar.domain.salary.query.dto.request.BasicSalarySearchRequest;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalRequest;

@Mapper
public interface BasicSalaryMapper {

  List<BasicSalaryDTO> findAllBasicSalaries(BasicSalarySearchRequest request);

  List<BasicSalaryDTO> findAllBasicSalariesByEmpId(SalaryApprovalRequest request);

  BasicSalaryDTO findBasicSalarySummaryByEmpIdAndYear(
      @Param("empId") Long empId,
      @Param("year") String year);

  List<BasicSalaryHistoryDTO> findAllBasicSalariesHistoryByEmpId(Long empId);

  BasicSalaryDTO findEmployeeBasicSalaryByEmpIdAndYear(
      @Param("empId") Long empId,
      @Param("year") String year);

  List<SalaryApprovalDTO> findAllByBasicSalary(SalaryApprovalRequest request);
  SalaryApprovalDTO findAllByBasicSalaryByDocId(SalaryApprovalRequest request);

  List<BasicSalarySummaryDTO> findBasicSalarySummaryByYear(SalaryApprovalRequest request);
}
