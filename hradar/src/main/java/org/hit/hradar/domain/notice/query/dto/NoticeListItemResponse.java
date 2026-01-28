package org.hit.hradar.domain.notice.query.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class NoticeListItemResponse {
    private Long id;
    private String title;
    private String categoryName;
    private LocalDateTime createdAt;
}