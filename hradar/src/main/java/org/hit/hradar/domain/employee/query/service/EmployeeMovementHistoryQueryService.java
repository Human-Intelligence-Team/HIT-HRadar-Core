package org.hit.hradar.domain.employee.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.query.dto.EmployeeMovementHistoryResponse;
import org.hit.hradar.domain.employee.query.mapper.EmployeeMovementHistoryQueryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeMovementHistoryQueryService {

  private final EmployeeMovementHistoryQueryMapper mapper;

  @Transactional(readOnly = true)
  public List<EmployeeMovementHistoryResponse> getHistory(Long comId, Long empId) {
    return mapper.findByCompanyAndEmpId(comId, empId);
  }
}
