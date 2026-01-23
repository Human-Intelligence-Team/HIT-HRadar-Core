package org.hit.hradar.domain.document.command.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.document.DocsErrorCode;
import org.hit.hradar.domain.document.command.domain.aggregate.Document;
import org.hit.hradar.domain.document.command.domain.aggregate.DocumentChunk;
import org.hit.hradar.domain.document.command.domain.application.csv.CsvParseResult;
import org.hit.hradar.domain.document.command.domain.application.csv.CsvParser;
import org.hit.hradar.domain.document.command.domain.application.dto.request.DocumentCommitRequest;
import org.hit.hradar.domain.document.command.domain.application.dto.response.DocumentPreviewResponse;
import org.hit.hradar.domain.document.command.domain.repository.DocumentChunkRepository;
import org.hit.hradar.domain.document.command.domain.repository.DocumentRepository;
import org.hit.hradar.domain.document.command.infrastructure.vector.VectorIndexClient;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class DocsCommandService {

    private final CsvParser csvParser;
    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository chunkRepository;
    private final VectorIndexClient vectorIndexClient;

    public DocumentPreviewResponse preview(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(DocsErrorCode.EMPTY_FILE);
        }

        CsvParseResult result = csvParser.parse(file);
        validateHeader(result.getHeaderIndex());

        return buildPreview(result);
    }

    public void commit(
            DocumentCommitRequest request,
            Long companyId,
            Long actorId
    ) {
        Document document = documentRepository.save(
                Document.create(companyId, request.getDocTitle(), actorId)
        );

        List<DocumentChunk> chunks = new ArrayList<>();
        int idx = 1;

        for (var c : request.getChunks()) {
            chunks.add(
                    DocumentChunk.create(
                            companyId,
                            document.getId(),
                            idx++,
                            c.getSection(),
                            c.getContent(),
                            actorId
                    )
            );
        }

        chunkRepository.saveAll(chunks);

        // Python 색인
        vectorIndexClient.index(companyId, document.getId(), chunks);
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
}

