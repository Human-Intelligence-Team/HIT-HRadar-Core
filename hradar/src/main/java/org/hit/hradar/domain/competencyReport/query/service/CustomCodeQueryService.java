package org.hit.hradar.domain.competencyReport.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CustomCodeSearchRequest;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.CustomCode;
import org.hit.hradar.domain.competencyReport.command.domain.repository.CustomCodeRepository;
import org.hit.hradar.domain.competencyReport.query.dto.CustomCodeDTO;
import org.hit.hradar.domain.competencyReport.query.dto.response.CustomCodeSearchResponse;
import org.hit.hradar.domain.competencyReport.query.mapper.CustomCodeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomCodeQueryService {

  private final CustomCodeRepository customCodeRepository;
  private final CustomCodeMapper customCodeMapper;
  /**
   * 커스텀 코드 group code 조회
   * @return
   */
  public CustomCodeSearchResponse customCodeByGroups(Long comId) {

    List<CustomCodeDTO> customCodes = customCodeMapper.findGroupCodes(comId);
    return new CustomCodeSearchResponse(customCodes);

  }

  /**
   * 커스텀 코드 custom code 조회
   * @param comId
   * @param request
   * @return
   */
  public CustomCodeSearchResponse customCodesByCustomCode(Long comId, CustomCodeSearchRequest request) {

    CustomCode code = customCodeRepository.findByCustomCodeId(request.getCustomCodeId());
    String groupCode = code.getGroupCode();

    List<CustomCodeDTO> customCodes = customCodeMapper.findAllCustomCodeByGroupCode(comId, groupCode);
    return new CustomCodeSearchResponse(customCodes);
  }

  /**
   * 커스텀 목록들 가져오기
   * @param comId
   * @return
   */
  public CustomCodeSearchResponse customCodes(Long comId) {

    String groupCode;

    groupCode = "CONTENT_TYPE";
    List<CustomCodeDTO> typeCodes = customCodeMapper.findAllCustomCodes(comId, groupCode);

    groupCode = "LEVEL";
    List<CustomCodeDTO> levelCodes = customCodeMapper.findAllCustomCodes(comId, groupCode);

    return new CustomCodeSearchResponse(levelCodes, typeCodes);
  }
}
