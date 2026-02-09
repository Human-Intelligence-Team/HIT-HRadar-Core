package org.hit.hradar.domain.approval.command.infrastructure;

import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalReference;
import org.hit.hradar.domain.approval.command.domain.repository.ApprovalReferenceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalReferenceJpaRepository
    extends JpaRepository<ApprovalReference, Long>, ApprovalReferenceRepository {

  void deleteByDocId(Long docId);

  java.util.List<org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalReference> findAllByDocId(Long docId);
}
