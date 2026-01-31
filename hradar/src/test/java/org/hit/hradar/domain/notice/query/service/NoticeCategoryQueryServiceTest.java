package org.hit.hradar.domain.notice.query.service;

import org.hit.hradar.domain.notice.query.dto.response.NoticeCategoryResponse;
import org.hit.hradar.domain.notice.query.mapper.NoticeCategoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoticeCategoryQueryServiceTest {

    @Mock
    private NoticeCategoryMapper mapper;

    @InjectMocks
    private NoticeCategoryQueryService service;

    @Test
    void 회사별_카테고리_조회() {
        // given
        Long companyId = 1L;

        List<NoticeCategoryResponse> responses = List.of(
                new NoticeCategoryResponse(1L, "공지"),
                new NoticeCategoryResponse(2L, "이벤트")
        );

        when(mapper.findByCompany(companyId)).thenReturn(responses);

        // when
        List<NoticeCategoryResponse> result = service.findByCompany(companyId);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("공지");

        verify(mapper).findByCompany(companyId);
    }
}
