package org.hit.hradar.domain.document.command.domain.repository;

import org.hit.hradar.domain.document.command.domain.aggregate.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository {
    <S extends Document> S save(S document);

    List<Document> findByCompanyIdOrderByCreatedAtDesc(Long companyId);

    Optional<Document> findByIdAndCompanyId(Long id, Long companyId);

    void deleteByIdAndCompanyId(Long documentId, Long companyId);
}
