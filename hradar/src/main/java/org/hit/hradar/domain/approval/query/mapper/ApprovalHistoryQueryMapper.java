package org.hit.hradar.domain.approval.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalWithdrawHistoryResponse;

@Mapper
public interface ApprovalHistoryQueryMapper {

  ApprovalWithdrawHistoryResponse selectWithdrawHistory(Long docId);
}
