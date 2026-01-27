package org.hit.hradar.domain.document.command.infrastructure;

import org.hit.hradar.domain.document.command.domain.aggregate.DocumentChunk;
import org.hit.hradar.domain.document.command.domain.repository.DocumentChunkRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentChunkJpaRepository extends DocumentChunkRepository, JpaRepository<DocumentChunk, Long> {
}
