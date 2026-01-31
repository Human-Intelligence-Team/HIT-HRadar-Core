package org.hit.hradar.domain.approval.command.infrastructure;

import java.util.List;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalDocumentTypeJpaRepository
    extends JpaRepository<ApprovalDocumentType, Long> {

  List<ApprovalDocumentType> findByCompanyIdAndActiveTrue(Long companyId);

  List<ApprovalDocumentType> findByCompanyId(Long companyId);
}
