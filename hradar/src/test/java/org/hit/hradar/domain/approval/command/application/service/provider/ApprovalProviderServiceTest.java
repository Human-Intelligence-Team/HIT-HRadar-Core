package org.hit.hradar.domain.approval.command.application.service.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalDraftCreateRequest;
import org.hit.hradar.domain.approval.command.domain.aggregate.*;
import org.hit.hradar.domain.approval.command.infrastructure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApprovalProviderServiceTest {

    @InjectMocks
    private ApprovalProviderService providerService;

    @Mock
    private ApprovalDocumentJpaRepository approvalDocumentJpaRepository;
    @Mock
    private ApprovalLineJpaRepository approvalLineJpaRepository;
    @Mock
    private ApprovalLineStepJpaRepository approvalLineStepJpaRepository;
    @Mock
    private ApprovalHistoryJpaRepository approvalHistoryJpaRepository;
    @Mock
    private ApprovalReferenceJpaRepository approvalReferenceJpaRepository;
    @Mock
    private ApprovalPayloadJpaRepository approvalPayloadJpaRepository;
    @Mock
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("신규 임시저장 성공: DRAFT 모드, 문서 생성만 되고 상신되지 않음")
    void save_Draft_New_Success() {
        // given
        Long writerId = 100L;
        Long companyId = 1L;
        ApprovalDraftCreateRequest request = ApprovalDraftCreateRequest.builder()
                .title("Title")
                .content("Content")
                .docType("LEAVE")
                .Payload(mock(JsonNode.class))
                .approverIds(List.of(200L, 300L))
                .referenceIds(List.of(400L))
                .build();

        when(approvalDocumentJpaRepository.save(any(ApprovalDocument.class))).thenAnswer(invocation -> {
            ApprovalDocument doc = invocation.getArgument(0);
            // Simulate ID generation
            ReflectionUtils.setField(doc, "docId", 123L); 
            return doc;
        });

        // Line creation
        when(approvalLineJpaRepository.save(any(ApprovalLine.class))).thenAnswer(invocation -> {
            ApprovalLine line = invocation.getArgument(0);
            ReflectionUtils.setField(line, "lineId", 999L);
            return line;
        });
        
        // when
        Long docId = providerService.save(null, writerId, companyId, request, ApprovalSaveMode.DRAFT);

        // then
        assertThat(docId).isEqualTo(123L);
        verify(approvalDocumentJpaRepository).save(any(ApprovalDocument.class));
        verify(approvalLineStepJpaRepository).saveAll(anyList());
        
        // Verify submit logic NOT called
        verify(approvalHistoryJpaRepository, never()).save(any(ApprovalHistory.class));
        // Verify step activation NOT called (no findFirst...WAITING)
        verify(approvalLineStepJpaRepository, never())
            .findFirstByLineIdAndApprovalStepStatusOrderByStepOrderAsc(any(), eq(ApprovalStepStatus.WAITING));
    }

    @Test
    @DisplayName("신규 상신 성공: SUBMIT 모드, 문서 생성 후 상태 변경 및 첫 번째 결재자 활성화")
    void save_Submit_New_Success() {
        // given
        Long writerId = 100L;
        Long companyId = 1L;
        ApprovalDraftCreateRequest request = ApprovalDraftCreateRequest.builder()
                .title("Title")
                .content("Content")
                .docType("LEAVE")
                .Payload(mock(JsonNode.class))
                .approverIds(List.of(200L, 300L))
                .referenceIds(List.of())
                .build();

        // 1. Doc Save
        when(approvalDocumentJpaRepository.save(any(ApprovalDocument.class))).thenAnswer(inv -> {
            ApprovalDocument doc = inv.getArgument(0);
            ReflectionUtils.setField(doc, "docId", 123L);
            return doc;
        });
        
        // 2. Line Save
        when(approvalLineJpaRepository.save(any(ApprovalLine.class))).thenAnswer(inv -> {
            ApprovalLine line = inv.getArgument(0);
            ReflectionUtils.setField(line, "lineId", 999L);
            return line;
        });

        // 3. Find Line for Submit phase
        ApprovalLine line = mock(ApprovalLine.class);
        when(line.getLineId()).thenReturn(999L);
        when(approvalLineJpaRepository.findByDocId(123L)).thenReturn(Optional.of(line));

        // 4. Find First Waiting Step
        ApprovalLineStep firstStep = mock(ApprovalLineStep.class);
        when(approvalLineStepJpaRepository.findFirstByLineIdAndApprovalStepStatusOrderByStepOrderAsc(
                999L, ApprovalStepStatus.WAITING))
                .thenReturn(Optional.of(firstStep));

        // when
        Long docId = providerService.save(null, writerId, companyId, request, ApprovalSaveMode.SUBMIT);

        // then
        assertThat(docId).isEqualTo(123L);
        verify(firstStep).changeToPending(); // Activated
        verify(approvalHistoryJpaRepository).save(any(ApprovalHistory.class)); // Submit history
    }

    @Test
    @DisplayName("수정 후 상신 성공: 기존 임시저장 문서를 수정하여 상신")
    void save_Submit_Update_Success() {
        // given
        Long docId = 123L;
        Long writerId = 100L;
        Long companyId = 1L;
        ApprovalDraftCreateRequest request = ApprovalDraftCreateRequest.builder()
                .title("New Title")
                .content("New Content")
                .docType("LEAVE")
                .Payload(null)
                .approverIds(List.of(200L))
                .referenceIds(List.of())
                .build();

        ApprovalDocument doc = mock(ApprovalDocument.class);
        // when(doc.getDocId()).thenReturn(docId); // Unnecessary
        when(doc.isOwner(writerId)).thenReturn(true);
        when(doc.isDraft()).thenReturn(true); // Existing is DRAFT
        when(approvalDocumentJpaRepository.findById(docId)).thenReturn(Optional.of(doc));

        ApprovalLine line = mock(ApprovalLine.class);
        when(line.getLineId()).thenReturn(999L);
        when(approvalLineJpaRepository.findByDocId(docId)).thenReturn(Optional.of(line));

        ApprovalLineStep firstStep = mock(ApprovalLineStep.class);
        when(approvalLineStepJpaRepository.findFirstByLineIdAndApprovalStepStatusOrderByStepOrderAsc(
                999L, ApprovalStepStatus.WAITING))
                .thenReturn(Optional.of(firstStep));

        // when
        providerService.save(docId, writerId, companyId, request, ApprovalSaveMode.SUBMIT);

        // then
        verify(doc).update(eq("New Title"), eq("New Content"), eq("LEAVE"));
        verify(doc).submit(writerId); // Changed status to IN_PROGRESS
        
        // Steps regenerated
        verify(approvalLineStepJpaRepository).deleteByLineId(999L);
        verify(approvalLineStepJpaRepository).saveAll(anyList());
        
        // Activated
        verify(firstStep).changeToPending();
    }
    
    // Helper to set ID on private fields if needed (though existing mocks handle it mostly)
    static class ReflectionUtils {
        static void setField(Object target, String fieldName, Object value) {
            try {
                java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
