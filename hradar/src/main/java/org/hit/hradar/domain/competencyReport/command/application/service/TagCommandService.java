package org.hit.hradar.domain.competencyReport.command.application.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.TagCreateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.TagDeleteRequest;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Tag;
import org.hit.hradar.domain.competencyReport.command.domain.repository.TagRepository;
import org.hit.hradar.domain.competencyReport.competencyReportErrorCode.CompetencyReportErrorCode;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagCommandService {

 private final TagRepository tagRepository;

  /**
   * 태그 등록
   * @param request
   */
  @Transactional
  public void createTag(TagCreateRequest request, Long comId) {

    String normalizedTagName = request.getTagName().trim();

    if (tagRepository.existsByTagName(normalizedTagName)) {
      throw new BusinessException(CompetencyReportErrorCode.TAG_NAME_ALREADY_EXISTS);
    }

    // create
    Tag tag = Tag.create(normalizedTagName,  comId);
    tagRepository.save(tag);
  }

  /**
   * 태그 삭제 (단건/다건)
   * @param tagDeleteRequest
   */
  @Transactional
  public void deleteTag(TagDeleteRequest tagDeleteRequest) {

    // validation check
    List<Long> tagIds = tagDeleteRequest.getTagIds();
    if (tagIds == null || tagIds.isEmpty()) {
      throw new BusinessException(CompetencyReportErrorCode.TAG_ID_REQUIRED);
    }

    tagRepository.deleteByTagIdIn(tagIds);
  }
}
