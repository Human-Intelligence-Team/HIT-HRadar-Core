package org.hit.hradar.domain.employee.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.employee.query.dto.EmployeeResponse;
import org.hit.hradar.domain.employee.query.mapper.EmployeeQueryMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeQueryService {

  private final EmployeeQueryMapper employeeQueryMapper;

  public EmployeeResponse getById(Long comId, Long empId) {
    return employeeQueryMapper.findById(comId, empId)
        .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE));
  }

  public List<EmployeeResponse> list(Long comId, Long deptId, Long positionId) {
    return employeeQueryMapper.findList(comId, deptId, positionId);
  }
}
