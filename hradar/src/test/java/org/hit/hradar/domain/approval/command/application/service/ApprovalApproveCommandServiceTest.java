package org.hit.hradar.domain.approval.command.application.service;

import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.domain.aggregate.*;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalHistoryJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalLineJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalLineStepJpaRepository;
import org.hit.hradar.domain.approval.event.ApprovalEvent;
import org.hit.hradar.domain.approval.event.ApprovalEventPublisher;
import org.hit.hradar.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApprovalApproveCommandServiceTest {

    @InjectMocks
    private ApprovalApproveCommandService approveService;

    @Mock
    private ApprovalLineStepJpaRepository stepRepository;
    @Mock
    private ApprovalLineJpaRepository lineRepository;
    @Mock
    private ApprovalDocumentJpaRepository docRepository;
    @Mock
    private ApprovalHistoryJpaRepository historyRepository;
    @Mock
    private ApprovalEventPublisher eventPublisher;

    @Test
    @DisplayName("중간 승인 성공: 다음 결재자가 있는 경우 문서 상태 유지")
    void approve_IntermediateStep_Success() {
        // given
        Long docId = 10L;
        Long employeeId = 1003L;
        Long accountId = 1L;
        Long companyId = 1L;

        // Mock Document (In Progress)
        ApprovalDocument doc = mock(ApprovalDocument.class);
        when(doc.isApprovable()).thenReturn(true);
        when(doc.getCompanyId()).thenReturn(companyId);
        when(docRepository.findByDocIdAndCompanyId(docId, companyId))
                .thenReturn(Optional.of(doc));

        // Mock Line
        ApprovalLine line = mock(ApprovalLine.class);
        when(line.getLineId()).thenReturn(100L);
        when(lineRepository.findByDocId(docId))
                .thenReturn(Optional.of(line));

        // Mock Current Step (Pending, approverId matches accountId)
        ApprovalLineStep currentStep = mock(ApprovalLineStep.class);
        when(stepRepository.findFirstByLineIdAndApprovalStepStatusAndApproverIdOrderByStepOrderAsc(
                100L, ApprovalStepStatus.PENDING, accountId))
                .thenReturn(Optional.of(currentStep));

        // Mock Next Step (Waiting exists)
        when(stepRepository.existsByLineIdAndApprovalStepStatus(100L, ApprovalStepStatus.PENDING))
                .thenReturn(true); // After approval, if we simulate state? No, logic checks pending AFTER logic.
        // Wait, logic checks 'existsByLineIdAndApprovalStepStatus' for PENDING.
        // If findFirst...Pending is called, it returns something.
        // Then we call 'changeToPending' on next Waiting.
        // Then logic checks 'exists...Pending'.
        // If next WAITING became PENDING, it returns true.

        // Simulating the effect of "changeToPending" requires real objects or mocking the subsequent repository call logic.
        // Here we mock the RETURN value of existsByLineId...
        
        // Arrange flow:
        // 1. Doc found
        // 2. Line found
        // 3. Current Step found
        // 4. step.approve() called (void)
        // 5. Next Waiting step found (Optional.of(nextStep))
        // 6. history saved
        // 7. existsByLineId(PENDING) -> true (since next step became pending)

        ApprovalLineStep nextStep = mock(ApprovalLineStep.class);
        when(stepRepository.findFirstByLineIdAndApprovalStepStatusOrderByStepOrderAsc(
                100L, ApprovalStepStatus.WAITING))
                .thenReturn(Optional.of(nextStep));

        // Mock logic 7: check pending existence
        when(stepRepository.existsByLineIdAndApprovalStepStatus(100L, ApprovalStepStatus.PENDING))
                .thenReturn(true); 

        // when
        approveService.approve(docId, employeeId, accountId, companyId);

        // then
        verify(currentStep).approve(accountId); // Correct ID used for validation
        verify(nextStep).changeToPending();     // Next step activated
        verify(historyRepository).save(any(ApprovalHistory.class));
        verify(doc, never()).approve();         // Doc should NOT be approved yet
        
        // Verify Event (APPROVED event is always published per step?)
        verify(eventPublisher).publisher(any(ApprovalEvent.class));
    }

    @Test
    @DisplayName("최종 승인 성공: 더 이상 대기자가 없으면 문서 승인 처리")
    void approve_FinalStep_Success() {
        // given
        Long docId = 10L;
        Long employeeId = 1003L;
        Long accountId = 1L;
        Long companyId = 1L;

        ApprovalDocument doc = mock(ApprovalDocument.class);
        when(doc.isApprovable()).thenReturn(true);
        when(doc.getCompanyId()).thenReturn(companyId);
        when(docRepository.findByDocIdAndCompanyId(docId, companyId))
                .thenReturn(Optional.of(doc));

        ApprovalLine line = mock(ApprovalLine.class);
        when(line.getLineId()).thenReturn(100L);
        when(lineRepository.findByDocId(docId))
                .thenReturn(Optional.of(line));

        ApprovalLineStep currentStep = mock(ApprovalLineStep.class);
        when(stepRepository.findFirstByLineIdAndApprovalStepStatusAndApproverIdOrderByStepOrderAsc(
                100L, ApprovalStepStatus.PENDING, accountId))
                .thenReturn(Optional.of(currentStep));

        // No waiting steps
        when(stepRepository.findFirstByLineIdAndApprovalStepStatusOrderByStepOrderAsc(
                100L, ApprovalStepStatus.WAITING))
                .thenReturn(Optional.empty());

        // No pending steps remaining (after this one is done)
        when(stepRepository.existsByLineIdAndApprovalStepStatus(100L, ApprovalStepStatus.PENDING))
                .thenReturn(false);

        // when
        approveService.approve(docId, employeeId, accountId, companyId);

        // then
        verify(currentStep).approve(accountId);
        verify(doc).approve();  // Final approval
    }

    @Test
    @DisplayName("대리 결재 성공: Proxy Approver ID가 일치할 때")
    void approve_Proxy_Success() {
        // given
        Long docId = 10L;
        Long employeeId = 1003L;
        Long accountId = 2L; // Proxy ID
        Long companyId = 1L;

        ApprovalDocument doc = mock(ApprovalDocument.class);
        when(doc.isApprovable()).thenReturn(true);
        when(docRepository.findByDocIdAndCompanyId(docId, companyId)).thenReturn(Optional.of(doc));

        ApprovalLine line = mock(ApprovalLine.class);
        when(line.getLineId()).thenReturn(100L);
        when(lineRepository.findByDocId(docId)).thenReturn(Optional.of(line));

        // Primary search fails
        when(stepRepository.findFirstByLineIdAndApprovalStepStatusAndApproverIdOrderByStepOrderAsc(
                100L, ApprovalStepStatus.PENDING, accountId))
                .thenReturn(Optional.empty());

        // Proxy search succeeds
        ApprovalLineStep proxyStep = mock(ApprovalLineStep.class);
        when(stepRepository.findFirstByLineIdAndApprovalStepStatusAndProxyApproverIdOrderByStepOrderAsc(
                100L, ApprovalStepStatus.PENDING, accountId))
                .thenReturn(Optional.of(proxyStep));
        
        when(stepRepository.existsByLineIdAndApprovalStepStatus(100L, ApprovalStepStatus.PENDING))
                .thenReturn(true); // assume intermediate

        // when
        approveService.approve(docId, employeeId, accountId, companyId);

        // then
        verify(proxyStep).approve(accountId);
    }

    @Test
    @DisplayName("실패: 권한 없음 (본인도 대리인도 아님)")
    void approve_Fail_Forbidden() {
        // given
        Long docId = 10L;
        Long accountId = 999L; // Stranger
        Long companyId = 1L;

        ApprovalDocument doc = mock(ApprovalDocument.class);
        when(doc.isApprovable()).thenReturn(true);
        when(docRepository.findByDocIdAndCompanyId(docId, companyId)).thenReturn(Optional.of(doc));

        ApprovalLine line = mock(ApprovalLine.class);
        when(line.getLineId()).thenReturn(100L);
        when(lineRepository.findByDocId(docId)).thenReturn(Optional.of(line));

        // Both searches fail
        when(stepRepository.findFirstByLineIdAndApprovalStepStatusAndApproverIdOrderByStepOrderAsc(any(), any(), any()))
                .thenReturn(Optional.empty());
        when(stepRepository.findFirstByLineIdAndApprovalStepStatusAndProxyApproverIdOrderByStepOrderAsc(any(), any(), any()))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> approveService.approve(docId, 100L, accountId, companyId))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ApprovalErrorCode.NOT_ALLOWED_APPROVER);
    }

    @Test
    @DisplayName("실패: 문서가 승인 가능 상태가 아님 (예: 이미 완료됨)")
    void approve_Fail_DocNotApprovable() {
        // given
        Long docId = 10L;
        Long companyId = 1L;

        ApprovalDocument doc = mock(ApprovalDocument.class);
        when(docRepository.findByDocIdAndCompanyId(docId, companyId)).thenReturn(Optional.of(doc));
        // doc.isApprovable() returns false
        when(doc.isApprovable()).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> approveService.approve(docId, 100L, 1L, companyId))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ApprovalErrorCode.CANNOT_APPROVE_NON_PENDING_STEP);
    }
}
