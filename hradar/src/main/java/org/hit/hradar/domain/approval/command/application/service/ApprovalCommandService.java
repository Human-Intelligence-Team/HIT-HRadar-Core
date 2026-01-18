package org.hit.hradar.domain.approval.command.application.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalHistory;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLine;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLineStep;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalStepStatus;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalHistoryJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalLineJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalLineStepJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalCommandService {

  private final ApprovalLineStepJpaRepository approvalLineStepRepository;
  private final ApprovalLineJpaRepository approvalLineRepository;
  private final ApprovalDocumentJpaRepository approvalDocumentRepository;
  private final ApprovalHistoryJpaRepository approvalHistoryRepository;

  //결재 문서를 승인한다.
  //결재 문서 조회 -> 결재선 조회
  //현재 처리해야 할 결재 단계(PENDING) 조회 -> 결재 단계 승인 처리
  //승인 히스토리 기록 -> 다음 결재 단계 존재 여부 확인
  //있으면: 다음 단계를 PENDING으로 전환, 결재선 step 증가
  //없으면: 문서 최종 승인 처리
  public void approve(Long docId, Long actorId) {

    // 1. 결재 문서 조회
    ApprovalDocument doc = approvalDocumentRepository.findById(docId)
        .orElseThrow(() ->
            new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

    // 2. 결재선 조회
    ApprovalLine line = approvalLineRepository.findByDocId(docId)
        .orElseThrow(() ->
            new BusinessException(ApprovalErrorCode.LINE_NOT_FOUND));

    // 3. 현재 처리해야 할 결재 단계(PENDING) 조회
    //    - 설계상 반드시 1건만 존재해야 함
    ApprovalLineStep currentStep =
        approvalLineStepRepository
            .findByLineIdAndApprovalStepStatus(
                line.getLineId(),
                ApprovalStepStatus.PENDING
            )
            .orElseThrow(() ->
                new BusinessException(ApprovalErrorCode.NO_PENDING_STEP));

    // 4. 결재 단계 승인 (도메인 행위)
    currentStep.approve(actorId);

    // 5. 승인 히스토리 기록
    approvalHistoryRepository.save(
        ApprovalHistory.approved(docId, actorId, currentStep)
    );

    // 6. 다음 결재 단계(step_order + 1) 존재 여부 확인
    Optional<ApprovalLineStep> nextStep =
        approvalLineStepRepository.findByLineIdAndStepOrder(
            line.getLineId(),
            line.getCurrentStep() + 1
        );

    if (nextStep.isPresent()) {
      // 6-1. 다음 결재 단계가 존재하는 경우
      //      - 다음 단계를 PENDING으로 전환
      //      - 결재선의 현재 단계(step) 증가
      nextStep.get().toPending();
      line.increaseStep();
    } else {
      // 6-2. 다음 단계가 없는 경우 (마지막 결재자 승인)
      //      - 문서 최종 승인 처리
      doc.approve();
    }
  }

  /**
   * 결재 문서를 반려한다.
   *
   * 처리 흐름:
   * 1. 결재 문서 조회
   * 2. 결재선 조회
   * 3. 현재 처리해야 할 결재 단계(PENDING) 조회
   * 4. 결재 단계 반려 처리
   * 5. 반려 히스토리 기록
   * 6. 문서 상태를 반려(REJECTED)로 변경
   *
   * @param docId   결재 문서 ID
   * @param actorId 실제 반려 처리한 사원 ID
   * @param reason 반려 사유
   */
  public void reject(Long docId, Long actorId, String reason) {

    // 1. 결재 문서 조회
    ApprovalDocument doc = approvalDocumentRepository.findById(docId)
        .orElseThrow(() ->
            new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

    // 2. 결재선 조회
    ApprovalLine line = approvalLineRepository.findByDocId(docId)
        .orElseThrow(() ->
            new BusinessException(ApprovalErrorCode.LINE_NOT_FOUND));

    // 3. 현재 처리해야 할 결재 단계(PENDING) 조회
    ApprovalLineStep currentStep =
        approvalLineStepRepository
            .findByLineIdAndApprovalStepStatus(
                line.getLineId(),
                ApprovalStepStatus.PENDING
            )
            .orElseThrow(() ->
                new BusinessException(ApprovalErrorCode.NO_PENDING_STEP));

    // 4. 결재 단계 반려 (도메인 행위)
    currentStep.reject(actorId, reason);

    // 5. 반려 히스토리 기록
    approvalHistoryRepository.save(
        ApprovalHistory.rejected(docId, actorId, currentStep, reason)
    );

    // 6. 문서 반려 처리
    doc.reject();
  }

}
