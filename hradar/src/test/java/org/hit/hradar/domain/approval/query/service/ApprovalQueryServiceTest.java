package org.hit.hradar.domain.approval.query.service;

import org.hit.hradar.domain.approval.query.dto.response.ApprovalMyDocumentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalReferenceDocumentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalRejectedDocumentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalTaskResponse;
import org.hit.hradar.domain.approval.query.mapper.ApprovalQueryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApprovalQueryServiceTest {

    @InjectMocks
    private ApprovalQueryService approvalQueryService;

    @Mock
    private ApprovalQueryMapper approvalQueryMapper;

    @Test
    @DisplayName("내 문서함 조회")
    void findMyDocuments() {
        Long empId = 1L;
        when(approvalQueryMapper.selectMyDocuments(empId)).thenReturn(Collections.emptyList());

        List<ApprovalMyDocumentResponse> result = approvalQueryService.findMyDocuments(empId);

        assertThat(result).isEmpty();
        verify(approvalQueryMapper).selectMyDocuments(empId);
    }

    @Test
    @DisplayName("결재 대기 문서함 조회")
    void findApprovalTasks() {
        Long empId = 1L;
        when(approvalQueryMapper.selectApprovalTasks(empId)).thenReturn(Collections.emptyList());

        List<ApprovalTaskResponse> result = approvalQueryService.findApprovalTasks(empId);

        assertThat(result).isEmpty();
        verify(approvalQueryMapper).selectApprovalTasks(empId);
    }

    @Test
    @DisplayName("반려 문서함 조회")
    void findRejectedDocuments() {
        Long empId = 1L;
        when(approvalQueryMapper.selectRejectedDocuments(empId)).thenReturn(Collections.emptyList());

        List<ApprovalRejectedDocumentResponse> result = approvalQueryService.findRejectedDocuments(empId);

        assertThat(result).isEmpty();
        verify(approvalQueryMapper).selectRejectedDocuments(empId);
    }

    @Test
    @DisplayName("참조 문서함 조회")
    void findReferenceDocuments() {
        Long empId = 1L;
        when(approvalQueryMapper.selectReferenceDocuments(empId)).thenReturn(Collections.emptyList());

        List<ApprovalReferenceDocumentResponse> result = approvalQueryService.findReferenceDocuments(empId);

        assertThat(result).isEmpty();
        verify(approvalQueryMapper).selectReferenceDocuments(empId);
    }
}
