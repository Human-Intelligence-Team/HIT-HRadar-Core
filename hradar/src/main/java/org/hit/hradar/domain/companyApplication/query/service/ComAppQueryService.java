package org.hit.hradar.domain.companyApplication.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.companyApplication.CompanyApplicationErrorCode;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppDetailResponse;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppListItemResponse;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppSearchRequest;
import org.hit.hradar.domain.companyApplication.query.mapper.ComAppQueryMapper;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.global.dto.AuthUser;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComAppQueryService {

  private final ComAppQueryMapper comAppQueryMapper;

  public List<ComAppListItemResponse> search(AuthUser authUser, ComAppSearchRequest request) {

    if (authUser == null || authUser.role() == null || !"admin".equalsIgnoreCase(authUser.role())) {
      throw new BusinessException(UserErrorCode.FORBIDDEN);
    }

    if (request == null) {
      request = new ComAppSearchRequest();
    }

    if (request.getCompanyName() != null) {
      String v = request.getCompanyName().trim();
      request.setCompanyName(v.isEmpty() ? null : v);
    }

    if (request.getBizNo() != null) {
      String v = request.getBizNo().trim();
      request.setBizNo(v.isEmpty() ? null : v);
    }

    if (request.getStatus() != null) {
      String v = request.getStatus().trim();
      request.setStatus(v.isEmpty() ? null : v);
    }

    return comAppQueryMapper.findList(request);
  }

  public ComAppDetailResponse getDetail(AuthUser authUser, Long applicationId) {

    if (authUser == null || authUser.role() == null || !"admin".equalsIgnoreCase(authUser.role())) {
      throw new BusinessException(UserErrorCode.FORBIDDEN);
    }

    ComAppDetailResponse detail = comAppQueryMapper.findDetail(applicationId);
    if (detail == null) {
      throw new BusinessException(CompanyApplicationErrorCode.APPLICATION_NOT_FOUND);
    }
    return detail;
  }

}
