package org.hit.hradar.domain.document.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "DOCUMENT_CHUNK")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentChunk extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;
    private Long documentId;
    private int chunkIndex;

    private String section;

    @Column(columnDefinition = "TEXT")
    private String content;

    public static DocumentChunk create(
            Long companyId,
            Long documentId,
            int index,
            String section,
            String content
    ) {
        DocumentChunk c = new DocumentChunk();
        c.companyId = companyId;
        c.documentId = documentId;
        c.chunkIndex = index;
        c.section = section;
        c.content = content;
        return c;
    }
}