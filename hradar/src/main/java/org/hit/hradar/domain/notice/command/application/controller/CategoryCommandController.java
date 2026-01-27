package org.hit.hradar.domain.notice.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.notice.command.application.dto.NoticeCategoryDto;
import org.hit.hradar.domain.notice.command.application.dto.NoticeCategoryRequest;
import org.hit.hradar.domain.notice.command.application.service.NoticeCategoryCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice/categories")
public class CategoryCommandController {

    private final NoticeCategoryCommandService noticeCategoryCommandService;

    @PostMapping
    public ApiResponse<Void> create(
            @RequestBody NoticeCategoryRequest req,
            @CurrentUser AuthUser authUser
    ) {
        NoticeCategoryDto dto = new NoticeCategoryDto(authUser.companyId(), req.getName());
        noticeCategoryCommandService.create(dto);
        return ApiResponse.success(null);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @PathVariable Long id,
            @RequestBody NoticeCategoryRequest req,
            @CurrentUser AuthUser authUser
    ) {
        NoticeCategoryDto dto = new NoticeCategoryDto(authUser.companyId(), req.getName());
        noticeCategoryCommandService.update(id, dto);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable Long id,
            @CurrentUser AuthUser authUser
    ) {
        noticeCategoryCommandService.delete(id, authUser.companyId());
        return ApiResponse.success(null);
    }
}
