package org.hit.hradar.domain.notice.command.application.dto;

import lombok.Getter;

@Getter
public class NoticeRequest {

    private Long categoryId;
    private String title;
    private String content;
}

