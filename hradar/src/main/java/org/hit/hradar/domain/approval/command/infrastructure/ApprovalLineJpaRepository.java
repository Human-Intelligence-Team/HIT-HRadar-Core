package org.hit.hradar.domain.approval.command.infrastructure;

import java.util.Optional;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalLineJpaRepository extends JpaRepository<ApprovalLine, Long> {

  Optional<ApprovalLine> findByDocId(Long docId);

}
