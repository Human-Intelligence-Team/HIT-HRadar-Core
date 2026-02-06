package org.hit.hradar.domain.salary.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalDTO;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalTargetDTO;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalRequest;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalTargetRequest;

@Mapper
public interface SalaryMapper {

  List<SalaryApprovalTargetDTO> findSalaryApprovalTargets(SalaryApprovalTargetRequest request);




}
