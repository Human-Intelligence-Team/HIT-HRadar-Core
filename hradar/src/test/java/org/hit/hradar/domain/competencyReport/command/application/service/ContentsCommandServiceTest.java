package org.hit.hradar.domain.competencyReport.command.application.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentCreateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentUpdateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.response.ContentUpdateResponse;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Content;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentRepository;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentTagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ContentsCommandServiceTest {
  @InjectMocks
  private ContentsCommandService contentsCommandService;

  @Mock
  private ContentRepository contentRepository;

  @Mock
  private ContentTagRepository contentTagRepository;

  @Test
  @DisplayName("컨텐츠 등록 성공: Reflection을 사용하여 실제 객체에 ID를 주입한다.")
  void createContents_Success() {
    // given
    List<Long> tagIds = List.of(1L, 2L);
    ContentCreateRequest request = new ContentCreateRequest(
        "제목",1L , "VIDEO", "BEGINNER", 60, "path", "notes", tagIds
    );

    // [변경점] save 호출 시, 전달받은 실제 객체에 ID를 강제로 심어줍니다.
    given(contentRepository.save(any(Content.class))).willAnswer(invocation -> {
      Content content = invocation.getArgument(0);
      // Reflection을 사용해 private 필드인 id에 100L을 주입
      ReflectionTestUtils.setField(content, "id", 100L);
      return content;
    });

    // when
    contentsCommandService.createContents(request, 1L);

    // then
    then(contentRepository).should(times(1)).save(any(Content.class));
    then(contentTagRepository).should(times(1)).saveAllWithPolicy(anyList());
  }

  @Test
  @DisplayName("컨텐츠 수정 성공: 기존 태그를 삭제하고 새로운 태그를 등록한다.")
  void updateContent_Success() {
    // given
    Long contentId = 100L;
    ContentUpdateRequest request = new ContentUpdateRequest(contentId, "수정 제목", "수정 내용", List.of(3L, 4L));
    Content existingContent = mock(Content.class);

    given(contentRepository.findByCompanyIdAndId(1L ,contentId)).willReturn(Optional.of(existingContent));

    // when
    ContentUpdateResponse response = contentsCommandService.updateContent(request, 1L);

    // then
    assertThat(response.getContentId()).isEqualTo(contentId);
    then(existingContent).should(times(1)).update(request); // 도메인 모델의 update 호출 확인
    then(contentTagRepository).should(times(1)).deleteAllByContentId(contentId);
    then(contentTagRepository).should(times(1)).saveAllWithPolicy(anyList());
  }

  @Test
  @DisplayName("컨텐츠 삭제 성공: 삭제 상태(Y)로 변경 후 저장한다.")
  void deleteContent_Success() {
    // given
    Long contentId = 100L;
    Content existingContent = mock(Content.class);
    given(existingContent.getIsDeleted()).willReturn('N');
    given(contentRepository.findByCompanyIdAndId(1L, contentId)).willReturn(Optional.of(existingContent));

    // when
    contentsCommandService.deleteContent(contentId, 1L);

    // then
    then(existingContent).should(times(1)).delete('Y');
    then(contentRepository).should(times(1)).save(existingContent);
  }

}