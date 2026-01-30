package org.hit.hradar.domain.leave.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.leave.LeaveErrorCode;
import org.hit.hradar.domain.leave.command.application.dto.request.LeavePolicyCreateRequest;
import org.hit.hradar.domain.leave.command.domain.aggregate.LeavePolicy;
import org.hit.hradar.domain.leave.command.infrastructure.LeavePolicyJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LeavePolicyCommandService {

  private final LeavePolicyJpaRepository leavePolicyJpaRepository;

  public void create(
      Long companyId,
      LeavePolicyCreateRequest request
  ) {
    if (leavePolicyJpaRepository.existsByCompanyIdAndTypeCodeAndUnitCode(
        companyId,
        request.getTypeCode(),
        request.getUnitCode()
    )) {
      throw new BusinessException(LeaveErrorCode.LEAVE_POLICY_DUPLICATE);
    }

    LeavePolicy policy = LeavePolicy.builder()
        .companyId(companyId)
        .typeCode(request.getTypeCode())
        .typeName(request.getTypeName())
        .unitCode(request.getUnitCode())
        .unitDays(request.getUnitDays())
        .isActive('Y')
        .build();

    leavePolicyJpaRepository.save(policy);
  }
}

