package org.hit.hradar.domain.approval.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.query.service.ApprovalQueryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalQueryController {

  private final ApprovalQueryService approvalQueryService;



}
