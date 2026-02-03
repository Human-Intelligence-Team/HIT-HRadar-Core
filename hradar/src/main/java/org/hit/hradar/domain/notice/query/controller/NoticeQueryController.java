package org.hit.hradar.domain.notice.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Notice Query", description = "공지사항 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeQueryController {

        private final NoticeQueryService noticeQueryService;
        private static final Logger bizLog = LoggerFactory.getLogger("business");

        @Operation(summary = "공지사항 목록 검색", description = "제목, 내용 등의 조건으로 공지사항 목록을 페이징 처리하여 조회합니다.")
        @GetMapping("/search")
        public ApiResponse<PageResponse<NoticeListItemResponse>> search(
                        NoticeSearchRequest req,
                        @CurrentUser AuthUser authUser) {
                bizLog.info("RAW PAGE = {}", req.getPage().getPage());
                bizLog.info("RAW size = {}", req.getPage().getSize());

                req.getCond().setCompanyId(authUser.companyId());
                return ApiResponse.success(
                                noticeQueryService.search(req));
        }

        @Operation(summary = "공지사항 상세 조회", description = "특정 공지사항의 상세 내용 및 첨부파일 정보를 조회합니다.")
        @GetMapping("/{noticeId}")
        public ApiResponse<NoticeDetailResponse> detail(
                        @PathVariable Long noticeId,
                        @CurrentUser AuthUser authUser) {
                return ApiResponse.success(
                                noticeQueryService.getDetail(
                                                noticeId,
                                                authUser.companyId()));
        }

}
