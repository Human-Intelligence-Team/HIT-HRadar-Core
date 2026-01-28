package org.hit.hradar.domain.me.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.me.dto.MeResponse;
import org.hit.hradar.domain.me.mapper.EmployeeMeQueryMapper;
import org.hit.hradar.global.dto.AuthUser;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.domain.user.UserErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeMeQueryService {

  private final EmployeeMeQueryMapper employeeMeQueryMapper;

  public MeResponse getMe(AuthUser authUser) {
    Long comId = authUser.companyId();
    Long empId = authUser.employeeId();

    if (comId == null || empId == null) {
      throw new BusinessException(UserErrorCode.FORBIDDEN);
    }

    MeResponse profile = employeeMeQueryMapper.findMe(comId, empId)
        .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE));

    return MeResponse.builder()
        .userId(authUser.userId())
        .role(authUser.role())

        .comId(profile.getComId())
        .companyCode(profile.getCompanyCode())
        .companyName(profile.getCompanyName())

        .empId(profile.getEmpId())
        .employeeName(profile.getEmployeeName())
        .employeeNo(profile.getEmployeeNo())
        .email(profile.getEmail())

        .deptId(profile.getDeptId())
        .deptName(profile.getDeptName())

        .positionId(profile.getPositionId())
        .positionName(profile.getPositionName())

        .createdAt(profile.getCreatedAt())
        .updatedAt(profile.getUpdatedAt())
        .build();
  }
}
