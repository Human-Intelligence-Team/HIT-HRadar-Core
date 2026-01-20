package org.hit.hradar.domain.salary.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.SalaryDTO;
import org.hit.hradar.domain.salary.query.dto.request.SalarySearchRequest;

@Mapper
public interface SalaryMapper {

  List<SalaryDTO> findAllSalaries(SalarySearchRequest request);

  List<SalaryDTO> findAllSalariesByEmpId(Long empId);

  BasicSalaryDTO findBasicSalaryByEmpIdAndYear(
      @Param("empId") Long empId,
      @Param("year") String year);
}
