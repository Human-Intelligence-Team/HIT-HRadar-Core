package org.hit.hradar.domain.notice.query.dto.request;

import lombok.Getter;
import org.hit.hradar.global.query.paging.PageRequest;

@Getter
public class NoticeSearchRequest {

    private NoticeSearchCondition cond = new NoticeSearchCondition();
    private PageRequest page = new PageRequest();
}
