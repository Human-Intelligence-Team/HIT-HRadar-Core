package org.hit.hradar.domain.companyApplication.command.application.service;

import org.hit.hradar.domain.companyApplication.command.application.dto.CreateComAppRequest;
import org.hit.hradar.domain.companyApplication.command.application.dto.CreateComAppResponse;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;
import org.hit.hradar.domain.companyApplication.command.domain.repository.ComAppRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComAppCommandService {

  private final ComAppRepository comAppRepository;

  // 신청서 생성
  @Transactional
  public CreateComAppResponse create(CreateComAppRequest req) {

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

}


