package org.hit.hradar.domain.approval.command.domain.repository;

import java.util.Optional;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLine;

public interface ApprovalLineRepository {

  Optional<ApprovalLine> findByDocId(Long docId);

}
