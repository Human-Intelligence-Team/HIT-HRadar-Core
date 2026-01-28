package org.hit.hradar.domain.notice.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.notice.query.dto.NoticeListItemResponse;
import org.hit.hradar.domain.notice.query.dto.NoticeSearchRequest;
import org.hit.hradar.domain.notice.query.mapper.NoticeMapper;
import org.hit.hradar.global.query.paging.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeQueryService {

    private final NoticeMapper mapper;

    public PageResponse<NoticeListItemResponse> search(
            NoticeSearchRequest req
    ) {
        int page = req.getPage().safePage();
        int size = req.getPage().safeSize();

        List<NoticeListItemResponse> items =
                mapper.search(
                        req.getCond(),
                        req.getPage().offset(),
                        size
                );

        long total = mapper.count(req.getCond());

        return PageResponse.of(items, page, size, total);
    }
}
