package org.hit.hradar.domain.notice.query.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hit.hradar.domain.notice.query.dto.NoticeSortKey;
import org.hit.hradar.global.query.sort.SortDirection;

@Getter
@Setter
public class NoticeSearchCondition {
    private Long companyId;
    private Long categoryId;
    private String keyword;
    private NoticeSortKey sortKey = NoticeSortKey.CREATED_AT;
    private SortDirection sortDir = SortDirection.DESC;
}