package org.hit.hradar.domain.competencyReport.command.application.service;


import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Tag;
import org.hit.hradar.domain.competencyReport.command.domain.infrastructure.repository.TagRepository;
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
   * @param tagName
   */
  @Transactional
  public void createTag(String tagName) {

    // validation check
    if  (tagName == null || tagName.isBlank()) {
      throw new BusinessException(CompetencyReportErrorCode.TAG_NAME_REQUIRED);
    }

    String normalizedTagName = tagName.trim();

    if (tagRepository.existsByTagName(normalizedTagName)) {
      throw new BusinessException(CompetencyReportErrorCode.TAG_NAME_ALREADY_EXISTS);
    }

    // create
    Tag tag = Tag.create(normalizedTagName);
    tagRepository.save(tag);
  }
}
