package org.hit.hradar.domain.company.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.company.query.dto.CompanyDetailResponse;
import org.hit.hradar.domain.company.query.dto.CompanyResponse;


@Mapper
public interface CompanyQueryMapper {


  CompanyDetailResponse findById(@Param("companyId") Long companyId);

  List<CompanyResponse> findAll(
      @Param("keyword") String keyword,
      @Param("includeDeleted") Boolean includeDeleted
  );
}
