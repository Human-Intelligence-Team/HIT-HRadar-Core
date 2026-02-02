package org.hit.hradar.domain.notice.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Notice Command", description = "공지사항 본문 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeCommandController {

        private final NoticeCommandService noticeCommandService;

        /** 이미지 드래그 업로드 */
        @Operation(summary = "이미지 업로드", description = "공지사항 본문에 삽입할 이미지를 업로드합니다 (드래그 앤 드롭).")
        @PostMapping("/images")
        public ApiResponse<String> uploadImage(
                        @CurrentUser AuthUser authUser,
                        @RequestPart MultipartFile image) {
                return ApiResponse.success(
                                noticeCommandService.uploadImage(authUser.companyId(), image));
        }

        @Operation(summary = "이미지 삭제", description = "업로드된 이미지를 서버에서 삭제합니다.")
        @DeleteMapping("/images")
        public ApiResponse<Void> deleteImage(
                        @RequestParam String imageUrl,
                        @CurrentUser AuthUser authUser) {
                noticeCommandService.deleteImage(authUser.companyId(), imageUrl);
                return ApiResponse.success(null);
        }

        @Operation(summary = "공지사항 생성", description = "새로운 공지사항을 작성하고 첨부파일을 업로드합니다.")
        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ApiResponse<Void> create(
                        @RequestPart NoticeRequest request,
                        @RequestPart(required = false) List<MultipartFile> files,
                        @CurrentUser AuthUser authUser) {
                NoticeDto dto = new NoticeDto(request.getCategoryId(), request.getTitle(), request.getContent(),
                                authUser.companyId());
                noticeCommandService.create(dto, files);
                return ApiResponse.success(null);
        }

        @Operation(summary = "공지사항 수정", description = "기존 공지사항의 내용 및 첨부파일을 수정합니다.")
        @PutMapping(value = "/{noticeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ApiResponse<Void> update(
                        @PathVariable Long noticeId,
                        @RequestPart NoticeRequest request,
                        @RequestPart(required = false) List<MultipartFile> files,
                        @CurrentUser AuthUser authUser) {
                NoticeDto dto = new NoticeDto(
                                request.getCategoryId(),
                                request.getTitle(),
                                request.getContent(),
                                authUser.companyId(),
                                request.getDeletedAttachmentIds());

                noticeCommandService.updateNotice(noticeId, dto, files);
                return ApiResponse.success(null);
        }

        @Operation(summary = "공지사항 삭제", description = "지정된 공지사항을 삭제합니다.")
        @DeleteMapping("/{noticeId}")
        public ApiResponse<Void> delete(
                        @PathVariable Long noticeId,
                        @CurrentUser AuthUser authUser) {
                noticeCommandService.deleteNotice(noticeId, authUser.companyId());
                return ApiResponse.success(null);
        }
}
