package org.hit.hradar.domain.salary.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.salary.query.dto.CompensationHistoryDTO;
import org.hit.hradar.domain.salary.query.dto.CompensationSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.request.CompensationHistorySearchRequest;

@Mapper
public interface CompensationSalaryMapper {

  List<CompensationHistoryDTO> findAllCompensationHistory(CompensationHistorySearchRequest request);

  List<CompensationSalaryDTO> findEmployeeCompensationSalarySummary(
      @Param("empId") Long empId,
      @Param("year") String year);

}
