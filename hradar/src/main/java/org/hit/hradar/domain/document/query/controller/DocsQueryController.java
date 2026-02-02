package org.hit.hradar.domain.document.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.document.query.dto.response.DocumentDetailResponse;
import org.hit.hradar.domain.document.query.dto.response.DocumentListResponse;
import org.hit.hradar.domain.document.query.service.DocsQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Tag(name = "Document Query", description = "사규/규정 문서 조회 API")
@RestController
@RequestMapping("/docs")
@RequiredArgsConstructor
public class DocsQueryController {

        private final DocsQueryService docsQueryService;

        @Operation(summary = "문서 템플릿 다운로드", description = "사규/규정 일괄 등록을 위한 CSV 템플릿 파일을 다운로드합니다.")
        @GetMapping("/template")
        public ResponseEntity<Resource> downloadTemplate() {

                String csv = "category,doc_title,section,content\n" +
                                "HR_DOCUMENT,연차휴가 규정,연차 발생 기준,연차휴가는 발생일 기준 1년간 사용 가능합니다.\n" +
                                "HR_DOCUMENT,연차휴가 규정,미사용 연차,미사용 연차는 자동으로 소멸됩니다.\n" +
                                "HR_DOCUMENT,휴가 신청 절차,신청 방법,휴가는 그룹웨어를 통해 신청해야 합니다.\n";

                byte[] bom = new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
                byte[] csvBytes = csv.getBytes(StandardCharsets.UTF_8);
                byte[] result = new byte[bom.length + csvBytes.length];

                System.arraycopy(bom, 0, result, 0, bom.length);
                System.arraycopy(csvBytes, 0, result, bom.length, csvBytes.length);

                ByteArrayResource resource = new ByteArrayResource(result);

                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION,
                                                "attachment; filename=document_template.csv")
                                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                                .contentLength(resource.contentLength())
                                .body(resource);
        }

        @Operation(summary = "문서 목록 조회", description = "회사의 모든 사규/규정 문서 목록을 조회합니다.")
        @GetMapping
        public ApiResponse<DocumentListResponse> getDocuments(
                        @CurrentUser AuthUser authUser) {
                return ApiResponse.success(
                                docsQueryService.getDocuments(authUser.companyId()));
        }

        @Operation(summary = "문서 상세 조회", description = "문서 ID로 특정 사규/규정 문서의 상세 내용을 조회합니다.")
        @GetMapping("/{documentId}")
        public ApiResponse<DocumentDetailResponse> getDocument(
                        @CurrentUser AuthUser authUser,
                        @PathVariable Long documentId) {
                return ApiResponse.success(
                                docsQueryService.getDocument(
                                                authUser.companyId(),
                                                documentId));
        }
}
