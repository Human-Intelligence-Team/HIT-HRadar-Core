package org.hit.hradar.domain.salary.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryHistoryDTO;
import org.hit.hradar.domain.salary.query.dto.request.BasicSalarySearchRequest;

@Mapper
public interface BasicSalaryMapper {

  List<BasicSalaryDTO> findAllBasicSalaries(BasicSalarySearchRequest request);

  List<BasicSalaryDTO> findAllBasicSalariesByEmpId(Long empId);

  BasicSalaryDTO findBasicSalarySummaryByEmpIdAndYear(
      @Param("empId") Long empId,
      @Param("year") String year);

  List<BasicSalaryHistoryDTO> findAllBasicSalariesHistoryByEmpId(Long empId);
}
