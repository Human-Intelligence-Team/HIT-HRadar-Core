package org.hit.hradar.domain.document.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.document.command.domain.aggregate.Document;
import org.hit.hradar.domain.document.command.domain.aggregate.DocumentChunk;
import org.hit.hradar.domain.document.command.domain.repository.DocumentChunkRepository;
import org.hit.hradar.domain.document.command.domain.repository.DocumentRepository;
import org.hit.hradar.domain.document.query.dto.response.DocumentDetailResponse;
import org.hit.hradar.domain.document.query.dto.response.DocumentListResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocsQueryService {

    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository chunkRepository;

    public DocumentListResponse getDocuments(Long companyId) {

        List<DocumentListResponse.Item> items =
                documentRepository.findByCompanyIdOrderByCreatedAtDesc(companyId)
                        .stream()
                        .map(DocumentListResponse.Item::from)
                        .toList();

        return new DocumentListResponse(items);
    }

    public DocumentDetailResponse getDocument(Long companyId, Long documentId) {

        Document document = documentRepository
                .findByIdAndCompanyId(documentId, companyId)
                .orElseThrow();

        List<DocumentChunk> chunks =
                chunkRepository.findByDocumentIdOrderByChunkIndex(documentId);

        return DocumentDetailResponse.from(document, chunks);
    }
}
