package org.hit.hradar.domain.notice.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.notice.query.dto.response.NoticeListItemResponse;
import org.hit.hradar.domain.notice.query.dto.request.NoticeSearchRequest;
import org.hit.hradar.domain.notice.query.service.NoticeQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.query.paging.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeQueryController {

    private final NoticeQueryService noticeQueryService;
    private static final Logger bizLog =
            LoggerFactory.getLogger("business");

    @GetMapping("/search")
    public ApiResponse<PageResponse<NoticeListItemResponse>> search(
            NoticeSearchRequest req
    ) {
        bizLog.info("[NOTICE SEARCH Start] req={}", req);

        return ApiResponse.success(
                noticeQueryService.search(req)
        );
    }
}
