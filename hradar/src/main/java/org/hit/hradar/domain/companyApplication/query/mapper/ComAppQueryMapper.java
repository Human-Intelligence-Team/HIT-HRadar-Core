package org.hit.hradar.domain.companyApplication.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppListItemResponse;

import java.util.List;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppSearchRequest;

@Mapper
public interface ComAppQueryMapper {
  List<ComAppListItemResponse> selectCompanyApplications(ComAppSearchRequest request);
}
