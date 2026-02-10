package org.hit.hradar.domain.employee.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.query.dto.EmployeeMovementHistoryListResponse;
import org.hit.hradar.domain.employee.query.dto.EmployeeMovementHistoryResponse;
import org.hit.hradar.domain.employee.query.mapper.EmployeeMovementHistoryQueryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeMovementHistoryQueryService {

  private final EmployeeMovementHistoryQueryMapper mapper;

  @Transactional(readOnly = true)
  public EmployeeMovementHistoryListResponse getHistory(Long comId, Long empId) {
    List<EmployeeMovementHistoryResponse> list = mapper.findByCompanyAndEmpId(comId, empId);

    return EmployeeMovementHistoryListResponse.of(list);
  }

  @Transactional(readOnly = true)
  public EmployeeMovementHistoryListResponse getAllHistories(Long companyId) {
    // Mapper의 메서드명과 일치하게 호출
    List<EmployeeMovementHistoryResponse> histories = mapper.findAllByCompanyId(companyId);

    return EmployeeMovementHistoryListResponse.of(histories);
  }
}
