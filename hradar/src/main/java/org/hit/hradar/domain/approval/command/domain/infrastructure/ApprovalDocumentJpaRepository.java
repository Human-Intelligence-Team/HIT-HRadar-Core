package org.hit.hradar.domain.approval.command.domain.infrastructure;

import java.util.Optional;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalDocumentJpaRepository extends JpaRepository<ApprovalDocument, Long> {

  Optional<ApprovalDocument> findById(Long docId);

}
