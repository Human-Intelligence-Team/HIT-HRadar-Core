package org.hit.hradar.domain.leave.query.service;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.leave.command.domain.aggregate.LeavePolicy;
import org.hit.hradar.domain.leave.command.infrastructure.LeavePolicyJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeavePolicyQueryService {

  private final LeavePolicyJpaRepository leavePolicyJpaRepository;

  public List<LeavePolicy> getPolicies(Long companyId) {
    List<LeavePolicy> list =
        leavePolicyJpaRepository.findByCompanyIdAndIsDeleted(companyId, 'N');

    return list == null ? Collections.emptyList() : list;
  }
}
