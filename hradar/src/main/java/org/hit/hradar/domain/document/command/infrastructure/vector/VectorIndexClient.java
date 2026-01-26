package org.hit.hradar.domain.document.command.infrastructure.vector;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.document.DocsErrorCode;
import org.hit.hradar.domain.document.command.domain.aggregate.DocumentChunk;
import org.hit.hradar.domain.document.command.domain.application.dto.request.VectorChunkRequest;
import org.hit.hradar.domain.document.command.domain.application.dto.request.VectorIndexRequest;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class VectorIndexClient {

    private final WebClient webClient;

    public VectorIndexClient(
            @Qualifier("vectorIndexWebClient") WebClient webClient
    ) {
        this.webClient = webClient;
    }

    /**
     * 동기 방식
     */
    public void index(Long companyId, Long documentId, List<DocumentChunk> chunks) {
        webClient.post()
                .uri("/index")
                .bodyValue(Map.of(
                        "companyId", companyId,
                        "documentId", documentId,
                        "chunks", chunks.stream().map(c ->
                                Map.of(
                                        "chunkId", c.getChunkIndex(),
                                        "content", c.getContent()
                                )
                        ).toList()
                ))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    /**
     * 비동기 방식
     */
    public void indexAsync(Long companyId, Long documentId, List<DocumentChunk> chunks) {

        if (chunks == null || chunks.isEmpty()) {
            log.warn(
                    "[VectorIndex] skip indexing: no chunks. companyId={}, documentId={}",
                    companyId, documentId
            );
            throw new BusinessException(DocsErrorCode.NO_VALID_CONTENT);
        }

        VectorIndexRequest request = new VectorIndexRequest(
                companyId,
                documentId,
                chunks.stream()
                        .map(c -> new VectorChunkRequest(
                                c.getChunkIndex(),
                                c.getContent()
                        ))
                        .toList()
        );

        // HTTP 요청
        webClient.post()
                .uri("/index")
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(v ->
                        log.info(
                                "[VectorIndex] indexing success. companyId={}, documentId={}",
                                companyId, documentId
                        )
                )
                .doOnError(e ->
                        log.error(
                                "[VectorIndex] indexing failed. body={}",
                                request,
                                e
                        )
                )
                .subscribe();
    }


    public void deleteIndex(Long companyId, Long documentId) {
        webClient.delete()
                .uri("/index/{companyId}/{documentId}", companyId, documentId)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(e ->
                        log.error("Vector delete failed. companyId={}, documentId={}",
                                companyId, documentId, e)
                )
                .subscribe();
    }
}

