package org.hit.hradar.domain.notice.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.notice.query.dto.response.NoticeDetailResponse;
import org.hit.hradar.domain.notice.query.dto.response.NoticeListItemResponse;
import org.hit.hradar.domain.notice.query.dto.request.NoticeSearchRequest;
import org.hit.hradar.domain.notice.query.service.NoticeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.hit.hradar.global.query.paging.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            NoticeSearchRequest req,
            @CurrentUser AuthUser authUser
    ) {
        bizLog.info("RAW PAGE = {}", req.getPage().getPage());
        bizLog.info("RAW size = {}", req.getPage().getSize());

        req.getCond().setCompanyId(authUser.companyId());
        return ApiResponse.success(
                noticeQueryService.search(req)
        );
    }

    @GetMapping("/{noticeId}")
    public ApiResponse<NoticeDetailResponse> detail(
            @PathVariable Long noticeId,
            @CurrentUser AuthUser authUser
    ) {
        return ApiResponse.success(
                noticeQueryService.getDetail(
                        noticeId,
                        authUser.companyId()
                )
        );
    }

}
