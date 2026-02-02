package org.hit.hradar.domain.goal.query.service;

import org.hit.hradar.domain.goal.query.dto.response.OkrListResponseDto;
import org.hit.hradar.domain.goal.query.mapper.OkrListMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OkrListQueryServiceTest {

    @InjectMocks
    private OkrListQueryService service;

    @Mock
    private OkrListMapper okrListMapper;

    @Test
    @DisplayName("OKR 목록 조회 - 성공")
    void getOkrs_success() {
        // given
        Long goalId = 1L;
        OkrListResponseDto dto = new OkrListResponseDto();
        
        when(okrListMapper.findOkrsByGoalId(goalId)).thenReturn(List.of(dto));

        // when
        List<OkrListResponseDto> result = service.getOkrs(goalId);

        // then
        assertThat(result).hasSize(1);
        verify(okrListMapper).findOkrsByGoalId(goalId);
    }
}
