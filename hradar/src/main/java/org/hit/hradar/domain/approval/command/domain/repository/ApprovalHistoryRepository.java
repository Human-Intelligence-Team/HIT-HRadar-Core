package org.hit.hradar.domain.approval.command.domain.repository;

import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalHistory;

public interface ApprovalHistoryRepository {

  ApprovalHistory save(ApprovalHistory history);

}
