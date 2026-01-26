package org.hit.hradar.domain.document.query.dto.response;

import org.hit.hradar.domain.document.command.domain.aggregate.Document;
import org.hit.hradar.domain.document.command.domain.aggregate.DocumentChunk;

import java.util.List;

public record DocumentDetailResponse(
        Long id,
        String title,
        String category,
        List<Chunk> chunks
        ) {

    public static DocumentDetailResponse from(
            Document doc,
            List<DocumentChunk> chunks
    ) {
        return new DocumentDetailResponse(
                doc.getId(),
                doc.getTitle(),
                doc.getCategory(),
                chunks.stream().map(Chunk::from).toList()
        );
    }

    public record Chunk(
            int index,
            String section,
            String content
    ) {
        public static Chunk from(DocumentChunk c) {
            return new Chunk(
                    c.getChunkIndex(),
                    c.getSection(),
                    c.getContent()
            );
        }
    }
}
