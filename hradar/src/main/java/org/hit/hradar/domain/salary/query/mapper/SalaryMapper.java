package org.hit.hradar.domain.salary.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.salary.query.dto.SalaryDTO;
import org.hit.hradar.domain.salary.query.dto.request.SalarySearchRequest;

@Mapper
public interface SalaryMapper {

  List<SalaryDTO> findAllSalaries(SalarySearchRequest request);

  List<SalaryDTO> findAllSalariesByEmpId(Long empId);
}
