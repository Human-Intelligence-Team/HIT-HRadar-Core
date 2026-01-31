package org.hit.hradar.domain.notice.query.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoticeCategoryResponse {

    private final Long id;
    private final String name;
}
