package org.hit.hradar.domain.evaluation.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationResponseDraftRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationResponseCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation/responses")
public class EvaluationResponseCommandController {

    private final EvaluationResponseCommandService responseCommandService;

    /*임시 저장*/
    @PostMapping("/draft")
    public ResponseEntity<ApiResponse<Void>> saveDraft(
            @RequestBody EvaluationResponseDraftRequest request,
            @CurrentUser AuthUser authUser
    ) {
        responseCommandService.saveDraft(
                request,
                authUser.employeeId()
        );

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/submit")
    public void submit(
            @RequestBody EvaluationResponseDraftRequest request,
            @CurrentUser AuthUser authUser
    ) {
        responseCommandService.submit(
                request,
                authUser.employeeId()
        );
    }
}
