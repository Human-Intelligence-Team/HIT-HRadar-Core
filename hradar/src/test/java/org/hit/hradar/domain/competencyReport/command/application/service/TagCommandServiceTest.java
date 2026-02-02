package org.hit.hradar.domain.competencyReport.command.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.TagCreateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.TagDeleteRequest;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Tag;
import org.hit.hradar.domain.competencyReport.command.domain.repository.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagCommandServiceTest {
  @InjectMocks
  private TagCommandService tagCommandService;

  @Mock
  private TagRepository tagRepository;

  @Test
  @DisplayName("태그 생성 성공: 중복된 이름이 없으면 정상적으로 저장된다.")
  void createTag_Success() {
    // given
    String tagName = "Java";
    TagCreateRequest request = new TagCreateRequest(tagName);

    // 중복 이름 확인 시 false 반환 설정
    given(tagRepository.existsByTagName(tagName)).willReturn(false);

    // when
    tagCommandService.createTag(request);

    // then
    then(tagRepository).should(times(1)).existsByTagName(tagName);
    then(tagRepository).should(times(1)).save(any(Tag.class));
  }

  @Test
  @DisplayName("태그 삭제 성공: 유효한 ID 리스트가 들어오면 삭제 쿼리가 실행된다.")
  void deleteTag_Success() {
    // given
    List<Long> tagIds = List.of(1L, 2L, 3L);
    TagDeleteRequest request = new TagDeleteRequest(tagIds);

    // when
    tagCommandService.deleteTag(request);

    // then
    then(tagRepository).should(times(1)).deleteByTagIdIn(tagIds);
  }

}