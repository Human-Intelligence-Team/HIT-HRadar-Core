package org.hit.hradar.domain.goal.query.service;

import org.hit.hradar.domain.goal.query.dto.response.OkrDetailResponseDto;
import org.hit.hradar.domain.goal.query.mapper.OkrDetailMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OkrDetailQueryServiceTest {

    @InjectMocks
    private OkrDetailQueryService service;

    @Mock
    private OkrDetailMapper okrDetailMapper;

    @Test
    @DisplayName("OKR Key Result 상세 조회 - 성공")
    void getKrDetail_success() {
        // given
        Long krId = 1L;
        OkrDetailResponseDto response = new OkrDetailResponseDto();

        when(okrDetailMapper.findKrSummary(krId)).thenReturn(response);
        when(okrDetailMapper.findKrLogs(krId)).thenReturn(Collections.emptyList());

        // when
        OkrDetailResponseDto result = service.getKrDetail(krId);

        // then
        assertThat(result).isNotNull();
        verify(okrDetailMapper).findKrSummary(krId);
        verify(okrDetailMapper).findKrLogs(krId);
    }
}
