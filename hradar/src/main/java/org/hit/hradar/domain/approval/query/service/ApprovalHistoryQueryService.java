package org.hit.hradar.domain.approval.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalWithdrawHistoryResponse;
import org.hit.hradar.domain.approval.query.mapper.ApprovalHistoryQueryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalHistoryQueryService {

  private final ApprovalHistoryQueryMapper approvalHistoryQueryMapper;

  public ApprovalWithdrawHistoryResponse findWithdrawHistory(Long docId) {
    return approvalHistoryQueryMapper.selectWithdrawHistory(docId);


  }
}
