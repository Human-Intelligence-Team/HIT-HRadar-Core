package org.hit.hradar.domain.notice.query.dto;

import lombok.Getter;
import org.hit.hradar.global.query.search.KeywordCondition;
import org.hit.hradar.global.query.sort.SortDirection;

@Getter
public class NoticeSearchCondition {
    private Long companyId;
    private Long categoryId;
    private KeywordCondition keyword;

    // 정렬: pinned DESC는 항상 고정 + 그 다음 sortKey/sortDir
    private NoticeSortKey sortKey = NoticeSortKey.CREATED_AT;
    private SortDirection sortDir = SortDirection.DESC;
}