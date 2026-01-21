package org.hit.hradar.domain.document.query.dto.response;

import org.hit.hradar.domain.document.command.domain.aggregate.Document;

import java.util.List;

public record DocumentListResponse(
        List<Item> documents
) {

    public record Item(
            Long id,
            String title,
            String category,
            String createdAt
    ) {
        public static Item from(Document doc) {
            return new Item(
                    doc.getId(),
                    doc.getTitle(),
                    "규정", // 지금은 고정, 나중에 enum 가능
                    doc.getCreatedAt().toString()
            );
        }
    }
}
