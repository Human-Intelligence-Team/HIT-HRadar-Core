package org.hit.hradar.domain.notice.command.application.controller;

import lombok.RequiredArgsConstructor;
//import org.hit.hradar.domain.notice.command.application.dto.NoticeCreateRequest;
import org.hit.hradar.domain.notice.command.application.dto.NoticeDto;
import org.hit.hradar.domain.notice.command.application.dto.NoticeRequest;
//import org.hit.hradar.domain.notice.command.application.dto.NoticeUpdateRequest;
import org.hit.hradar.domain.notice.command.application.service.NoticeCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeCommandController {

    private final NoticeCommandService noticeCommandService;

    /** 이미지 드래그 업로드 */
    @PostMapping("/images")
    public ApiResponse<String> uploadImage(
            @CurrentUser AuthUser authUser,
            @RequestPart MultipartFile image
    ) {
        return ApiResponse.success(
                noticeCommandService.uploadImage(authUser.companyId(), image)
        );
    }

    @DeleteMapping("/images")
    public ApiResponse<Void> deleteImage(
            @RequestParam String imageUrl,
            @CurrentUser AuthUser authUser
    ) {
        noticeCommandService.deleteImage(authUser.companyId(), imageUrl);
        return ApiResponse.success(null);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Void> create(
            @RequestPart NoticeRequest request,
            @RequestPart(required = false) List<MultipartFile> files,
            @CurrentUser AuthUser authUser
    ) {
        NoticeDto dto = new NoticeDto(request.getCategoryId(), request.getTitle(), request.getContent(), authUser.companyId());
        noticeCommandService.create(dto, files);
        return ApiResponse.success(null);
    }

    @PutMapping("/{noticeId}")
    public ApiResponse<Void> update(
            @PathVariable Long noticeId,
            @RequestBody NoticeRequest request,
            @CurrentUser AuthUser authUser
    ) {
        NoticeDto dto = new NoticeDto(
                request.getCategoryId(),
                request.getTitle(),
                request.getContent(),
                authUser.companyId()
        );

        noticeCommandService.updateNotice(noticeId, dto);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{noticeId}")
    public ApiResponse<Void> delete(
            @PathVariable Long noticeId,
            @CurrentUser AuthUser authUser
    ) {
        noticeCommandService.deleteNotice(noticeId, authUser.companyId());
        return ApiResponse.success(null);
    }
}
