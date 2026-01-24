package org.hit.hradar.domain.approval.command.infrastructure;

import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalReferenceJpaRepository
    extends JpaRepository<ApprovalReference, Long> {

  void deleteByDocId(Long docId);

}
