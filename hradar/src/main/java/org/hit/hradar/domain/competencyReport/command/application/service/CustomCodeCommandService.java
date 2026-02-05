package org.hit.hradar.domain.competencyReport.command.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CustomCodeCreateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CustomCodeDeleteRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.response.CustomCodeExistResponse;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.CustomCode;
import org.hit.hradar.domain.competencyReport.command.domain.repository.CustomCodeRepository;
import org.hit.hradar.domain.competencyReport.competencyReportErrorCode.CompetencyReportErrorCode;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomCodeCommandService {

  private final CustomCodeRepository customCodeRepository;

  @Transactional
  public void createCustomCode(CustomCodeCreateRequest request, Long comId) {

    Long customCodeId = request.getCustomCodeId();
    CustomCode code = customCodeRepository.findByCustomCodeId(customCodeId);

    CustomCode createCode = CustomCode.create(
        comId, code.getGroupCode(), code.getGroupName(), request.getCustomCode(), request.getCustomName(),
        request.getCustomDesc());

    customCodeRepository.save(createCode);
  }

  public CustomCodeExistResponse existCustomCode(String customCode, Long comId) {

    Boolean exist = customCodeRepository.existsByCustomCodeAndComId(customCode, comId);
    return new CustomCodeExistResponse(exist);
  }

  @Transactional
  public void deleteCustomCode(CustomCodeDeleteRequest request, Long comId) {

    // validation check
    List<Long> codeIds = request.getCustomCodeIds();
    if (codeIds == null || codeIds.isEmpty()) {
      throw new BusinessException(CompetencyReportErrorCode.CUSTOM_CODE_ID_REQUIRED);
    }

    // customCodeRepository.deleteByCustomCodeIdInAndComId(codeIds, comId);

    customCodeRepository.deleteByComIdAndCustomCodeIdIn(comId, codeIds);

  }
}
