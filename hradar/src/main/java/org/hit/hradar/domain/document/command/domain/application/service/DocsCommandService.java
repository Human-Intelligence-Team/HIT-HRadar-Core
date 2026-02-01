package org.hit.hradar.domain.document.command.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.document.DocsErrorCode;
import org.hit.hradar.domain.document.command.domain.aggregate.Document;
import org.hit.hradar.domain.document.command.domain.aggregate.DocumentChunk;
import org.hit.hradar.domain.document.command.domain.application.csv.CsvParseResult;
import org.hit.hradar.domain.document.command.domain.application.csv.CsvParser;
import org.hit.hradar.domain.document.command.domain.application.dto.request.DocumentIndexEvent;
import org.hit.hradar.domain.document.command.domain.application.dto.request.DocumentCreateRequest;
import org.hit.hradar.domain.document.command.domain.application.dto.request.DocumentIndexEventType;
import org.hit.hradar.domain.document.command.domain.application.dto.request.VectorChunkRequest;
import org.hit.hradar.domain.document.command.domain.application.dto.response.DocumentPreviewResponse;
import org.hit.hradar.domain.document.command.domain.application.event.DocumentIndexInternalEvent;
import org.hit.hradar.domain.document.command.domain.repository.DocumentChunkRepository;
import org.hit.hradar.domain.document.command.domain.repository.DocumentRepository;
import org.hit.hradar.domain.document.command.infrastructure.vector.DocumentIndexProducer;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DocsCommandService {

    private final CsvParser csvParser;
    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository chunkRepository;
    private final ApplicationEventPublisher publisher;

    public DocumentPreviewResponse preview(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(DocsErrorCode.EMPTY_FILE);
        }

        CsvParseResult result = csvParser.parse(file);
        validateHeader(result.getHeaderIndex());

        return buildPreview(result);
    }

    private void validateHeader(Map<String, Integer> headerIndex) {
        List<String> required = List.of("category", "doc_title", "section", "content");
        if (!headerIndex.keySet().containsAll(required)) {
            throw new BusinessException(DocsErrorCode.INVALID_HEADER);
        }
    }

    private DocumentPreviewResponse buildPreview(CsvParseResult result) {
        List<DocumentPreviewResponse.ChunkPreview> chunks = new ArrayList<>();
        String docTitle = null;
        int i = 1;

        for (String[] row : result.getRows()) {
            String title = get(row, result, "doc_title");
            String section = get(row, result, "section");
            String content = get(row, result, "content");

            if (content == null || content.isBlank()) continue;
            if (docTitle == null) docTitle = title;

            chunks.add(new DocumentPreviewResponse.ChunkPreview(i++, section, content));
        }

        if (chunks.isEmpty()) {
            throw new BusinessException(DocsErrorCode.NO_VALID_CONTENT);
        }

        return new DocumentPreviewResponse(docTitle, chunks.size(), chunks);
    }

    private String get(String[] row, CsvParseResult result, String col) {
        Integer idx = result.getHeaderIndex().get(col);
        return (idx == null || idx >= row.length) ? null : row[idx].trim();
    }

    public void create(DocumentCreateRequest request, Long companyId) {

        Document document = documentRepository.save(
                Document.create(companyId, request.getDocTitle(), request.getCategory())
        );

        List<DocumentChunk> chunks = saveChunks(companyId, document.getId(), request);

        publisher.publishEvent(
                new DocumentIndexInternalEvent(
                        companyId,
                        document.getId(),
                        DocumentIndexEventType.CREATE,
                        toVectorChunks(chunks)
                )
        );
    }

    public void update(Long documentId, DocumentCreateRequest request, Long companyId) {

        Document document = documentRepository.findByIdAndCompanyId(documentId, companyId)
                .orElseThrow();

        document.updateDocument(request.getDocTitle(), request.getCategory());
        chunkRepository.deleteByDocumentId(documentId);

        List<DocumentChunk> chunks = saveChunks(companyId, documentId, request);

        publisher.publishEvent(
                new DocumentIndexInternalEvent(
                        companyId,
                        documentId,
                        DocumentIndexEventType.UPDATE,
                        toVectorChunks(chunks)
                )
        );
    }

    public void delete(Long documentId, Long companyId) {

        chunkRepository.deleteByDocumentId(documentId);
        documentRepository.deleteByIdAndCompanyId(documentId, companyId);

        publisher.publishEvent(
                new DocumentIndexInternalEvent(
                        companyId,
                        documentId,
                        DocumentIndexEventType.DELETE,
                        null
                )
        );
    }

    private List<DocumentChunk> saveChunks(
            Long companyId,
            Long documentId,
            DocumentCreateRequest request
    ) {
        List<DocumentChunk> chunks = new ArrayList<>();
        int idx = 1;

        for (var c : request.getChunks()) {
            chunks.add(
                    DocumentChunk.create(
                            companyId,
                            documentId,
                            idx++,
                            c.getSection(),
                            c.getContent()
                    )
            );
        }

        return chunkRepository.saveAll(chunks);
    }

    private List<VectorChunkRequest> toVectorChunks(List<DocumentChunk> chunks) {
        return chunks.stream()
                .map(c -> new VectorChunkRequest(
                        c.getChunkIndex(),
                        c.getContent()
                ))
                .toList();
    }
}

