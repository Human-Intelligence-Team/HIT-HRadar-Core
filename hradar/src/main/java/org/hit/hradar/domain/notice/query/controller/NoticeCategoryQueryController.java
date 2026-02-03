package org.hit.hradar.domain.notice.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.notice.query.dto.response.NoticeCategoryResponse;
import org.hit.hradar.domain.notice.query.service.NoticeCategoryQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Notice Category Query", description = "공지사항 카테고리 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notices/categories")
public class NoticeCategoryQueryController {

    private final NoticeCategoryQueryService noticeCategoryQueryService;

    @Operation(summary = "카테고리 목록 조회", description = "회사의 모든 공지사항 카테고리 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<List<NoticeCategoryResponse>> list(
            @CurrentUser AuthUser authUser) {
        return ApiResponse.success(noticeCategoryQueryService.findByCompany(authUser.companyId()));
    }
}
