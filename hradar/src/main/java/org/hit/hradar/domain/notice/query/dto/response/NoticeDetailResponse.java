package org.hit.hradar.domain.notice.query.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

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
}
