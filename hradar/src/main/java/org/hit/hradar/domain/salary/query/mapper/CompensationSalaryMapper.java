package org.hit.hradar.domain.salary.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.salary.query.dto.CompensationHistoryDTO;
import org.hit.hradar.domain.salary.query.dto.CompensationSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalDTO;
import org.hit.hradar.domain.salary.query.dto.request.CompensationSearchRequest;
import org.hit.hradar.domain.salary.query.dto.request.CompensationHistorySearchRequest;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalRequest;

@Mapper
public interface CompensationSalaryMapper {

  List<CompensationHistoryDTO> findAllCompensationHistory(CompensationHistorySearchRequest request);

  List<CompensationSalaryDTO> findAllCompensationSalaries(CompensationSearchRequest request);

  CompensationSalaryDTO findCompensationSalaries(CompensationSearchRequest request);

  List<SalaryApprovalDTO> findAllByCompensationSalaries(SalaryApprovalRequest request);

  SalaryApprovalDTO findAllByCompensationSalariesByDocId (SalaryApprovalRequest request);
}
