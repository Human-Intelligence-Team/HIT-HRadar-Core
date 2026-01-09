package org.hit.hradar.domain.competencyReport.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.domain.infrastructure.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagCommandService {

 private final TagRepository tagRepository;

  /**
   * 태그 등록
   * @param tagName
   */
  public void createTag(String tagName) {

    tagRepository.save(tagName);
  }
}
