package org.hit.hradar.domain.notice.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.notice.query.dto.request.NoticeSearchCondition;
import org.hit.hradar.domain.notice.query.dto.response.NoticeListItemResponse;
import org.hit.hradar.domain.notice.query.dto.request.NoticeSearchRequest;
import org.hit.hradar.domain.notice.query.mapper.NoticeMapper;
import org.hit.hradar.global.query.paging.PageResponse;
import org.hit.hradar.global.query.search.KeywordCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeQueryService {

    private final NoticeMapper mapper;

    private static final Logger bizLog =
            LoggerFactory.getLogger("business");

    public PageResponse<NoticeListItemResponse> search(
            NoticeSearchRequest req
    ) {

        bizLog.info(
                "[NOTICE SEARCH COND] companyId={}, categoryId={}, keyword={}, sortKey={}, sortDir={}",
                req.getCond().getCompanyId(),
                req.getCond().getCategoryId(),
                req.getCond().getKeyword(),
                req.getCond().getSortKey(),
                req.getCond().getSortDir()
        );

        NoticeSearchCondition cond = req.getCond();
        KeywordCondition keywordCond =
                KeywordCondition.of(cond.getKeyword());

        int page = req.getPage().safePage();
        int size = req.getPage().safeSize();
        int offset = req.getPage().offset();

        bizLog.info(
                "[NOTICE SEARCH PAGE] page={}, size={}, offset={}",
                page,
                size,
                req.getPage().offset()
        );

        bizLog.info(
                "[NOTICE SEARCH QUERY] cond={}, offset={}, size={}",
                req.getCond(),
                req.getPage().offset(),
                size
        );

        String keywordLike =
                keywordCond != null ? keywordCond.like() : null;

        List<NoticeListItemResponse> items =
                mapper.search(cond, keywordLike, offset, size);

        long total = mapper.count(cond, keywordLike);

        return PageResponse.of(items, page, size, total);
    }
}
