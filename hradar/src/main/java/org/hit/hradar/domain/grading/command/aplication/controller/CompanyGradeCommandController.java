package org.hit.hradar.domain.grading.command.aplication.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterCompanyGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.dto.request.UpdateCompanyGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.CompanyGradeCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades")
public class CompanyGradeCommandController {

    private final CompanyGradeCommandService companyGradeCommandService;

    //회사 등급 등록
    @PostMapping
    public ResponseEntity<Long> registerCompanyGrade(
            @CurrentUser AuthUser authUser,
            @RequestBody RegisterCompanyGradeRequestDto dto
    ){
        Long gradeId = companyGradeCommandService.registerCompanyGrade(authUser.companyId(), dto);
        return ResponseEntity.ok(gradeId);
    }

    //회사 등급 수정
    @PutMapping("/{gradeId}")
    public ResponseEntity<ApiResponse<Void>> updateCompanyGrade(
            @CurrentUser AuthUser authUser,
            @PathVariable Long gradeId,
            @RequestBody UpdateCompanyGradeRequestDto dto
    ) {
        companyGradeCommandService.updateCompanyGrade(authUser.companyId(), gradeId, dto);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //회사 등급 삭제
    @DeleteMapping("/{gradeId}")
    public ResponseEntity<ApiResponse<Void>> deleteCompanyGrade(
            @CurrentUser AuthUser authUser,
            @PathVariable Long gradeId
    ) {
        companyGradeCommandService.deleteCompanyGrade(authUser.companyId(), gradeId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
