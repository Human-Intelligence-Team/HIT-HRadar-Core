package org.hit.hradar.domain.document.command.domain.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentIndexEvent {

    private String eventId;
    private Long companyId;
    private Long documentId;
    private DocumentIndexEventType type;
    private List<VectorChunkRequest> chunks;
}
