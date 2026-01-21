package org.hit.hradar.domain.grading.command.aplication.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterGradeObjectionRequestDto;
import org.hit.hradar.domain.grading.command.aplication.dto.request.ResolveGradeObjectionRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.GradeObjectionCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grade-objections")
public class GradeObjectionCommandController {
    private final GradeObjectionCommandService gradeObjectionCommandService;

    // 이의제기 등록
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerObjection(
            @RequestBody RegisterGradeObjectionRequestDto request
    ) {
        gradeObjectionCommandService.registerObjection(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // 승인
    @PostMapping("/{objectionId}/accept")
    public ResponseEntity<ApiResponse<Void>> acceptObjection(
            @PathVariable Long objectionId,
            @RequestBody ResolveGradeObjectionRequestDto request,
            @CurrentUser AuthUser authUser
    ) {
        gradeObjectionCommandService.acceptObjection(
                objectionId,
                request,
                authUser.employeeId()
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // 반려
    @PostMapping("/{objectionId}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectObjection(
            @PathVariable Long objectionId,
            @RequestBody ResolveGradeObjectionRequestDto request,
            @CurrentUser AuthUser authUser
    ) {
        gradeObjectionCommandService.rejectObjection(
                objectionId,
                request,
                authUser.employeeId()
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
