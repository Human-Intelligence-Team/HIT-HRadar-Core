package org.hit.hradar.domain.approval.command.infrastructure;

import java.util.List;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalDocumentTypeJpaRepository
    extends JpaRepository<ApprovalDocument, Long> {

  List<ApprovalDocument> findByActiveTrueAndIsTemplateTrue();

}
