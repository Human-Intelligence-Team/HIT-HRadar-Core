package org.hit.hradar.domain.companyApplication.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppListItemResponse;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppSearchRequest;
import org.hit.hradar.domain.companyApplication.query.mapper.ComAppQueryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComAppQueryService {

  private final ComAppQueryMapper comAppQueryMapper;

  public List<ComAppListItemResponse> search(ComAppSearchRequest request) {
    return comAppQueryMapper.selectCompanyApplications(request);
  }
}
