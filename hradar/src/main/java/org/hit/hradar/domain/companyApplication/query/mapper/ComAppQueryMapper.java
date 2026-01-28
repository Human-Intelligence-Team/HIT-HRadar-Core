package org.hit.hradar.domain.companyApplication.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppDetailResponse;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppListItemResponse;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppSearchRequest;

@Mapper
public interface ComAppQueryMapper {

  List<ComAppListItemResponse> findList(ComAppSearchRequest request);

  ComAppDetailResponse findDetail(@Param("applicationId") Long applicationId);
}
