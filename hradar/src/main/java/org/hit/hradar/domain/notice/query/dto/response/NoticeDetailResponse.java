package org.hit.hradar.domain.notice.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @Setter
    @NoArgsConstructor
    public static class AttachmentResponse {
        private Long id;
        private String url;
        private String originalName;
    }
}
