package org.hit.hradar.domain.competencyReport.command.application.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentUpdateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.response.ContentUpdateResponse;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentCreateRequest;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Content;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentTag;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentRepository;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentTagRepository;
import org.hit.hradar.domain.competencyReport.competencyReportErrorCode.CompetencyReportErrorCode;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentsCommandService {

  private final ContentRepository contentRepository;
  private final ContentTagRepository contentTagRepository;

  /**
   * 학습 컨텐츠 등록
   * @param request
   */
  @Transactional
  public void createContents(ContentCreateRequest request, Long comId){

    // 학습 컨텐츠 등록
    request.setComId(comId);
    Content content = Content.create(request);
    contentRepository.save(content);

    // 학습 컨텐츠 ID로 학습 컨텐츠로 tag 연결
    Long contentId = content.getId();
    List<Long> tagIds = request.getTags();

    // content-tag create
    createContentTags(contentId, tagIds);
  }

  @Transactional
  public ContentUpdateResponse updateContent(ContentUpdateRequest request, Long comId) {

    // exist
    Long contentId = request.getContentId();
    Content content = contentRepository.findByCompanyIdAndId(comId, contentId)
        .orElseThrow(() -> new BusinessException(CompetencyReportErrorCode.CONTENT_NOT_FOUND));

    // update - content
    content.update(request);

    // update - tag
    List<Long> tagIds = request.getTags();
    contentTagRepository.deleteAllByContentId(contentId); // delete
    createContentTags(contentId, tagIds); // content-tag create

    return new ContentUpdateResponse(contentId);

  }

  /**
   * 학습컨텐츠 태그 추가
   * @param contentId
   * @param tagIds
   */
  private void createContentTags(Long contentId, List<Long> tagIds){

    if (tagIds != null && !tagIds.isEmpty()) {
      List<ContentTag> contentTags = tagIds.stream()
          .map(tagId -> ContentTag.create(contentId, tagId))
          .toList();

      contentTagRepository.saveAllWithPolicy(contentTags);
    }
  }

  @Transactional
  public void deleteContent(Long contentId, Long  comId) {

    if (contentId == null) {
      throw new BusinessException(CompetencyReportErrorCode.CONTENT_ID_REQUIRED);
    }

    Content content = contentRepository.findByCompanyIdAndId(comId, contentId)
        .orElseThrow(() ->
            new BusinessException(CompetencyReportErrorCode.CONTENT_NOT_FOUND)
        );

    if ("Y".equals(content.getIsDeleted())) {
      throw new BusinessException(CompetencyReportErrorCode.CONTENT_ALREADY_DELETED);
    }

    // soft delete
    content.delete('Y');
    contentRepository.save(content);

    contentTagRepository.deleteAllByContentId(contentId);

  }

}
