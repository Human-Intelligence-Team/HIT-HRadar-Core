package org.hit.hradar.domain.document.command.domain.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DocumentPreviewResponse {

    private String docTitle;
    private int totalChunks;
    private List<ChunkPreview> chunks;

    @Getter
    @AllArgsConstructor
    public static class ChunkPreview {
        private int chunkIndex;
        private String section;
        private String content;
    }
}
