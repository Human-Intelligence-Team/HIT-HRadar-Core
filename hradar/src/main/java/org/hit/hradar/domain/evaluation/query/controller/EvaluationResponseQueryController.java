package org.hit.hradar.domain.evaluation.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationResponseQueryDto;
import org.hit.hradar.domain.evaluation.query.service.EvaluationResponseQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-responses")
public class EvaluationResponseQueryController {

    private final EvaluationResponseQueryService queryService;


    @GetMapping("/{assignmentId}")
    public ResponseEntity<List<EvaluationResponseQueryDto>> getResponses(
            @PathVariable Long assignmentId
    ) {
        return ResponseEntity.ok(
                queryService.getResponses(assignmentId)
        );
    }
}
