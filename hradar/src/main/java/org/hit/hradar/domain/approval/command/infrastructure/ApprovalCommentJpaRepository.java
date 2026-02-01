package org.hit.hradar.domain.approval.command.infrastructure;

import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalComment;
import org.hit.hradar.domain.approval.command.domain.repository.ApprovalCommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalCommentJpaRepository
    extends JpaRepository<ApprovalComment, Long>, ApprovalCommentRepository {
}
