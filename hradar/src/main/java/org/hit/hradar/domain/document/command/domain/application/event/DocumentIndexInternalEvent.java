package org.hit.hradar.domain.document.command.domain.application.event;

import org.hit.hradar.domain.document.command.domain.application.dto.request.DocumentIndexEventType;
import org.hit.hradar.domain.document.command.domain.application.dto.request.VectorChunkRequest;

import java.util.List;

public record DocumentIndexInternalEvent(
        Long companyId,
        Long documentId,
        DocumentIndexEventType type,
        List<VectorChunkRequest> chunks
) {}