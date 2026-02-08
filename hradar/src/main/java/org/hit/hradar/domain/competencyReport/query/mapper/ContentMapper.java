package org.hit.hradar.domain.competencyReport.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.competencyReport.query.dto.ContentDTO;
import org.hit.hradar.domain.competencyReport.query.dto.ContentRowDTO;
import org.hit.hradar.domain.competencyReport.query.dto.request.ContentSearchRequest;

@Mapper
public interface ContentMapper {

  List<ContentRowDTO> findAllContents(ContentSearchRequest request);

  ContentDTO findContentByContentId(
      @Param("id") Long id,
       @Param("comId") Long comId);

  List<ContentRowDTO> findContentByCompetencyReportId(
      @Param("competencyReportId") Long competencyReportId,
      @Param("comId") Long comId);
}
