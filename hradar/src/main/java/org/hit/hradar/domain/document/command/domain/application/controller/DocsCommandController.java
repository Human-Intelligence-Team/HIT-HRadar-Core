package org.hit.hradar.domain.document.command.domain.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.document.command.domain.application.dto.request.DocumentCommitRequest;
import org.hit.hradar.domain.document.command.domain.application.dto.response.DocumentPreviewResponse;
import org.hit.hradar.domain.document.command.domain.application.service.DocsCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/docs")
@RequiredArgsConstructor
public class DocsCommandController {

    private final DocsCommandService docsCommandService;

    @PostMapping(value = "/preview", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> previewCsv(
            @RequestPart("file") MultipartFile file
    ) {

        return ResponseEntity.ok(ApiResponse.success(docsCommandService.preview(file)));
    }

    @PostMapping("/commit")
    public ResponseEntity<ApiResponse<Void>> commit(
            @CurrentUser AuthUser authUser,
            @RequestBody DocumentCommitRequest request
    ) {
        docsCommandService.commit(
                request,
                authUser.companyId(),
                authUser.userId()
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
