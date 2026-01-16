package org.hit.hradar.domain.competencyReport.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.competencyReport.query.dto.CompetencyReportDTO;
import org.hit.hradar.domain.competencyReport.query.dto.CycleDTO;
import org.hit.hradar.domain.competencyReport.query.dto.request.CompetencyReportSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.request.CompReportCycleSearchRequest;
import org.springframework.data.repository.query.Param;

@Mapper
public interface CompetencyReportMapper {

  List<CompetencyReportDTO> findAllByUserId(
      @Param("userId") Long userId,
      @Param("request") CompetencyReportSearchRequest request
  );

  List<CompetencyReportDTO> findAllByDepthId(CompetencyReportSearchRequest request);

  List<CycleDTO> findAllCycle(CompReportCycleSearchRequest request);

  List<CompetencyReportDTO> findAllByCycleId(CompReportCycleSearchRequest request);
}
