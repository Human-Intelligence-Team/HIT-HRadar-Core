package org.hit.hradar.domain.document.command.domain.repository;

import org.hit.hradar.domain.document.command.domain.aggregate.DocumentChunk;

import java.util.List;

public interface DocumentChunkRepository {
    <S extends DocumentChunk> List<S> saveAll(Iterable<S> entities);

    List<DocumentChunk> findByDocumentIdOrderByChunkIndex(Long documentId);

    void deleteByDocumentId(Long documentId);
}
