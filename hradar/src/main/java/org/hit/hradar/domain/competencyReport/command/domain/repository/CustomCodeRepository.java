package org.hit.hradar.domain.competencyReport.command.domain.repository;


import java.util.List;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.CustomCode;

public interface CustomCodeRepository {

  CustomCode save(CustomCode code);

  CustomCode findByCustomCodeId(Long customCodeId);

  Boolean existsByCustomCodeAndComId(String customCode, Long comId);

  void deleteByComIdAndCustomCodeIdIn(Long comId, List<Long> codeIds);
}
