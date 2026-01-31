package org.hit.hradar.domain.notice.query.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class NoticeDetailResponse {

    private Long noticeId;
    private String title;
    private String content;

    private Long categoryId;
    private String categoryName;

    private String createdByName;
    private String updatedByName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<AttachmentResponse> attachments;

    @Getter
    public static class AttachmentResponse {
        private String url;
        private String originalName;
    }
}
