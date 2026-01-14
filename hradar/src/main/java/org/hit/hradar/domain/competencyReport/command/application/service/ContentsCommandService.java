package org.hit.hradar.domain.competencyReport.command.application.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentsRequest;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Content;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentTag;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentRepository;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentsCommandService {

  private final ContentRepository contentsRepository;
  private final ContentTagRepository contentTagRepository;

  /**
   * 학습 컨텐츠 등록
   * @param request
   */
  @Transactional
  public void createContents(ContentsRequest request){

    // 학습 컨텐츠 등록
    Content content = Content.create(request);
    contentsRepository.save(content);

    // 학습 컨텐츠 ID로 학습 컨텐츠로 tag 연결
    Long contentId = content.getId();
    List<Long> tagIds = request.getTags();

    if (tagIds != null && !tagIds.isEmpty()) {
      List<ContentTag> contentTags = tagIds.stream()
          .map(tagId -> ContentTag.create(contentId, tagId))
          .toList();

      // contentTagRepository.saveAll(contentTags);
      contentTagRepository.saveAllWithPolicy(contentTags);

    }
  }
}
