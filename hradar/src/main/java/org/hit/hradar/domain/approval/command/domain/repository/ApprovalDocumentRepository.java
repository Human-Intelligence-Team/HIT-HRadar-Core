package org.hit.hradar.domain.approval.command.domain.repository;

import java.util.Optional;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;

public interface ApprovalDocumentRepository {

  Optional<ApprovalDocument> findById(Long docId);

}
