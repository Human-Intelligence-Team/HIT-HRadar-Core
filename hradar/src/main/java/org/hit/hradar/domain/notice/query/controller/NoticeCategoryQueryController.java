package org.hit.hradar.domain.notice.query.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices/categories")
public class NoticeCategoryQueryController {

    private final NoticeCategoryQueryService noticeCategoryQueryService;

    @GetMapping
    public ApiResponse<List<NoticeCategoryResponse>> list(
            @CurrentUser AuthUser authUser
    ) {
        return ApiResponse.success(noticeCategoryQueryService.findByCompany(authUser.companyId()));
    }
}
