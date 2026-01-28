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
            @RequestParam Long companyId,
            @RequestPart MultipartFile image
    ) {
        return ApiResponse.success(
                noticeCommandService.uploadImage(companyId, image)
        );
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

//    @PutMapping("/{id}")
//    public ApiResponse<Void> update(
//            @PathVariable Long id,
//            @RequestBody NoticeUpdateRequest request
//    ) {
//        noticeCommandService.update(id, request);
//        return ApiResponse.success(null);
//    }
//
//    @DeleteMapping("/{id}")
//    public ApiResponse<Void> delete(@PathVariable Long id) {
//        noticeCommandService.delete(id);
//        return ApiResponse.success(null);
//    }
}
