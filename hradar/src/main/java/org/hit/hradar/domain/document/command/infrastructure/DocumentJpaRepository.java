package org.hit.hradar.domain.document.command.infrastructure;

import org.hit.hradar.domain.document.command.domain.aggregate.Document;
import org.hit.hradar.domain.document.command.domain.repository.DocumentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentJpaRepository extends DocumentRepository, JpaRepository<Document, Long> {
}
