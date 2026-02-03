package org.hit.hradar.domain.notice.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.notice.command.application.dto.NoticeCategoryDto;
import org.hit.hradar.domain.notice.command.application.dto.NoticeCategoryRequest;
import org.hit.hradar.domain.notice.command.application.service.NoticeCategoryCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notice Category Command", description = "공지사항 카테고리 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notices/categories")
public class CategoryCommandController {

    private final NoticeCategoryCommandService noticeCategoryCommandService;

    @Operation(summary = "카테고리 생성", description = "공지사항 분류를 위한 새로운 카테고리를 생성합니다.")
    @PostMapping
    public ApiResponse<Void> create(
            @RequestBody NoticeCategoryRequest req,
            @CurrentUser AuthUser authUser) {
        NoticeCategoryDto dto = new NoticeCategoryDto(authUser.companyId(), req.getName());
        noticeCategoryCommandService.create(dto);
        return ApiResponse.success(null);
    }

    @Operation(summary = "카테고리 수정", description = "기존 카테고리의 이름을 수정합니다.")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @PathVariable Long id,
            @RequestBody NoticeCategoryRequest req,
            @CurrentUser AuthUser authUser) {
        NoticeCategoryDto dto = new NoticeCategoryDto(authUser.companyId(), req.getName());
        noticeCategoryCommandService.update(id, dto);
        return ApiResponse.success(null);
    }

    @Operation(summary = "카테고리 삭제", description = "지정된 카테고리를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable Long id,
            @CurrentUser AuthUser authUser) {
        noticeCategoryCommandService.delete(id, authUser.companyId());
        return ApiResponse.success(null);
    }
}
