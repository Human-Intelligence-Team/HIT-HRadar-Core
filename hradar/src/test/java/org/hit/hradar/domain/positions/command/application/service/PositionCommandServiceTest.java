package org.hit.hradar.domain.positions.command.application.service;

import org.hit.hradar.domain.positions.command.application.dto.CreatePositionRequest;
import org.hit.hradar.domain.positions.command.application.dto.UpdatePositionRequest;
import org.hit.hradar.domain.positions.command.domain.aggregate.Positions;
import org.hit.hradar.domain.positions.command.domain.repository.PositionRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PositionCommandServiceTest {

    @InjectMocks
    private PositionCommandService positionCommandService;

    @Mock
    private PositionRepository positionRepository;

    @Test
    @DisplayName("직급 등록 성공")
    void createPosition_Success() {
        // given
        Long comId = 1L;
        CreatePositionRequest request = new CreatePositionRequest("New Position", 5);

        given(positionRepository.existsByNameAndComIdAndIsDeleted("New Position", comId, 'N')).willReturn(false);

        // when
        positionCommandService.createPosition(request, comId);

        // then
        verify(positionRepository).save(any(Positions.class));
    }

    @Test
    @DisplayName("직급 등록 실패 - 중복된 이름")
    void createPosition_Fail_Duplicate() {
        // given
        Long comId = 1L;
        CreatePositionRequest request = new CreatePositionRequest("Duplicate Name", 1);

        given(positionRepository.existsByNameAndComIdAndIsDeleted("Duplicate Name", comId, 'N')).willReturn(true);

        // when & then
        assertThatThrownBy(() -> positionCommandService.createPosition(request, comId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("이미 존재하는 직급명입니다.");
    }

    @Test
    @DisplayName("직급 삭제 성공")
    void deletePosition_Success() {
        // given
        Long positionId = 10L;
        Long comId = 1L;
        Positions position = Positions.builder()
                .positionId(positionId)
                .comId(comId)
                .name("Test")
                .rank(1)
                .build();

        given(positionRepository.findByPositionIdAndComIdAndIsDeleted(positionId, comId, 'N'))
                .willReturn(Optional.of(position));

        // when
        positionCommandService.deletePosition(positionId, comId);

        // then
        assertThat(position.getIsDeleted()).isEqualTo('Y');
    }

    @Test
    @DisplayName("직급 수정 성공")
    void updatePosition_Success() {
        // given
        Long positionId = 10L;
        Long comId = 1L;
        UpdatePositionRequest request = new UpdatePositionRequest("Updated Rank", 3);

        Positions position = Positions.builder()
                .positionId(positionId)
                .comId(comId)
                .name("Old Rank")
                .rank(1)
                .build();

        given(positionRepository.findByPositionIdAndComIdAndIsDeleted(positionId, comId, 'N'))
                .willReturn(Optional.of(position));

        // when
        positionCommandService.updatePosition(positionId, comId, request);

        // then
        assertThat(position.getName()).isEqualTo("Updated Rank");
    }
}
