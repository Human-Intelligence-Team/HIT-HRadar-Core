package org.hit.hradar.domain.notice.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.notice.query.dto.NoticeListItemResponse;
import org.hit.hradar.domain.notice.query.dto.NoticeSearchRequest;
import org.hit.hradar.domain.notice.query.service.NoticeQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.query.paging.PageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeQueryController {

    private final NoticeQueryService noticeQueryService;

    @GetMapping("/search")
    public ApiResponse<PageResponse<NoticeListItemResponse>> search(
            NoticeSearchRequest req
    ) {
        return ApiResponse.success(
                noticeQueryService.search(req)
        );
    }
}
