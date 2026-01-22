package org.hit.hradar.domain.companyApplication.command.application.service;

import org.hit.hradar.domain.companyApplication.CompanyApplicationErrorCode;
import org.hit.hradar.domain.companyApplication.command.application.dto.CreateComAppRequest;
import org.hit.hradar.domain.companyApplication.command.application.dto.CreateComAppResponse;
import org.hit.hradar.domain.companyApplication.command.application.dto.RejectComAppResponse;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;
import org.hit.hradar.domain.companyApplication.command.domain.repository.ComAppRepository;
import org.hit.hradar.domain.companyApplication.command.infrastructure.ComAppJpaRepository;
import org.hit.hradar.domain.user.command.application.service.UserService;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComAppCommandService {

  private final ComAppRepository comAppRepository;
  private final ComAppJpaRepository comAppJpaRepository;
  private final UserService userService;



  @Transactional
  public CreateComAppResponse create(CreateComAppRequest req) {
    // 신청서 생성
    CompanyApplication app = CompanyApplication.builder()
        .companyName(req.getCompanyName())
        .bizNo(req.getBizNo())
        .comTel(req.getComTel())
        .address(req.getAddress())
        .status(CompanyApplicationStatus.SUBMITTED)
        .name(req.getName())
        .email(req.getEmail())
        .loginId(req.getLoginId())
        .build();

    CompanyApplication saved = comAppRepository.save(app);

    return CreateComAppResponse.builder()
        .applicationId(saved.getAppId())
        .companyApplicationStatus(saved.getStatus().name())
        .build();
  }

  @Transactional
  public RejectComAppResponse reject(Long applicationId, Long reviewerUserId, String reason) {
    // 신청서 반려
    userService.requirePlatformAdmin(reviewerUserId);

    CompanyApplication app = comAppJpaRepository.findByIdForUpdate(applicationId)
        .orElseThrow(() -> new BusinessException(CompanyApplicationErrorCode.APPLICATION_NOT_FOUND));

    app.rejectNow(reason, reviewerUserId);

    return RejectComAppResponse.builder()
        .appId(app.getAppId())
        .status(app.getStatus())
        .rejectReason(app.getRejectReason())
        .reviewedAt(app.getReviewedAt())
        .build();
  }
}


