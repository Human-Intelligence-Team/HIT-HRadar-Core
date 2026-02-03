package org.hit.hradar.domain.employee.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.employee.query.dto.EmployeeListRequest;
import org.hit.hradar.domain.employee.query.dto.EmployeeListResponse;
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

  public EmployeeListResponse list(Long comId, EmployeeListRequest request) {

    Long deptId = null;
    Long positionId = null;

    if (request != null) {
      deptId = request.getDeptId();
      positionId = request.getPositionId();
    }

    List<EmployeeResponse> employees = employeeQueryMapper.findList(comId, deptId, positionId, request.getEmployeeName());
    return EmployeeListResponse.of(employees);
  }
}
