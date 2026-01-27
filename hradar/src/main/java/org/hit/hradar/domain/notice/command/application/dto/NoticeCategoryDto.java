package org.hit.hradar.domain.notice.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NoticeCategoryDto {

    private final Long companyId;
    private final String name;
}
