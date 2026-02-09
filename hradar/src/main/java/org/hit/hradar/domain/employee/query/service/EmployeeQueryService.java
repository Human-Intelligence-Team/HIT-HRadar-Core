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

    Long deptId = request.getDeptId(); // No null check needed if request object exists, simplified
    Long positionId = request.getPositionId();
    String name = request.getEmployeeName();
    String keyword = request.getKeyword();

    // 1. Total Count
    long totalCount = employeeQueryMapper.countList(comId, deptId, positionId, name, keyword);

    // 2. Data List with Pagination
    List<EmployeeResponse> employees = employeeQueryMapper.findList(
        comId, deptId, positionId, name, keyword,
        request.getOffset(), request.getSize());

    return EmployeeListResponse.of(employees, totalCount, request.getSize());
  }
}
