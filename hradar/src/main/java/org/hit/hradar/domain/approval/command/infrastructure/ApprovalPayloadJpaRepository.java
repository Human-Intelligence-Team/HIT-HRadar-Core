package org.hit.hradar.domain.approval.command.infrastructure;

import java.util.Optional;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalPayload;
import org.hit.hradar.domain.approval.command.domain.repository.ApprovalPayloadRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalPayloadJpaRepository extends JpaRepository<ApprovalPayload, Long>,
    ApprovalPayloadRepository {

  Optional<ApprovalPayload> findByDocId(Long docId);

}
