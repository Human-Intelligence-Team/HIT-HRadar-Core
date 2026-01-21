package org.hit.hradar.domain.document.command.infrastructure.vector;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.document.command.domain.aggregate.DocumentChunk;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class VectorIndexClient {

    private final WebClient webClient;

    public void index(Long companyId, Long documentId, List<DocumentChunk> chunks) {
        webClient.post()
                .uri("/index")
                .bodyValue(Map.of(
                        "companyId", companyId,
                        "documentId", documentId,
                        "chunks", chunks.stream().map(c ->
                                Map.of(
                                        "chunkId", c.getId(),
                                        "content", c.getContent()
                                )
                        ).toList()
                ))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}

