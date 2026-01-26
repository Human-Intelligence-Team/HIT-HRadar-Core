package org.hit.hradar.domain.document.command.domain.application.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class DocumentCreateRequest {
    private String docTitle;
    private String category;
    private List<ChunkRequest> chunks;

    @Getter
    public static class ChunkRequest {
        private String section;
        private String content;
    }
}
