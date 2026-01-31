package org.hit.hradar.domain.notice.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NoticeDto {

    private final Long categoryId;
    private final String title;
    private final String content;
    private final Long companyId;
}
