package org.hit.hradar.domain.notice.query.dto;

import lombok.Getter;
import org.hit.hradar.global.query.paging.PageRequest;

@Getter
public class NoticeSearchRequest {
    private NoticeSearchCondition cond;
    private PageRequest page;       // offset 페이징
}