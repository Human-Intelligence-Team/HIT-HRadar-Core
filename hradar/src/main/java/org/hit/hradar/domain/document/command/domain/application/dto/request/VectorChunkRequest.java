package org.hit.hradar.domain.document.command.domain.application.dto.request;

public record VectorChunkRequest(
                Integer chunkId,
                String title,
                String content) {
}