package org.hit.hradar.domain.salary.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.salary.query.dto.CompensationHistoryDTO;
import org.hit.hradar.domain.salary.query.dto.request.CompensationHistorySearchRequest;

@Mapper
public interface CompensationSalaryMapper {

  List<CompensationHistoryDTO> findAllCompensationHistory(CompensationHistorySearchRequest request);

}
