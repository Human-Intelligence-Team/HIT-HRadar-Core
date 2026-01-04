package org.hit.hradar.domain.document.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "DOCUMENT_CHUNK")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentChunk {

    @Id
    @Column(name = "chunk_id")
    private Long id;

    @Column(name = "document_id", nullable = false)
    private Long documentId;

    @Column(name = "chunk_index", nullable = false)
    private int chunkIndex;

    @Column(name = "content_text", nullable = false)
    private String contentText;

    @Column(name = "vector_id", nullable = false, length = 100)
    private String vectorId;

    @Column(name = "token_count")
    private Integer tokenCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false)
    private SourceType sourceType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
