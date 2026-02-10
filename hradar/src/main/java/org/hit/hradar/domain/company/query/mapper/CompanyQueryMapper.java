package org.hit.hradar.domain.company.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.company.query.dto.CompanyDetailResponse;
import org.hit.hradar.domain.company.query.dto.CompanyResponse;
import org.hit.hradar.domain.company.query.dto.CompanySearchCondition;

@Mapper
public interface CompanyQueryMapper {

  CompanyDetailResponse findById(@Param("companyId") Long companyId);

  List<CompanyResponse> findAll(
      @Param("cond") CompanySearchCondition cond,
      @Param("offset") int offset,
      @Param("size") int size);

  long count(@Param("cond") CompanySearchCondition cond);
}
