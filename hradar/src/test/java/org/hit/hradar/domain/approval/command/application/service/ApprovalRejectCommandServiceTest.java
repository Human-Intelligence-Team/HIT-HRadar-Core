package org.hit.hradar.domain.approval.command.application.service;

import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.domain.aggregate.*;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalHistoryJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalLineJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalLineStepJpaRepository;
import org.hit.hradar.domain.approval.event.ApprovalEvent;
import org.hit.hradar.domain.approval.event.ApprovalEventPublisher;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.RejectionEvent;
import org.hit.hradar.domain.evaluation.command.infrastructure.RejectionEventJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApprovalRejectCommandServiceTest {

    @InjectMocks
    private ApprovalRejectCommandService rejectService;

    @Mock
    private ApprovalDocumentJpaRepository docRepository;
    @Mock
    private ApprovalLineJpaRepository lineRepository;
    @Mock
    private ApprovalLineStepJpaRepository stepRepository;
    @Mock
    private ApprovalHistoryJpaRepository historyRepository;
    @Mock
    private RejectionEventJpaRepository rejectionEventRepository;
    @Mock
    private ApprovalEventPublisher eventPublisher;

    @Test
    @DisplayName("반려 성공: 문서와 결재 단계를 찾아 반려 처리")
    void reject_Success() {
        // given
        Long docId = 10L;
        Long employeeId = 1003L;
        Long accountId = 1L;
        String reason = "Not Good";

        ApprovalDocument doc = mock(ApprovalDocument.class);
        when(doc.isApprovable()).thenReturn(true);
        when(doc.getWriterId()).thenReturn(200L); // Writer for RejectionEvent
        when(docRepository.findById(docId)).thenReturn(Optional.of(doc));

        ApprovalLine line = mock(ApprovalLine.class);
        when(line.getLineId()).thenReturn(100L);
        when(lineRepository.findByDocId(docId)).thenReturn(Optional.of(line));

        ApprovalLineStep currentStep = mock(ApprovalLineStep.class);
        when(stepRepository.findFirstByLineIdAndApprovalStepStatusAndApproverIdOrderByStepOrderAsc(
                100L, ApprovalStepStatus.PENDING, accountId))
                .thenReturn(Optional.of(currentStep));

        // when
        rejectService.reject(docId, employeeId, accountId, reason);

        // then
        verify(currentStep).reject(accountId, reason);
        verify(historyRepository).save(any(ApprovalHistory.class));
        verify(rejectionEventRepository).save(any(RejectionEvent.class));
        verify(doc).reject();
        verify(eventPublisher).publisher(any(ApprovalEvent.class));
    }

    @Test
    @DisplayName("실패: 반려 사유 누락")
    void reject_Fail_NoReason() {
        // when & then
        assertThatThrownBy(() -> rejectService.reject(1L, 1L, 1L, ""))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ApprovalErrorCode.REJECT_REASON_REQUIRED);
    }
}
