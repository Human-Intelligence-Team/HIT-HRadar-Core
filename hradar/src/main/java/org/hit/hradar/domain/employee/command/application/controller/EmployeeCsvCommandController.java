package org.hit.hradar.domain.employee.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.command.application.dto.reponse.EmployeeCsvPreviewResponse;
import org.hit.hradar.domain.employee.command.application.dto.reponse.EmployeeCsvUploadResponse;
import org.hit.hradar.domain.employee.command.application.dto.request.EmployeeCsvRegisterRequest;
import org.hit.hradar.domain.employee.command.application.service.EmployeeCsvPreviewService;
import org.hit.hradar.domain.employee.command.application.service.EmployeeCsvRegisterService;
import org.hit.hradar.domain.employee.command.application.service.EmployeeCsvTemplateService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.util.List;
import java.nio.charset.StandardCharsets;

/**
 * 사원 CSV 업로드 및 등록 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees/csv")
public class EmployeeCsvCommandController {

    private final EmployeeCsvPreviewService employeeCsvPreviewService;
    private final EmployeeCsvRegisterService employeeCsvRegisterService;
    private final EmployeeCsvTemplateService employeeCsvTemplateService;

    /**
     * 사원 일괄 등록용 CSV 템플릿 파일을 다운로드합니다.
     *
     * @return CSV 파일 리소스
     */
    @GetMapping("/template")
    public ResponseEntity<ByteArrayResource> downloadTemplate() {
        byte[] data = employeeCsvTemplateService.getTemplateBytes();
        ByteArrayResource resource = new ByteArrayResource(data);

        String filename = "사원등록_템플릿.csv";
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .contentLength(data.length)
                .body(resource);
    }

    /**
     * CSV 파일을 업로드하여 미리보기를 수행합니다.
     * <p>
     * 파일 내의 사원 정보를 파싱하고, 유효성 검사 결과를 반환합니다.
     * </p>
     *
     * @param authUser 로그인한 사용자 정보
     * @param file     업로드할 CSV 파일
     * @return 각 행별 유효성 검사 결과 및 전체 요약
     */
    @PostMapping(value = "/preview", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EmployeeCsvPreviewResponse>> previewCsv(
            @CurrentUser AuthUser authUser,
            @RequestPart("file") MultipartFile file) {
        EmployeeCsvPreviewResponse response = employeeCsvPreviewService.preview(authUser.companyId(), file);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * CSV 파일을 업로드하여 사원 일괄 등록을 수행합니다.
     * <p>
     * 재검증 후 유효한 경우에만 일괄 등록 처리됩니다.
     * </p>
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<EmployeeCsvUploadResponse>> registerCsv(
            @CurrentUser AuthUser authUser,
            @RequestBody List<EmployeeCsvRegisterRequest> requests) {
        EmployeeCsvUploadResponse response = employeeCsvRegisterService.register(authUser.companyId(), requests);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
