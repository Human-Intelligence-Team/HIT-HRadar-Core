package org.hit.hradar.domain.notice.command.application.service;

import org.hit.hradar.domain.notice.NoticeErrorCode;
import org.hit.hradar.domain.notice.command.application.dto.NoticeCategoryDto;
import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeCategory;
import org.hit.hradar.domain.notice.command.domain.repository.NoticeCategoryRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoticeCategoryCommandServiceTest {

    @InjectMocks
    private NoticeCategoryCommandService service;

    @Mock
    private NoticeCategoryRepository noticeCategoryRepository;

    /**
     * 카테고리 생성 성공
     */
    @Test
    void create_success() {
        // given
        NoticeCategoryDto req = new NoticeCategoryDto(1L, "공지");

        when(noticeCategoryRepository.existsByCompanyIdAndNameAndIsDeletedNot(
                1L, "공지", 'Y'
        )).thenReturn(false);

        NoticeCategory saved = NoticeCategory.create(1L, "공지");

        when(noticeCategoryRepository.save(any(NoticeCategory.class)))
                .thenReturn(saved);

        // when
        Long id = service.create(req);

        // then
        assertThat(id).isNull(); // JPA id는 유닛 테스트에선 null이 정상

        verify(noticeCategoryRepository)
                .existsByCompanyIdAndNameAndIsDeletedNot(1L, "공지", 'Y');
        verify(noticeCategoryRepository).save(any(NoticeCategory.class));
    }

    /**
     * 카테고리 생성 실패 - 이름 중복
     */
    @Test
    void create_duplicateName_throwException() {
        // given
        NoticeCategoryDto req = new NoticeCategoryDto(1L, "공지");

        when(noticeCategoryRepository.existsByCompanyIdAndNameAndIsDeletedNot(
                1L, "공지", 'Y'
        )).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(NoticeErrorCode.DUPLICATED_CATEGORY_NAME);

        verify(noticeCategoryRepository, never()).save(any());
    }

    /**
     * 카테고리 수정 성공
     */
    @Test
    void update_success() {
        // given
        Long categoryId = 10L;
        Long companyId = 1L;

        NoticeCategory category = NoticeCategory.create(companyId, "공지");
        NoticeCategoryDto req = new NoticeCategoryDto(companyId, "변경");

        when(noticeCategoryRepository.findByIdAndCompanyId(categoryId, companyId))
                .thenReturn(Optional.of(category));

        // when
        service.update(categoryId, req);

        // then
        assertThat(category.getName()).isEqualTo("변경");
    }

    /**
     * 카테고리 수정 실패 - 대상 없음
     */
    @Test
    void update_notFound_throwException() {
        // given
        Long categoryId = 10L;
        Long companyId = 1L;

        NoticeCategoryDto req = new NoticeCategoryDto(companyId, "변경");

        when(noticeCategoryRepository.findByIdAndCompanyId(categoryId, companyId))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> service.update(categoryId, req))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(NoticeErrorCode.NOT_FOUND_CATEGORY);
    }

    /**
     * 카테고리 삭제 성공
     */
    @Test
    void delete_success() {
        // given
        Long categoryId = 10L;
        Long companyId = 1L;

        NoticeCategory category = NoticeCategory.create(companyId, "공지");

        when(noticeCategoryRepository.findByIdAndCompanyId(categoryId, companyId))
                .thenReturn(Optional.of(category));

        // when
        service.delete(categoryId, companyId);

        // then
        assertThat(category.getIsDeleted()).isEqualTo('Y');
    }

    /**
     * 카테고리 삭제 실패 - 대상 없음
     */
    @Test
    void delete_notFound_throwException() {
        // given
        Long categoryId = 10L;
        Long companyId = 1L;

        when(noticeCategoryRepository.findByIdAndCompanyId(categoryId, companyId))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> service.delete(categoryId, companyId))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(NoticeErrorCode.NOT_FOUND_CATEGORY);
    }
}
