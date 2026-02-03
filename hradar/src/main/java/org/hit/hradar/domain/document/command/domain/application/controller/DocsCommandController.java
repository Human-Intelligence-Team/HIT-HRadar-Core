package org.hit.hradar.domain.document.command.domain.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.document.command.domain.application.dto.request.DocumentCreateRequest;
import org.hit.hradar.domain.document.command.domain.application.service.DocsCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Document Command", description = "사규/규정 문서 관리 API")
@RestController
@RequestMapping("/docs")
@RequiredArgsConstructor
public class DocsCommandController {

        private final DocsCommandService docsCommandService;

        @Operation(summary = "CSV 미리보기", description = "업로드한 CSV 파일의 내용을 파싱하여 미리보기를 제공합니다.")
        @PostMapping(value = "/preview", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<?> previewCsv(
                        @RequestPart("file") MultipartFile file) {
                return ResponseEntity.ok(ApiResponse.success(docsCommandService.preview(file)));
        }

        @Operation(summary = "문서 등록", description = "새로운 사규/규정 문서를 등록합니다.")
        @PostMapping
        public ResponseEntity<ApiResponse<Void>> createDocs(
                        @CurrentUser AuthUser authUser,
                        @RequestBody DocumentCreateRequest request) {
                docsCommandService.create(
                                request,
                                authUser.companyId());
                return ResponseEntity.ok(ApiResponse.success(null));
        }

        @Operation(summary = "문서 수정", description = "기존 사규/규정 문서의 내용을 수정합니다.")
        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> updateDocs(
                        @PathVariable Long id,
                        @CurrentUser AuthUser authUser,
                        @RequestBody DocumentCreateRequest request) {
                docsCommandService.update(
                                id,
                                request,
                                authUser.companyId());
                return ResponseEntity.ok(ApiResponse.success(null));
        }

        @Operation(summary = "문서 삭제", description = "사규/규정 문서를 삭제합니다.")
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteDocs(
                        @PathVariable Long id,
                        @CurrentUser AuthUser authUser) {
                docsCommandService.delete(
                                id,
                                authUser.companyId());
                return ResponseEntity.ok(ApiResponse.success(null));
        }
}
