package org.hit.hradar.domain.document.command.domain.application.dto.request;

import java.util.List;

public record VectorIndexRequest(
        Long companyId,
        Long documentId,
        List<VectorChunkRequest> chunks
) {}